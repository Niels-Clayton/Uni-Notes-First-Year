// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2018T1
* Name:
* Username:
* ID:
*/

import ecs100.*;
import java.io.*;
import java.util.*;
import java.awt.Color;

/**
* This is related to your program from assignment 3 which analysed temperature levels
* However, instead of reading data from the user, it reads the data from
* a file into an ArrayList.
* It can also cope with much larger sets of numbers.
* The numbers are guaranteed to be integers but the values can be
*   negative and the signal swings above and below zero.
*
* The methods you are to complete all focus on the ArrayList of data.
* CORE
*  read:              reads numbers into an ArrayList.
*  display:           displays the waveform.
*  showSpread:        displays the spread with two horizontal lines and returns its value.
* COMPLETION
*  displayDistortion: shows in red the distorted part of the signal.
*  highlightPeaks:    plots the peaks with small green circles.
*  normalise:         normalises all the values down so there is no distortion.
* CHALLENGE
*  doEnvelope:        displays the upper envelope.
*  save:              saves the current waveform values into a file.
*  select and edit:   let the user select a regions of the waveform with the mouse
*                     to remove them.  Add a save button to save the edited values.
*/

public class WaveformAnalyser{
    // Fields
    private ArrayList<Double> waveform;   // the field to hold the ArrayList of values
    private ArrayList<Double> peaks;
    private ArrayList<Integer> Xpeak;

    // Constant: the threshold for the distortion level
    public static final double THRESHOLD = 200;

    // Constants: the dimensions of the graph for the display method
    public static final int GRAPH_LEFT = 10;
    public static final int ZERO_LINE = 300;

    // Constant fields holding the size of the circles for the highlightPeaks method
    public static final int SIZE_CIRCLE = 10;

    /** Constructor:
    */
    public WaveformAnalyser(){
        this.setupGUI();
    }

    /** Set up the nine buttons and mouselistener */
    public void setupGUI(){
        //core
        UI.addButton("Read Data", this::read);
        UI.addButton("Display Waveform", this::display);
        UI.addButton("Spread", this::showSpread);
        //completion
        UI.addButton("Display Distortion", this::displayDistortion);
        UI.addButton("Peaks", this::highlightPeaks);
        UI.addButton("Normalise", this::normalise);
        //challenge
        UI.addButton("Envelope", this::doEnvelope);
        UI.addButton("Save", this::save);
        UI.addButton("Quit", UI::quit);
        UI.setMouseListener(this::doMouse);   // only for challenge
    }

    /**
    * [CORE]
    * Clears the panes,
    * Creates an ArrayList stored in a field, then
    * Asks user for a waveform file (eg waveform1.txt)
    * Reads data from the file into the ArrayList
    */
    public void read(){
        int i = 0;
        double point1 = 0;
        double point2 = 0;
        double point3 = 0;
        UI.clearPanes();
        this.waveform = new ArrayList<Double>();
        this.peaks = new ArrayList<Double>();
        this.Xpeak = new ArrayList<Integer>();
        String filename = UIFileChooser.open();
        try
        {
            Scanner scan = new Scanner (new File(filename));
            while(scan.hasNext())
            {
                double value = scan.nextDouble();
                waveform.add(value);
            }
        }
        catch (IOException e){UI.println("File failure:" + e);}

        UI.println(this.waveform.size()+" data points have been read from " + filename);

        for (i = 1; i < this.waveform.size()-1; i++)
        {
            point1 = this.waveform.get(i-1);
            point2 = this.waveform.get(i);
            point3 = this.waveform.get(i+1);
            if(point2>point1 && point2>point3)
            {
                peaks.add(this.waveform.get(i));
                Xpeak.add(i);
            }
        }
    }

