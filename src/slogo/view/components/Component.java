package slogo.view.components;

import java.lang.reflect.Method;
import java.util.ResourceBundle;
import javafx.scene.layout.Pane;
import slogo.ReflectionException;
import slogo.view.Actions;

public abstract class Component extends Pane {

  private static final String RESOURCES_UI_PATH = "resources/UI/";
  private static final String RESOURCES_COMMAND_PATH = "resources/Languages/";
  private static final String METHODS_PATH = RESOURCES_UI_PATH + "ReflectionMethods";
  private static final String DEFAULT_RESOURCE_PATH = RESOURCES_UI_PATH + "Default";

  private static ResourceBundle promptBundle;
  private ResourceBundle commandBundle;
  private ResourceBundle methodBundle;
  private ResourceBundle defaultsResources;

  private String key;
  private Actions actions;

  protected Component(String language, String key, Actions actions) {
    this.actions = actions;
    this.key = key;
    defaultsResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PATH);
    methodBundle = ResourceBundle.getBundle(METHODS_PATH);
    promptBundle = ResourceBundle.getBundle(RESOURCES_UI_PATH + language);
    commandBundle = ResourceBundle.getBundle(RESOURCES_COMMAND_PATH + language);
  }

  protected String getCommandByKey(String key, int type) {
    //Splitting command by | ex: forward|fd
    String raw = commandBundle.getString(key);
    return raw.split("\\|")[type];
  }

  protected void handleAction(String value) {
    try {
      Method m = actions.getClass().getDeclaredMethod(methodBundle.getString(key), String.class);
      m.invoke(actions, value);
    } catch (Exception e) {
      throw  new ReflectionException("InvalidMethod", methodBundle.getString(key));
    }
  }

  protected String getDefaultFromKey(String key) {
    return defaultsResources.getString(key);
  }

  public String getPromptFromKey(String key) {
    return promptBundle.getString(key);
  }

}
