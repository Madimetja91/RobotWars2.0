package za.co.wethinkcode.toyworld;

import org.json.JSONObject;

public class ForwardCommand extends Command {
    public ForwardCommand(double argument) {
        super("forward", String.valueOf(argument));
    }

    private JSONObject getResponse(IWorld.UpdateResponse response, Robot robot){
        JSONObject responseJSON = new JSONObject();

        if (response.equals(IWorld.UpdateResponse.SUCCESS)){
            responseJSON.put("result", "OK");
            responseJSON.put("data", robot.getRobotForwardPositive());
            responseJSON.put("state", robot.getRobotState());
        }
        else if (response.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)){
            responseJSON.put("result", "OK");
            responseJSON.put("data", robot.getRobotForwardNegative());
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
        int nrSteps = Integer.parseInt(getArgument());
        System.out.println(nrSteps);
        IWorld.UpdateResponse updateResponse = target.updateWorldPosition(world, nrSteps, target);
        JSONObject responseJson = getResponse(updateResponse, target);
//        if (target.updateWorldPosition(nrSteps).equals(IWorld.UpdateResponse.SUCCESS)) {
//            target.setStatus("Moved forward by "+nrSteps+" steps.");
//        } else if (target.updateWorldPosition(nrSteps).equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)){
//            target.setStatus("Sorry, There is an obstacle on the way.");
//        }
//        else{
//            target.setStatus("Sorry, I cannot go outside my safe zone.");
//        }
        return responseJson;
    }
}
