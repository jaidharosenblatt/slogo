package view.SettingsTab;

import java.lang.reflect.Method;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import view.Actions;

public abstract class LabeledDropdown extends HBox {

  protected LabeledDropdown(String prompt, String methodName, Actions target) {
    Text text = new Text(prompt);
    getChildren().add(text);
    setPadding((new Insets(10, 5, 10, 5)));
    setAlignment(Pos.CENTER);
    setSpacing(10.0);
    text.getStyleClass().add("settings-text");
  }

  protected void handleAction(String value, String methodName, Actions target) {
    try {
      Method m = target.getClass().getDeclaredMethod(methodName, String.class);
      m.invoke(target, value);
    } catch (Exception e) {
      // FIXME: typically make your own custom exception to throw
      throw new RuntimeException("Improper configuration", e);
    }
  }
}
