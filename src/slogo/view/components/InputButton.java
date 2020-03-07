package slogo.view.components;

import javafx.scene.control.Button;
import slogo.view.Actions;
import slogo.view.terminal.TerminalInput;

public class InputButton extends Component {

  public InputButton(String language, String key, Actions actions, TerminalInput input) {
    super(language, key, actions);
    Button button = new Button(getPromptFromKey(key));
    button.getStyleClass().add("terminal-button");
    button.setOnAction(handler -> handleAction(input.getText()));
    getChildren().add(button);
  }
}
