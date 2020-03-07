package slogo.Model.Parsing;

import slogo.Model.Commands.Command;
import slogo.Model.ErrorHandling.ParsingException;

import java.util.*;

public class Parser{

    private static final String START_UNLIMITED_PARAMETERS_CHARACTER = "(";
    private static final String END_UNLIMITED_PARAMETERS_CHARACTER = ")";

    private CommandFactory factory;
    private CommandManager commandManager;
    private Stack<Command> unlimitedParametersCommands;
    private Stack<String> argumentStack;
    private Stack<Command> commandStack;
    private Stack<Integer> countFromStack;
    public Parser(CommandManager cm){
        factory = new CommandFactory(cm.getLanguageConverter(), cm.getMethodExplorer());
        commandManager = cm;
        unlimitedParametersCommands = new Stack<>();
        argumentStack = new Stack<>();
        commandStack = new Stack<>();
        countFromStack = new Stack<>();
        countFromStack.push(0);
    }

    /**
     * Creates a list of commands to execute in order based on the input from the console
     * @param input from the Console
     */
    public double parseCommands(String input) throws ParsingException {
        if(input == null || input.length() == 0){
            return 0;
        }
        EntityListIterator entityIterator = new EntityListIterator(input);
        return parseEntityList(entityIterator);
    }



    private double parseEntityList(EntityListIterator entityIterator) throws ParsingException {
        double lastReturn = 0;
        while(entityIterator.hasNext()){
            try {
                String item = entityIterator.next();
                item = checkStartUnlimitedParameters(entityIterator, item);
                if(item.equals(END_UNLIMITED_PARAMETERS_CHARACTER)){
                    handleStacksAtEndOfUnlimitedParameters(lastReturn);
                }
                else{
                    determineCommandOrArg(item);
                }

            }
            catch (ArrayIndexOutOfBoundsException e){
                throw new ParsingException("MissingCloseBracket", "]");
            }
            lastReturn = combineCommandsArgs();
        }
        checkUnfulfilledCommands();
        return lastReturn;
    }

    private void handleStacksAtEndOfUnlimitedParameters(double lastReturn) {
        unlimitedParametersCommands.pop();
        commandStack.pop();
        countFromStack.pop();
        argumentStack.push(lastReturn + "");
    }

    private void determineCommandOrArg(String item) throws ParsingException {
        if(factory.isCommand(item)){
            pushCommand(item);
        }
        else{
            argumentStack.push(item);
        }
    }

    private String checkStartUnlimitedParameters(EntityListIterator entityIterator, String item) throws ParsingException {
        if(item.equals(START_UNLIMITED_PARAMETERS_CHARACTER)){
            item = entityIterator.next();
            if(!factory.isCommand(item)){
                throw new ParsingException("UnlimitedParamNotCommand", item);
            }
            unlimitedParametersCommands.push(pushCommand(item));
            item = entityIterator.next();
        }
        return item;
    }

    private void checkUnfulfilledCommands() throws ParsingException {
        if(commandStack.size() > 0){
            String unfulfilled = "";
            while(commandStack.size() > 0){
                unfulfilled = unfulfilled + commandStack.pop();
            }
            throw new ParsingException("UnfulfilledCommands", unfulfilled);
        }
    }

    private Command pushCommand(String item) throws ParsingException {
        Command com = factory.getCommand(item);
        commandStack.push(com);
        countFromStack.push(argumentStack.size());
        return com;
    }

    private double combineCommandsArgs() throws ParsingException {
        double ret = 0;
        try {
            while(!commandStack.isEmpty()){
                Command topCom = commandStack.peek();
                int numArguments = argumentStack.size();
                if(numArguments - countFromStack.peek() >= topCom.getNumArguments()) {
                    ret = handleParamsArgs(numArguments);
                }
                else{
                    return ret;
                }
            }
        }
        catch(EmptyStackException e){
            throw new ParsingException("UnrecognizedEntity", argumentStack.peek());
        }
        return ret;
    }

    private double handleParamsArgs(int numArguments) throws ParsingException {
        Command topCom;
        double ret;
        topCom = commandStack.pop();
        List<String> params = getParametersFromArgStack(numArguments);
        ret = commandManager.actOnCommand(topCom, params);
        String result = ret + "";
        if (!unlimitedParametersCommands.isEmpty() && topCom == unlimitedParametersCommands.peek()) {
            commandStack.push(topCom);
            if(topCom.getNumArguments() > 1){
                argumentStack.push(result);
            }
        }
        else {
            argumentStack.push(result);
        }
        return ret;
    }

    private List<String> getParametersFromArgStack(int numArguments) {
        List<String> params = new ArrayList<>();
        for (int i = 0; i < numArguments - countFromStack.peek(); i++) {
            params.add(argumentStack.pop());
        }
        Collections.reverse(params);
        return params;
    }
}
