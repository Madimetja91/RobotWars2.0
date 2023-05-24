package za.co.wethinkcode.toyworld;

import za.co.wethinkcode.toyworld.Position;
import za.co.wethinkcode.toyworld.Obstacle;

import java.util.List;

/**
 * Interface to represent a maze. A World will be loaded with a Maze, and will delegate the work to check if a path is blocked by certain obstacles etc to this maze instance.
 */
public interface Maze {
    /**
     * @return the list of obstacles, or an empty list if no obstacles exist.
     */
    List<Obstacle> getObstacles();

    /**
     * Checks if this maze has at least one obstacle that blocks the path that goes from coordinate (x1, y1) to (x2, y2).
     * Since our robot can only move in horizontal or vertical lines (no diagonals yet), we can assume that either x1==x2 or y1==y2.
     *
     * @param a first position
     * @param b second position
     * @return `true` if there is an obstacle in the way
     */
    boolean blocksPath(Position a, Position b);

    /**
     * Checks if the specified position is blocked by an obstacle.
     *
     * @param position the position to check
     * @return `true` if there is an obstacle at the position
     */
    boolean blocksPosition(Position position);

    /**
     * Gets the width of the maze (number of columns).
     *
     * @return the width of the maze
     */
    int getWidth();

    /**
     * Gets the height of the maze (number of rows).
     *
     * @return the height of the maze
     */
    int getHeight();

    /**
     * Checks if the specified cell at coordinates (x, y) is an obstacle.
     *
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @return `true` if the cell is an obstacle
     */
    boolean isObstacle(int x, int y);

    void generate();
}