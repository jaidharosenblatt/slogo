package slogo.Model.Commands.TurtleCommands;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class PenDownCommand extends BackEndCommand {

    @Override
    public int getNumArguments() {
        return 0;
    }

    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) {
        myTurtle.setPenState(1);
        commandManager.getTurtleManager().addInternalState(myTurtle);
        return 1;
    }

}
