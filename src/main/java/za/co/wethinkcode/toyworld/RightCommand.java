package za.co.wethinkcode.toyworld;

import org.json.JSONObject;

public class RightCommand extends Command{
    public RightCommand() {
        super("right");
    }

    @Override
    public JSONObject execute(IWorld world, Robot target) {
        return null;
    }

    @Override
    public JSONObject execute(World world, Robot target) {
        target.getWorld().updateDirection(true);
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("result", "OK");
        responseJSON.put("data", target.getRobotForwardPositive());
        responseJSON.put("state", target.getRobotState());
        return responseJSON;
    }
}
