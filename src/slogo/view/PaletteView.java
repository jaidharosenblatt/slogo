package slogo.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import slogo.Model.Parsing.LanguageConverter;

public class PaletteView extends GridPane {

  private ObservableList<String> palette;
  private static final String PATH = "resources/Palettes/";
  private ResourceBundle colorsResources;
  private Actions actions;
  private LanguageConverter languageConverter;
  private VBox background;
  private VBox pen;
  public PaletteView(LanguageConverter language, ObservableList list, Actions actions) {
    palette = list;
    languageConverter = language;
    colorsResources = ResourceBundle.getBundle(PATH + language.getLanguage());
    this.actions = actions;
    background = bindList();
    pen = bindList();
    palette.addListener((ListChangeListener.Change<? extends String> e)->handleColorAdded(e));
    add(background, 0,0,1,1);
    add(pen, 1, 0, 1, 1);
  }

  private void handleColorAdded(ListChangeListener.Change<? extends String> e) {
    if (e.wasAdded()){
      addAdditionalColor();
    }
  }

  private void addAdditionalColor() {
    background.getChildren().add(createColorOption(palette.size()-1));
    pen.getChildren().add(createColorOption(palette.size()-1));
  }

  private void changePenColor(int selectedItem) {
    actions.handlePenColor(selectedItem+"");
  }

  private VBox bindList(){
    VBox vbox = new VBox();
    for (int i=0; i< palette.size(); i++){
      vbox.getChildren().add(createColorOption(i));
    }
    return vbox;
  }

  private Node createColorOption(int i) {
    HBox color = new HBox();
    Label label = new Label(""+i);
    Rectangle rect = new Rectangle();
    rect.setWidth(100);
    rect.setHeight(10);
    rect.setFill(getColor(i));
    color.getChildren().addAll(label, rect);
    color.setSpacing(10);
    return color;
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

  public List getImmutableList(){
    List<Color> colors = new ArrayList<>();
    for (int i =0; i<palette.size(); i++){
      colors.add(getColor(i));
    }
    return Collections.unmodifiableList(colors);
  }

}