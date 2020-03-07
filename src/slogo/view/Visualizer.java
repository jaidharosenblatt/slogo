package slogo.view;

import java.util.*;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.FrontEndExternal;
import slogo.Model.Parsing.LanguageConverter;
import slogo.Model.TurtleModel.ImmutableTurtle;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.view.terminal.Terminal;
import slogo.view.turtledisplay.TurtleManager;

public class Visualizer implements FrontEndExternal {

  private static final String DEFAULT_LANGUAGE = "English";
  private static ResourceBundle resourceBundle;
  private String language;
  private TurtleManager turtleManager;
  private TabPaneView tabPaneView;
  private Terminal terminal;
  private LanguageConverter languageConverter;
  private static final double SCENE_WIDTH = 800;
  private static final double SCENE_HEIGHT = 600;

  public Visualizer(Stage stage, LanguageConverter language, Actions actions) {
    BorderPane root = new BorderPane();

    initializeVariables(stage, language, actions);
    addPanesToRoot(root);

    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    scene.getStylesheets().add("resources/Styles/default.css");
    stage.setScene(scene);
    stage.show();
  }

  public Visualizer(Stage stage, LanguageConverter language, Actions actions, double width, double height,
                    double backgroundIndex, List<ImmutableTurtle> turtles) {
    BorderPane root = new BorderPane();

    initializeVariables(stage, language, actions);
    addPanesToRoot(root);

    Scene scene = new Scene(root, width, height);
    scene.getStylesheets().add("resources/Styles/default.css");
    stage.setScene(scene);
    stage.show();


    Map<Double, List<ImmutableTurtle>> turtleMap = new HashMap<>();
    for (ImmutableTurtle turtle : turtles) {
      turtleMap.put(turtle.getID(), turtles);
    }
    try {
      updateTurtle(turtleMap);
    }
    catch(ParsingException e) {

    }

  }

  private void initializeVariables(Stage stage, LanguageConverter language, Actions actions) {
    languageConverter = language;
    setBundle();
    stage.setTitle(resourceBundle.getString("Title"));
    terminal = new Terminal(languageConverter, actions);
    turtleManager = new TurtleManager();
    tabPaneView = new TabPaneView(languageConverter, actions);
  }

  private void addPanesToRoot(BorderPane root) {
    Pane displayNode = turtleManager;
    displayNode.getStyleClass().add("display");

    TabPane tabNode = tabPaneView.getTabPane();

    root.setCenter(displayNode);
    BorderPane.setAlignment(tabNode, Pos.TOP_LEFT);
    root.setLeft(tabNode);
    root.setBottom(terminal);
  }

  private void setBundle() {
    try {
      resourceBundle = ResourceBundle
          .getBundle("resources/UI/" + language);
    } catch (MissingResourceException e) {
      this.language = DEFAULT_LANGUAGE;
      resourceBundle = ResourceBundle
          .getBundle("resources/UI/" + language);
    }
  }

  public void setInputText(String text) {
    terminal.setInputText(text);
  }

  @Override
  public void updateTurtle(Map<Double, List<ImmutableTurtle>> turtleList) throws ParsingException {
    //Map<Integer, List<ImmutableTurtle>> turtles = new HashMap<>();
    //turtles.put(0,turtleList);
    turtleManager.updateTurtles(turtleList);
  }
  @Override
  public void displayError(Exception error) {
    terminal.setErrorText(error.getMessage());
  }

  @Override
  public void bindTabs(LanguageConverter language, ObservableList history, ObservableList variables,
      ObservableMap methods, ObservableList palette) {
    tabPaneView.createHistoryTab(language, history);
    tabPaneView.createMethodTab(language, methods);
    tabPaneView.createVariableTab(language, variables);
    tabPaneView.createPaletteTab(language, palette);
  }

  @Override
  public void setHistoryLanguage(String newLanguage) {
    tabPaneView.setHistoryLanguage(newLanguage);
  }

  @Override
  //FIXME
  public void setBackgroundColor(double color) {
    turtleManager.setBackgroundColor(Color.RED);
  }

  public void resetTrail(double index){
    turtleManager.resetTrail(index);
  }

}
