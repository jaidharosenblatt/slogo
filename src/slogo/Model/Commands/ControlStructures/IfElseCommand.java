package slogo.Model.Commands.ControlStructures;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class IfElseCommand extends BackEndCommand {

    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        double expr = getDoubleParameter(params.get(0), commandManager.getVariableExplorer());
        int whichToExecute = 1;
        if(expr == 0.0){
            whichToExecute = 2;
        }
        return commandManager.parseCommands(params.get(whichToExecute));
    }
}
