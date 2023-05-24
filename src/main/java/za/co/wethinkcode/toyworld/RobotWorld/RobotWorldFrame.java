package za.co.wethinkcode.toyworld.RobotWorld;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.toyworld.Maze;
import za.co.wethinkcode.toyworld.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;

public class RobotWorldFrame extends JFrame {

    private Box player;
    private Box enemy;
    private boolean gameOver;

    public World world;

    private String gameTitle = "Robot World";

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public RobotWorldFrame(Maze maze) {
        player = new Box(200, 300, 50, 50, "src/main/java/za/co/wethinkcode/toyworld/assets/robot01.png", 10);
        enemy = new Box(50, 50, 50, 50, "src/main/java/za/co/wethinkcode/toyworld/assets/roboto2.png", 10);
        gameOver = false;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setTitle(gameTitle); // Set the title of the frame

        JPanel contentPane = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                drawWorldBackground(g);
                player.draw(g);
                enemy.draw(g);

                if (gameOver) {
                    g.setColor(Color.YELLOW);
                    g.setFont(new Font("MV Boil", Font.BOLD, 45));
                    g.drawString("GAME OVER!", 150, 100);
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawWorldBackground(g);
                player.draw(g);
                enemy.draw(g);

                if (gameOver) {
                    g.setColor(Color.YELLOW);
                    g.setFont(new Font("MV Boil", Font.BOLD, 45));
                    g.drawString("GAME OVER!", 150, 100);
                }
            }
        };

        contentPane.setPreferredSize(new Dimension(800, 800));
        setContentPane(contentPane);
        addKeyListener(new AL());
        setFocusable(true);
        requestFocusInWindow();

        // Connect to the server
        try {
            socket = new Socket("localhost", 5000); // Replace with your server IP address and port number
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            startListening();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawWorldBackground(Graphics g) {
        // Fill the background with a color
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the maze in the background
        if (world != null && world.getMaze() != null) {
            Maze maze = world.getMaze();
            int mazeWidth = maze.getWidth();
            int mazeHeight = maze.getHeight();

            int cellSize = Math.min(getWidth() / mazeWidth, getHeight() / mazeHeight);

            for (int x = 0; x < mazeWidth; x++) {
                for (int y = 0; y < mazeHeight; y++) {
                    int obstacleX = x * cellSize;
                    int obstacleY = y * cellSize;
                    int obstacleSize = cellSize;

                    if (maze.isObstacle(x, y)) {
                        g.setColor(Color.GREEN);
                        g.fillRect(obstacleX, obstacleY, obstacleSize, obstacleSize);
                    } else {
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(obstacleX, obstacleY, obstacleSize, obstacleSize);
                        g.setColor(Color.BLACK);
                        g.drawString("0", obstacleX + obstacleSize / 2, obstacleY + obstacleSize / 2);
                    }
                }
            }
        }
    }

    public void checkCollision() {
        // Collision checking logic here
    }

    private void startListening() {
        Thread thread = new Thread(() -> {
            try {
                String serverResponse;
                while ((serverResponse = reader.readLine()) != null) {
                    // Handle server response
                    System.out.println("Server: " + serverResponse);

                    // Example response format: {"command":"MOVE_UP","robot":"Robot1","arguments":[1]}
                    JSONObject response = new JSONObject(serverResponse);
                    String command = response.getString("command");
                    String robot = response.getString("robot");
                    JSONArray arguments = response.getJSONArray("arguments");

                    // Update player and enemy positions based on server response
                    if (robot.equals("player")) {
                        // Update player position based on the command and arguments received
                        updatePlayerPosition(command, arguments);
                    } else if (robot.equals("enemy")) {
                        // Update enemy position based on the command and arguments received
                        updateEnemyPosition(command, arguments);
                    }

                    // Repaint the frame
                    repaint();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void updatePlayerPosition(String command, JSONArray arguments) {
        int steps = arguments.getInt(0);
        // Update player position based on the received command and steps
        if (command.equals("forward")) {
            player.moveUp(steps);
        } else if (command.equals("back")) {
            player.moveDown(steps);
        } else if (command.equals("turn")) {
            // Handle turn command if needed
        }
    }

    private void updateEnemyPosition(String command, JSONArray arguments) {
        // Update enemy position based on the received command and arguments
    }

    private void sendCommandToServer(String command) {
        try {
            writer.write(command + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWorld(World world) {
        this.world = world;
        // Update the GUI to reflect the new world state
        // You can access the world object to retrieve information and update the GUI components accordingly
        // ...
    }

    public class AL extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);

            // Send player movement command to the server
            String command;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    command = "forward 1"; // Adjust command format according to the server requirements
                    sendCommandToServer(command);
                    break;
                case KeyEvent.VK_DOWN:
                    command = "back 1"; // Adjust command format according to the server requirements
                    sendCommandToServer(command);
                    break;
                case KeyEvent.VK_LEFT:
                    command = "turn left"; // Adjust command format according to the server requirements
                    sendCommandToServer(command);
                    break;
                case KeyEvent.VK_RIGHT:
                    command = "turn right"; // Adjust command format according to the server requirements
                    sendCommandToServer(command);
                    break;
            }

            checkCollision();
            repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Maze maze = new ToyWorldMaze();
            RobotWorldFrame frame = new RobotWorldFrame(maze);
            frame.world = new World();
            frame.setVisible(true);
        });
    }
}
