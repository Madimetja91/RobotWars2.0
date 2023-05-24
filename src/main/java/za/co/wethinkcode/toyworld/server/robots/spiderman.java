package za.co.wethinkcode.toyworld.server.robots;

import za.co.wethinkcode.toyworld.Direction;
import za.co.wethinkcode.toyworld.Robot;
import za.co.wethinkcode.toyworld.World;

public class spiderman extends Robot {
    String robotName;
    public spiderman(String name, int x, int y) {
        super("spiderman", x, y);
        this.robotName = name;
//        this.status = "NORMAL";
//        this.x= x;
//        this.y= y;
//        this.direction = Direction.NORTH;
    }

    public spiderman(String name, String make) {
        super(name, make);
    }
}
