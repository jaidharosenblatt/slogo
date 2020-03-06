package slogo.Model.Parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LanguageConverter {
    private Map<Pattern, String> oldCommands;
    private Map<Pattern, String> newCommands;
    private ResourceBundle myOldLanguageResources;
    private ResourceBundle myNewLanguageResources;
    private String oldLanguage;
    private static final String RESOURCES = "resources";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private static final String COMMAND_NAME_RESOURCES = "languages";
    private static final String DEFAULT_COMMAND_NAME_PACKAGE = COMMAND_NAME_RESOURCES + ".";
    private static final String DEFAULT_COMMAND_NAME_RESOURCE_PACKAGE = DEFAULT_RESOURCE_PACKAGE + DEFAULT_COMMAND_NAME_PACKAGE;

    public LanguageConverter(String oldLanguage) {
        updateLanguage(oldLanguage);
    }

    public String translateString(String command, String newLanguage) {
        initializeSecondLanguage(newLanguage);
        String[] splits = command.split("\\s");
        splits = command.trim().split("\\s+");
        for (int i = 0; i < splits.length; i++) {
            for (Pattern key: oldCommands.keySet())
            if (match(splits[i], key)) {
                String commandString = oldCommands.get(key);
                for (Pattern newString: newCommands.keySet()){
                    if (match(commandString, newString))
                        splits[i] = newCommands.get(newString).split("\\|")[0];
                }

            }

        }
        String result = String.join(" ", splits);
        return result;
    }

//    public String getCommand(String command) {
//        String result = "";
//        command = command.trim();
//        for (Pattern key : oldCommands.keySet())
//            if (match(command, key))
//                result = oldCommands.get(key);
//        return result;
//    }

    public void updateLanguage(String language) {
        myOldLanguageResources = ResourceBundle.getBundle(DEFAULT_COMMAND_NAME_RESOURCE_PACKAGE + language);
        initializeMap();
        oldLanguage=language;
    }

//    private void initializeMap() {
//        oldCommands = new HashMap<>();
//        for (String key : myOldLanguageResources.keySet()) {
//            String val = myOldLanguageResources.getString(key);
//            String[] options = val.split("\\|");
//            for (String option : options) {
//                oldCommands.put(Pattern.compile(option), key);
//            }
//        }
//    }

    private void initializeSecondLanguage(String newLanguage) {
        myNewLanguageResources = ResourceBundle.getBundle(DEFAULT_COMMAND_NAME_RESOURCE_PACKAGE + newLanguage);
        newCommands = new HashMap<>();
        for (String key : myNewLanguageResources.keySet()) {
            String val = myNewLanguageResources.getString(key);
            newCommands.put(Pattern.compile(key), val);
        }
    }

    private void initializeMap() {
        oldCommands = new HashMap<>();
        for (String key : myOldLanguageResources.keySet()) {
            String val = myOldLanguageResources.getString(key);
            oldCommands.put(Pattern.compile(val), key);
        }
    }

    public String getCommandOfficialName(String command){
        for (Pattern key : oldCommands.keySet()) {
            if(match(command, key)) {
                return oldCommands.get(key);
            }
        }
        return null;
    }
    public String getLanguage(){
        return oldLanguage;
    }
    private boolean match(String text, Pattern regex) {
       return  regex.matcher(text).matches();
    }
}
