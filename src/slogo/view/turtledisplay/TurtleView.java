package slogo.view.turtledisplay;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class TurtleView extends Pane {

  private ImageView graphic;
  private Point2D position;
  private int turtleVisible = 1;
  private int penActive = 1;
  private double heading;

  protected TurtleView(Image image, double x, double y, double heading) {
    this.graphic = new ImageView(image);
    graphic.setFitWidth(50);
    graphic.setFitHeight(50);

    Point2D point = new Point2D(x, y);
    getChildren().add(graphic);

    setPosition(point);
    setHeading(heading);
  }

  protected void setPosition(Point2D point) {
    this.position = point;
    graphic.setX(point.getX() - graphic.getBoundsInLocal().getWidth() / 2);
    graphic.setY(point.getY() - graphic.getBoundsInLocal().getHeight() / 2);
  }

  protected Point2D getPosition() {
    return this.position;
  }

  protected double getHeading() {
    return this.heading;
  }

  protected void setHeading(double heading) {
    graphic.setRotate(this.heading = heading);
  }

  protected int isTurtleVisible() {
    return turtleVisible;
  }

  protected void setTurtleVisible(int isVisible) {
    this.turtleVisible = isVisible;
    graphic.setVisible(isVisible == 1);
  }

  protected int isPenActive() {
    return penActive;
  }

  protected void setGraphicImage(Image image) {
    graphic.setImage(image);
  }

  protected void setPenActive(int penActive) {
    this.penActive = penActive;
  }

}