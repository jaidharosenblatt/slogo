package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import slogo.Model.Parsing.LanguageConverter;

public class PaletteView extends VBox {

  private ObservableList<String> palette;
  private static final String PATH = "resources/Palettes/";
  private ResourceBundle colorsResources;
  private Actions actions;
  private LanguageConverter languageConverter;
  public PaletteView(LanguageConverter language, ObservableList list, Actions actions) {
    palette = list;
    languageConverter = language;
    colorsResources = ResourceBundle.getBundle(PATH + language.getLanguage());
    this.actions = actions;
    ComboBox background = bindList();
    ComboBox pen = bindList();
    background.getSelectionModel().selectedItemProperty().
            addListener(e-> changeBackgroundColor(background.getSelectionModel().getSelectedIndex()));
    pen.getSelectionModel().selectedItemProperty()
            .addListener(e -> changePenColor(pen.getSelectionModel().getSelectedIndex()));
    getChildren().addAll(background, pen);
    setSpacing(30);
  }

  private void changePenColor(int selectedItem) {
    actions.handlePenColor(selectedItem+"");
  }

  private ComboBox bindList(){
    ComboBox comboBox = new ComboBox();
    comboBox.itemsProperty().bind(new SimpleObjectProperty<>(palette));
    return comboBox;
  }

  private void changeBackgroundColor(int selectedItem) {
    actions.handleBackgroundColor(selectedItem+"");
  }


  public Color getColor(int index){
    String s = palette.get(index);
    String[] components = s.split(" ");
    int[] colorRGB = new int[3];
    for (int j=0; j<colorRGB.length; j++){
      colorRGB[j]= Integer.parseInt(components[j]);
    }
    java.awt.Color c = new java.awt.Color(colorRGB[0], colorRGB[1], colorRGB[2]);
    int a = c.getAlpha();
    double opacity = a / 255.0 ;
    Color color = new Color(c.getRed()/255.0, c.getGreen()/255.0, c.getBlue()/255.0, opacity);
    return color;
  }

  public void addColor(Color c){
    int red = (int)(c.getRed() * 255);
    int green = (int)(c.getGreen() * 255);
    int blue = (int)(c.getBlue() * 255);
    palette.add(red+" "+green+" "+blue);
  }

}