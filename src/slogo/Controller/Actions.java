package slogo.Controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author jaidharosenblatt, ryanweeratunga - Implements the Observer design pattern to hold all of
 * the possible actions and create a change event that our controller can view. This class was
 * created since we could not figure out how to use reflection to get methods directly in the
 * Controller class.
 */
public class Actions {

  private final static String ACTIONS_RESOURCE_PATH = "resources/UI/Actions";
  private ResourceBundle actionsBundle = ResourceBundle.getBundle(ACTIONS_RESOURCE_PATH);

  private String data;
  private List<PropertyChangeListener> listeners = new ArrayList<>();

  /**
   * Sends an event listener with a label "Command"
   *
   * @param value to send
   */
  public void handleCommand(String value) {
    notifyListeners(actionsBundle.getString("Command"), this.data, this.data = value);
  }

  /**
   * Sends an event listener with a label "Clear"
   *
   * @param result to send
   */
  public void handleClear(String result) {
    notifyListeners(actionsBundle.getString("TerminalInput"), this.data, this.data = "");
  }

  /**
   * Sends an event listener with a label "Reset"
   *
   * @param value to send
   */
  public void handleReset(String value) {
    notifyListeners(actionsBundle.getString("Reset"), this.data, value);
  }

  public void handleTurtleImage(String value) {
    notifyListeners(actionsBundle.getString("TurtleImage"), this.data, this.data = value);
  }

  /**
   * Sends an event listener with a label "Language"
   *
   * @param value to send
   */
  public void handleLanguage(String value) {
    notifyListeners(actionsBundle.getString("Language"), this.data, this.data = value);
  }

  /**
   * Sends an event listener with a label "BackgroundColor"
   *
   * @param value to send
   */
  public void handleBackgroundColor(String value) {
    notifyListeners(actionsBundle.getString("BackgroundColor"), this.data, this.data = value);
  }

  /**
   * Sends an event listener with a label "TerminalInput"
   *
   * @param value to send
   */
  public void handleHistoryVariable(String value) {
    notifyListeners(actionsBundle.getString("TerminalInput"), this.data, this.data = value);
  }

  /**
   * Sends an event listener with a label "TurtleState"
   *
   * @param id      turtle id
   * @param showing value to send
   */
  public void handleTurtleState(String id, String showing) {
    PropertyChangeEvent e = new PropertyChangeEvent(this, actionsBundle.getString("TurtleState"),
        this.data,
        this.data = showing);
    e.setPropagationId(id);
    notifyListeners(actionsBundle.getString("TurtleState"), e);
  }

  /**
   * Sends an event listener with a label "TerminalInput". Duplicated with handleHistoryVariable
   *
   * @param value to send
   */
  public void handleMethodDisplay(String value) {
    notifyListeners(actionsBundle.getString("TerminalInput"), this.data, this.data = value);
  }

  /**
   * Notify Action's event listener (the Controller class) that a method button has been pressed
   *
   * @param property the name of the event
   * @param oldValue the previous value
   * @param newValue the new value
   */
  private void notifyListeners(String property, String oldValue, String newValue) {
    for (PropertyChangeListener name : listeners) {
      name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
    }
  }

  private void notifyListeners(String property, PropertyChangeEvent e) {
    for (PropertyChangeListener name : listeners) {
      name.propertyChange(e);
    }
  }

  /**
   * Used to add a new listener to this classes events
   * @param newListener the class to listen
   */
  public void addChangeListener(PropertyChangeListener newListener) {
    listeners.add(newListener);
  }
    for (int )
}
