package slogo.Model.Commands;

import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public interface Command {

    public int getNumArguments();
    public abstract double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException;
}
