package slogo.view.components;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import slogo.view.Actions;
import slogo.view.components.Component;

public class TurtleImageDropdown extends Component {

  private static final String LAYOUT_RESOURCE_PATH = "resources/UI/Layouts";
  private ResourceBundle layouts = ResourceBundle.getBundle(LAYOUT_RESOURCE_PATH);
  private ComboBox<String> comboBox;

  protected TurtleImageDropdown(String language, String methodName, Actions target) {
    super(language, methodName, target);
    ObservableList<String> displayText = getTurtleImages();

    comboBox = new ComboBox<>();
    comboBox.setValue(getDefaultFromKey(methodName));
    comboBox.itemsProperty().bind(new SimpleObjectProperty<>(displayText));
    comboBox.setOnAction(
        handler -> {
          String value = comboBox.getValue().split(". ")[0];
          String command = getCommandByKey("SetShape", 0) + " " + value;
          handleAction(command);
        });

    getChildren().add(comboBox);
  }

  private ObservableList<String> getTurtleImages() {
    String[] images = layouts.getString("TurtleImages").split(",");
    List<String> displayText = new ArrayList<>();
    for (int i = 0; i < images.length; i++) {
      displayText.add(i + ". " + images[i]);
    }
    Collections.sort(displayText);
    return FXCollections.observableList(displayText);
  }


}
