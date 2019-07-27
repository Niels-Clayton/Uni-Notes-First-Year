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

/** Runs a simulation of balls falling to the ground.
 *      creates two DroppingBalls
 *  Repeatedly
 *     makes each ball take a step until it has got to the ground
 *     then make a new ball and starts the outer loop again.
 */

public class BallDropper{

    public BallDropper(){
        this.setupGUI();
    }

    public void setupGUI(){
        UI.addButton("drop", this::dropTwoBalls);
        UI.addButton("drop & bounce", this::dropAndBounce);
        UI.addButton("Quit", UI::quit);
        UI.setWindowSize(850,500);
        UI.setDivider(0);
    }

    public void dropTwoBalls(){
        UI.drawLine(0,DroppingBall.GROUND, 800 ,DroppingBall.GROUND);    // the ground
        DroppingBall ballA = this.makeNewBall();
        DroppingBall ballB = this.makeNewBall();
        ballA.draw();
        ballB.draw();

        while (true){
            // make them step
            ballA.step();
            ballB.step();

            // redraw the balls in their new position and pause
            UI.clearGraphics();
            UI.setColor(Color.black);
            UI.drawLine(0,DroppingBall.GROUND, 800 ,DroppingBall.GROUND);    // the ground

            ballA.draw();
            ballB.draw();

            if (ballA.getHeight() <= 0) {      // if at ground edge, make new one
                ballA = this.makeNewBall();
            }
            if (ballB.getHeight() <= 0) {      // if at right edge, make new one
                ballB = this.makeNewBall();
            }

            UI.sleep(40); // pause of 40 milliseconds
        }
    }

    /**
        Challenge:
        Make the balls bounce. the initial path of the ball can follow the same path,
        but once the 'this.getHeight' method returns the x value must be reset to 0,
        and the coeffecient of x must be multiplied by 3/4 in order to give it slowly
        decreasing bounces.
    */
    public void dropAndBounce(){

        UI.drawLine(0,DroppingBall.GROUND, 800 ,DroppingBall.GROUND);    // the ground
        DroppingBall ballA = this.makeNewBall();
        DroppingBall ballB = this.makeNewBall();
        ballA.draw();
        ballB.draw();

        while (true){

            ballA.step();
            ballB.step();

            if (ballA.getHeight() <= 0) {
                ballA.bounce();
            }

            if (ballB.getHeight() <= 0) {
                ballB.bounce();
            }

            UI.clearGraphics();
            UI.setColor(Color.black);
            UI.drawLine(0,DroppingBall.GROUND, 800 ,DroppingBall.GROUND);

            ballA.draw();
            ballB.draw();


            if ( ballA.getWidth() > 800) {
                ballA = this.makeNewBall();
            }
            if ( ballB.getWidth() > 800) {
                ballB = this.makeNewBall();
            }

            UI.sleep(40); // pause of 40 milliseconds
        }
    }

    /** Helper method that makes a new DroppingBall with random initial values */
    public DroppingBall makeNewBall(){
        double initHeight = 100 + Math.random()*300; // random height between 100 and 400.
        double xSpeed = 3.5 + Math.random()*5;       // random step size between 2 and 5.
        Color col = Color.getHSBColor((float)Math.random(),1,1);
        return new DroppingBall(0 , initHeight, xSpeed, col);
    }


    // Main
    /** Create a new BallDropper object and call bounceAround */
    public static void main(String[] arguments){
        BallDropper bouncer = new BallDropper();
    }

}
