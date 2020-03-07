package slogo.Model.Commands.ControlStructures;

import slogo.Model.Parsing.CommandManager;
import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class DoTimesCommand extends BackEndCommand {

    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        String[] limitString = params.get(0).split(" ");
        String varName = limitString[1];
        double limit = getDoubleParameter(limitString[2], commandManager.getVariableExplorer());
        return repeatAction(commandManager, params.get(1), varName, 1.0, limit, 1.0);
    }
}
