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

/**
* A wire is a connection between two points
* It is drawn as a horizontal line from the first point
*   followed by a vertical line to the second point.
*   (If the points are (x1,y1) and (x2, y2), the mid point is (x1,y2))
* It needs a constructor
* It needs methods to
*  draw the wire
*  highlight the wire
*  determine if a point (x,y) is on the wire (or close enough to the wire)
*  rotate the wire (exchanging start and end points)
*  move the wire.
*/

//Hint: to see if (x,y) is close enough to a wire:
//  check it is within the bounding box of the wire
//  (ie, x is between the left side and the right side, and
//       y is between the top and the bottom of the wire)
//  then check if y is within THRESHOLD of the horizonal part of the wire (y1) OR
//                x is within THRESHOLD of the vertical part of the wire (x2)



public class Wire {
    public static final double THRESHOLD = 10; // distance from the line that counts as "on"

    private double x1;  // initial click of the mouse
    private double y1;
    private double x2;  // release of the mouse
    private double y2;
    private boolean highlight;
    private Color col = Color.black;

    public Wire(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void draw_wire(){

        UI.setColor(col);

        if(Math.abs(this.x1-this.x2)<= 7 && Math.abs(this.y1-this.y2)<= 7){
            UI.drawOval(this.x1-4,this.y1-4,8,8);
        }
        else{
            UI.drawLine(this.x1, this.y1, this.x2, this.y1);
            UI.drawLine(this.x2, this.y1, this.x2, this.y2);
        }
    }

    public void highlight_wire(){
        this.col = Color.green;
        this.draw_wire();
        this.col = Color.black;
    }

    public boolean on_wire(double x, double y){
        if(((((this.x1 - this.x2) < 0) && (x<=this.x2) && (x>=this.x1)) &&
           (((this.y1 - this.y2) < 0) && (y<=this.y2) && (y>=this.y1)) &&
           ((y < y1 + this.THRESHOLD) && (y > y1 - this.THRESHOLD)) ||
           ((x < x2 + this.THRESHOLD) && (x > x2 - this.THRESHOLD))) ||

           ((((this.x1 - this.x2) < 0) && (x<=this.x2) && (x>=this.x1)) &&
           (((this.y1 - this.y2) > 0) && (y>=this.y2) && (y<=this.y1)) &&
           ((y < y1 + this.THRESHOLD) && (y > y1 - this.THRESHOLD)) ||
           ((x < x2 + this.THRESHOLD) && (x > x2 - this.THRESHOLD))) ||

           ((((this.x1 - this.x2) > 0) && (x>=this.x2) && (x<=this.x1)) &&
           (((this.y1 - this.y2) < 0) && (y<=this.y2) && (y>=this.y1)) &&
           ((y < y1 + this.THRESHOLD) && (y > y1 - this.THRESHOLD)) ||
           ((x < x2 + this.THRESHOLD) && (x > x2 - this.THRESHOLD))) ||

           ((((this.x1 - this.x2) > 0) && (x>=this.x2) && (x<=this.x1)) &&
           (((this.y1 - this.y2) > 0) && (y>=this.y2) && (y<=this.y1)) &&
           ((y < y1 + this.THRESHOLD) && (y > y1 - this.THRESHOLD)) ||
           ((x < x2 + this.THRESHOLD) && (x > x2 - this.THRESHOLD)))){
            return true;
        }
        else{
            return false;
        }
    }

    public void move(){
        
    }

    //rotate the wire by swapping beginning and end points
    public void rotate_wire(){
        double x = this.x1;
        double y = this.y1;
        this.x1 = this.x2;
        this.y1 = this.y2;
        this.x2 = x;
        this.y2 = y;
    }

    /* String of details of the Wire, for saving to a file or for debugging. */
    public String toString(){
        return "wire " +this.x1+" "+this.y1+" "+this.x2+" "+this.y2+" ";
    }
}
