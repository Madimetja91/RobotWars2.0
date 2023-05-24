package za.co.wethinkcode.toyworld.client;

import com.google.gson.Gson;
import org.json.JSONObject;
import za.co.wethinkcode.toyworld.Robot;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SimpleClient {

    private static String robotName= "";
//    int[] defaultPosition = {0, 0};
//    State clientState = new State(5,defaultPosition, 5, "NORTH","NORMAL");


    private static JSONObject createLaunchRequest(String[] command){
        JSONObject request = new JSONObject();
        String[] make = {command[1]};

        request.put("command", command[0]);
        request.put("robot", robotName);
        request.put("arguments", make);
        return request;

    }
    public static JSONObject createRequestMovement(String[] command){
        JSONObject request = new JSONObject();
        int argSteps = Integer.parseInt(command[1]);
        int[] steps = {argSteps};

        request.put("robot",robotName);
        request.put("command",command[0]);
        request.put("arguments",steps);
        return request;
    }
    public static JSONObject createRequestTurn(String[] command){
        JSONObject request = new JSONObject();
        String[] turn = {command[1]};
        request.put("robot",robotName);
        request.put("command",command[0]);
        request.put("argument", turn);
        return request;
    }
    private static JSONObject createFireRequest(String[] command){
        JSONObject request = new JSONObject();
//        String[] make = {command[1]};

        request.put("command", command[0]);
        request.put("robot", robotName);
        request.put("arguments", "[]");

        return request;

    }

    public static JSONObject createRequestRobots(String[] command){
        JSONObject request = new JSONObject();
//        String[] robots = {command[1]};
        request.put("robot", robotName);
        request.put("command", command[0]);
        request.put("argument","[]");
        return  request;
    }

    public static void main(String args[]) {
        try (
                Socket socket = new Socket("localhost", 5000);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        )
        {
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("what do you want to name your robot: ");
//            String name = scanner.nextLine().trim();

            while (true){
                Scanner scanner = new Scanner(System.in);
                System.out.println("What do you want to do next?");
                String commandString = scanner.nextLine().trim();
                String[] commandArgs = commandString.split(" ");
                JSONObject request;
                if (commandArgs[0].equals("launch") && commandArgs.length==3){
                    robotName += commandArgs[2];
                    request = createLaunchRequest(commandArgs);
                } else if (commandArgs[0].equals("state")) {
                    request = createLaunchRequest(commandArgs);
                } else if (commandArgs[0].equals("forward")) {
                    request = createRequestMovement(commandArgs);
                } else if (commandArgs[0].equals("back")) {
                    request = createRequestMovement(commandArgs);
                } else if (commandArgs[0].equals("turn")) {
                    request = createRequestTurn(commandArgs);
                } else if(commandArgs[0].equals("robots")){
                    request = createLaunchRequest(commandArgs);
                }else if(commandArgs[0].equals("look")){
                    request = createRequestRobots(commandArgs);
                }
                else if (commandArgs[0].equals("fire")){
                    request = createFireRequest(commandArgs);
                    out.println(request.toString());
                    out.flush();
                    String messageFromServer = in.readLine();
                    Response serverResponse = new Gson().fromJson(messageFromServer, Response.class);
//                    System.out.println("Response: "+messageFromServer);
                    System.out.println("The current ammunition count is " + serverResponse.getState().getShots());
//                    State clientState = new State();

                } else if (commandArgs[0].equals("reload")) {
                    request = createFireRequest(commandArgs);
                    out.println(request.toString());
                    out.flush();
                    String messageFromServer = in.readLine();
                    ResponseUpdate serverResponse = new Gson().fromJson(messageFromServer, ResponseUpdate.class);
//                    System.out.println("Response: "+messageFromServer);
                    System.out.println("Your current status is " + serverResponse.getState().getStatus());

                } else if (commandArgs[0].equals("repair")) {
                    request = createFireRequest(commandArgs);
                    out.println(request.toString());
                    out.flush();
                    String messageFromServer = in.readLine();
                    ResponseUpdate serverResponse = new Gson().fromJson(messageFromServer, ResponseUpdate.class);
//                    System.out.println("Response: "+messageFromServer);
                    System.out.println("Your current status is " + serverResponse.getState().getStatus());

                } else{
                    request = createLaunchRequest(commandArgs);
                }
                out.println(request.toString());
                out.flush();
                String messageFromServer = in.readLine();
                System.out.println("Response: "+messageFromServer);
//                JSONObject unpackResponse = new JSONObject(messageFromServer);
//                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}