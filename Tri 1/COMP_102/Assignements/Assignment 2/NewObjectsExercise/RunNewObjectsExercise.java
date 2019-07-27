// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Exercise for COMP102 - 2018T1
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

/** Run NewObjectsExercise methods   */

public class RunNewObjectsExercise{

    public static void main(String[] arguments){
        NewObjectsExercise noe = new NewObjectsExercise();
        UI.initialise();
        UI.addButton("Clear", UI::clearPanes );
        UI.addButton("Flowers", noe::growFlowers );
        UI.addButton("Balloons", noe::flyBalloons );
        UI.addButton("Cars", noe::driveCars );
        UI.addButton("Quit", UI::quit );
    }
}
