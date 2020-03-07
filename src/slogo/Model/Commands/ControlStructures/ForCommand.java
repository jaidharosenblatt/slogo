package slogo.Model.Commands.ControlStructures;

import slogo.Model.Parsing.CommandManager;
import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class ForCommand extends BackEndCommand {


    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        String[] argParts = params.get(0).split(" ");
        String name = argParts[1];
        double start = getDoubleParameter(argParts[2], commandManager.getVariableExplorer());
        double end = getDoubleParameter(argParts[3], commandManager.getVariableExplorer());
        double iterator = getDoubleParameter(argParts[4], commandManager.getVariableExplorer());
        double ret = repeatAction(commandManager, params.get(1), name, start, end, iterator);
        commandManager.getVariableExplorer().removeVariableByName(name);
        return ret;
    }
}
