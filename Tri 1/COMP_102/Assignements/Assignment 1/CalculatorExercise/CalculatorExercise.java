// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Exercise for COMP102 - 2018T1
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

/** Program for calculating various things
 * Some methods convert between two units
 * Other methods perform other simple calculations 
 */

public class CalculatorExercise{

    /** Ask for miles then convert to kilometers */
    final double CONVERSION = 1.60934;
    public void milesToKilometers(){
        double Miles = UI.askDouble("what is the distance in miles");
        double Kilometers = Miles * CONVERSION;
        UI.println(Miles+" Miles is equal too "+Kilometers+" Kilometers");
    }   

    /** Ask for base and height, then calculate area of triangle
     */
    public void triangleArea(){
        double base=UI.askDouble("what is the base length of the triangle");
        double height=UI.askDouble("what is the height of the triangle");
        double area=base*height/2;
        UI.println("The area of your triangle is "+area);

    }

    /** Ask for grams then convert to ounces*/
    final double convert = 0.03;
    public void gramsToOunces(){
        double gram=UI.askDouble("How many grams do you have");
        double Ounces=gram*convert;
        UI.println(gram+" gram's is "+Ounces+" ounces");

    }

    /** Ask for radius, and then compute surface of a sphere */
    public void surfaceAreaOfSphere(){
        double radius=UI.askDouble("what is the radius of the circle?");
        double area=4*3.141*(radius*radius); 
        UI.println("the area of the sphere is "+area);
        

    }

    /** Ask for kelvin then convert to Fahrenheit */
    public void kelvinToFahrenheit(){
        /*# YOUR CODE HERE */

    }

    /** Ask for number of pizzas and cost per pizza, then compute cost of order of pizzas */
    public void costOfPizzas(){
        /*# YOUR CODE HERE */

    }

}
