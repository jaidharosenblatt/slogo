package slogo.Model.Parsing;

import slogo.Model.TurtleModel.ImmutableTurtle;
import slogo.Model.TurtleModel.Turtle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TurtleManager implements Iterable{
    private List<ImmutableTurtle> internalStates;
    private List<Turtle> turtles;
    private List<ImmutableTurtle> previousInternalStates;
    private List<Turtle> previousTurtles;

    public TurtleManager(){
        internalStates = new ArrayList<>();
        Turtle startTurtle = new Turtle(1);
        turtles = new ArrayList<>();
        turtles.add(startTurtle);
        previousInternalStates = new ArrayList<>();
        previousTurtles = new ArrayList<>();
    }

    protected void backupInternalStateList() {
        previousInternalStates = new ArrayList<>();
        for(ImmutableTurtle turtle: internalStates){
            previousInternalStates.add(new Turtle(turtle));
        }
    }

    protected void backupTurtleList() {
        previousTurtles = new ArrayList<>();
        for(Turtle turtle: turtles){
            previousTurtles.add(new Turtle(turtle));
        }
    }

    protected List<ImmutableTurtle> undoAction(){
        turtles = previousTurtles;
        previousTurtles = new ArrayList<>();
        return previousInternalStates;
    }

    protected List<ImmutableTurtle> getInternalStates() {
        return internalStates;
    }

    protected void clearInternalStates(){
        internalStates = new ArrayList<>();
    }

    @Override
    public TurtleIterator iterator() {
        return new TurtleIterator(turtles);
    }
}
