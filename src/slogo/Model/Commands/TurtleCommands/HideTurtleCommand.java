package slogo.Model.Commands.TurtleCommands;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class HideTurtleCommand extends BackEndCommand {

    @Override
    public int getNumArguments() {
        return 0;
    }

    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) {
        myTurtle.setShowing(0);
        commandManager.getTurtleManager().addInternalState(myTurtle);
        return 0;
    }

}
