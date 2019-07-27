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
import java.util.*;
import java.io.*;

/** RunPaintCalculator   */
public class RunPaintCalculator{
    public static void main(String[] arguments){
        PaintCalculator pc = new PaintCalculator();
        UI.initialise();
        UI.addButton("Core", pc::calculatePaintCore ); 
        UI.addButton("Completion", pc::calculatePaintCompletion );
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1);    // Expand the Text pane
    }	
}
