package slogo.Model.Commands.BooleanOperations;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class EqualCommand extends BackEndCommand {

    @Override
    public int getNumArguments() {
        return 2;
    }

    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        boolean isEqual = getDoubleParameter(params.get(0), commandManager.getVariableExplorer()) ==
                getDoubleParameter(params.get(1), commandManager.getVariableExplorer());
        return isEqual ? 1.0 : 0.0;
    }
}
