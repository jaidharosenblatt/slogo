package slogo.xml;

import slogo.Model.TurtleModel.ImmutableTurtle;
import slogo.Model.TurtleModel.Turtle;

import java.io.File;

public class XMLMain {
    public static void main (String[] args){
        XMLParser asdf = new XMLParser();
        Configuration config = asdf.getConfiguration(new File("data/example1.xml"));
        for (ImmutableTurtle turtle : config.getTurtles()) {
            System.out.println(turtle.getX());
            System.out.println(turtle.getY());
            System.out.println(turtle.getHeading());
            System.out.println(turtle.getPenState());
            System.out.println(turtle.getShowing());
            System.out.println(turtle.isActive());
            System.out.println(turtle.getPenColorIndex());
            System.out.println(turtle.getPenThickness());
            System.out.println(turtle.getTurtleImageIndex());
            System.out.println(turtle.getID());
            System.out.println("");
        }
    }
}
