package slogo.Model.Commands.TurtleCommands;

import slogo.Model.Parsing.CommandManager;
import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class BackwardCommand extends BackEndCommand {

    @Override
    public int getNumArguments() {
        return 1;
    }

    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        double pixBackward = getDoubleParameter(params.get(0), commandManager.getVariableExplorer());
        myTurtle.incrementX(pixBackward * Math.sin(Math.toRadians(myTurtle.getHeading())) * -1);
        myTurtle.incrementY(pixBackward * Math.cos(Math.toRadians(myTurtle.getHeading())) * -1);
        commandManager.getTurtleManager().addInternalState(myTurtle);
        return pixBackward;

    }
}
