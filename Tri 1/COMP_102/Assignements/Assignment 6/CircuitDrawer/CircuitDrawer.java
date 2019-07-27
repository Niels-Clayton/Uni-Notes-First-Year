// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2018T1
* Name:
* Username:
* ID:
*/

import ecs100.*;
import java.awt.Color;

public class CircuitDrawer{
    private String buttonText = "Horizontal";
    private int clickCounter = 0;
    private String currentAction;
    private String rotation = "horizontal";
    private double clickX;
    private double clickY;
    private String label;
    private double size = 45;

    // fields to store data:
    //  - the tool that the user has selected (which control what component
    //     will be drawn by the mouse)
    //    The tools are "resistor", "capacitor", "source", "wire", "label", or "eraser"
    //  - the mode: whether the component should be horizontal or vertical
    //  - the contents of the label
    //  - the position the mouse was pressed,
    /*# YOUR CODE HERE */

    //Constructor
    public CircuitDrawer(){
        this.setupGUI();
    }

    /** Sets up the user interface - mouselistener, buttons, and (completion only) textField */
    public void setupGUI(){
        UI.setMouseListener( this::doMouse );
        UI.addButton("Reset", this::clearGraphics);
        UI.addButton("Resistor", this::doSetResistor);
        UI.addButton("capacitor", this::doSetCapacitor);
        UI.addButton("Source", this::doSetSource);
        UI.addButton("Draw Wire", this::doSetWire);
        UI.addTextField("lable", this::doSetLabel);
        UI.addButton("Rotate", this::doSwitchDirection);
        UI.addSlider( "Eraser Size", 20,100, 45, this::eraserSize);
        UI.addButton("Eraser", this::doSetEraser);
        UI.drawString(this.buttonText , 20, 20);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(0.0);  // Hide the text area.
    }

    // Methods to change the tool that controls what will be drawn next
    // These methods just save information to the fields.
    /** Respond to the resistor button */

    public void clearGraphics(){
        UI.clearGraphics();
        UI.drawString("Horizontal" , 20, 20);
        this.clickCounter = 0;
    }
    
    public void eraserSize(double size){
        this.size = size;
    }

    public void doSetResistor(){
        this.currentAction = "resistor";
    }
    /** Respond to the capacitor button */
    public void doSetCapacitor(){
        this.currentAction = "capacitor";
    }

    /** Respond to the source button */
    public void doSetSource(){
        this.currentAction = "source";
    }

    /** Respond to the wire button */
    public void doSetWire(){
        this.currentAction = "wire";
    }

    /** Respond to the eraser button */
    public void doSetEraser(){
        this.currentAction = "erase";
    }

    /** Respond to the text field (completion only) */
    public void doSetLabel(String v){
        this.currentAction = "string";
        this.label = v;
    }

    /**
    * Respond to the horiz/vert button (completion only)
    * Switches the mode from horizontal to vertical, or back
    * Ideally, (Challenge) it should show the user what the current state is,
    * eg by drawing a horizonal/vertical bar in the top left corner,
    * or by calling setText on the button to change the label
    */
    public void doSwitchDirection(){
        /*# YOUR CODE HERE */
        this.clickCounter ++;
        if(this.clickCounter %2 == 1)
        {
            UI.eraseString("Horizontal" , 20, 20);
            UI.drawString("Vertical" , 20, 20);
            this.rotation = "vertical";
        }
        else
        {
            UI.eraseString("Vertical" , 20, 20);
            UI.drawString("Horizontal" , 20, 20);
            this.rotation = "horizontal";
        }
    }

    /**
    * Respond to mouse events
    * When pressed, remember the position.
    * When released, draw what is specified by current tool
    * Uses the value stored in the field to determine which kind of component to draw (or to erase)
    *  It should call the drawWire, drawResistor, drawCapacitor, drawSource, drawLabel,
    *  or doErase, methods passing the x and y where the mouse was released.
    */
    public void doMouse(String action, double x, double y) {

        if(currentAction.equals("resistor") && action.equals("released"))
        {
            this.drawResistor(x,y);
        }
        else if(currentAction.equals("capacitor") && action.equals("released"))
        {
            this.drawCapacitor(x,y);
        }
        else if(currentAction.equals("source") && action.equals("released"))
        {
            this.drawSource(x,y);
        }
        else if(currentAction.equals("wire"))
        {
            if(action.equals("pressed"))
            {
                this.clickX = x;
                this.clickY = y;
            }
            else if(action.equals("released"))
            {
                this.drawWire(x,y);
            }
        }
        else if(currentAction.equals("erase")&& action.equals("released"))
        {
            this.doErase(x,y);
        }
        else if(currentAction.equals("string") && action.equals("released"))
        {
            this.drawLabel(x,y);
        }
    }

