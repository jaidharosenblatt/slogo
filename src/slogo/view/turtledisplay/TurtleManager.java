package slogo.view.turtledisplay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.TurtleModel.ImmutableTurtle;

public class TurtleManager extends Pane {

  private Map<Double, TurtleView> turtleMap;

  public TurtleManager() {
    turtleMap = new HashMap<>();
    turtleMap.put(1.0, createTurtle(1));
    updatePane();
  }

  public void updateTurtles(Map<Double, List<ImmutableTurtle>> turtles) throws ParsingException {
    for (double i: turtles.keySet()) {
      List<ImmutableTurtle> turtleList = turtles.get(i);
      if (turtleMap.get(i) == null) {
        turtleMap.put((double) i, createTurtle(i));
      }
      for (ImmutableTurtle turtleState : turtleList) {
        TurtleView turtle = turtleMap.get((double) i);
        turtle.setPenState(turtleState.getPenState());
        //      turtle.setPenColor(turtleProperties.getPenColorIndex());
        turtle.setTurtleHeading(turtleState.getHeading());
        turtle.setPenThickness(turtleState.getPenThickness());
        turtle.setTurtleVisibility(turtleState.getShowing());
        if (checkTurtleOutOfBounds(turtleState)) {
          throw new ParsingException("OutOfBoundsException");
        }
        turtle.moveTurtle(new Point2D(turtleState.getX(), -1 * turtleState.getY()));
      }
    }
    updatePane();
  }

  public void resetTrail(double i) {
    TurtleView turtle = turtleMap.get(i);
    turtle.reset();
  }

  private TurtleView createTurtle(double i) {
    TurtleView turtle = new TurtleView(i);
    turtle.translateXProperty().bind(widthProperty().divide(2));
    turtle.translateYProperty().bind(heightProperty().divide(2));

    return turtle;
  }

  public void setBackgroundColor(Color color) {
    setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
  }

  private void updatePane() {
    getChildren().clear();
    for (double index : turtleMap.keySet()) {
      getChildren().add(turtleMap.get(index));
    }
  }

  private Boolean checkTurtleOutOfBounds(ImmutableTurtle turtle) {
    return turtle.getX() > getWidth() / 2
        || turtle.getX() < -1 * getWidth() / 2 ||
        turtle.getY() > getHeight() / 2 || turtle.getY() < -1 *
        getHeight() / 2;
  }
}
