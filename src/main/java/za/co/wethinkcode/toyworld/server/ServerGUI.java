package za.co.wethinkcode.toyworld.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGUI implements Runnable {
    public static final int PORT = 5000;
    private final SimpleServer simpleServer;

    public ServerGUI(SimpleServer simpleServer) {
        this.simpleServer = simpleServer;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("GUI server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                GUIConnectionHandler connectionHandler = new GUIConnectionHandler(clientSocket, simpleServer);
                Thread connectionThread = new Thread(connectionHandler);
                connectionThread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static class GUIConnectionHandler implements Runnable {
        private final Socket socket;
        private final SimpleServer simpleServer;

        public GUIConnectionHandler(Socket socket, SimpleServer simpleServer) {
            this.socket = socket;
            this.simpleServer = simpleServer;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream out = new PrintStream(socket.getOutputStream());

                String messageFromGUI;
                while ((messageFromGUI = in.readLine()) != null) {
                    System.out.println("Message \"" + messageFromGUI + "\" from GUI");

                    // Process the message and get the response from the SimpleServer
                    String response = simpleServer.clientRequest(messageFromGUI);

                    out.println(response);
                }
            } catch (IOException ex) {
                System.out.println("GUI connection closed");
            } finally {
                closeQuietly();
            }
        }

        private void closeQuietly() {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
