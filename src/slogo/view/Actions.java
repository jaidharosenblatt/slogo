package slogo.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Actions {

  private static final String COMMAND = "Command";
  private static final String RESET = "Reset";
  private static final String PEN_COLOR = "Pen Color";
  private static final String THICKNESS = "Thickness";
  private static final String BACKGROUND_COLOR = "Background Color";
  private static final String TURTLE_IMAGE = "Turtle Image";
  private static final String LANGUAGE = "Language";
  private static final String TERMINAL_INPUT = "Input Change";
  private static final String TURTLE_STATE = "Change Turtle State";
  private static final String METHOD_DISPLAY = "Method Display";
  private static final String XML = "Load XML";

  private String data;
  private List<PropertyChangeListener> listeners = new ArrayList<>();

  public void handleCommand(String value) {
    notifyListeners(COMMAND, this.data, this.data = value);
  }

  public void handleClear(String result) {
    notifyListeners(TERMINAL_INPUT, this.data, this.data = "");
  }

  public void handleReset(String value) {
    notifyListeners(RESET, this.data, value);
  }

  public void handleTurtleImage(String value) {
    notifyListeners(TURTLE_IMAGE, this.data, this.data = value);
  }

  public void handleLanguage(String value) {
    notifyListeners(LANGUAGE, this.data, this.data = value);
  }

  public void handleBackgroundColor(String value) {
    notifyListeners(BACKGROUND_COLOR, this.data, this.data = value);
  }

  public void handlePenColor(String value) {
    notifyListeners(PEN_COLOR, this.data, this.data = value);
  }

  public void handleThickness(String value) {
    notifyListeners(THICKNESS, this.data, this.data = value);
  }

  public void handleHistoryVariable (String value) { notifyListeners(TERMINAL_INPUT, this.data, this.data=value); }

  public void handleTurtleState(String id, String showing) {
    PropertyChangeEvent e = new PropertyChangeEvent(this, TURTLE_STATE, this.data, this.data = showing);
    e.setPropagationId(id);
    notifyListeners(TURTLE_STATE, e);
  }

  public void handleMethodDisplay(String value){ notifyListeners(TERMINAL_INPUT, this.data, this.data = value);}

  public void handleLoadXML(String value){ notifyListeners(XML, this.data, this.data = value); }

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

  public void addChangeListener(PropertyChangeListener newListener) {
    listeners.add(newListener);
  }



}
