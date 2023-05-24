package za.co.wethinkcode.toyworld;

import org.json.JSONObject;

public class LeftCommand extends Command{
    public LeftCommand() {
        super("left");
    }

    @Override
    public JSONObject execute(IWorld world, Robot target) {
        return null;
    }

    @Override
    public JSONObject execute(World world, Robot target) {
        target.getWorld().updateDirection(false);
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("result", "OK");
        responseJSON.put("data", target.getRobotForwardPositive());
        responseJSON.put("state", target.getRobotState());
        return responseJSON;

//        JSONObject responseJson = getResponse(target.getWorld().updateDirection(false), target);
//        return responseJson;
    }
}
