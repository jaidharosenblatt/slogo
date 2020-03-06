package slogo.view.components;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import slogo.view.Actions;
import slogo.xml.Chooser;

import java.io.File;

public class LoadXML extends Component {
    private File dataFile;

    public LoadXML(String language, String key, Actions actions){
        super(language, key, actions);
        Button button = new Button(getPromptFromKey());
        button.getStyleClass().add("terminal-button");
        button.setOnAction(handler -> handleLoadXML());
        getChildren().add(button);
    }

    private void handleLoadXML() {
        dataFile = Chooser.FILE_CHOOSER.showOpenDialog(new Stage());
        handleAction(dataFile.getPath());
    }
}
