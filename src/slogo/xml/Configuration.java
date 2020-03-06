package slogo.xml;

import slogo.Model.TurtleModel.ImmutableTurtle;
import slogo.Model.TurtleModel.Turtle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Configuration {
    public static final List<String> CONFIGURATION_FIELDS = List.of(
            "width",
            "height",
            "backgroundIndex",
            "turtleCount",
            "language"
            //TODO: don't forget about methods and variables
            // also get RGB values
    );
    public static final List<String> TURTLE_FIELDS = List.of(
            "x",
            "y",
            "heading",
            "penState",
            "showing",
            "isActive",
            "penThickness",
            "penIndex",
            "image",
            "id"
    );
    public static final String TURTLE = "turtle";

    private double myWidth;
    private double myHeight;
    private int backgroundIndex;
    private int turtleCount;
    private String myLanguage;

    private List<ImmutableTurtle> myTurtles;

    public Configuration (String width, String height, String backgroundIndex, String turtleCount, String language, List<List<Double>> turtleList) {
        myWidth = Double.parseDouble(width);
        myHeight = Double.parseDouble(height);
        this.backgroundIndex = Integer.parseInt(backgroundIndex);
        this.turtleCount = Integer.parseInt(turtleCount);
        myLanguage = language;
        createTurtles(turtleList);
    }

    public Configuration (Map<String, String> configValues, List<List<Double>> turtleList) {
        this(configValues.get(CONFIGURATION_FIELDS.get(0)),
                configValues.get(CONFIGURATION_FIELDS.get(1)),
                configValues.get(CONFIGURATION_FIELDS.get(2)),
                configValues.get(CONFIGURATION_FIELDS.get(3)),
                configValues.get(CONFIGURATION_FIELDS.get(4)),
                turtleList);
    }

    private void createTurtles(List<List<Double>> turtleList) {
        myTurtles = new ArrayList<>();
        for (List<Double> values : turtleList) {
            myTurtles.add(new Turtle(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4),
                    values.get(5), values.get(6), values.get(7), values.get(8), values.get(9)));
        }
    }

    public double getWidth() {
        return myWidth;
    }

    public double getHeight() {
        return myHeight;
    }

    public int getBackgroundIndex() {
        return backgroundIndex;
    }

    public int getTurtleCount() {
        return turtleCount;
    }

    public String getLanguage() {
        return myLanguage;
    }

    public List<ImmutableTurtle> getTurtles() {
        return myTurtles;
    }
}
