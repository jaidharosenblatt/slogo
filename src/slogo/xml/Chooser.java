package slogo.xml;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

public class Chooser {
    public static final String DATA_FILE_EXTENSION = "*.xml";
    public static final FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);

    public Chooser() {}

    public static void showMessage (Alert.AlertType type, String message) {
        new Alert(type, message).showAndWait();
    }

    private static FileChooser makeChooser (String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
        // start searching for files in data folder
        result.setInitialDirectory(new File(System.getProperty("user.dir") + File.separator + "data" + File.separator + "xml"));
        result.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Text Files", extensionAccepted));
        return result;
    }
}
