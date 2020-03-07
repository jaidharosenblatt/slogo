package slogo.Controller;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.Model.BackEndExternal;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Explorers.MethodExplorer;
import slogo.Model.Explorers.PaletteExplorer;
import slogo.Model.Explorers.Variables.VariableExplorer;
import slogo.Model.Parsing.CommandManager;
import slogo.Model.Parsing.LanguageConverter;
import slogo.Model.TurtleModel.ImmutableTurtle;
import slogo.view.Actions;
import slogo.view.Visualizer;
import slogo.xml.XMLParser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

public class Controller implements PropertyChangeListener {

  private Visualizer myVisualizer;
  private BackEndExternal backendManager;
  private Actions myActions;
  private History myHistory;
  private static final String DEFAULT_LANGUAGE = "English";
  private String language = DEFAULT_LANGUAGE;
  private LanguageConverter languageConverter;


  public Controller(Stage stage) {
    myActions = new Actions();
    myActions.addChangeListener(this);
    languageConverter = new LanguageConverter(language);
    myVisualizer = new Visualizer(stage, languageConverter, myActions);
    PaletteExplorer myPE = new PaletteExplorer(languageConverter, myActions);
    MethodExplorer myME = new MethodExplorer(languageConverter);
    VariableExplorer myVE = new VariableExplorer();
    backendManager = new CommandManager(myVisualizer, myME, myVE, myPE, languageConverter);
    myHistory = new History();
    myVisualizer.bindTabs(languageConverter, myHistory.getInputs(), myVE.getDisplayVariables(),
        myME.getMethodNames(), myPE.getList());
  }

  // public Controller(File file) { set the variables based on the xml file

  //}

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
        languageConverter.updateLanguage(value);
        this.language = value;
        break;
      case "Background Color":
        //myVisualizer.setBackgroundColor(Color.web(value));
        myVisualizer.setBackgroundColor(Integer.parseInt(value));
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
        System.out.println(value);
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

    myVisualizer.resetTrail(0);
  }

  private void handleRun(String command) {
    //myParser.setLanguage(language);
    //myTurtle.changeLanguage(language);
    try {
      Map<Double, List<ImmutableTurtle>> turtleList = backendManager.parseTurtleStatesFromCommands(command);
      myHistory.addInput(command);
      myVisualizer.updateTurtle(turtleList);
    }
    catch(Exception e) {
      myVisualizer.displayError(e);
    }
  }

  // check for screen bounds
}
