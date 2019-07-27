// This program is copyright VUW.

import ecs100.*;
import java.awt.Color;
import java.awt.Color;
import java.util.*;

import java.awt.Color;
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2018T1
 * Name:
 * Username:
 * ID:
 */

import java.awt.Color;

/** The program contains several methods for analysing the readings of the temperature levels over the course of a day.
 *  There are several things about the temperature levels that a user may be interested in:
 *    The average temperature level.
 *    How the temperatures rose and fell over the day.
 *    The maximum and the minimum temperature levels during the day.
 */
public class TemperatureAnalyser{

    /* analyse reads a sequence of temperature levels from the user and prints out
     *    average, maximum, and minimum level and plots all the levels
     *    by calling appropriate methods
     */
    public void analyse(){
        UI.clearPanes();
        ArrayList<Double> listOfNumbers = UI.askNumbers(" Enter levels");
        if (listOfNumbers.size() != 0) {
            this.printAverage(listOfNumbers);
            this.plotLevels(listOfNumbers);

            UI.printf("Maximum level was:  %2.2f\n", this.maximumOfList(listOfNumbers));
            UI.printf("Minimum level was:  %2.2f\n", this.minimumOfList(listOfNumbers));
        }
        else {
            UI.println("No readings");
        }
    }

    /** Print the average level
     *   - There is guaranteed to be at least one level,
     *   - The method will need a variable to keep track of the sum, which
     *     needs to be initialised to an appropriate value.
     *  CORE
     */
    public void printAverage(ArrayList<Double> listOfNumbers) {

        double total=0.0;
        int count = 0;
        for (count = 0; count < listOfNumbers.size() ; count++) {
            total = total + listOfNumbers.get(count);
        }
        // Use %f for double's and float's, and %d for integers.
        UI.printf("the average temperature is %2.2f\n",total/count);
    }

