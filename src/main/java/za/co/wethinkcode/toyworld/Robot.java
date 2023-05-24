package za.co.wethinkcode.toyworld;

//import za.co.wethinkcode.robotworlds.maze.EmptyMaze;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Robot {
//    public static final Position CENTRE = new Position(0, 0);
//    public ArrayList<Command> history;

//    private Position position;
//    private Direction currentDirection;
    private String status;
    private String name;
    private World world;
    private Position currentPos;

    public int x;
    public int y;
    private String make;
    private Maze maze;

    private IWorld.Direction currentDirection;
//    List<HashMap<String, Object>>
public Robot(String name,int x, int y) {
    //if name is spiderman
    this.name = name;
    this.status = "NORMAL";
    this.x = x;
    this.y = y;
    this.currentPos = new Position(this.x, this.y);
    this.currentDirection = IWorld.Direction.NORTH;

}
    public Robot(String name,int x, int y, World world){
        //if name is spiderman
        this.name = name;
        this.status = "NORMAL";
        this.x= x;
        this.y= y;
        this.currentPos = new Position(this.x, this.y);
        this.currentDirection = IWorld.Direction.NORTH;
        this.world = world;
        //else if name is ironman

//        this.position = CENTRE;
//        this.history = new ArrayList<Command>();
//        this.currentDirection = Direction.NORTH;
//        this.maze = new EmptyMaze();
    }

    public Robot(String name, String make){
        this.name = name;
        this.make = make;
        this.status = "NORMAL";

    }

    public Robot(String name,IWorld world) {
        this.name = name;
        this.status = "Ready";
//        this.position = CENTRE;
//        this.history = new ArrayList<Command>();
//        this.currentDirection = Direction.NORTH;
//        this.maze = new EmptyMaze();
//        this.world = world;
    }
    public World getWorld(){
        return this.world;
    }

    public String getStatus() {
        return this.status;
    }
    public Position getCurrentPosition(){
        return this.currentPos;
    }
    public IWorld.Direction getDirection(){
        return this.currentDirection;
    }
    public void setPosition(Position position){
        this.currentPos = position;
    }

    public JSONObject getRobotData(){
        JSONObject dataJSON = new JSONObject();
        int[] robotPosition = {this.currentPos.getX(),this.currentPos.getY()};

        dataJSON.put("position", robotPosition);
        dataJSON.put("visibility",true);
        dataJSON.put("reload",3);
        dataJSON.put("repair",3);
        dataJSON.put("shields",2);
        return dataJSON;


    }
    public JSONObject getRobotState(){
        JSONObject stateJSON = new JSONObject();
        int[] robotPosition = {this.currentPos.getX(),this.currentPos.getY()};

        stateJSON.put("position", robotPosition);
        stateJSON.put("direction",this.currentDirection);
        stateJSON.put("status",this.status);
        stateJSON.put("shots",5);
        stateJSON.put("shields",2);
        return stateJSON;
    }
//    public JSONObject getRobotLook(){
//        JSONObject lookJson = new JSONObject();
//    }
    public JSONObject getRobotForwardPositive(){
        JSONObject forwardJSON = new JSONObject();
//        int[] robotPosition = {this.x,this.y};
        //if path blocked message should be obstructed
        forwardJSON.put("message", "Done");
        return  forwardJSON;
    }
    public JSONObject getRobotForwardNegative(){
        JSONObject forwardJSON = new JSONObject();
//        int[] robotPosition = {this.x,this.y};
        //if path blocked message should be obstructed
        forwardJSON.put("message", "Obstructed");
        return  forwardJSON;
    }
    public JSONObject getRobotBackPositive(){
        JSONObject backJSON = new JSONObject();
//        int[] robotPosition = {this.x,this.y};
        //if path blocked message should be obstructed
        backJSON.put("message :", "Done");
        return  backJSON;
    }
    public JSONObject getRobotBackNegative(){
        JSONObject backJSON = new JSONObject();
//        int[] robotPosition = {this.x,this.y};
        //if path blocked message should be obstructed
        backJSON.put("message :", "Obstructed");
        return  backJSON;
    }


//    public boolean handleCommand(Command command) {
////        addToHistory(command);
//        return command.execute(this);
//    }
    public IWorld.UpdateResponse updateWorldPosition(World world, int nrSteps, Robot target){
        return world.updatePosition(nrSteps, target);

    }
//    public void IWorld

    public void updateWorldDirection(boolean turnRight){
//        this.updateDirection(turnRight);
        this.updateWorldDirection(turnRight);
    }

//    public void addToHistory(Command command) {
//
//        if (!command.getName().equals("off") && (!command.getName().equals("help")) && !command.getName().equals("replay")) {
//            this.history.add(command);
//        }
//    }

    @Override
    public String toString() {
        return "[" + currentPos.getX() + "," + currentPos.getY() + "] "
                + this.name + "> " + this.status;
    }

//    public Position getPosition() {
//        return this.position;
//    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void keyPressed(KeyEvent e) {
    }

    public void draw(Graphics graphics) {
    }
}
