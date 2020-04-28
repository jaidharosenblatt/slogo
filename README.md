Simple Logo (Slogo) Parser
====

This project implements a development environment that helps users write SLogo programs.

Names:Milan Bhat (mb554), Ryan Weeratunga (rkw14), Robert Chen (rec43), Jaidha Rosenblatt (jrr59)

![alt text](https://i.imgur.com/g0QiPA6.png "Flower")
![alt text](https://i.imgur.com/YoZ56A6.png
 "Multiple turtles")

### Timeline

Start Date: 2/13

Finish Date: 3/7

Hours Spent: 320 total

### Primary Roles
* Jaidha Rosenblatt - Front End
    * Primary Responsibilities
        * Styles
        * SettingView
        * Components package (actions, component, factory, many objects)
        * SettingTab
        * TurtleManager
        * TurtleView
        * Turtle (gui)
        * Trail (gui)
        * Terminal
        * Implementing listener design pattern
    * Secondary Responsibilities
        * History
        * HistoryView
        * Variable
        * VariableExplorer
        * VariableExplorerView
        * Method
* Robert Chen - Controller
    * Primary Responsibilities
        * Controller
    * Secondary Responsibilities
        * Parsing
        * Command + subclasses
        * Error Handling
        * Turtle
        * Pen
        * Trail
 * Milan Bhat - Backend
     * Primary Responsibilities
         * Parsing
         * Command + subclasses
         * Error Handling
         * Turtle
         * Pen
         * Trail
     * Secondary Responsibilities
         * Controller
 * Ryan Weeratunga - Tabs and Explorers
     * Primary Responsibilities
         * History
         * HistoryView
         * Variable
         * VariableExplorer
         * VariableExplorerView
         * Method
     * Secondary Responsibilities
         * PenView
         * TurtleView
         * TrailView
         * Visualizer
         * Console
         * UserInterface
         * Display

### Running the Program

Main class: slogo.Main.java, mark /resources as resources root

Data files needed: Everything under resources/ and slogo/resources/

Features implemented:
* Basic commands for moving the turtle, math operations, boolean operations, turtle queries, variables, and control structures
* User defined commands
* Error catching for parsing issues
* Seeing results of turtle executing commands
* Set background color, pen color, pen thickness, turtle image from UI and from commands
* See current variables, user-defined commands, color palette, current turtle states, and history in different tabs
* Choose language to operate in
* Changing language can happen dynamically and will change history and user-defined commands as well
* Accessing a help page
* Clicking to execute commands
* Clicking to load history
* Moving the turtles graphically
* Change pen properties graphically
* Recognize and correctly perform multiple turtle commands
* Recognize and correctly perform display commands
* Recognize and correctly perform commands with unlimited parameters

### Notes/Assumptions

Assumptions or Simplifications:

* With multiple turtles, if a command is run that involves parsing again, such as Repeat, the internal parsing command will be run on each turtle a number of times equal to number of active turtles.
* Assume no issues with properties files
* Always need at least one turtle active


Interesting data files:

Known Bugs:
* Clear screen command does not work clear since it is overridden by parser passing immutable turtles

Extra credit:
* Dark mode
* Slider for changing GUI turtle movement