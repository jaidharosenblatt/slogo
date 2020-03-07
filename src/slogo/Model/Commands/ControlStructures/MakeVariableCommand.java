package slogo.Model.Commands.ControlStructures;

import slogo.Model.Parsing.CommandManager;
import slogo.Model.Commands.BackEndCommand;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Explorers.Variables.DoubleVariable;
import slogo.Model.Explorers.Variables.Variable;
import slogo.Model.TurtleModel.Turtle;

import java.util.List;

public class MakeVariableCommand extends BackEndCommand {


    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        Variable<Double> var = new DoubleVariable(params.get(0), getDoubleParameter(params.get(1), commandManager.getVariableExplorer()));
        commandManager.getVariableExplorer().addVariable(var);
        return var.getValue();
    }
}
