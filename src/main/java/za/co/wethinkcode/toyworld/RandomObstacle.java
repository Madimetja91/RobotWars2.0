package za.co.wethinkcode.toyworld;

import za.co.wethinkcode.toyworld.Position;
import za.co.wethinkcode.toyworld.Obstacle;
import za.co.wethinkcode.toyworld.SquareObs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomObstacle implements Maze{
    public List<Obstacle> lsObstacles;

    public RandomObstacle(){
        this.lsObstacles = new ArrayList<>();
    }

    @Override
    public List<Obstacle> getObstacles() {
        Random random = new Random();

        int numObstacles = random.nextInt(11);
        for (int i = 0; i < numObstacles; i++) {
            int x = random.nextInt(201)-100;
            int y = random.nextInt(401)-200;
            lsObstacles.add(new SquareObs(x,y));

        }
        return lsObstacles;
    }



    @Override
    public boolean blocksPath(Position a, Position b) {
        return false;
    }

    @Override
    public boolean blocksPosition(Position position) {
        return false;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public boolean isObstacle(int x, int y) {
        return false;
    }

    @Override
    public void generate() {

    }
}