    /**
    * [CORE]
    * Displays the waveform as a line graph,
    * The n'th value in waveform is displayed at
    *    x-position is GRAPH_LEFT + n
    *    y-position is ZERO_LINE - the value
    * Plots a line graph of all the points with a blue line between
    *  each pair of adjacent points
    * Draw the horizontal line representing the value zero.
    * Uses GRAPH_LEFT and ZERO_LINE for the dimensions and positions of the graph.
    * Don't worry if the lines go off the window
    */
    public void display(){
        if (this.waveform == null){ //there is no data to display
            UI.println("No waveform to display");
            return;
        }
        UI.clearGraphics();
        UI.setColor(Color.black);
        UI.drawLine(GRAPH_LEFT, ZERO_LINE, GRAPH_LEFT + this.waveform.size() , ZERO_LINE);
        double point1 = 0;
        double point2 = 0;
        int i;
        for (i = 0; i < this.waveform.size()-1; i++){
            point1 = this.waveform.get(i);
            point2 = this.waveform.get(i+1);
            UI.drawLine(GRAPH_LEFT+i, ZERO_LINE-point1, GRAPH_LEFT+(i+1), ZERO_LINE-point2);
        }
    }

    /**
    * [CORE]
    * The spread is the difference between the maximum and minimum values of the waveform.
    * Finds the maximum and minimum values, then
    * Displays the spread by drawing two horizontal lines on top of the waveform:
    *   one green line for the maximum value, and
    *   one red line for the minimum value.
    */
    public void showSpread() {
        if (this.waveform == null){ //there is no data to display
            UI.println("No waveform to display");
            return;
        }
        this.display();
        int i;
        double max = -1000;
        double min = 1000;  // large number so that any number in the array will initally be smaller than it.
        for (i = 0; i < this.waveform.size()-1; i++){
            if (this.waveform.get(i) > max)
            {
                max = this.waveform.get(i);
            }
            else if(this.waveform.get(i) < min)
            {
                min = this.waveform.get(i);
            }
        }
        UI.setColor(Color.green);
        UI.drawLine(GRAPH_LEFT, ZERO_LINE-max, GRAPH_LEFT + this.waveform.size(), ZERO_LINE-max);
        UI.setColor(Color.red);
        UI.drawLine(GRAPH_LEFT, ZERO_LINE-min, GRAPH_LEFT + this.waveform.size(), ZERO_LINE-min);
    }

    /**
    * [COMPLETION]  [Fancy version of display]
    * Display the waveform as a line graph.
    * Draw a line between each pair of adjacent points
    *   * If neither of the points is distorted, the line is BLUE
    *   * If either of the two end points is distorted, the line is RED
    * Draw the horizontal lines representing the value zero and thresholds values.
    * Uses THRESHOLD to determine distorted values.
    * Uses GRAPH_LEFT and ZERO_LINE for the dimensions and positions of the graph.
    * [Hint] You may find Math.abs(int a) useful for this method.
    * You may assume that all the values are between -250 and +250.
    */
    public void displayDistortion() {
        if (this.waveform == null){ //there is no data to display
            UI.println("No waveform to display");
            return;
        }
        UI.clearGraphics();
        UI.setColor(Color.black);
        UI.drawLine(GRAPH_LEFT, ZERO_LINE, GRAPH_LEFT + this.waveform.size() , ZERO_LINE);
        UI.drawLine(GRAPH_LEFT, ZERO_LINE - THRESHOLD, GRAPH_LEFT + this.waveform.size() , ZERO_LINE-THRESHOLD);
        UI.drawLine(GRAPH_LEFT, ZERO_LINE + THRESHOLD, GRAPH_LEFT + this.waveform.size() , ZERO_LINE+THRESHOLD);

        double point1 = 0;
        double point2 = 0;
        int i;
        for (i = 0; i < this.waveform.size()-1; i++){
            point1 = this.waveform.get(i);
            point2 = this.waveform.get(i+1);
            if(Math.abs(point1)>THRESHOLD || Math.abs(point2)>THRESHOLD)
            {
                UI.setColor(Color.red);
                UI.drawLine(GRAPH_LEFT+i, ZERO_LINE-point1, GRAPH_LEFT+(i+1), ZERO_LINE-point2);
            }
            else
            {
                UI.setColor(Color.black);
                UI.drawLine(GRAPH_LEFT+i, ZERO_LINE-point1, GRAPH_LEFT+(i+1), ZERO_LINE-point2);
            }
        }
    }


