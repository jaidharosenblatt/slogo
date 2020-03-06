package slogo;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.Controller.Controller;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

  /**
   * Start of the program.
   */
  public static void main(String[] args) { launch(args); }

  @Override
  public void start(Stage primaryStage) {
    new Controller(primaryStage);
  }
}
