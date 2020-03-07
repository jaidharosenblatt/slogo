package slogo.view.tabdisplay;

import javafx.collections.*;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;
import slogo.Model.Explorers.Variables.Variable;
import slogo.Model.Parsing.LanguageConverter;

import java.util.ResourceBundle;

public class VariableView <E>{
    private ObservableList<Variable> variables;
    private Tab myTab;
    private TableView<Variable> tableView;
    private LanguageConverter languageConverter;
    private static final String RESOURCE_PATH = "resources/UI/";
    private static ResourceBundle resourceBundle;
    public VariableView(LanguageConverter language, ObservableList variableList){
        resourceBundle = ResourceBundle.getBundle(RESOURCE_PATH + language.getLanguage());
        variables=variableList;
        myTab = new Tab(resourceBundle.getString("VariableTab"));
        this.tableView = new TableView<Variable>(variables);
        languageConverter = language;
        setupTab();
    }


    /**
     * returns Tab for Variables
     * @return myTab
     */
    public Tab getTab(){
        return myTab;
    }

    private void setupTab() {
        VBox vbox = new VBox();
        Button clear = makeClearButton();
        setupTableView();
        vbox.getChildren().addAll(clear, tableView);
        vbox.setAlignment(Pos.CENTER);
        myTab.setContent(vbox);
    }
    private void setupTableView() {
        TableColumn variableNameCol = new TableColumn(resourceBundle.getString("VariableTab"));
        TableColumn valueCol = new TableColumn(resourceBundle.getString("Value"));
        variableNameCol.setCellValueFactory(new PropertyValueFactory<Variable,String>("Name"));
        valueCol.setCellValueFactory(new PropertyValueFactory<Variable,E>("Value"));

        valueCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        valueCol.setOnEditCommit(e-> editVariable((TableColumn.CellEditEvent)e));
        tableView.setEditable(true);
        tableView.setItems(variables);
        tableView.getColumns().addAll(variableNameCol, valueCol);
        tableView.refresh();
    }

    private void editVariable(TableColumn.CellEditEvent<Variable, E> e) {
        Variable v = tableView.getSelectionModel().getSelectedItem();
        v.setValue(e.getNewValue());
    }

    private Button makeClearButton() {
        Button clearButton = new Button(resourceBundle.getString("ClearButton"));
        clearButton.setOnAction(e->emptyVariables());
        return clearButton;
    }

    private void emptyVariables() {
        if (!variables.isEmpty()) {
            variables.clear();
        }
    }
}
