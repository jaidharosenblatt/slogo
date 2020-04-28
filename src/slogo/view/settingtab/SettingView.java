package slogo.view.settingtab;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.*;
import javafx.scene.text.Text;
import slogo.Model.Parsing.LanguageHandler;
import slogo.Controller.Actions;
import slogo.view.components.Component;
import slogo.view.components.ComponentFactory;
import slogo.view.components.HelpButton;

/**
 * @author jaidharosenblatt A tab that uses reflection to add buttons specified in layouts.
 * Dependent on Component and ComponentFactory.
 */
public class SettingView extends VBox {

  private static final String PREFIX = "resources/UI/";
  private static final String RESOURCES_LAYOUTS = PREFIX + "Layouts";
  public static final int W_PADDING = 10;
  public static final int V_PADDING = 2;

  private static ResourceBundle layouts;

  /**
   * Constructs a settings tab
   *
   * @param language the gui language
   * @param actions  the possible methods to map actions to
   */
  public SettingView(LanguageHandler language, Actions actions) {
    layouts = ResourceBundle.getBundle(RESOURCES_LAYOUTS);
    setupTab(language, actions);
  }

  private HBox createLabel(String prompt) {
    HBox hbox = new HBox();
    Text text = new Text(prompt);
    hbox.getChildren().add(text);
    hbox.setPadding((new Insets(W_PADDING)));
    hbox.setAlignment(Pos.CENTER);
    hbox.setSpacing(V_PADDING);
    text.getStyleClass().add("settings-text");
    return hbox;
  }

  private void setupTab(LanguageHandler language, Actions actions) {
    List<String> buttonList = Arrays.asList(layouts.getString("SettingView").split(","));
    setSpacing(V_PADDING);
    ComponentFactory componentFactory = new ComponentFactory(language);

    for (String key : buttonList) {
      HBox hbox = new HBox();
      Component component = componentFactory.getComponent(language, key, actions);
      hbox.getChildren().add(createLabel(component.getPromptFromKey(key)));
      hbox.getChildren().add(component);

      getChildren().add(hbox);
    }
    HelpButton helpButton = new HelpButton();
    LoadXML loadButton = new LoadXML();
    getChildren().addAll(helpButton, loadButton);
  }
}
