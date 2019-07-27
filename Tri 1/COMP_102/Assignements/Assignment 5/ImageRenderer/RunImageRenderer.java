// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2018T1
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

public class RunImageRenderer{

    public static void main(String[] args){
        ImageRenderer ir = new ImageRenderer();
        UI.initialise();
        UI.addButton("Clear", UI::clearGraphics );
        UI.addButton("Render (core)", ir::renderImageCore );
        UI.addButton("Render (compl)", ir::renderAnimatedImage );
        UI.addButton("Render (chal)", ir::renderChallengeImages );
        UI.addButton("Quit", UI::quit );
        UI.setWindowSize(850, 700);
        UI.setDivider(0.0);
    }
}
