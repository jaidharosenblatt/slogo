package slogo.Model.TurtleModel;

import java.util.ArrayList;
import java.util.List;

public class Turtle implements ImmutableTurtle{
    private static final double DEFAULT_PEN_THICKNESS = 10;
    private double myX;
    private double myY;
    private double myHeading;
    private double myPenState;
    private double myShowing;
    private double myIsActive;
    private double myPenThickness;
    private double myPenColorIndex;
    private double myTurtleImageIndex;
    private double myId;

    public Turtle(int id){
        this(0, 0, 0, 1, 1, 1, DEFAULT_PEN_THICKNESS, 0,0, id);
    }

    public Turtle(ImmutableTurtle originalTurtle){
        this.myX = originalTurtle.getX();
        this.myY = originalTurtle.getY();
        this. myHeading = originalTurtle.getHeading();
        this.myPenState = originalTurtle.getPenState();
        this.myShowing = originalTurtle.getShowing();
        this.myIsActive = originalTurtle.isActive();
        this.myPenThickness = originalTurtle.getPenThickness();
        this.myPenColorIndex = originalTurtle.getPenColorIndex();
        this.myTurtleImageIndex = originalTurtle.getTurtleImageIndex();
        this.myId = originalTurtle.getID();
    }

    public Turtle(double x, double y, double heading, double penState, double showing, double isActive,
                           double penThickness, double penColorIndex, double turtleImageIndex, double id){
        this.myX = x;
        this.myY = y;
        this.myHeading = heading;
        this.myPenState = penState;
        this.myShowing = showing;
        this.myIsActive = isActive;
        this.myPenThickness = penThickness;
        this.myPenColorIndex = penColorIndex;
        this.myTurtleImageIndex = turtleImageIndex;
        this.myId = id;
    }

    public ImmutableTurtle getImmutableTurtle(){
        return new Turtle(this);
    }

    public void setX(double x) {
        myX = x;
    }

    public void setY(double y) {
        myY = y;
    }

    public void setToHome() {
        myX = 0;
        myY = 0;
    }

    public void setHeading(double heading) {
        myHeading = heading;
    }

    public void setPenState(int state) {
        myPenState = state;
    }

    public void setShowing(int showing) {
        myShowing = showing;
    }

    public void setIsActive(double isActive) {
        myIsActive = isActive;
    }

    public void setPenColorIndex(double myPenColorIndex) {
        this.myPenColorIndex = myPenColorIndex;
    }

    public void setPenThickness(double myPenThickness) {
        this.myPenThickness = myPenThickness;
    }

    public void setTurtleImageIndex(double myTurtleImageIndex) {
        this.myTurtleImageIndex = myTurtleImageIndex;
    }

    public void incrementX(double addX){
        myX+=addX;
    }

    public void incrementY(double addY){
        myY+=addY;
    }

    public void incrementHeading(double turn){
        myHeading+=turn;
    }

    @Override
    public double getX() {
        return myX;
    }

    @Override
    public double getY() {
        return myY;
    }

    @Override
    public double getHeading() {
        return myHeading;
    }

    @Override
    public double getPenState() {
        return myPenState;
    }

    @Override
    public double getShowing() {
        return myShowing;
    }

    @Override
    public double isActive() {
        return myIsActive;
    }

    @Override
    public double getPenColorIndex() {
        return myPenColorIndex;
    }

    @Override
    public double getPenThickness() {
        return myPenThickness;
    }

    @Override
    public double getTurtleImageIndex() {
        return myTurtleImageIndex;
    }

    @Override
    public double getID() {
        return myId;
    }
}
