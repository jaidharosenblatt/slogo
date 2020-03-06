package slogo.view.components;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import slogo.ReflectionException;
import slogo.view.Actions;

public class ComponentFactory {

  private static final String RESOURCES_CLASS_PATH = "resources/UI/ReflectionClass";
  private static final ResourceBundle promptBundle = ResourceBundle.getBundle(RESOURCES_CLASS_PATH);
  private static final String CLASS_PATH = "slogo.view.components.";

  public Component getComponent(String language, String key, Actions actions) {
    try {
      System.out.println(promptBundle.getString(key));
      Class<?> clazz = Class.forName(CLASS_PATH + promptBundle.getString(key));
      Constructor<?> constructor = clazz
          .getDeclaredConstructor(String.class, String.class, Actions.class);
      Component component = (Component) constructor.newInstance(language, key, actions);
      return component;
    } catch (ClassNotFoundException e) {
      throw new ReflectionException("InvalidClass", key);
    } catch (NoSuchMethodException e) {
      throw new ReflectionException("InvalidMethod", key);
    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
      e.printStackTrace();
      throw new ReflectionException("InvalidInstantiation", key);
    }

  }

}