    /**
    * [COMPLETION]
    * Plots the peaks with small green circles.
    *    A peak is defined as a value that is greater or equals to both its
    *    neighbouring values.
    * Note the size of the circle is in the constant SIZE_CIRCLE
    * You may assume that peaks values differ from their neighbouring points.
    */
    public void highlightPeaks() {
        this.displayDistortion();
        double point1 = 0;
        double point2 = 0;
        double point3 = 0;
        int i;
        for (i = 1; i < this.waveform.size()-2; i++){
            point1 = this.waveform.get(i-1);
            point2 = this.waveform.get(i);
            point3 = this.waveform.get(i+1);
            if((point2>point1 && point2>point3) || (point2<point1 && point2<point3))
            {
                UI.drawOval(GRAPH_LEFT+i-(SIZE_CIRCLE/2), ZERO_LINE-point2-(SIZE_CIRCLE/2), SIZE_CIRCLE, SIZE_CIRCLE);
            }
        }

    }

    /**
    * [COMPLETION]
    * Finds the largest value (positive or negative) in the waveform, and
    * "normalises" all the values - multiplies them by threshold/maximum - so
    * that the largest value is now equal to the distortion threshold.
    * Then redraws the waveform.
    */
    public void normalise() {
        int i;
        double point1 = 0;
        double point2 = 0;
        double point3 = 0;
        double max = -1000;
        double normalise = 0;
        double newValue;
        double newPeak;
        for (i = 0; i < this.waveform.size(); i++){
            if (Math.abs(this.waveform.get(i)) > max)
            {
                max = this.waveform.get(i);
            }
            normalise = THRESHOLD/max;
        }
        for (i = 0; i < this.waveform.size(); i++)
        {
            newValue = this.waveform.get(i)*normalise;
            waveform.set(i, newValue);
        }
        for (i = 0; i < this.peaks.size(); i++)
        {
            newPeak = this.peaks.get(i)*normalise;
            peaks.set(i, newPeak);
        }
        this.displayDistortion(); //use display if displayDistortion isn't complete
    }

    /**
    * [CHALLENGE]
    * Displays the upper envelope with GREEN lines connecting all the peaks.
    *    A peak is defined as a point that is greater or equal to *both* neighbouring points.
    */
    public void doEnvelope(){
        if (this.waveform == null){ //there is no data to display
            UI.println("No waveform to display");
            return;
        }
        this.display();

        double peak1 = 0;
        double peak2 = 0;
        double Xpeak1 = 0;
        double Xpeak2 = 0;
        int i = 0;

        for (i = 0; i< this.peaks.size()-1; i++)
        {
            peak1 = this.peaks.get(i);
            peak2 = this.peaks.get(i+1);
            Xpeak1 = this.Xpeak.get(i);
            Xpeak2 = this.Xpeak.get(i+1);
            UI.drawLine(GRAPH_LEFT + Xpeak1, ZERO_LINE - peak1, GRAPH_LEFT + Xpeak2, ZERO_LINE - peak2);
        }
    }

    /**
    * [CHALLENGE]
    * Saves the current waveform values into a file
    */
    public void save(){
        String fileName = UI.askString("what would you like to call the file?");
        try
        {
            PrintStream out = new PrintStream(new File(fileName+".txt"));
            for(int i = 0; i < this.waveform.size(); i++)
            {
                out.printf("%f \n", this.waveform.get(i));
            }
            out.close();
            UI.printf("The wave form has been saved in the file %s", fileName);
        }
        catch (IOException e) { UI.println("File error:" + e); }
    }

    private int index1;
    /**
    * [CHALLENGE]
    * Lets user select a region of the waveform with the mouse
    * and deletes that section of the waveform.
    */
    public void doMouse(String action, double x, double y){
        /*# YOUR CODE HERE */

    }

    // Main
    public static void main(String[] arguments){
        new WaveformAnalyser();
    }

}
