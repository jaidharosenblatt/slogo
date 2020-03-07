package slogo.Model.Commands.ControlStructures;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class IfCommand extends BackEndCommand {



    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        double expr = getDoubleParameter(params.get(0), commandManager.getVariableExplorer());
        if(isZero(expr)){
            return 0.0;
        }
        return commandManager.parseCommands(params.get(1));
    }
}
