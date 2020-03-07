package slogo.Model.Commands.BooleanOperations;

import slogo.Model.Parsing.CommandManager;
import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class AndCommand extends BackEndCommand {


    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        if(getDoubleParameter(params.get(0), commandManager.getVariableExplorer()) != 0 && getDoubleParameter(params.get(1), commandManager.getVariableExplorer()) != 0){
            return 1.0;
        }
        return 0.0;
    }
}
