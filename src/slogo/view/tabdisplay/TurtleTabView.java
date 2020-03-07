package slogo.view.tabdisplay;

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
import slogo.view.Actions;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class TurtleTabView {
    private ResourceBundle resourceBundle;
    private ResourceBundle actionResources;
    private ResourceBundle layoutResources;
    private static final String RESOURCES_PATH = "resources/UI/";
    private static final String RESOURCE_LAYOUTS = RESOURCES_PATH + "Layouts";
    private static final String METHODS_PATH = RESOURCES_PATH + "ReflectionMethods";
    private LanguageConverter languageConverter;
    private Tab myTab;
    private Actions actions;
    private TableView<ImmutableTurtle> tableView;

    public TurtleTabView(LanguageConverter language, Actions actions) {
        languageConverter = language;
        resourceBundle = ResourceBundle.getBundle(RESOURCES_PATH + language.getLanguage());
        layoutResources = ResourceBundle.getBundle(RESOURCE_LAYOUTS);
        actionResources = ResourceBundle.getBundle(METHODS_PATH);
        myTab = new Tab(resourceBundle.getString("TurtleTab"));
        this.actions = actions;
        tableView = new TableView<>();
        setupTab();
    }

    private void setupTab() {
        VBox vbox = new VBox();
        String[] cols = layoutResources.getString("TurtleTabView").split(",");
        for (String col : cols) {
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

    private void addUneditableColumn(TableColumn c, String col) {
        c.setCellValueFactory(new PropertyValueFactory<ImmutableTurtle, Object>(col));
    }

    private void addCheckBox(TableColumn c, String col) {
        c.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ImmutableTurtle, Boolean>, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ImmutableTurtle, Boolean> param) {
                        return new SimpleBooleanProperty(param.getValue().getShowing() == 1);
                    }
                });
        CheckBoxTableCell cell = new CheckBoxTableCell();
        c.setCellFactory(cell.forTableColumn(c));
        tableView.setOnMouseClicked(e -> editTurtleState(cell));
    }

    private void editTurtleState(CheckBoxTableCell c) {
        ImmutableTurtle t = tableView.getSelectionModel().getSelectedItem();
        if (t != null) {
            String status = t.getShowing() == 1 ? "pu" : "pd";
            actions.handleTurtleState(t.getX() + "", status);
        }
    }
    /**
     * returns Tab for states of turtles
     * @return myTab
     */
    public Tab getTab() {
        return myTab;
    }
    /**
     * reset table after each run with states of each turtle
     * @param turtle list of turtles
     */
    public void setTable(ImmutableTurtle turtle) {
        List<ImmutableTurtle> list = List.of(turtle);
        tableView.setItems(FXCollections.observableList(list));
    }
}
