package slogo.view;

import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Pair;
import slogo.Model.Commands.ControlStructures.UserDefinedInstructionCommand;
import slogo.Model.Parsing.LanguageConverter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class MethodView {
    private static ResourceBundle resourceBundle;
    private LanguageConverter languageConverter;
    private Tab myTab;
    private ObservableMap<String, UserDefinedInstructionCommand> methods;
    private ListView<String> listView;
    private List<PropertyChangeListener> listener;
    private Actions actions;
    public MethodView(LanguageConverter language, ObservableMap savedMethodNames, Actions actions){
        languageConverter = language;
        methods = savedMethodNames;
        resourceBundle = ResourceBundle.getBundle("resources/UI/" + language.getLanguage());
        this.actions = actions;
        listView = new ListView<String>();
        listener = new ArrayList<>();
        myTab = new Tab(resourceBundle.getString("MethodTab"));
        setupTab();
    }

    private void setupTab() {
        VBox vbox = new VBox();
        Button clearButton = makeClearButton();
        setupListView();
        vbox.getChildren().addAll(clearButton, listView);
        vbox.setAlignment(Pos.CENTER);
        myTab.setContent(vbox);
    }

    private void setupListView() {
        methods.addListener((MapChangeListener.Change<? extends String, ? extends UserDefinedInstructionCommand> c)-> handle(c));
        listView.setOnMouseClicked(e -> displayMethod());
    }

    private void displayMethod() {
        String s = listView.getSelectionModel().getSelectedItem();
        String str = s.replace("\t", " ");
        int space = str.indexOf(" ");
        String methodName = str.substring(0, space);
        String methodArgs = str.substring(str.indexOf("(")+1, str.indexOf(")"));
        int parameters = Integer.parseInt(methodArgs);
        if (parameters > 0 ) {
            String display = determineDisplay(methodName, parameters, str.substring(str.indexOf("[")));
            displayDialogBox(methodName, display, parameters);
        }
        else
            setTerminal(methodName, null);
    }

    private void displayDialogBox(String methodName, String display, int parameters) {
        VBox vbox = createInputFields(parameters);
        Dialog dialog = getDialog(display, vbox);
        Optional<List<String>> result = dialog.showAndWait();
        if (result.isPresent()){
            setTerminal(methodName, result.get());
        }
    }

    private Dialog getDialog(String display, VBox vbox) {
        Dialog dialog = new Dialog();
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        //TODO make not hard coded
        dialog.setTitle("planning");
        dialog.setHeaderText(display);
        dialog.getDialogPane().setContent(vbox);
        setupInput(vbox, dialog);
        return dialog;
    }

    private void setupInput(VBox vbox, Dialog dialog) {
        dialog.setResultConverter(new Callback<ButtonType, List<String>>() {
            @Override
            public List<String> call(ButtonType b) {
                if (b == ButtonType.OK) {
                    List<String> parameters = new ArrayList<>();
                    for (Node n: vbox.getChildren()){
                        TextField f = (TextField) n;
                        parameters.add(f.getText());
                    }
                    return parameters;
                }
                return null;
            }
        });
    }

    private VBox createInputFields(int parameters) {
        VBox vbox = new VBox();
        for (int i =0; i<parameters; i++){
            TextField input = new TextField();
            int j = i+1;
            //TODO make not hard coded
            input.setPromptText("Parameter "+j);
            vbox.getChildren().add(input);
        }
        vbox.setSpacing(10);
        return vbox;
    }

    private void setTerminal(String methodName, List<String> parameters) {
        String display = methodName;
        if (parameters != null){
            for (String arg: parameters)
                display+=" "+arg;
        }

        actions.handleMethodDisplay(display);
    }


    private String determineDisplay(String name, int parameters, String command){
        String result = name+ " [ ";
        for (int i=0; i< parameters; i++){
            result +="? ";
        }
        result += "] ";
        result+= command;
        return result;
    }
    private void handle(MapChangeListener.Change<? extends String,? extends UserDefinedInstructionCommand> c) {
        if (c.wasAdded()){
            String str = "("+methods.get(c.getKey()).getNumArguments()+") ["+methods.get(c.getKey()).getCommands()+"]";
            listView.getItems().add(c.getKey()+"\t"+str);
        }
        else if (c.wasRemoved()){
            listView.getItems().remove(c.getKey());
        }
    }


    private Button makeClearButton() {
        Button clearButton = new Button(resourceBundle.getString("ClearButton"));
        clearButton.setOnAction(e->clearSavedMethods());
        return clearButton;
    }

    private void clearSavedMethods() {
        if (!methods.isEmpty()){
            methods.clear();
            listView.getItems().clear();
        }
    }
    public Tab getTab(){ return myTab;}

}
