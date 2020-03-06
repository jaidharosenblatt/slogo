package slogo.view.turtledisplay;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Trail extends Pane{

  private List<Node> trail = new ArrayList<>();
  private Color currentColor;
  private double currentThickness;

  protected Trail(Double thickness, Color color) {
    this.currentThickness = thickness;
    this.currentColor = color;
  }

  protected Color getCurrentColor() {
    return currentColor;
  }

  protected void setCurrentColor(Color currentColor) {
    this.currentColor = currentColor;
  }

  protected double getCurrentThickness() {
    return currentThickness;
  }

  protected void setCurrentThickness(double currentThickness) {
    this.currentThickness = currentThickness;
  }

  protected void addLine(Point2D start, Point2D end) {
    Line line = new Line();
    line.setStrokeWidth(currentThickness);
    line.setStroke(currentColor);
    line.setStartX(start.getX());
    line.setStartY(start.getY());
    line.setEndX(end.getX());
    line.setEndY(end.getY());
    getChildren().add(line);
    trail.add(line);
  }
}
