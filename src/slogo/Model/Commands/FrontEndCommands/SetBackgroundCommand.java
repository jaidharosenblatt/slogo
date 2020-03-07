package slogo.Model.Commands.FrontEndCommands;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class SetBackgroundCommand extends BackEndCommand {
    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        double index = getIntegerParameter(params.get(0), commandManager.getVariableExplorer());
        commandManager.getDisplay().setBackgroundColor(index);
        return index;
    }

}
