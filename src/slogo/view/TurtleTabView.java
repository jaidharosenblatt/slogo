package slogo.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import slogo.Model.Parsing.LanguageConverter;
import slogo.Model.TurtleModel.ImmutableTurtle;
import slogo.ReflectionException;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class TurtleTabView {
    private static ResourceBundle resourceBundle;
    private static ResourceBundle actionResources;
    private static final String RESOURCES_LAYOUT = "resources/UI/";
    private static final String RESOURCES_TURTLE_COLS = "resources/Layouts/turtlestab/";
    private LanguageConverter languageConverter;
    private Tab myTab;
    private Actions actions;
    private TableView<ImmutableTurtle> tableView;
    public TurtleTabView(LanguageConverter language, Actions actions){
        languageConverter = language;
        resourceBundle = ResourceBundle.getBundle(RESOURCES_LAYOUT+language.getLanguage());
        actionResources = ResourceBundle.getBundle(RESOURCES_TURTLE_COLS+language.getLanguage());
        myTab = new Tab(resourceBundle.getString("TurtleTab"));
        this.actions= actions;
        tableView= new TableView<>();
        setupTab();
    }

    private void setupTab() {
        VBox vbox = new VBox();
        List<String> columnList = Collections.list(actionResources.getKeys());
        for (String col: columnList){
            TableColumn column = createColumn(col);
            tableView.getColumns().add(column);
        }
        tableView.setEditable(false);
        tableView.refresh();
        vbox.getChildren().add(tableView);
        vbox.setAlignment(Pos.CENTER);
        myTab.setContent(vbox);
    }

    private TableColumn createColumn(String col) {
        TableColumn column = new TableColumn(col.toUpperCase());
        try {
            Method m = this.getClass().getDeclaredMethod(actionResources.getString(col), TableColumn.class, String.class);
            m.invoke(this, column, col);
        } catch (Exception e) {
            throw new ReflectionException("InvalidMethod", actionResources.getString(col));
        }
        return column;
    }

    private void addUneditableColumn(TableColumn c, String col){
        c.setCellValueFactory(new PropertyValueFactory<ImmutableTurtle,Object>(col));
    }

    private void addCheckBox(TableColumn c, String col){
        c.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ImmutableTurtle,Boolean>, ObservableValue<Boolean>>()
                {
                    //This callback tell the cell how to bind the data model 'Registered' property to
                    //the cell, itself.
                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ImmutableTurtle, Boolean> param)
                    {
                        return new SimpleBooleanProperty(param.getValue().getShowing() == 1);
                    }
                });
        CheckBoxTableCell cell = new CheckBoxTableCell();
        c.setCellFactory(cell.forTableColumn(c));
        tableView.setOnMouseClicked(e -> editTurtleState(cell));
    }

    private void editTurtleState(CheckBoxTableCell c) {
            ImmutableTurtle t = tableView.getSelectionModel().getSelectedItem();
            String status = t.getShowing()==1 ? "pu" : "pd";
            actions.handleTurtleState(t.getX() + "", status);
    }

    /**
     * returns Tab for states of turtles
     * @return myTab
     */
    public Tab getTab(){ return myTab; }

    public void setTable(ImmutableTurtle turtle){
        List<ImmutableTurtle> list = List.of(turtle);
        tableView.setItems(FXCollections.observableList(list));
    }
}
