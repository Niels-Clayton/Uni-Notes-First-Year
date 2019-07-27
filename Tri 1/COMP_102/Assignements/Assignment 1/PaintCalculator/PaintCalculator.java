// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2018T1
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

/** Program for calculating amount of paint required to paint a room */

public class PaintCalculator{

    public static final double DOOR_HEIGHT = 2.1;        // Height of the doors
    public static final double DOOR_WIDTH = 0.8;         // Width of the doors
    public static final double SQ_METERS_PER_LITRE = 15.0; // Area covered by 1 litre of paint

    /** Calculates and prints litres of paint needed to paint a room
     *  with four walls (excluding the doors, floor, and ceiling)
     */
    public void calculatePaintCore(){
    double roomwidth = UI.askDouble("What is the width of the room? ");
    double roomlength = UI.askDouble("What is the Length of the room? ");
    double roomheight = UI.askDouble("What is the height of the room? ");
    double doors = UI.askDouble("How many doors in the room? ");
    double roomarea = ((roomwidth*roomheight*2+roomlength*roomheight*2)-(DOOR_WIDTH*DOOR_HEIGHT*doors));
    double paint=roomarea/SQ_METERS_PER_LITRE;
    UI.println ("You will need "+ paint +" L of paint");


    }

    /** Calculates and prints litres of paint needed to paint
     *  - the four walls of a room (excluding the doors and windows)
     *  - the ceiling (different type of paint)
     */
    public void calculatePaintCompletion(){
      double roomwidth = UI.askDouble("What is the width of the room? ");
      double roomlength = UI.askDouble("What is the Length of the room? ");
      double roomheight = UI.askDouble("What is the height of the room? ");
      double doors = UI.askDouble("How many doors in the room? ");
      double windows = UI.askDouble("How many windows in the room?");
      double winheight = UI.askDouble("What is the height of the windows?");
      double winwidth = UI.askDouble("What is the width of the windows?");
      double roomarea = ((roomwidth*roomheight*2+roomlength*roomheight*2)-((DOOR_WIDTH*DOOR_HEIGHT*doors)-(winwidth*winheight*windows)));
      double paint=roomarea/SQ_METERS_PER_LITRE;
      double ceiling=roomwidth*roomheight;
      double ceilingpaint=ceiling/SQ_METERS_PER_LITRE;
      UI.println ("You will need "+ paint +"L of paint for the walls, and "+ceilingpaint+"L of paint for the ceiling.");

    }

}
