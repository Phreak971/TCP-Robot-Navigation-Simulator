import java.awt.*;
import java.net.*;
import java.io.*;

public class ThreadedConnectionHandler extends Thread {
    private Socket clientSocket = null;                // Client socket object
    private ObjectInputStream is = null;            // Input stream
    private ObjectOutputStream os = null;            // Output stream
    private RobotsArea robotsArea;
    private Button robot = null;

    // The constructor for the connection handler
    public ThreadedConnectionHandler(Socket clientSocket, RobotsArea rob) {
        this.clientSocket = clientSocket;
        //Set up the area for robots
        robotsArea = rob;
    }

    // Will eventually be the thread execution method - can't pass the exception back
    public void run() {
        try {
            this.is = new ObjectInputStream(clientSocket.getInputStream());
            this.os = new ObjectOutputStream(clientSocket.getOutputStream());
            while (this.readCommand()) {
            }
        } catch (IOException e) {
            System.out.println("There was a problem with the Input/Output Communication:");
            e.printStackTrace();
        }
    }

    // Receive and process incoming string commands from client socket
    private boolean readCommand() {
        String s = null;
        try {
            s = (String) is.readObject();
        } catch (Exception e) {    // catch a general exception
            this.closeSocket();
            robotsArea.removeFromRobotsList(robot);
            return false;
        }
        System.out.println(s);
        String[] vals = s.split(",");
        if (robot == null) {
            robot = robotsArea.create_robot(vals[0], Integer.parseInt(vals[1]), Integer.parseInt(vals[2]));
            robotsArea.addToRobotsList(robot);
            robotsArea.checkColliding(robot);
        } else {
            robot.setBounds(Integer.parseInt(vals[1]), Integer.parseInt(vals[2]), 100, 30);
            robotsArea.checkColliding(robot);
        }
        return true;
    }

    // Send a generic object back to the client
    private void send(Object o) {
        try {
            this.os.writeObject(o);
            this.os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Send a pre-formatted error message to the client
    public void sendError(String message) {
        this.send("Error:" + message);    //remember a String IS-A Object!
    }

    // Close the client socket
    public void closeSocket() { //gracefully close the socket connection
        try {
            this.os.close();
            this.is.close();
            this.clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
