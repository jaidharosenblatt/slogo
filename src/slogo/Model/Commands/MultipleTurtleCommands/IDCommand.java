package slogo.Model.Commands.MultipleTurtleCommands;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class IDCommand extends BackEndCommand {
    @Override
    public int getNumArguments() {
        return 0;
    }

    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        return myTurtle.getID();
    }
}
