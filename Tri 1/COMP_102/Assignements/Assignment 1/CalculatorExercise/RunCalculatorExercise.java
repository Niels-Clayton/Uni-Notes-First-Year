// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Exercise for COMP102 - 2018T1
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.awt.Color;

/** RunCalculatorExercise for testing the calculator exercise methods   */
public class RunCalculatorExercise{

    /** Construct a new CalculatorExercise object and initialise the interface */
    public static void main (String[] args){
        CalculatorExercise ce = new CalculatorExercise();
        UI.initialise();
        UI.addButton("milesToKilometers", ce::milesToKilometers );
        UI.addButton("triangleArea", ce::triangleArea );
        UI.addButton("gramsToOunces", ce::gramsToOunces );
        UI.addButton("surfaceAreaOfSphere", ce::surfaceAreaOfSphere );
        UI.addButton("kelvinToFahrenheit", ce::kelvinToFahrenheit );
        UI.addButton("costOfPizzas", ce::costOfPizzas );
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1);   // expands the text area
    }

}
