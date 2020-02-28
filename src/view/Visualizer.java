package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.FrontEndExternal;
import slogo.ImmutableTurtle;
import slogo.ParsingException;

public class Visualizer implements FrontEndExternal, PropertyChangeListener {

  private static final String DEFAULT_LANGUAGE = "English";
  private static ResourceBundle resourceBundle;
  private String language;
  private Display display;
  private TabPaneView tabPaneView;
  private Terminal terminal;

  private static final double SCENE_WIDTH = 800;
  private static final double SCENE_HEIGHT = 500;
  private final String RESET = "Reset";
  private static final String PEN_COLOR = "Pen Color";
  private static final String BACKGROUND_COLOR = "Background Color";
  private static final String HISTORY_VARIABLE = "HistoryVariable";
  private static final String TURTLE_IMAGE = "TurtleImage";

  public Visualizer(Stage stage, String language) {
    this.language = language;
    setBundle(stage);
    stage.setTitle(resourceBundle.getString("Title"));

    BorderPane root = new BorderPane();

    terminal = new Terminal(language);

    display = new Display();

    tabPaneView = new TabPaneView(language);
    tabPaneView.addChangeSettingsListener(this);
    addPanesToRoot(root);

    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    scene.getStylesheets().add("resources/Styles/default.css");
    stage.setScene(scene);
    stage.show();
  }

  private void addPanesToRoot(BorderPane root) {
    Pane displayNode = display.getPane();
    displayNode.getStyleClass().add("display");

    Node terminalNode = terminal.getPane();

    TabPane tabNode = tabPaneView.getTabPane();

    root.setCenter(displayNode);
    BorderPane.setAlignment(tabNode, Pos.TOP_LEFT);
    root.setLeft(tabNode);
    root.setBottom(terminalNode);
  }

  private void setBundle(Stage stage) {
    try {
      resourceBundle = ResourceBundle
          .getBundle("resources/ui/" + language);
    } catch (MissingResourceException e) {
      this.language = DEFAULT_LANGUAGE;
      resourceBundle = ResourceBundle
          .getBundle("resources/ui/" + language);
    }
  }

  /**
   * Implements Observer Design pattern
   *
   * @param evt
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    switch (evt.getPropertyName()) {
      case RESET:
        display.resetPane();
        break;
      case PEN_COLOR:
        display.setPenColor(Color.web(evt.getNewValue().toString()));
        break;
      case BACKGROUND_COLOR:
        display.setBackgroundColor(Color.web(evt.getNewValue().toString()));
        break;
      case HISTORY_VARIABLE:
        terminal.setInputText(evt.getNewValue().toString());
        break;
      case TURTLE_IMAGE:
        display.updateTurtleImage(evt.getNewValue().toString());
        break;
    }
  }

  @Override
  public void updateTurtle(List<ImmutableTurtle> turtleList) throws ParsingException {

    for (ImmutableTurtle turtle : turtleList) {
      display.setTurtleHeading(turtle.getHeading());
      display.setPenState(turtle.getPenState());
      display.setTurtleVisibility(turtle.getShowing());
      if (checkTurtleOutOfBounds(turtle)) {
        throw new ParsingException("OutOfBoundsException", turtleList.indexOf(turtle));
      }
      display.moveTurtle(new Point2D(turtle.getX(), -1 * turtle.getY()));
    }
  }

  private Boolean checkTurtleOutOfBounds(ImmutableTurtle turtle) {
    return turtle.getX() > display.getPane().getWidth() / 2
        || turtle.getX() < -1 * display.getPane().getWidth() / 2 ||
        turtle.getY() > display.getPane().getHeight() / 2 || turtle.getY() < -1 *
        display.getPane().getHeight() / 2;
  }

  @Override
  public ImmutableTurtle getCurrentTurtle() {
    return display.getTurtleState();
  }

  @Override
  public String getLanguage() {
    return tabPaneView.getLanguage();
  }

  @Override
  public void displayError(Exception error) {
    terminal.setErrorText(error.getMessage());
  }

  public void bindTabs(String language, ObservableList history, ObservableList variables,
      ObservableMap methods) {
    tabPaneView.createHistoryTab(language, history);
    tabPaneView.addChangeHistoryListener(this);
    tabPaneView.createMethodTab(language, methods);
    tabPaneView.createVariableTab(language, variables);
  }

}
