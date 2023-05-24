package za.co.wethinkcode.toyworld.server;

import za.co.wethinkcode.toyworld.World;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class MultiServers {
    private static World world = new World(400,400);
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Scanner scanner = new Scanner(System.in);



        ServerSocket s = new ServerSocket( SimpleServer.PORT);
        System.out.println("Server running & waiting for client connections.");
        while(true) {
            try {
                
                Socket socket = s.accept();
                System.out.println("Connection: " + socket);

                Runnable r = new SimpleServer(socket, world);
                Thread task = new Thread(r);
                task.start();

                System.out.println("\nWhat do you want to do next?");
                System.out.println("choose one of the following robots commands of continue: \n"+
                        "-quit \n"+
                        "-robots \n"+
                        "-dump");
//                String command = scanner.nextLine();
//                if(command.equals("robots")){
//                    RobotsCommand();
//                }


            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}