package slogo.Model.Commands.TurtleCommands;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class ClearScreenCommand extends BackEndCommand {




    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        myTurtle = new Turtle(myTurtle.getID());
        commandManager.getDisplay().resetTrail(myTurtle.getID());
        commandManager.getDisplay().resetErrorBar();
        return 0;
    }
}