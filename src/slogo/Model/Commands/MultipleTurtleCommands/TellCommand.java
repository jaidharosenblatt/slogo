package slogo.Model.Commands.MultipleTurtleCommands;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.Parsing.TurtleModelManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class TellCommand extends BackEndCommand {


    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        String[] activeTurtleIDStrings = removeOuterBrackets(params.get(0)).strip().split(" ");
        TurtleModelManager turtleManager = commandManager.getTurtleManager();
        turtleManager.inactivateAll();
        double currentTurtleID = 0;
        for(String idString: activeTurtleIDStrings){
            currentTurtleID = getIntegerParameter(idString, commandManager.getVariableExplorer());
            turtleManager.addInternalState(turtleManager.activateTurtle(currentTurtleID));
        }
        return currentTurtleID;
    }
}
