package za.co.wethinkcode.toyworld;

import org.json.JSONObject;

public class BackCommand extends Command{
    public BackCommand(double argument) {
        super("back", String.valueOf(argument));
    }
    private JSONObject getResponse(IWorld.UpdateResponse response, Robot robot){
        JSONObject responseJSON = new JSONObject();

        if (response.equals(IWorld.UpdateResponse.SUCCESS)){
            responseJSON.put("result", "OK");
            responseJSON.put("data", robot.getRobotBackPositive());
            responseJSON.put("state", robot.getRobotState());
        }
        else if (response.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)){
            responseJSON.put("result", "OK");
            responseJSON.put("data", robot.getRobotBackNegative());
            responseJSON.put("state", robot.getRobotState());
        }
        return responseJSON;
    }

    @Override
    public JSONObject execute(IWorld world, Robot target) {
        return null;
    }

    @Override
    public JSONObject execute(World world, Robot target) {
        int nrSteps = -(Integer.parseInt(getArgument()));
        IWorld.UpdateResponse updateResponse = target.updateWorldPosition( world, nrSteps, target);
        JSONObject responseJson = getResponse(updateResponse, target);

        return responseJson;
    }
}
