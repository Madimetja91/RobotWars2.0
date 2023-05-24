package za.co.wethinkcode.toyworld.server;

import com.google.gson.Gson;
import org.json.JSONObject;
import za.co.wethinkcode.toyworld.*;
import za.co.wethinkcode.toyworld.client.ResponseUpdate;
import za.co.wethinkcode.toyworld.client.State;
import za.co.wethinkcode.toyworld.client.StateStatus;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class SimpleServer implements Runnable {
    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;
    private final World world;
    public static ArrayList<SimpleServer> clients = new ArrayList<>();
    public Robot robot;

    public SimpleServer(Socket socket, World world) throws IOException {
        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);

        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Waiting for client...");
        this.world = world;
        this.clients.add(this);
    }


    public static ArrayList<SimpleServer> getClientHandlers() {
        return clients;
    }

    public String clientRequest(String messageFromClient) {
        JSONObject requestFromClient = new JSONObject(messageFromClient);
        JSONObject response = new JSONObject();

        if (requestFromClient.getString("command").equals("launch")) {
            // Launch command
            String robotType = requestFromClient.getString("robot");
            String argument = requestFromClient.getJSONArray("arguments").getString(0);
            this.robot = this.world.launchRobotInWorld(robotType, argument);

            response.put("result", "OK");
            response.put("data", robot.getRobotData());
            response.put("state", robot.getRobotState());
        } else if (requestFromClient.getString("command").equals("state")) {
            // State command
            response.put("state", robot.getRobotState());
        } else if (requestFromClient.getString("command").equals("look")) {
            // Look command
            response.put("result", "OK");
            response.put("data", "");
            response.put("state", robot.getRobotState());
        } else if (requestFromClient.getString("command").equals("forward")) {
            // Forward command
            double distance = requestFromClient.getJSONArray("arguments").getDouble(0);
            Command command = new ForwardCommand(distance);
            response = command.execute(this.world, this.robot);
        } else if (requestFromClient.getString("command").equals("back")) {
            // Back command
            double distance = requestFromClient.getJSONArray("arguments").getDouble(0);
            Command command = new BackCommand(distance);
            response = command.execute(this.world, this.robot);
        } else if (requestFromClient.getString("command").equals("turn")) {
            // Turn command
            String direction = requestFromClient.getJSONArray("arguments").getString(0);
            Command command;
            if (direction.equals("left")) {
                command = new LeftCommand();
            } else {
                command = new RightCommand();
            }
            response = command.execute(this.world, this.robot);
        } else if (requestFromClient.getString("command").equals("repair")) {
            // Repair command
            ResponseUpdate serverResponse = new ResponseUpdate("Done", new StateStatus("Repair"));
            return new Gson().toJson(serverResponse);
        } else if (requestFromClient.getString("command").equals("reload")) {
            // Reload command
            ResponseUpdate serverResponse = new ResponseUpdate("Done", new StateStatus("RELOAD"));
            return new Gson().toJson(serverResponse);
        } else if (requestFromClient.getString("command").equals("fire")) {
            // Fire command
            State currentState = new Gson().fromJson(robot.getRobotState().toString(), State.class);
            Response serverResponse = new Response("HIT", new State(
                    currentState.getShields(),
                    currentState.getPosition(),
                    currentState.getShots() > 0 ? currentState.getShots() - 1 : 0,
                    currentState.getDirection(),
                    currentState.getStatus()
            ));
            return new Gson().toJson(serverResponse);
        }

        return response.toString();
    }


    public void run() {
        try {
            String messageFromClient;
            while ((messageFromClient = in.readLine()) != null) {
                System.out.println("Message \"" + messageFromClient + "\" from " + clientMachine);
                String responseStr = clientRequest(messageFromClient);
                out.println(responseStr);
            }
        } catch (IOException ex) {
            System.out.println("Shutting down single client server");
        } finally {
            closeQuietly();
        }
    }

    private void closeQuietly() {
        try {
            in.close();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            World world = new World();
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            SimpleServer simpleServer = new SimpleServer(serverSocket.accept(), world);
            ServerGUI guiServer = new ServerGUI(simpleServer);
            Thread guiThread = new Thread(guiServer);
            guiThread.start();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                SimpleServer clientHandler = new SimpleServer(clientSocket, world);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void start() {
        int port = 8080; // Specify the port number for the server

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle the client request, e.g., process incoming data, send response, etc.
                // You can implement your server logic here

                // Close the client socket
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
