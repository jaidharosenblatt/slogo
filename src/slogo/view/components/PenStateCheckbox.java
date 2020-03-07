package slogo.view.components;

import javafx.scene.control.CheckBox;
import slogo.view.Actions;

public class PenStateCheckbox extends Component {

  protected PenStateCheckbox(String language, String key, Actions actions) {
    super(language, key, actions);
    CheckBox checkBox = new CheckBox();
    checkBox.setSelected(true);
    checkBox.selectedProperty().addListener(
        handler -> {
          String status =
              checkBox.isSelected() ? getCommandByKey("PenDown", 0) : getCommandByKey("PenUp", 0);
          handleAction(status);
        });
    getChildren().add(checkBox);
  }
}
