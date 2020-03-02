package slogo.Model;


import slogo.Model.Commands.Command;
import slogo.Model.Commands.ControlStructures.UserDefinedInstructionCommand;
import slogo.SlogoRuntimeException;

import java.util.List;

/**
 * Interface containing methods that are intended to be used between different components of the Backend
 */
public interface BackEndInternal {

    /**
     * Update the Turtle based on a given command
     * @param command is the command to use as instructions
     * @throws SlogoRuntimeException if the command cannot be executed
     */
    public void updateTurtle(Command command) throws SlogoRuntimeException;

    /**
     * Update the Pen based on a given command
     * @param command is the command to use as instructions
     * @throws SlogoRuntimeException if the command cannot be executed
     */
    public void updatePen(Command command) throws SlogoRuntimeException;

    /**
     * Update the Trail based on a given command
     * @param command is the command to use as instructions
     * @throws SlogoRuntimeException if the command cannot be executed
     */
    public void updateTrail(Command command) throws SlogoRuntimeException;

    /**
     * Creates a variable based on a command
     * @param command the command to use to create a variable
     */
    public void createVariable(Command command);

    /**
     * Create a Method from a list of Commands
     * A Methdod is a group of commands that can be called once and executed in order
     * @param commandList
     * @return a Method with the group of commands
     */
    public UserDefinedInstructionCommand createMethod(List<Command> commandList);

    /**
     *Update the history that contains past actions
     */
    public void updateHistory();

}