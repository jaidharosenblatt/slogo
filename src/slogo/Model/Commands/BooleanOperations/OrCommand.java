package slogo.Model.Commands.BooleanOperations;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class OrCommand extends BackEndCommand {


    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        boolean isOr = getDoubleParameter(params.get(0), commandManager.getVariableExplorer()) != 0 ||
                getDoubleParameter(params.get(1), commandManager.getVariableExplorer()) != 0;
        return isOr ? 1.0 : 0.0;
    }
}
