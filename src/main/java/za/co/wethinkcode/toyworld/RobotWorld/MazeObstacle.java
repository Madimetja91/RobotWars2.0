package za.co.wethinkcode.toyworld.RobotWorld;

import za.co.wethinkcode.toyworld.Maze;
import za.co.wethinkcode.toyworld.Obstacle;
import za.co.wethinkcode.toyworld.Position;

public class MazeObstacle implements Obstacle {
    private Position position;
    private int size;

    public MazeObstacle(Position position, int size) {
        this.position = position;
        this.size = size;
    }

    @Override
    public int getBottomLeftX() {
        return position.getX() - (size / 2);
    }

    @Override
    public int getBottomLeftY() {
        return position.getY() - (size / 2);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean blocksPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        int obstacleX = this.position.getX();
        int obstacleY = this.position.getY();
        int halfSize = size / 2;

        // Check if the position falls within the obstacle's area
        boolean withinXRange = x >= (obstacleX - halfSize) && x <= (obstacleX + halfSize);
        boolean withinYRange = y >= (obstacleY - halfSize) && y <= (obstacleY + halfSize);

        return withinXRange && withinYRange;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        int ax = a.getX();
        int ay = a.getY();
        int bx = b.getX();
        int by = b.getY();
        int obstacleX = this.position.getX();
        int obstacleY = this.position.getY();
        int halfSize = size / 2;

        boolean intersectsX = (ax <= obstacleX + halfSize && bx >= obstacleX - halfSize)
                || (ax >= obstacleX - halfSize && bx <= obstacleX + halfSize);
        boolean intersectsY = (ay <= obstacleY + halfSize && by >= obstacleY - halfSize)
                || (ay >= obstacleY - halfSize && by <= obstacleY + halfSize);

        return intersectsX && intersectsY;
    }

    @Override
    public int getX() {
        return position.getX();
    }

    @Override
    public int getY() {
        return position.getY();
    }

    @Override
    public int getWidth() {
        return size;
    }

    @Override
    public int getHeight() {
        return size;
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
