package za.co.wethinkcode.toyworld.client;

public class State {
    private int shields;
    private int[] position;
    private int shots;
    private String direction;
    private String status;

    public State(int shields, int[] position, int shots, String direction, String status) {
        this.shields = shields;
        this.position = position;
        this.shots = shots;
        this.direction = direction;
        this.status = status;
    }

    public int getShields() {
        return shields;
    }

    public void setShields(int shields) {
        this.shields = shields;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
