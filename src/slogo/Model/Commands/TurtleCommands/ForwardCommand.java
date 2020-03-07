package slogo.Model.Commands.TurtleCommands;

import slogo.Model.Parsing.CommandManager;
import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class ForwardCommand extends BackEndCommand {

    @Override
    public int getNumArguments() {
        return 1;
    }

    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        double pixForward = getDoubleParameter(params.get(0), commandManager.getVariableExplorer());
        myTurtle.incrementX(pixForward * Math.sin(Math.toRadians(myTurtle.getHeading())));
        myTurtle.incrementY(pixForward * Math.cos(Math.toRadians(myTurtle.getHeading())));
        commandManager.getTurtleManager().addInternalState(myTurtle);
        return pixForward;
    }
}
