// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2018T1
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.util.*;
import java.awt.Color;

/**
 * Class representing components (resistors, capacitors, sources, or connectors).
 * Components have methods
 *   redraw the component
 *   highlight the component
 *   determine if a point (x,y) is on the component
 *   rotate the component (between horizontal and vertical)
 *   move the component to a new position
 */
public class Component{

    // fields
    private String type;  // "resistor" or "capacitor" or "source" or "connector"
    private double x;
    private double y;
    private boolean horizontal = true;
    private String label = "";
    private double length = 30;
    private double width = 11;

    /**
     * Construct a new Component, specifying the type and the position
     */
    public Component(String type, double x, double y){
	this.x = x;
	this.y = y;
        this.type = type;
        if (type.equals("resistor")){
            this.length = 30;
            this.width = 11;
        }
        else if (type.equals("capacitor")){
            this.length = 21;
            this.width = 21;
        }
        else if (type.equals("source")){
            this.length = 44;
            this.width = 30;
        }
        else if (type.equals("connector")){
            this.length = 6;
            this.width = 6;
        }
    }

    /**
     * Construct a new Component, specifying the type, position, direction, and label
     */
    public Component(String type, double x, double y, boolean horiz, String label){
	this.x = x;
	this.y = y;
        this.type = type;
        this.horizontal = horiz;
        this.label = label;
        if (type.equals("resistor")){
            this.length = 30;
            this.width = 11;
        }
        else if (type.equals("capacitor")){
            this.length = 21;
            this.width = 21;
        }
        else if (type.equals("source")){
            this.length = 44;
            this.width = 30;
        }
        else if (type.equals("connector")){
            this.length = 6;
            this.width = 6;
        }
    }

    public double x_position(){
        return this.x;
    }
    public double y_position(){
        return this.y;
    }


    /**
     * Returns true if the point (x, y) is on top of the component
     */
    public boolean on(double x, double y){
	double dist = Math.hypot(x-this.x, y-this.y);
	return dist < this.length/2;
    }

    /**
     * Changes the position of the component to (x, y)
     */
    public void moveTo(double x, double y){
	this.x = x;
	this.y = y;
    }


    /**
     * Draws the component on the graphics pane.
     */
    public void redraw(){
        if (this.horizontal) {
            UI.drawImage(this.type+"-horizontal.png", this.x-this.length/2,
                         this.y-this.width/2, this.length, this.width);
        }
        else {
            UI.drawImage(this.type+"-vertical.png", this.x-this.width/2,
                         this.y-this.length/2, this.width, this.length);
        }
	if (this.label.length()>0){
	    UI.drawString(this.label, this.x+1, this.y-this.length/2+3);
	}
    }

    /**
     * Highlights the component on the graphics pane by drawing a green circle arount it.
     */
    public void highlight(){
	UI.setColor(Color.green);
	UI.drawOval(this.x-this.length/2-2, this.y-this.length/2-2, this.length+4, this.length+4);
	UI.setColor(Color.black);
    }

    /**
     * Rotates the component between horizontal and vertical
     */
    public void rotate(){
	this.horizontal = ! this.horizontal;
    }

    /**
     * Add a label to the component
     */
    public void setLabel(String str){
	if (str!=null){
	    this.label = str;
	}
    }

    /** Returns a string description of the component in a form suitable for
     *  writing to a file in order to reconstruct the component later
     */
    public String toString(){
	return this.type+" "+this.x +" "+ this.y +" "+ this.horizontal +" "+ this.label;
    }

}
