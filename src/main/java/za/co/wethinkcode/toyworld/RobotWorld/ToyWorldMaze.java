package za.co.wethinkcode.toyworld.RobotWorld;

import za.co.wethinkcode.toyworld.Maze;
import za.co.wethinkcode.toyworld.Obstacle;
import za.co.wethinkcode.toyworld.Position;

import java.util.ArrayList;
import java.util.List;

public class ToyWorldMaze implements Maze {
    private int[][] maze;
    private List<Obstacle> obstacles;
    private Position topLeft;
    private Position bottomRight;
    private boolean [][] mazeStructure;

    public ToyWorldMaze() {
        // Robot Worlds MAZE structure
        this.maze = new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        this.topLeft = new Position(0, 0);
        this.bottomRight = new Position(maze[0].length - 1, maze.length - 1);

        obstacles = new ArrayList<>();
        initializeObstacles();
        calculateMazeBounds();
    }

    private void calculateMazeBounds() {
        if (!obstacles.isEmpty()) {
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxY = Integer.MIN_VALUE;

            for (Obstacle obstacle : obstacles) {
                Position position = obstacle.getPosition();
                minX = Math.min(minX, position.getX());
                minY = Math.min(minY, position.getY());
                maxX = Math.max(maxX, position.getX());
                maxY = Math.max(maxY, position.getY());
            }

            topLeft = new Position(minX, minY);
            bottomRight = new Position(maxX, maxY);
        }
    }

    private void initializeObstacles() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 1) {
                    Position position = new Position(j, i);
                    Obstacle obstacle = new MazeObstacle(position, 1);
                    obstacles.add(obstacle);
                }
            }
        }
    }

    @Override
    public List<Obstacle> getObstacles() {
        return obstacles;
    }
    @Override
    public boolean blocksPath(Position a, Position b) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.blocksPath(a, b)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean blocksPosition(Position position) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.blocksPosition(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getWidth() {
        return maze[0].length;
    }

    @Override
    public int getHeight() {
        return maze.length;
    }

    @Override
    public boolean isObstacle(int x, int y) {
        if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
            return false; // Return false for out-of-bounds coordinates
        }
        return maze[y][x] == 1;
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Position getBottomRight() {
        return bottomRight;
    }
    public void generate() {
        // Implement your maze generation algorithm here
        // This is just a simple example

        int width = getWidth(); // Width of the maze
        int height = getHeight(); // Height of the maze

        // Create a 2D array to represent the maze structure
        boolean[][] mazeStructure = new boolean[width][height];

        // Set all cells in the maze to be walls (obstacles)
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mazeStructure[j][i] = true;
            }
        }

        // Randomly start at a position
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        mazeStructure[x][y] = false; // Mark the starting position as a path

        // Perform a random walk in the maze
        int numSteps = width * height; // Number of steps to take
        while (numSteps > 0) {
            // Generate a random direction (up, down, left, or right)
            int direction = (int) (Math.random() * 4);

            // Move in the selected direction
            if (direction == 0 && y > 0) {
                y--; // Move up
            } else if (direction == 1 && y < height - 1) {
                y++; // Move down
            } else if (direction == 2 && x > 0) {
                x--; // Move left
            } else if (direction == 3 && x < width - 1) {
                x++; // Move right
            }

            // Mark the current position as a path
            mazeStructure[x][y] = false;

            numSteps--;
        }

        // Update the maze structure in the Maze object
        setMazeStructure(mazeStructure);
    }


    public void setMazeStructure(boolean[][] mazeStructure) {
        this.mazeStructure = mazeStructure;
    }

    public boolean[][] getMazeStructure() {
        return mazeStructure;
    }
}
