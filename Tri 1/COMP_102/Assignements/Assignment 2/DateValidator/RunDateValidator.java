// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2018T1
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

/** Run DateValidator methods */

public class RunDateValidator {

    public static void main(String[] args){
        DateValidator dv = new DateValidator();
        UI.initialise();
        UI.addButton("Clear", UI::clearText );
        UI.addButton("Validate Date Core", dv::doCore );
        UI.addButton("Validate Date Completion", dv::doCompletion );
        UI.addButton("Quit", UI::quit );
        UI.setDivider(1);       // Expand the text area
    }
}
