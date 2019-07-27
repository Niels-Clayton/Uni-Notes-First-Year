// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Exercise for COMP102 - 2018T1
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

/** Interface to run Conditionals Exercise methods   */

public class RunConditionalsExercise{

    public static void main(String[] args){
        ConditionalsExercise ce = new ConditionalsExercise();
        UI.initialise();
        UI.addButton("Clear", UI::clearPanes );
        UI.addButton("validHour", ce::validHour);
        UI.addButton("wordGame", ce::wordGame);
        UI.addButton("drawAFlag", ce::drawAFlag);
        UI.addButton("longestWord", ce::longestWord);
        UI.addButton("Quit", UI::quit );
        UI.setDivider(0.3);
    }	
}
