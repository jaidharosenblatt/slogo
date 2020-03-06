package slogo.Controller;

import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.Model.BackEndExternal;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Explorers.MethodExplorer;
import slogo.Model.Explorers.PaletteExplorer;
import slogo.Model.Explorers.Variables.VariableExplorer;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.TurtleModel.ImmutableTurtle;
import slogo.view.Actions;
import slogo.view.Visualizer;
import slogo.xml.Chooser;
import slogo.xml.Configuration;
import slogo.xml.XMLException;
import slogo.xml.XMLParser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

public class Controller implements PropertyChangeListener {

  private Stage myStage;
  private Visualizer myVisualizer;
  private BackEndExternal backendManager;
  private Actions myActions;
  private History myHistory;
  private XMLParser myXMLParser;
  private Configuration myConfiguration;
  private static final String DEFAULT_LANGUAGE = "English";
  private String language = DEFAULT_LANGUAGE;

  private List<ImmutableTurtle> turtleList;

  public Controller(Stage stage) {
    myActions = new Actions();
    myActions.addChangeListener(this);
    myStage = stage;
    myVisualizer = new Visualizer(stage, language, myActions);
    PaletteExplorer myPE = new PaletteExplorer(language, myActions);
    MethodExplorer myME = new MethodExplorer(language);
    VariableExplorer myVE = new VariableExplorer();
    backendManager = new CommandManager(myVisualizer, myME, myVE, myPE, language);
    myHistory = new History();
    myVisualizer.bindTabs(this.language, myHistory.getInputs(), myVE.getDisplayVariables(),
        myME.getMethodNames(), myPE.getList());
  }

  public Controller(File file, Stage stage) { //set the variables based on the xml file
    this(stage);
    myXMLParser = new XMLParser();
    myConfiguration = myXMLParser.getConfiguration(file);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String value = evt.getNewValue().toString();
    switch (evt.getPropertyName()){
      case "Command":
        handleRun(value);
        break;
      case "Reset":
        handleReset();
        break;
      case "Language":
        backendManager.setLanguage(value);
        myVisualizer.setHistoryLanguage(value);
        this.language = value;
        break;
      case "Pen Color":
        myVisualizer.setPenColor(Color.web(value));
        break;
      case "Background Color":
        myVisualizer.setBackgroundColor(Color.web(value));
        break;
      case "Turtle Image":
        myVisualizer.setTurtleImage(value);
        break;
      case "Thickness":
        myVisualizer.setPenThickness(Double.parseDouble(value));
        break;
      case "Pen Status":
        //FIXME update pen status in backend
        myVisualizer.setPenStatus(Integer.parseInt(value));
        break;
      case "Input Change":
        myVisualizer.setInputText(value);
        break;
      case "Change Turtle State":
        myVisualizer.setInputText(evt.getPropagationId().toString()+" "+evt.getNewValue().toString());
        break;
      case "Background Color Index":
        //TODO update backend index of background color
        break;
      case "Load XML":
        new Controller(new File(value), myStage);
        break;
    }
  }

  //FIXME
  private void handleReset() {
    /*
    try {
      turtleList = backendManager.parseTurtleStatesFromCommands("clearscreen");
      myHistory.addInput("clearscreen");
      myVisualizer.updateTurtle(turtleList);
    }
    catch(Exception e) {
      myVisualizer.displayError(e);
    }
    // clearscreen not implemented right now
     */

    myVisualizer.resetTrail();
  }

  private void handleRun(String command) {
    //myParser.setLanguage(language);
    //myTurtle.changeLanguage(language);
    try {
      turtleList = backendManager.parseTurtleStatesFromCommands(command);
      myHistory.addInput(command);
      myVisualizer.updateTurtle(turtleList);
    }
    catch(Exception e) {
      myVisualizer.displayError(e);
    }
  }

  // check for screen bounds
}
