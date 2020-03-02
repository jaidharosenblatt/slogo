package slogo.Model.Parsing;

import slogo.Model.Explorers.MethodExplorer;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleModel.ImmutableTurtle;
import slogo.Model.TurtleModel.Turtle;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Explorers.Variables.VariableExplorer;
import slogo.view.Visualizer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private static final String EXECUTE_COMMAND_METHOD_NAME = "executeCommand";

    private Visualizer frontend;
    private MethodExplorer methodExplorer;
    private VariableExplorer variableExplorer;
    private List<ImmutableTurtle> internalStates;
    private List<Turtle> turtles;
    private String language;


    public CommandManager(Visualizer v, MethodExplorer me, VariableExplorer ve, String lang){
        language = lang;
        frontend = v;
        methodExplorer = me;
        variableExplorer = ve;
        internalStates = new ArrayList<>();
        Turtle startTurtle = new Turtle(1, true);
        turtles = new ArrayList<>();
        turtles.add(startTurtle);
    }


    public double actOnCommand(Command command, List<String> params) throws ParsingException {
        try {
            double ret = 0;
            for(Turtle turtle: turtles){
                if(turtle.isActive()){
                    Method method = command.getClass().getDeclaredMethod(EXECUTE_COMMAND_METHOD_NAME, CommandManager.class, Turtle.class, List.class);
                    ret = (double) method.invoke(command, this, turtle, params);
                }
            }
            return ret;
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new ParsingException("ExecuteMissing", command.toString());
        } catch (InvocationTargetException e) {
            if(e.getCause() instanceof ParsingException){
                throw (ParsingException) e.getCause();
            }
            throw new ParsingException("CommandExecuteError", command.toString());
        }
    }

    public List<ImmutableTurtle> getInternalStates() {
        return internalStates;
    }

    public void clearInternalStates(){
        internalStates = new ArrayList<>();
    }

    public MethodExplorer getMethodExplorer() {
        return methodExplorer;
    }

    public VariableExplorer getVariableExplorer(){
        return variableExplorer;
    }

    public List<Turtle> getTurtles(){
        return turtles;
    }

    public void setLanguage(String lang){
        language = lang;
    }

    public String getLanguage(){
        return language;
    }

}