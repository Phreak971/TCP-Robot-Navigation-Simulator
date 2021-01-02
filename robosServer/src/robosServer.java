
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.*;
import java.io.*;

public class robosServer {
    private static int portNumber = 5050;
    private static RobotsArea robotsArea;

    public static void main(String args[]) {
        File lol = new File("sounds/doorbell.wav");
        boolean listening = true;
        ServerSocket serverSocket = null;
        // Set up the Server Socket
        try {
            serverSocket = new ServerSocket(portNumber);
            robotsArea = new RobotsArea();
        } catch (IOException e) {
            System.exit(1);
        }

        // Server is now listening for connections or would not get to this point
        while (listening) // almost infinite loop - loop once for each client request
        {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(lol));
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                listening = false;   // end the loop - stop listening for further client requests
            }
            ThreadedConnectionHandler con = new ThreadedConnectionHandler(clientSocket, robotsArea);
            con.start();
        }
        // Server is no longer listening for client connections - time to shut down.
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
