package slogo.Model.Commands.MultipleTurtleCommands;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.Parsing.TurtleModelManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class AskCommand extends BackEndCommand {

    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        TurtleModelManager turtleModelManager = commandManager.getTurtleManager();
        List<Double> initialActive = turtleModelManager.getActiveTurtleIDs();
        new TellCommand().executeCommand(commandManager, myTurtle, params.subList(0, 1));
        double ret = commandManager.parseCommands(params.get(1));
        turtleModelManager.activateTurtles(initialActive);
        return ret;
    }
}
