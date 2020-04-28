fgetCommandByKeypackage slogo.view.components;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;
import slogo.Model.Parsing.LanguageHandler;
import slogo.Controller.Actions;

/**
 * @author jaidharosenblatt, ryanweeratunga extends component to create a dropdown for all of the
 * possible languages found in resources
 */
public class LanguageDropdown extends Component {

  private ComboBox<String> comboBox;

  private static final String DEFAULT_LANGUAGE = "English";
  private static final String LANGUAGE_PATH = "src/resources/languages/";

  protected LanguageDropdown(LanguageHandler language, String key, Actions actions) {
    super(language, key, actions);
    comboBox = new ComboBox<>();
    comboBox.setValue(DEFAULT_LANGUAGE);
    comboBox.getItems().addAll(getLanguages());
    comboBox.setOnAction(
        handler -> handleAction(comboBox.getValue()));
    getChildren().add(comboBox);
  }

  private List<String> getLanguages() {
    File directoryPath = new File(LANGUAGE_PATH);
    List<String> languages = new ArrayList<>();
    for (String s : directoryPath.list()) {
      int index = s.indexOf(".");
      String substring = s.substring(0, index);
      languages.add(substring);
    }
    return languages;
  }

}
