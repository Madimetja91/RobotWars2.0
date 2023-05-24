package za.co.wethinkcode.toyworld.server;

import org.json.JSONObject;
import za.co.wethinkcode.toyworld.IWorld;
import za.co.wethinkcode.toyworld.World;
import za.co.wethinkcode.toyworld.Command;
import za.co.wethinkcode.toyworld.Robot;
import java.util.ArrayList;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

public class RobotsCommand extends Command {

    private Robot roBot;
    public  RobotsCommand(){
        super("robots");

    }

    @Override
public JSONObject execute(IWorld world, Robot target) {
        ArrayList<SimpleServer> clientHandlers = SimpleServer.getClientHandlers();
        JsonObject response = new JsonObject();
        response.addProperty("response", "successful");
        ArrayList<JsonObject> robots = new ArrayList<>();

        for(SimpleServer clientHandler : clientHandlers){
            JsonObject robot = new JsonObject();
            robot.addProperty("robot", target.getName());
            robot.addProperty("State", target.getRobotState().toString());
            robot.addProperty("position", "["+target.getCurrentPosition().getX() + "," + target.getCurrentPosition().getY()+"]");
            robot.addProperty("direction",target.getDirection().toString());

            robots.add(robot);
            System.out.println("Robots connected: "+clientHandler.robot.getName());
        }
        response.add("robots", new Gson().toJsonTree(robots));
        String jsonResponse = new Gson().toJson(response);
        System.out.println(jsonResponse);
        return new JSONObject(response.toString());
    }

    @Override
    public JSONObject execute(World world, Robot target) {
        return null;
    }


//    @Override
//    public boolean execute(Robot target) {
//        ArrayList<ClientHandler> clientHandlers = ClientHandler.getClientHandlers();
//        JsonObject response = new JsonObject();
//        response.addProperty("response", "successful");
//        ArrayList<JsonObject> robots = new ArrayList<>();
//
//        for (ClientHandler clientHandler : clientHandlers) {
//            JsonObject robot = new JsonObject();
//            robot.addProperty("robot", clientHandler.getClientUsername());
//            robot.addProperty("State", clientHandler.getClientState());
//            robot.addProperty("position", "[" + target.getPosition().getX() + "," + target.getPosition().getY() + "]");
//            robot.addProperty("direction", target.getCurrentDirection().toString());
//
//            robots.add(robot);
//            System.out.println("Robots connected: " + clientHandler.getClientUsername());
//        }
//        response.add("robots", new Gson().toJsonTree(robots));
//
//        // Extract individual properties and print them
//        System.out.println("Response: " + response.get("response").getAsString());
//        JsonArray robotArray = response.getAsJsonArray("robots");
//        for (JsonElement robotElement : robotArray) {
//            JsonObject robotObject = robotElement.getAsJsonObject();
//            System.out.println("Robot: " + robotObject.get("robot").getAsString());
//            System.out.println("State: " + robotObject.get("State").getAsString());
//            System.out.println("Position: " + robotObject.get("position").getAsString());
//            System.out.println("Direction: " + robotObject.get("direction").getAsString());
//        }
//
//        return true;
//    }


}
