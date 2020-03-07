package slogo.Model.Commands.TurtleCommands;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class GoHomeCommand extends BackEndCommand {



    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) {
        double oldX = myTurtle.getX();
        double oldY = myTurtle.getY();
        myTurtle.setToHome();

        commandManager.getTurtleManager().addInternalState(myTurtle);
        return Math.hypot(myTurtle.getX() - oldX, myTurtle.getY() - oldY);
    }
}
