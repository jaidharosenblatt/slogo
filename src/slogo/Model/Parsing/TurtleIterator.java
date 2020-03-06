package slogo.Model.Parsing;

import slogo.Model.TurtleModel.Turtle;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class TurtleIterator<T> implements Iterator {

    List<Turtle> turtleSet;
    int index;

    public TurtleIterator(List<Turtle> turtleList){
        turtleSet = turtleList;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < turtleSet.size();
    }

    @Override
    public Turtle next() {
        Turtle currentTurtle = turtleSet.get(index);
        index++;
        return currentTurtle;
    }
}
