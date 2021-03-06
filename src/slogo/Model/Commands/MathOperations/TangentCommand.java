package slogo.Model.Commands.MathOperations;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class TangentCommand extends BackEndCommand {

    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        return Math.tan(getDoubleParameter(params.get(0), commandManager.getVariableExplorer()));
    }
}