    /**
     * Plot a bar graph of the sequence of levels,
     * using narrow rectangles whose heights are equal to the level.
     * [Core]
     *   - Plot the bars.
     * [Completion]
     *   - Draws a horizontal line for the x-axis (or baseline) without any labels.
     *   - Any level greater than 400 should be plotted as if it were just 400, putting an
     *         asterisk ("*") above it to show that it has been cut off.
     * [Challenge:]
     *   - The graph should also have labels on the axes, roughly every 50 pixels.
     *   - The graph should also draw negative temperature levels correctly.
     *   - Scale the y-axis and the bars so that the largest numbers and the smallest just fit on the graph.
     *     The numbers on the y axis should reflect the scaling.
     *   - Scale the x-axis so that all the bars fit in the window.
     */
    public void plotLevels(ArrayList<Double> listOfNumbers)
    {
        int base = 420;              //base of the graph
        int left = 50;               //left of the graph
        int step = 25;               //distance between plotted points
        int top = 20;                //distance from top of screen
        int count = 0;
        double scale = 0;
        double label_int = 0;

        if (this.minimumOfList(listOfNumbers)>=0)
        {
            scale = this.maximumOfList(listOfNumbers);
            for (count = 0; count < listOfNumbers.size() ; count++)
            {
                UI.setColor(Color.decode("#58db1a"));

                if (listOfNumbers.get(count)>400)
                {
                    UI.fillRect(((left*count)+75), (base-400), 25, 400);
                    UI.drawString("*",((left*count)+85),top);
                }
                else
                {
                    UI.fillRect(((left*count)+75), (base-((400/scale)* listOfNumbers.get(count))), 25, ((400/scale)* listOfNumbers.get(count)));
                }
            }
        }

        else if (this.maximumOfList(listOfNumbers)<0)
        {
            scale = -1*(this.minimumOfList(listOfNumbers));
            for (count = 0; count < listOfNumbers.size() ; count++)
            {
                UI.setColor(Color.decode("#58db1a"));

                if (listOfNumbers.get(count)<-300)
                {
                    UI.fillRect(((left*count)+75), base, 25, 300);
                    UI.drawString("*", ((left*count)+85), base+320);
                }
                else
                {
                    UI.fillRect(((left*count)+75), (base), 25, ((300/scale)* (-1*listOfNumbers.get(count))));
                }
            }
        }

        else
        {

            if (this.maximumOfList(listOfNumbers)>=(-1*(this.minimumOfList(listOfNumbers))))
            {
                //scale = (this.maximumOfList(listOfNumbers)-(this.minimumOfList(listOfNumbers)));
                if (this.maximumOfList(listOfNumbers)>400)
                {
                    scale = 400;
                    for (count = 0; count < listOfNumbers.size() ; count++)
                    {
                        UI.setColor(Color.decode("#58db1a"));
                        if (listOfNumbers.get(count)>400)
                        {
                            UI.fillRect(((left*count)+75), (base-400), 25, 400);
                            UI.drawString("*",((left*count)+85),top);
                        }
                        else if (listOfNumbers.get(count)>0 &&listOfNumbers.get(count)<=400)
                        {
                            UI.setColor(Color.decode("#58db1a"));
                            UI.fillRect(((left*count)+75), (base-((400/scale)* listOfNumbers.get(count))), 25, ((400/scale)* listOfNumbers.get(count)));
                        }
                        else if (listOfNumbers.get(count)<-300)
                        {
                            UI.setColor(Color.decode("#58db1a"));
                            UI.fillRect(((left*count)+75), base, 25, 300);
                            UI.drawString("*", ((left*count)+85), base+320);
                        }
                        else
                        {
                            UI.setColor(Color.decode("#58db1a"));
                            UI.fillRect(((left*count)+75), (base), 25, ((400/scale)* (-1*listOfNumbers.get(count))));
                        }
                    }
                }
                else
                {
                    scale = this.maximumOfList(listOfNumbers);
                    for (count = 0; count < listOfNumbers.size() ; count++)
                    {
                        UI.setColor(Color.decode("#58db1a"));
                        if (listOfNumbers.get(count)>400)
                        {
                            UI.fillRect(((left*count)+75), (base-400), 25, 400);
                            UI.drawString("*",((left*count)+85),top);
                        }
                        else if (listOfNumbers.get(count)>0 &&listOfNumbers.get(count)<=400)
                        {
                            UI.setColor(Color.decode("#58db1a"));
                            UI.fillRect(((left*count)+75), (base-((400/scale)* listOfNumbers.get(count))), 25, ((400/scale)* listOfNumbers.get(count)));
                        }
                        else if (listOfNumbers.get(count)<-300)
                        {
                            UI.setColor(Color.decode("#58db1a"));
                            UI.fillRect(((left*count)+75), base, 25, 300);
                            UI.drawString("*", ((left*count)+85), base+320);
                        }
                        else
                        {
                            UI.setColor(Color.decode("#58db1a"));
                            UI.fillRect(((left*count)+75), (base), 25, ((400/scale)* (-1*listOfNumbers.get(count))));
                        }
                    }
                }
            }
            else
            {
                if (this.minimumOfList(listOfNumbers)<-300)
                {
                    scale = 300;
                    for (count = 0; count < listOfNumbers.size() ; count++)
                    {
                        UI.setColor(Color.decode("#58db1a"));

                        if (listOfNumbers.get(count)>400)
                        {
                            UI.fillRect(((left*count)+75), (base-400), 25, 400);
                            UI.drawString("*",((left*count)+85),top);
                        }
                        else if (listOfNumbers.get(count)>0)
                        {
                            UI.fillRect(((left*count)+75), (base-((300/scale)* listOfNumbers.get(count))), 25, ((300/scale)* listOfNumbers.get(count)));
                        }
                        else if (listOfNumbers.get(count)<-300)
                        {
                            UI.fillRect(((left*count)+75), base, 25, 300);
                            UI.drawString("*", ((left*count)+85), base+320);
                        }
                        else
                        {
                            UI.fillRect(((left*count)+75), (base), 25, ((300/scale)* (-1*listOfNumbers.get(count))));
                        }
                    }
                }
                else
                {
                    scale = -1*(this.minimumOfList(listOfNumbers));
                    for (count = 0; count < listOfNumbers.size() ; count++)
                    {
                        UI.setColor(Color.decode("#58db1a"));

                        if (listOfNumbers.get(count)>300)
                        {
                            UI.fillRect(((left*count)+75), (base-300), 25, 300);
                            UI.drawString("*",((left*count)+85),top);
                        }
                        else if (listOfNumbers.get(count)>0)
                        {
                            UI.fillRect(((left*count)+75), (base-((300/scale)* listOfNumbers.get(count))), 25, ((300/scale)* listOfNumbers.get(count)));
                        }
                        else if (listOfNumbers.get(count)<-300)
                        {
                            UI.fillRect(((left*count)+75), base, 25, 300);
                            UI.drawString("*", ((left*count)+85), base+320);
                        }
                        else
                        {
                            UI.fillRect(((left*count)+75), (base), 25, ((300/scale)* (-1*listOfNumbers.get(count))));
                        }
                    }
                }
            }
        }

        for (int x = 0; x<15 ; x++)
        {
            UI.setColor(Color.black);
            UI.drawLine(left,base,(((count*step)*2)+50)+left,base);     // draws the x-axis based off how many bars there will be
            UI.drawLine(left, top ,left, base+300);

            UI.drawLine(left-3, (top+(50*x)), left, (top+(50*x)));            // draws the notiches along the y-axis
        }

        if (-1*(this.minimumOfList(listOfNumbers))<=this.maximumOfList(listOfNumbers))
        {
            if (this.maximumOfList(listOfNumbers)>400)
            {
                label_int = 400;
                for (double x = 0; x<=14 ; x++ )
                {
                    double labels = label_int - (x*(label_int/8));
                    String number_axis = String.format("%3.1f", labels);
                    UI.drawString(number_axis, (left-50), (top+(50*x)));
                }
            }
            else
            {
                label_int = this.maximumOfList(listOfNumbers);
                for (double x = 0; x<=14 ; x++ )
                {
                    double labels = label_int - (x*(label_int/8));
                    String number_axis = String.format("%3.1f", labels);
                    UI.drawString(number_axis, (left-50), (top+(50*x)));
                }
            }

        }
        else
        {
            if (this.minimumOfList(listOfNumbers)<-300)
            {
                label_int = -300;
                for (double x = 0; x<=14 ; x++ )
                {
                    double labels = label_int - (x*(label_int/6));
                    String number_axis = String.format("%3.1f", labels);
                    UI.drawString(number_axis, (left-45), (720-(50*x)));
                }
            }
            else
            {
                label_int = (this.minimumOfList(listOfNumbers));
                for (double x = 0; x<=14 ; x++ )
                {
                    double labels = label_int - (x*(label_int/6));
                    String number_axis = String.format("%3.1f", labels);
                    UI.drawString(number_axis, (left-45), (720-(50*x)));
                }
            }

        }
    }



    /** Find and return the maximum level in the list
     *   - There is guaranteed to be at least one level,
     *   - The method will need a variable to keep track of the maximum, which
     *     needs to be initialised to an appropriate value.
     *  COMPLETION
     */
    public double maximumOfList(ArrayList<Double> listOfNumbers)
    {
        double max = -1000000;  //Can't use, Double.MIN_VALUE, as the smalles value if can give is 0.0, and I need negative numbers.
        int count = 0;
        for (count = 0; count < listOfNumbers.size() ; count++)
        {
            if (listOfNumbers.get(count) >= max)
            {
                max = listOfNumbers.get(count);
            }
        }
        return max;
    }

    /** Find and return the minimum level in the list
     *   - There is guaranteed to be at least one level,
     *   - The method will need a variable to keep track of the minimum, which
     *     needs to be initialised to an appropriate value.
     *  COMPLETION
     */
    public double minimumOfList(ArrayList<Double> listOfNumbers)
    {
        double min =Double.MAX_VALUE;
        int count = 0;
        for (count = 0; count < listOfNumbers.size() ; count++)
        {
            if (listOfNumbers.get(count) <= min)
            {
                min = listOfNumbers.get(count);
            }
        }
        return min;
    }
}
