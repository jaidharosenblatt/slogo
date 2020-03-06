package slogo.Model.Parsing;

import slogo.Model.BackEndExternal;
import slogo.Model.Explorers.MethodExplorer;
import slogo.Model.Commands.Command;
import slogo.Model.Explorers.PaletteExplorer;
import slogo.Model.TurtleModel.ImmutableTurtle;
import slogo.Model.TurtleModel.Turtle;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Explorers.Variables.VariableExplorer;
import slogo.view.Visualizer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandManager implements BackEndExternal {
    private static final String EXECUTE_COMMAND_METHOD_NAME = "executeCommand";

    private Visualizer frontend;
    private MethodExplorer methodExplorer;
    private VariableExplorer variableExplorer;
    private PaletteExplorer paletteExplorer;
    private TurtleManager turtleManager;
    private LanguageConverter languageConverter;


    public CommandManager(Visualizer v, MethodExplorer me, VariableExplorer ve, PaletteExplorer pe, LanguageConverter lang){
        languageConverter = lang;
        frontend = v;
        methodExplorer = me;
        variableExplorer = ve;
        paletteExplorer = pe;
        turtleManager = new TurtleManager();

    }

    @Override
    public List<ImmutableTurtle> parseTurtleStatesFromCommands(String input) throws ParsingException {
        clearInternalStates();
        parseCommands(input);


        List<ImmutableTurtle> ret = getInternalStates();
        System.out.println("States:");
        for(ImmutableTurtle t: ret){
            System.out.println(t.getY());
        }
        turtleManager.backupTurtleList();
        turtleManager.backupInternalStateList();

        return getInternalStates();
    }

    @Override
    public List<ImmutableTurtle> undoAction(){
        return turtleManager.undoAction();
    }



    public double parseCommands(String input) throws ParsingException {
        Parser parser = new Parser(this);
        return parser.parseCommands(input);
    }




    protected double actOnCommand(Command command, List<String> params) throws ParsingException {
        try {
            double ret = 0;
            TurtleIterator iterator = turtleManager.iterator();
            while(iterator.hasNext()){
                Turtle turtle = iterator.next();
                if(turtle.isActive() == 1){
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
        return turtleManager.getInternalStates();
    }

    private void clearInternalStates(){
        turtleManager.clearInternalStates();
    }

    public MethodExplorer getMethodExplorer() {
        return methodExplorer;
    }

    public VariableExplorer getVariableExplorer(){
        return variableExplorer;
    }

    public PaletteExplorer getPaletteExplorer(){
        return paletteExplorer;
    }

    public Visualizer getDisplay(){
        return frontend;
    }

    @Override
    public void setLanguage(String lang){
        methodExplorer.convertLanguage(lang);
    }

    public LanguageConverter getLanguageConverter(){
        return languageConverter;
    }

}
