package slogo.Model.Parsing;

import slogo.Model.TurtleModel.Turtle;

import java.util.*;

public class TurtleIterator<T> implements Iterator {

    Map<Double, Turtle> turtleSet;
    List<Double> keyList;
    int index;

    public TurtleIterator(Map<Double, Turtle> turtleList){
        turtleSet = turtleList;
        keyList = new ArrayList<>();
        keyList.addAll(turtleList.keySet());
        Collections.sort(keyList);
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < keyList.size();
    }

    @Override
    public Turtle next() {
        Turtle currentTurtle = turtleSet.get(keyList.get(index));
        index++;
        return currentTurtle;
    }
}
