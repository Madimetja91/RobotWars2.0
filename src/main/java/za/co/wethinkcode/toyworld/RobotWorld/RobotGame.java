package za.co.wethinkcode.toyworld.RobotWorld;

import za.co.wethinkcode.toyworld.Maze;
import za.co.wethinkcode.toyworld.World;
import za.co.wethinkcode.toyworld.server.SimpleServer;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class RobotGame {
    public static void main(String[] args) throws AWTException, IOException {
        // Launch GUI on a separate thread
        Thread guiThread = new Thread(() -> {
            EventQueue.invokeLater(() -> {
                Maze maze = new ToyWorldMaze();
                World world = new World();

                RobotWorldFrame myFrame = new RobotWorldFrame(maze);
                myFrame.world = world;
                myFrame.setVisible(true);
            });
        });
        guiThread.start();

        // Launch server on the main thread
        Maze maze = new ToyWorldMaze();
        World world = new World();
        SimpleServer server = new SimpleServer((Socket) maze, world);
        server.start();
    }
}
