package slogo.Model.Commands.MathOperations;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class PowerCommand extends BackEndCommand {



    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        return Math.pow(getDoubleParameter(params.get(0), commandManager.getVariableExplorer()),
                        getDoubleParameter(params.get(1), commandManager.getVariableExplorer()));
    }
}
