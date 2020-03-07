package slogo.Model.Commands.ControlStructures;

import slogo.Model.Parsing.CommandManager;
import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class RepeatCommand extends BackEndCommand {



    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        double numTimes = getDoubleParameter(params.get(0), commandManager.getVariableExplorer());
        double ret = repeatAction(commandManager, params.get(1), ":repcount", 1.0, numTimes, 1.0);
        commandManager.getVariableExplorer().removeVariableByName(":repcount");
        return ret;
    }
}
