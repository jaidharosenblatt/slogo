package slogo.Commands.ControlStructures;

import slogo.CommandManager;
import slogo.Commands.BackEndCommand;
import slogo.ParsingException;
import slogo.Turtle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MakeUserInstructionCommand extends BackEndCommand {
    @Override
    public int getNumArguments() {
        return 3;
    }

    @Override
    public double executeCommand(CommandManager commandManager, Turtle myTurtle, List<String> params) throws ParsingException {
        try{
            Double.parseDouble(params.get(0));
            throw new ParsingException("InvalidMethodName");
        }
        catch (NumberFormatException e) {
            List<String> paramNames = new ArrayList<>();
            String[] namesParts = params.get(1).split(" ");
            String[] names = Arrays.copyOfRange(namesParts, 1, namesParts.length -1);
            paramNames.addAll(Arrays.asList(names));
            for (int i = 0; i < paramNames.size(); i++) {
                if (paramNames.get(i).equals("")) {
                    paramNames.remove(i);
                }
            }
            UserDefinedInstructionCommand newMethod = new UserDefinedInstructionCommand(params.get(0), removeOuterBrackets(params.get(2)), paramNames);
            commandManager.getMethodExplorer().addMethod(newMethod);
            return 0;
        }
    }
}
