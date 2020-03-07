package slogo.Model.Commands.TurtleCommands;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class SetTowardsCommand extends BackEndCommand {


    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        Double towardX = getDoubleParameter(params.get(0), commandManager.getVariableExplorer());
        Double towardY = getDoubleParameter(params.get(1), commandManager.getVariableExplorer());

        if(towardX == myTurtle.getX() && towardY == myTurtle.getY()) {
            throw new ParsingException("TowardSelfException");
        }

        double oldHeading = myTurtle.getHeading();
        double heading = Math.toDegrees(Math.atan((towardX-myTurtle.getX())/(towardY-myTurtle.getY())));
        myTurtle.setHeading(heading);

        commandManager.getTurtleManager().addInternalState(myTurtle);
        return heading - oldHeading;
    }

}