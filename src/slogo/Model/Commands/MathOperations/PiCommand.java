package slogo.Model.Commands.MathOperations;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class PiCommand extends BackEndCommand {

    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) {
        return Math.PI;
    }
}
