package slogo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Turtle {

    private static final String RESOURCES = "resources";
    private static final String METHOD_RESOURCES = "CommandMethodNames";
    public static final String DEFAULT_METHOD_RESOURCE_PACKAGE = RESOURCES + "/" + METHOD_RESOURCES + ".";
    private static final String TURTLE_METHODS_PROPERTIES = "turtleMethods";
    private static final String TURTLE_METHODS_FILEPATH = DEFAULT_METHOD_RESOURCE_PACKAGE + TURTLE_METHODS_PROPERTIES;
    private ResourceBundle myResources;


    private int myX;
    private int myY;
    private int myHeading;
    private int penState;
    private int showing;

    public Turtle(){
        myX = 0;
        myY = 0;
        myHeading = 0;
        penState = 1;
        showing = 1;
        myResources = ResourceBundle.getBundle(TURTLE_METHODS_FILEPATH);
    }

    public void actOnCommand(Command command) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] classParts = command.getClass().toString().split("\\.");
        String key = classParts[classParts.length - 1].replace("Command", ""); //TODO change command to be generalized
        String methodName = myResources.getString(key);
        Method[] t = this.getClass().getMethods();
        Method method = this.getClass().getDeclaredMethod(methodName, command.getClass());
        method.invoke(this, command);


    }

    private void moveForward(ForwardCommand forward){
        myX+= forward.getPixelsForward();
    }

    public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }

    public int getHeading() {
        return myHeading;
    }

    public int getPenState(){
        return penState;
    }

    public int getShowing(){
        return showing;
    }
}