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

/** DroppingBall represents a ball that falls towards the ground.
 *    Each time the step() method is called, it will take one step.
 * For the Challenge part, the ball bounces when it reaches the ground.
 */

public class DroppingBall{
    // Fields to store
    //   the state of the ball:  x, height, stepX, stepY, colour
    //   other constants for the ball: size, position of the ground
    /*# YOUR CODE HERE */

    public static final int GROUND = 450;   // ground level.
    public static final int SIZE = 30;      // diameter of the ball
    private double y;
    public double x;
    private double speed;
    private Color col;
    private double count;
    private double yValue = this.y;
    private double xValue;
    private double xTotal;

    // Constructor
    /** Construct a new DroppingBall object.
     *  Parameters are the initial x position, the height above the ground,
     *  the initial speed to the right (ie, the horizontal step size), and
     *  the colour.
     *  Stores the parameters into fields (computing the y position from the height)
     *  and initialises the other fields,
     */
    public DroppingBall(double x, double h, double s, Color c)
    {
        this.x = x;
        this.y = h;
        this.speed = s;
        this.col = c;
    }

    /** Return the height of the ball above the ground */
    public double getHeight(){

        double height = this.GROUND - this.yValue - this.SIZE;
        return height;
    }

    public double getWidth(){
        double width = this.x;
        return width;
    }
    // Methods
    /** Move the ball one step.
     *  Changes its height and x position using the vertical and horizonal steps
     *  If it would go below ground, then change its y position to ground level
     */
    public void step(){
        this.x = this.x + this.speed;
        //I want to model a porabola, therefor the y value of the ball must be a function of x
        //y = -x^2 +c where c is the y position of the turning point, therefor
        //c = the initial y value (this.y)
        this.xValue = this.xValue + this.speed;
        double xPosition = this.xValue -this.xTotal;
        this.yValue = (0.01*(Math.pow(xPosition,2)))+this.y;
    }

    //  xTotal stores the length from the vertex of the porabola to the x-intercept,
    //  as this will need to remain constant, it is only measured once, hence the loop.
    //  resets the xValue, so that when the porabola reaches it's +x-intercept it is
    //  drawn again from it's -x-intercept.
    public void bounce(){
        if(this.count < 1)
        {
            this.xTotal = this.x;
            this.count++;
        }
        this.xValue = 0;

    }


    /** Draw the ball on the Graphics Pane in its current position */
    public void draw(){
        UI.setColor(this.col);
        UI.fillOval(this.x, this.yValue, this.SIZE, this.SIZE);
    }
}
