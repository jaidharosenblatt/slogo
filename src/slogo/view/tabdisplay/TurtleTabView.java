package slogo.view.tabdisplay;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import slogo.Model.Parsing.LanguageHandler;
import slogo.Model.TurtleModel.ImmutableTurtle;
import slogo.ReflectionException;
import slogo.view.Actions;

import java.lang.reflect.Method;
import java.util.*;

public class TurtleTabView {
    private ResourceBundle resourceBundle;
    private ResourceBundle actionResources;
    private ResourceBundle layoutResources;
    private static final String RESOURCES_PATH = "resources/UI/";
    private static final String RESOURCE_LAYOUTS = RESOURCES_PATH + "Layouts";
    private static final String METHODS_PATH = RESOURCES_PATH + "ReflectionMethods";
    private Map<Double, ImmutableTurtle> allTurtles;
    private LanguageHandler languageHandler;
    private Tab myTab;
    private Actions actions;
    private TableView<ImmutableTurtle> tableView;

    public TurtleTabView(LanguageHandler language, Actions actions) {
        languageHandler = language;
        resourceBundle = ResourceBundle.getBundle(RESOURCES_PATH + language.getLanguage());
        layoutResources = ResourceBundle.getBundle(RESOURCE_LAYOUTS);
        actionResources = ResourceBundle.getBundle(METHODS_PATH);
        myTab = new Tab(resourceBundle.getString("TurtleTab"));
        this.actions = actions;
        allTurtles = new HashMap<>();
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
                        return new SimpleBooleanProperty((int)(param.getValue().getActive()) == 1);
                    }
                });
        CheckBoxTableCell cell = new CheckBoxTableCell();
        c.setCellFactory(cell.forTableColumn(c));
        tableView.setOnMouseClicked(e -> editTurtleState(cell));
    }

    private void editTurtleState(CheckBoxTableCell c) {
        LanguageHandler l = new LanguageHandler("English");
        ImmutableTurtle t = tableView.getSelectionModel().getSelectedItem();
        if (t != null) {
            String status = t.getShowing() == 1 ? "0.0" : "1.0";

            //actions.handleTurtleState(t.getID() + "", l.getLanguageCommand("tell"));
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
     * @param turtleList list of turtles
     */
    public void setTable(Map<Double, List<ImmutableTurtle>> turtleList) {
        List<ImmutableTurtle> completedTurtles = new ArrayList<>() ;
        List<ImmutableTurtle> inactiveTurtles = new ArrayList<>();
        int max = findMaxSteps(turtleList);
        inactiveTurtles = findInactiveTurtles(turtleList);
        for (int j =0; j<max; j++){
            tableView.getItems().clear();
            for (double i: turtleList.keySet()){
                if (turtleList.get(i).size() -1 == j) {
                    completedTurtles.add(turtleList.get(i).get(j));
                    allTurtles.putIfAbsent(i, turtleList.get(i).get(j));
                }
                else if (max < turtleList.get(i).size())
                    tableView.getItems().add(turtleList.get(i).get(j));
            }
            for (ImmutableTurtle t: completedTurtles)
                tableView.getItems().add(t);
            for (ImmutableTurtle t: inactiveTurtles){
                tableView.getItems().add(t);
            }
        }
    }

    private List<ImmutableTurtle> findInactiveTurtles(Map<Double, List<ImmutableTurtle>> turtleList) {
        List<ImmutableTurtle> inactive = new ArrayList<>();
        for (Double i: allTurtles.keySet()){
            System.out.println(allTurtles.get(i).isActive());
            if (!turtleList.containsKey(i))
                inactive.add(allTurtles.get(i));
        }
        return inactive;
    }

    private int findMaxSteps(Map<Double, List<ImmutableTurtle>> turtleList) {
        int max = -1;
        for (double i: turtleList.keySet()){
            if (turtleList.get(i).size()> max)
                max = turtleList.get(i).size();
        }
        return max;
    }

}
