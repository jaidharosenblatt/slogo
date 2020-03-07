package slogo.Model.Commands.FrontEndCommands;

import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class SetPaletteCommand extends BackEndCommand {


    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        double index = getIntegerParameter(params.get(0), commandManager.getVariableExplorer());
        double rValue = getIntegerParameter(params.get(1), commandManager.getVariableExplorer());
        double gValue = getIntegerParameter(params.get(2), commandManager.getVariableExplorer());
        double bValue = getIntegerParameter(params.get(3), commandManager.getVariableExplorer());
        commandManager.getPaletteExplorer().addColor(rValue, gValue, bValue, index);
        return index;
    }
}
