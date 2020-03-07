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
import java.util.List;
import java.util.Map;

public class CommandManager implements BackEndExternal {
    private static final String EXECUTE_COMMAND_METHOD_NAME = "executeCommand";

    private Visualizer frontend;
    private MethodExplorer methodExplorer;
    private VariableExplorer variableExplorer;
    private PaletteExplorer paletteExplorer;
    private TurtleModelManager turtleManager;
    private LanguageConverter languageConverter;
    private Turtle currentTurtle;


    public CommandManager(Visualizer v, MethodExplorer me, VariableExplorer ve, PaletteExplorer pe, LanguageConverter lang){
        languageConverter = lang;
        frontend = v;
        methodExplorer = me;
        variableExplorer = ve;
        paletteExplorer = pe;
        turtleManager = new TurtleModelManager();
        currentTurtle = new Turtle(1);

    }

    @Override
    public Map<Double, List<ImmutableTurtle>> parseTurtleStatesFromCommands(String input) throws ParsingException {
        clearInternalStates();

        parseCommands(input);


        Map<Double, List<ImmutableTurtle>> retMap = getInternalStates();
        System.out.println("States:");
        for(Double d: retMap.keySet()){
            System.out.println("Index: " + d);
            for(ImmutableTurtle t: retMap.get(d)){
                System.out.println("\tID: " + t.getID()+ "    Y: " + t.getY());
            }
        }


        turtleManager.backupTurtleList();
        turtleManager.backupInternalStateList();

        return retMap;
    }

    @Override
    public Map<Double, List<ImmutableTurtle>> undoAction(){
        return turtleManager.undoAction();
    }



    public double parseCommands(String input) throws ParsingException {
        TurtleIterator iterator = turtleManager.iterator();
        double ret = 0;
        while(iterator.hasNext()){
            currentTurtle = iterator.next();
            Parser parser = new Parser(this);
            ret = parser.parseCommands(input);
        }
        return ret;
    }




    protected double actOnCommand(Command command, List<String> params) throws ParsingException {
        double ret = 0;
        try {
            if(currentTurtle.isActive() == 1.0) {
                Method method = command.getClass().getDeclaredMethod(EXECUTE_COMMAND_METHOD_NAME, CommandManager.class, Turtle.class, List.class);
                ret = (double) method.invoke(command, this, currentTurtle, params);
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new ParsingException("ExecuteMissing", command.toString());
        } catch (InvocationTargetException e) {
            if(e.getCause() instanceof ParsingException){
                throw (ParsingException) e.getCause();
            }
            throw new ParsingException("CommandExecuteError", command.toString());
        }
        return ret;
    }

    private Map<Double, List<ImmutableTurtle>> getInternalStates() {
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

    public TurtleModelManager getTurtleManager(){
        return turtleManager;
    }

    @Override
    public void setLanguage(String lang){
        methodExplorer.convertLanguage(lang);
    }

    public LanguageConverter getLanguageConverter(){
        return languageConverter;
    }

}
