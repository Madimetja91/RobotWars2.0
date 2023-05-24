package za.co.wethinkcode.toyworld;
//import za.co.wethinkcode.toyworld.Obs;
import za.co.wethinkcode.toyworld.Position;
import za.co.wethinkcode.toyworld.Maze;

import java.util.List;
public abstract class AbstractWorld implements IWorld {
    public Position position = CENTRE;

    public IWorld.Direction currentDirection = Direction.NORTH;
    public final Position TOP_LEFT = new Position(-100, 200);
    public final Position BOTTOM_RIGHT = new Position(100, -200);
    public Maze maze;
    List<Obstacle> obstacles;

    public AbstractWorld(Maze maze) {
        this.obstacles = maze.getObstacles();
        this.maze = maze;

    }
    @Override
    public abstract void updateDirection(boolean turnRight);

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    @Override
    public boolean isNewPositionAllowed(Position position) {
        return position.isIn(TOP_LEFT,BOTTOM_RIGHT);
    }

    @Override
    public boolean isAtEdge() {
        return getPosition().getY() == TOP_LEFT.getY() || getPosition().getY() == BOTTOM_RIGHT.getY() ||
                getPosition().getX() == TOP_LEFT.getX() || getPosition().getX() == BOTTOM_RIGHT.getX();
    }

    @Override
    public List<Obstacle> getObstacles() {
        return maze.getObstacles();
    }

    @Override
    public void reset() {
        this.position = CENTRE;
//        this.currentDirection = Direction.UP;
    }
}
