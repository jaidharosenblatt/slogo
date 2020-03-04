package slogo.Model.Commands.TurtleCommands;


import slogo.Model.CommandManager;
import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class LeftCommand extends BackEndCommand {

    @Override
    public int getNumArguments() {
        return 1;
    }

    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        Double degreesLeft = getDoubleParameter(params.get(0), commandManager.getVariableExplorer());
        myTurtle.incrementHeading(degreesLeft * -1);
        commandManager.getInternalStates().add(myTurtle.getImmutableTurtle());
        return degreesLeft;
    }
}
