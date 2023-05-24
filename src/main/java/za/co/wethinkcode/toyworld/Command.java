package za.co.wethinkcode.toyworld;


//import za.co.wethinkcode.robotworlds.Client.BackCommand;
//import za.co.wethinkcode.robotworlds.Client.LeftCommand;
//import za.co.wethinkcode.robotworlds.Client.RightCommand;
//import za.co.wethinkcode.robotworlds.Client.ForwardCommand;
//import za.co.wethinkcode.robotworlds.Server.quitCommand;

import org.json.JSONObject;

public abstract class Command {

    private final String name;
    private String argument;

    public abstract JSONObject execute(IWorld world, Robot target);

    public abstract JSONObject execute(World world, Robot target);

    public Command(String name) {
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    public String getName() {                                                                           //<2>
        return name;
    }

    public String getArgument() {
        return this.argument;
    }

//    public static Command create(String instruction) {
//        String[] args = instruction.toLowerCase().trim().split(" ");
//        switch (args[0]) {
////            case "shutdown":
//            case "quit":
//                return new quitCommand();
////            case "help":
////                return new HelpCommand();
//            case "forward":
//                return new ForwardCommand(args[1]);
//            case "back":
//                return new BackCommand(args[1]);
//            case "right":
//                return new RightCommand();
//            case "left":
//                return new LeftCommand();
//            default:
//                throw new IllegalArgumentException("Unsupported command: " + instruction);
//        }
//    }
}