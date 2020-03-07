package slogo.Model.Commands.TurtleQueries;

import slogo.Model.Parsing.CommandManager;
import slogo.Model.Commands.BackEndCommand;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class XCoordinateCommand extends BackEndCommand {
    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) {
        return myTurtle.getX();
    }

}
