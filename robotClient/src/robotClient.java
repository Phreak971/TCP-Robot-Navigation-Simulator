import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class robotClient {

    private static int portNumber = 5050;
    private Socket socket = null;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    private Robot myRobot;

    robotClient(String[] args) {
        if (isValidIP(args[1])) {
            if (!connectToServer(args[1])) {
                myRobot = new Robot(args[0], args[1], true);
                myRobot.setConnected(false);

            } else {
                myRobot = new Robot(args[0], args[1]);
                myRobot.setConnected(true);

            }
        }
    }

    //template code to connect to server
    public boolean connectToServer(String serverIP) {
        try { // open a new socket to the server
            this.socket = new Socket(serverIP, portNumber);
            this.os = new ObjectOutputStream(this.socket.getOutputStream());
            this.is = new ObjectInputStream(this.socket.getInputStream());
        } catch (Exception e) {
            return false;

        }
        return true;
    }

    //template for sending data to server
    public void send(Object o) {
        try {
            os.writeObject(o);
            os.flush();
        } catch (Exception e) {
            System.out.println("Exception Occurred on Sending: " + e.toString());
            myRobot.setConnected(false);
        }
    }

    private static void useage() {
        System.out.println("Usage ");
        System.out.println("robotClient.java robot-name server-address");
        System.out.println("Example");
        System.out.println("robotClient.java Sofia 127.0.0.1");
    }

    private static boolean isValidIP(String ipAddr) {
        if (ipAddr.equals("localhost"))
            return true;
        Pattern ptn = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
        Matcher mtch = ptn.matcher(ipAddr);
        return mtch.find();
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            robotClient myClient = new robotClient(args);
            while (myClient.myRobot.isConnected()) {
                myClient.send(String.format("%s,%d,%d", args[0], myClient.myRobot.getMyPosition().getX(), myClient.myRobot.getMyPosition().getY()));
                myClient.myRobot.update_last_messgae();
                try {
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            useage();
        }
    }
}

