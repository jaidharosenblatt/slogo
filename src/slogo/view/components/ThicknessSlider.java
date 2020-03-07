package slogo.view.components;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import slogo.view.Actions;
import javafx.scene.control.Slider;

public class ThicknessSlider extends Component {

  protected ThicknessSlider(String language, String key, Actions actions) {
    super(language, key, actions);
    HBox hbox = new HBox();
    Slider slider = new Slider();
    slider.setShowTickLabels(true);
    slider.setValue(Double.parseDouble(getDefaultFromKey(key)));
    slider.setMax(30);
    slider.setMajorTickUnit(5);

    Button button = new Button(getPromptFromKey("PenThicknessButton"));
    button.setOnAction(handler -> {
      String command = getCommandByKey("SetPenSize",0) + slider.getValue();
      handleAction(command);
    });

    hbox.getChildren().addAll(button, slider);
    getChildren().add(hbox);
  }
}