    /**
    * Draw a resistor centered at the point x, y.
    * (either a thin rectangle with short wires, or a zig-zag.)
    * Core: only horizontal required
    * Completion: horizontal or vertical depending on the mode.
    */
    public void drawResistor(double x, double y){
        double length = 30;    // size in the longer  dimension
        double width = 10;     // size in the shorter dimension
        double stub = 10;      // the length of the wires on each end

        if(this.rotation.equals("horizontal"))
        {
            UI.drawRect(x-(length/2), y-(width/2), length, width);
            UI.drawLine(x-stub-(length/2), y, x-(length/2), y);
            UI.drawLine(x+stub+(length/2), y, x+(length/2), y);
        }
        else
        {
            UI.drawRect(x-(width/2), y-(length/2), width, length);
            UI.drawLine(x, y-stub-(length/2), x, y-(length/2));
            UI.drawLine(x, y+stub+(length/2), x, y+(length/2));
        }
        //UI.drawLine(x-5-stub, y-2, x+5+stub, y-2);
        //UI.drawImage(this.resImage, x-5, y-15, width, length);

    }

    /**
    * Draw a capacitor centered at the point x, y.
    *  (Two parallel lines with wires on each side)
    * Core: only horizontal required
    * Completion: horizontal or a vertical, depending on the mode.
    */
    public void drawCapacitor(double x, double y){
        double gap = 6;
        double length = 26;
        double stub = 10;

        if(this.rotation.equals("horizontal"))
        {
            UI.drawLine(x-gap/2, y-length/2, x-gap/2, y+length/2);
            UI.drawLine(x+gap/2, y-length/2, x+gap/2, y+length/2);
            UI.drawLine(x-(gap/2)-stub, y, x-gap/2, y);
            UI.drawLine(x+(gap/2)+stub, y, x+gap/2, y);
        }
        else
        {
            UI.drawLine(x-length/2, y-gap/2, x+length/2, y-gap/2);
            UI.drawLine(x-length/2, y+gap/2, x+length/2, y+gap/2);
            UI.drawLine(x, y-(gap/2)-stub, x, y-gap/2);
            UI.drawLine(x, y+(gap/2)+stub, x, y+gap/2);
        }
    }

    /**
    * Draw a source centered at the point x, y.
    *  (Circle with wires on each side)
    * Core: only horizontal required
    * Completion: horizontal or vertical, depending on the mode.
    */
    public void drawSource(double x, double y){
        double radius = 34;
        double stub = 10;
        UI.drawOval(x-radius/2, y-radius/2, radius, radius);
        if(this.rotation.equals("horizontal"))
        {
            UI.drawLine(x-radius/2, y, x-(radius/2)-stub, y);
            UI.drawLine(x+radius/2, y, x+(radius/2)+stub, y);
        }
        else
        {
            UI.drawLine(x, y-radius/2, x, y-(radius/2)-stub);
            UI.drawLine(x, y+radius/2, x, y+(radius/2)+stub);
        }
    }

    /**
    * Draw a wire from the point where the user pressed the mouse to the point x,y.
    * Core: may be a straight line.
    * Completion: The wire should have a horizontal part followed by a vertical part
    * If the distance between the two points is very small, it just puts a circle at (x y)
    */
    public void drawWire(double x, double y){
        if(((this.clickX - x)<10)&&((this.clickX - x> -10)) && ((this.clickY - y)<10)&&((this.clickY - y> -10)))
        {
            UI.drawOval(x-3,y-3,6,6);
        }
        else
        {
            UI.drawLine(this.clickX, this.clickY, x, this.clickY);
            UI.drawLine(x, this.clickY, x, y);
        }
    }

    /**
    * Erase a circular region around the position x, y
    * Should be big enough to erase a single component.
    */
    public void doErase(double x, double y){
        double radius = this.size;
        UI.eraseOval(x-radius/2, y-radius/2, radius, radius);
    }

    /**
    * Draw a label at position x, y.  Always horizontal.
    * Uses the label that was stored in a field.
    * Completion only.
    */
    public void drawLabel(double x, double y){
        double length = this.label.length();
        UI.drawString(this.label,x-((length/2)*6),y+6);

    }

    // Main:  constructs a new CircuitDrawer object
    public static void main(String[] arguments){
        new CircuitDrawer();
    }

}
