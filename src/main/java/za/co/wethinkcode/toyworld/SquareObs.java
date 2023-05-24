package za.co.wethinkcode.toyworld;

import za.co.wethinkcode.toyworld.Position;

public class SquareObs implements Obstacle {
    private final int x;
    private final int y;
    private final int size = 5;

    public SquareObs(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int getBottomLeftX() {
        return this.x;
    }

    @Override
    public int getBottomLeftY() {
        return this.y;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public boolean blocksPosition(Position position) {
        if (position.getX() >= this.getBottomLeftX()&& position.getX()< this.getBottomLeftX() + getSize() &&
                position.getY() >= this.getBottomLeftY() && position.getY() < this.getBottomLeftY() + getSize())
            return true;
        else{
            return false;
        }
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        if (a.getX() == b.getX()){
            if (a.getY() < b.getY()){
                return a.getX() >= getBottomLeftX() && a.getX() <= getBottomLeftX() + getSize() &&
                        b.getY() >= getBottomLeftY() && a.getY() <= getBottomLeftY();
            }else {
                return b.getX() >= getBottomLeftX() && b.getX() <= getBottomLeftX() + getSize() &&
                        b.getY() <= getBottomLeftY() + getSize() && a.getY() >= getBottomLeftY() + getSize();
            }
        }
        else if (a.getY() == b.getY()){
            if (a.getX() < b.getX()){
                return b.getY() >= getBottomLeftY() && b.getY() <= getBottomLeftY() + getSize() &&
                        b.getX() >= getBottomLeftX() && a.getX() <= getBottomLeftX();
            }else {
                return  b.getY() >= getBottomLeftY() && b.getY() <= getBottomLeftY() + getSize() &&
                        b.getX() <= getBottomLeftX() + getSize() && a.getX() >= getBottomLeftX() +getSize();
            }
        }
        return false;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
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
    public Position getPosition() {
        return null;
    }
}
