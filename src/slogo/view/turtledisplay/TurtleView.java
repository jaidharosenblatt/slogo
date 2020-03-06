package slogo.view.turtledisplay;

import java.util.ResourceBundle;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TurtleView extends Pane {

  private Trail trail;
  private Turtle turtle;
  private int index;
  private static final String TURTLE_PATH = "turtles/";
  private static final String DEFAULT_RESOURCE_PATH = "resources/UI/Default";
  private ResourceBundle defaults = ResourceBundle.getBundle(DEFAULT_RESOURCE_PATH);
  private Image turtleImage;

  public TurtleView(int index) {
    this.index = index;
    turtleImage = getImageByName(TURTLE_PATH + defaults.getString("TurtleImage"));
    reset();
  }

  public void reset() {
    getChildren().clear();
    this.turtle = new Turtle(turtleImage, 0, 0, 0);
    this.trail = new Trail(Double.parseDouble(defaults.getString("PenThickness")),
        Color.web(defaults.getString("PenColor")));

    getChildren().addAll(trail, turtle);
  }

  public void moveTurtle(Point2D newCoordinate) {
    Point2D oldCoordinate = turtle.getPosition();
    turtle.setPosition(newCoordinate);
    if (turtle.isPenActive() == 1) {
      trail.addLine(oldCoordinate, newCoordinate);
    }
  }

  public void setTurtleHeading(double newHeading) {
    turtle.setHeading(newHeading);
  }

  public void setTurtleVisibility(double state) {
    turtle.setTurtleVisible(state);
  }

  public void setPenState(double state) {
    turtle.setPenActive(state);
  }


  public void setPenThickness(Double thickness) {
    trail.setCurrentThickness(thickness);
  }

  public void setTurtleImage(String filename) {
    Image image = getImageByName(TURTLE_PATH + filename);
    turtleImage = image;
    turtle.setGraphicImage(image);
  }

  public void setPenColor(Color currentColor) {
    trail.setCurrentColor(currentColor);
  }

  private Image getImageByName(String name) {

    return new Image(this.getClass().getClassLoader().getResourceAsStream(name));
  }

}
