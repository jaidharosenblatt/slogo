package slogo.Model.Parsing;

import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.TurtleModel.ImmutableTurtle;
import slogo.Model.TurtleModel.Turtle;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

public class TurtleModelManager implements Iterable{
    private Map<Double, List<ImmutableTurtle>> internalStates;
    private Map<Double, Turtle> turtles;
    private Map<Double, List<ImmutableTurtle>> previousInternalStates;
    private Map<Double, Turtle> previousTurtles;

    public TurtleModelManager(){
        internalStates = new HashMap<>();
        turtles = new HashMap<>();
        previousInternalStates = new HashMap<>();
        previousTurtles = new HashMap<>();
        addTurtle(1);
    }

    protected void backupInternalStateList() {
        previousInternalStates = new HashMap<>();
        for(double id: internalStates.keySet()){
            for(ImmutableTurtle turtle: internalStates.get(id)){
                previousInternalStates.putIfAbsent(id, new ArrayList<>());
                previousInternalStates.get(id).add(new Turtle(turtle));
            }
        }

    }

    protected void backupTurtleList() {
        previousTurtles = new HashMap<>();
        for(double id: turtles.keySet()){
            previousTurtles.putIfAbsent(id, new Turtle(turtles.get(id)));
        }
    }

    protected Map<Double, List<ImmutableTurtle>> undoAction(){
        turtles = previousTurtles;
        previousTurtles = new HashMap<>();
        return previousInternalStates;
    }

    protected Map<Double, List<ImmutableTurtle>> getInternalStates() {
        return internalStates;
    }

    public void addInternalState(Turtle turtle){
        internalStates.putIfAbsent(turtle.getID(), new ArrayList<>());
        internalStates.get(turtle.getID()).add(turtle.getImmutableTurtle());
    }

    protected void clearInternalStates(){
        internalStates = new HashMap<>();
    }

    public void addTurtle(double id) {
        turtles.putIfAbsent(id, new Turtle(id));
    }

    @Override
    public TurtleIterator iterator() {
        return new TurtleIterator(turtles);
    }

    public void inactivateAll() {
        for(Turtle turtle: turtles.values()){
            turtle.setIsActive(0);
        }
    }

    public void activateTurtle(double id){
        turtles.putIfAbsent(id, new Turtle(id));
        turtles.get(id).setIsActive(1);
    }
}
