package slogo.Model.Commands.FrontEndCommands;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class SetPenColorCommand extends BackEndCommand {
    @Override
    public int getNumArguments() {
        return 1;
    }

    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        double index = getIntegerParameter(params.get(0), commandManager.getVariableExplorer());
        myTurtle.setPenColorIndex(index);
        return index;
    }
}