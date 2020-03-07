package slogo.Model.Commands.TurtleCommands;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class SetPositionCommand extends BackEndCommand {

    @Override
    public int getNumArguments() {
        return 2;
    }

    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        double oldX = myTurtle.getX();
        double oldY = myTurtle.getY();

        Double newX = getDoubleParameter(params.get(0), commandManager.getVariableExplorer());
        Double newY = getDoubleParameter(params.get(1), commandManager.getVariableExplorer());

        myTurtle.setX(newX);
        myTurtle.setY(newY);

        commandManager.getTurtleManager().addInternalState(myTurtle);
        return Math.hypot(newX - oldX, newY - oldY);
    }

}
