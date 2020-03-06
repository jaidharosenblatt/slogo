package slogo.view.terminal;

import java.util.ResourceBundle;
import javafx.scene.layout.BorderPane;
import slogo.Model.Parsing.LanguageConverter;
import slogo.view.Actions;

public class Terminal extends BorderPane {

  private static final String RESOURCES_TERMINAL = "resources/Layouts/Terminal/";
  private static final String RESOURCES_UI = "resources/UI/";
  private static ResourceBundle uiResources;
  private TerminalButtons buttons;
  private TerminalInput input;
  private ErrorBar errorBar;
  private static final double HEIGHT = 150;

  public Terminal(LanguageConverter language, Actions actions) {
    uiResources = ResourceBundle.getBundle(RESOURCES_UI + language.getLanguage());
    setPrefHeight(HEIGHT);

    errorBar = new ErrorBar();
    input = new TerminalInput(uiResources.getString("TerminalPrompt"));
    buttons = new TerminalButtons(language.getLanguage(), actions, input);

    setTop(errorBar);
    setCenter(input);
    setLeft(buttons);
  }

  public void setInputText(String text) {
    input.setText(text);
  }

  public void setErrorText(String text) {
    errorBar.setText(text);
  }

}
