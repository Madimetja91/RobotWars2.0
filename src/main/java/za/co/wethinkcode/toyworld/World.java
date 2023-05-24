package za.co.wethinkcode.toyworld;

import za.co.wethinkcode.toyworld.RobotWorld.ToyWorldMaze;

import java.util.ArrayList;
import java.util.List;

import static za.co.wethinkcode.toyworld.IWorld.CENTRE;
import static za.co.wethinkcode.toyworld.IWorld.Direction.*;

public class World {
    private int worldX;
    private int worldY;

    private List<Obstacle> obstacles;
    private ToyWorldMaze toyWorldMaze;
    private static int x;
    private static int y;
    private Maze maze;
    public Position position = CENTRE;

    public IWorld.Direction currentDirection = NORTH;
    private Position TOP_LEFT;
    private Position BOTTOM_RIGHT;

    private static World instance;

    public World(int x, int y) {
        this.worldX = x;
        this.worldY = y;
        this.TOP_LEFT = new Position(-this.worldX / 2, this.worldY / 2);
        this.BOTTOM_RIGHT = new Position(this.worldX / 2, -this.worldY / 2);
        this.obstacles = new ArrayList<>();
    }

    public World() {
        this.toyWorldMaze = toyWorldMaze;
        this.TOP_LEFT = toyWorldMaze.getTopLeft();
        this.BOTTOM_RIGHT = toyWorldMaze.getBottomRight();
    }

    public static World getInstance() {
        if (instance == null) {
            instance = new World(x, y);
        }
        return instance;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public ToyWorldMaze getMaze() {
        return toyWorldMaze;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public IWorld.Direction doEast() {
        if (NORTH.equals(this.currentDirection)) {
            return this.currentDirection = EAST;
        } else if (EAST.equals(this.currentDirection)) {
            return this.currentDirection = SOUTH;
        } else if (SOUTH.equals(this.currentDirection)) {
            return this.currentDirection = WEST;
        } else if (WEST.equals(this.currentDirection)) {
            return this.currentDirection = NORTH;
        }
        return this.currentDirection;
    }

    public IWorld.Direction doWest() {
        if (NORTH.equals(this.currentDirection)) {
            return this.currentDirection = WEST;
        } else if (WEST.equals(this.currentDirection)) {
            return this.currentDirection = SOUTH;
        } else if (SOUTH.equals(this.currentDirection)) {
            return this.currentDirection = EAST;
        } else if (EAST.equals(this.currentDirection)) {
            return this.currentDirection = NORTH;
        }
        return this.currentDirection;
    }

    public void updateDirection(boolean turnRight) {
        if (turnRight) {
            doEast();
        } else {
            doWest();
        }
    }

    public IWorld.UpdateResponse updatePosition(int nrSteps, Robot robot) {
        int newX = robot.getCurrentPosition().getX();
        int newY = robot.getCurrentPosition().getY();
        switch (robot.getDirection()) {
            case NORTH:
                newY = newY + nrSteps;
                break;
            case EAST:
                newX = newX + nrSteps;
                break;
            case SOUTH:
                newY = newY - nrSteps;
                break;
            case WEST:
                newX = newX - nrSteps;
                break;
        }
        Position newPosition = new Position(newX, newY);
        if (maze.blocksPath(robot.getCurrentPosition(), newPosition)) {
            return IWorld.UpdateResponse.FAILED_OBSTRUCTED;
        }
        if (isNewPositionAllowed(newPosition)) {
            robot.setPosition(newPosition);
            return IWorld.UpdateResponse.SUCCESS;
        } else {
            return IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD;
        }
    }

    public boolean isNewPositionAllowed(Position position) {
        boolean withinTop = position.getY() <= this.TOP_LEFT.getY();
        boolean withinBottom = position.getY() >= this.BOTTOM_RIGHT.getY();
        boolean withinLeft = position.getX() >= this.TOP_LEFT.getX();
        boolean withinRight = position.getX() <= this.BOTTOM_RIGHT.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    public void reset() {
        this.currentDirection = NORTH;
    }

    public Robot launchRobotInWorld(String name, String make) {
        int playerX = 100;
        int playerY = 100;
        int enemyX = -200;
        int enemyY = -200;
        Robot player = new Robot(name, playerX, playerY, this);
        Robot enemy = new Robot("Enemy", enemyX, enemyY, this);
        return player;
    }
}
