package slogo;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.paint.Color;
import slogo.Model.ErrorHandling.ParsingException;
import slogo.Model.Parsing.LanguageConverter;
import slogo.Model.TurtleModel.ImmutableTurtle;

import java.util.List;

/**
 * Interface containing Frontend methods that are meant for the Backend to use
 */
public interface FrontEndExternal {

  /**
   * Updates the turtle display using a list of all the states of a turtle
   *
   * @param turtleList a list of turtle states from the backend
   */
  void updateTurtle(List<ImmutableTurtle> turtleList) throws ParsingException;


  /**
   * Handle an error and tell the User what issue occurred
   *
   * @param error the error that was thrown in the backend
   */
  void displayError(Exception error);

  /**
   * Passes a list of tabs as observable map and lists to be able to bind to their external methods
   * in the controller
   *
   * @param language  the current language
   * @param history   a list of previous commands
   * @param variables a list of current variables
   * @param methods   a map of method name to commands
   * @param palette   a list of the current color palette
   */
  void bindTabs(LanguageConverter language, ObservableList history, ObservableList variables,
                ObservableMap methods, ObservableList palette);

  /**
   * Translates the history to a new language
   *
   * @param newLanguage the new language to set history to
   */
  void setHistoryLanguage(String newLanguage);

  /**
   * Set the current background color of the display
   *
   * @param color the color to set it to
   */
  void setBackgroundColor(double color);

  /**
   * Set text to the terminal input
   *
   * @param text the text to display
   */
  void setInputText(String text);

  void resetTrail(int index);


}
