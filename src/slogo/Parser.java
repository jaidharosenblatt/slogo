package slogo;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Parser {



    private CommandFactory factory;
    private Stack<IntegerVariable> executionTimesStack;
    private IntegerVariable executionTimes;
    private int executionLimit;
    private boolean creatingMethod;
    private MethodExplorer methodExplorer;
    private int lastReturnFromMethod;

    public Parser(String lang, MethodExplorer me){
        methodExplorer = me;
        factory = new CommandFactory(lang, methodExplorer);
        executionTimesStack = new Stack<>();
        executionTimes = new IntegerVariable("*1", 1);
        executionLimit = 1;
        creatingMethod = false;
        lastReturnFromMethod = 0;
    }

    /**
     * Creates a list of commands to execute in order based on the input from the console
     * @param input from the Console
     * @return a list of commands to execute
     */
    public List<ImmutableTurtle> parseStringToCommands(String input, Turtle turtle) throws ParsingException{
        //for executiontimes
        List<ImmutableTurtle> stateList = new ArrayList<>();
        String[] lineList = input.split("\n");
        for(int i = 0; i < lineList.length; i++){
            stateList.addAll(parseLine(lineList[i], turtle));
        }

        //Pop next executiontime
        return stateList;
    }

    private List<ImmutableTurtle> parseLine(String line, Turtle turtle) {
        List<ImmutableTurtle> states = new ArrayList<>();

        Stack<Integer> argumentStack = new Stack<>();
        Stack<Command> commandStack = new Stack<>();

        String[] entityList = line.split(" ");
        for(String item: entityList){
            try{
                int arg = Integer.parseInt(item);
                argumentStack.push(arg);
            }
            catch (NumberFormatException e){
                try{
                    Command com = factory.getCommand(item);
                    commandStack.push(com);

                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                } catch (NoSuchMethodException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
            combineCommandsArgs(states, argumentStack, commandStack, turtle);
        }

        return states;

    }

    private void combineCommandsArgs(List<ImmutableTurtle> states, Stack<Integer> argumentStack, Stack<Command> commandStack, Turtle turtle) {
        int numArguments = argumentStack.size();
        try {
            Command topCom = commandStack.peek();
            while(numArguments >= topCom.getNumArguments()){
                topCom = commandStack.pop();
                List<Integer> params = new ArrayList<>();
                for(int i = 0; i < numArguments; i++){
                    params.add(argumentStack.pop());
                }
                Collections.reverse(params);
                topCom.setArguments(params);
                int result = 0;
                if(topCom instanceof SlogoMethod){
                    states.addAll(parseStringToCommands(((SlogoMethod) topCom).getCommandsAsString(), turtle));
                    result = lastReturnFromMethod;
                }
                else {
                    result = turtle.actOnCommand(topCom);
                    states.add(turtle.getImmutableTurtle());
                }
                argumentStack.add(result);
                numArguments = argumentStack.size();
                if(commandStack.size() > 0) {
                    topCom = commandStack.peek();
                }
                else{
                    break;
                }
            }
        }
        catch(EmptyStackException | ParsingException e){
            System.out.println("Incompatible number of commands and arguments");
        }
    }


    public static void main(String[] args) {
        MethodExplorer me = new MethodExplorer();
        Parser t = new Parser("English", me);
        Turtle turt = new Turtle();

        SlogoMethod m = new SlogoMethod("NewMeth", new ArrayList<>(), new ArrayList<>());

        m.addCommand("fd 5");
        m.addCommand("fd fd 10");

        me.addMethod(m);
        String s = "NewMeth";
        try {
            List<ImmutableTurtle> x = t.parseStringToCommands(s, turt);
            for(ImmutableTurtle c: x){
                System.out.println(c.getX());
            }

        } catch (ParsingException e) {
            e.printStackTrace();
        }
    }
}
