package slogo.view;

import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.paint.Color;
import slogo.Model.Parsing.LanguageConverter;
import slogo.Model.TurtleModel.ImmutableTurtle;
import slogo.view.settingtab.SettingView;


public class TabPaneView {

  private SettingView settingView;
  private HistoryView historyView;
  private MethodView methodView;
  private TurtleTabView turtleTabView;
  private TabPane tabPane;
  private VariableView variableView;
  private PaletteView paletteView;
  private static ResourceBundle uiResources;
  private Actions actions;
  private static final String PREFIX = "resources/UI/";

  public TabPaneView(LanguageConverter language, Actions actions) {
    uiResources = ResourceBundle.getBundle(PREFIX + language.getLanguage());
    tabPane = new TabPane();
    tabPane.getStyleClass().add("tabPane");

    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    createSettingTab(language, actions);
    createTurtleTab(language, actions);
    this.actions = actions;
  }

  protected TabPane getTabPane() {
    return tabPane;
  }


  public void createPaletteTab(LanguageConverter language, ObservableList list) {
    paletteView = new PaletteView(language,list, actions);
    Tab tab = new Tab(uiResources.getString("PaletteTab"));
    tab.setContent(paletteView);
    tabPane.getTabs().add(tab);
  }

  private void createSettingTab(LanguageConverter language, Actions actions) {
    settingView = new SettingView(language, actions);
    Tab tab = new Tab(uiResources.getString("SettingTab"));
    tab.setContent(settingView);
    tabPane.getTabs().add(tab);
  }

  private void createTurtleTab(LanguageConverter language, Actions actions) {
    turtleTabView = new TurtleTabView(language, actions);
    Tab tab = new Tab(uiResources.getString("TurtleTab"));
    tabPane.getTabs().add(turtleTabView.getTab());
  }

  public void createMethodTab(LanguageConverter language, ObservableMap methodList) {
    methodView = new MethodView(language, methodList, actions);
    tabPane.getTabs().add(methodView.getTab());
  }

  public void createVariableTab(LanguageConverter language, ObservableList variableList) {
    variableView = new VariableView(language, variableList);
    tabPane.getTabs().add(variableView.getTab());
  }

  public void createHistoryTab(LanguageConverter language, ObservableList historyList) {
    historyView = new HistoryView(language, historyList, actions);
    tabPane.getTabs().add(historyView.getTab());
  }

  public Color getColor(int index){
    return paletteView.getColor(index);
  }
  public void updateTurtleTab(ImmutableTurtle turtle){
    turtleTabView.setTable(turtle);
  }

    public void setHistoryLanguage(String newLanguage) {
      historyView.setLanguage(newLanguage);
    }

}
