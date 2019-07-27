import lang.stride.*;
import ecs100.*;
import java.util.*;
import java.io.*;

/**
 * EarthquakeAnalyser Analyses data about a collection of 4335 NZ earthquakes from May 2016 to May 2017 Each line of the file "earthquake-data.txt" has a description of one earthquake: ID time longitude	latitude magnitude depth region Data is from http://quakesearch.geonet.org.nz/ Note bigearthquake-data.txt has just the 421 earthquakes of magnitude 4.0 and above which may be useful for testing, since it is not as big as the full file.
 * Core:  two methods: loadData Loads the data from a file into a field containing an ArrayList of Earthquake objects. Hint : to make an Earthquake object, read one line from the file and pass it as the argument to the Earthquake constructor Make sure any previous values in the field are removed findBigOnes Lists all the earthquakes in the ArrayList that have a magnitude 5.5 and over. Hint: see the methods in the Earthquake class, especially getMagnitude and toString
 * Completion: one method: findPairs Compares each Earthquake in the list with every other Earthquake and finds every pair of "close" earthquakes - earthquakes that - are within 1km of each other, and - have depths within 1km of each other, and - are separated by at least 24 hours days For each pair, prints - their ID's, - the distance between them - the depth difference - the number of days between them.
 * Challenge: one method findFollowOns; Constructs a new ArrayList containing every earthquake with a magnitude that is at least 6.0 For each such earthquake on this list - finds a list of all the "follow-on" earthquakes: later earthquakes within a distance of 10km and depth difference <= 10km. - If there are at least two follow-on earthquakes, then it prints out the full details of the big earthquake followed by ID, magnitude and days since the big one for each follow-on earthquake
 * The file "example-output.txt" has sample output for the "bigearthquake-data.txt" file, which only contains earthquakes with magnitude 4 and above.
 */
public class EarthquakeAnalyser extends Earthquake
{
    /* This program is copyright VUW. You are granted permission to use it to construct your answer to a COMP102 assignment. You may not distribute it in any other way without permission. Code for COMP102 - 2018T1 Name: Username: ID:*/
    private ArrayList<Earthquake> earthquakes =  new  ArrayList < Earthquake > ();
    private ArrayList<Earthquake> bigQuakes =  new  ArrayList < Earthquake > ();
    private Earthquake quake;

    /**
     * Construct a new EarthquakeAnalyser object and initialise the interface
     */
    public EarthquakeAnalyser()
    {
        this.setupGUI();
    }

    /**
     * 
     */
    public void setupGUI()
    {
        UI.initialise();
        UI.addButton("Load", this :: loadData);
        UI.addButton("Big ones", this :: findBigOnes);
        UI.addButton("Pairs", this :: findPairs);
        UI.addButton("Follow-ons", this :: findFollowOns);
        UI.addButton("Quit", UI :: quit);
        UI.setDivider(1.0);
        /* text pane only*/
    }

    /* Load data from the data file into the earthquakes field*/

    /**
     * 
     */
    public void loadData()
    {
        String filename = UIFileChooser.open("Data File");
        try {
            Scanner scan =  new  Scanner( new  File(filename));
            while (scan.hasNext()) {
                String data = scan.nextLine();
                Earthquake info =  new  Earthquake(data);
                earthquakes.add(info);
            }
        }
        catch (IOException e) {
            UI.printf("File Failure %s \n", e);
        }
        UI.printf("Loaded %d earthquakes into list\n", this.earthquakes.size());
        UI.println("----------------------------");
        Trace.println(this.earthquakes.get(1));
    }

    /**
     * Print details of all earthquakes with a magnitude of 5.5 or more
     */
    public void findBigOnes()
    {
        int count = 0;
        UI.println("Earthquakes 5.5 and above");
        for (final Earthquake quake : this.earthquakes) {
            if (quake.getMagnitude() >= 5.5) {
                UI.println(quake.toString());
                count = count + 1;
            }
        }
        UI.printf("there were %d earthquakes with a magnitude greater than 5.5\n", count);
        UI.println("------------------------");
    }

    /**
     * Print all pairs of earthquakes within 1km of each other and within 1km depth from each other and separated by at least 1 day;
     */
    public void findPairs()
    {
        UI.println("Close earthquakes");
        int i = 0;
        while (i < earthquakes.size()) {
            this.quake = earthquakes.get(i);
            int j = i + 1;
            while (j < this.earthquakes.size()) {
                if (this.quake.distanceTo(earthquakes.get(j)) <= 1 && this.quake.timeBetween(earthquakes.get(j)) >= 1440 && Math.abs(this.quake.getDepth() - earthquakes.get(j).getDepth()) <= 1) {
                    UI.printf("%s and %s: distance=%1.2f, depth diff=%1.2f, days apart=%3.0f\n", this.quake.getID(), earthquakes.get(j).getID(), this.quake.distanceTo(earthquakes.get(j)), Math.abs(this.quake.getDepth() - earthquakes.get(j).getDepth()), (this.quake.timeBetween(earthquakes.get(j))) / 1440);
                }
                j = j + 1;
            }
            i = i + 1;
        }
        UI.println("----------------------------");
    }

    /**
     * Constructs a new ArrayList containing every earthquake with a magnitude that is at least 6 For each earthquake on this list - finds a list of all the "follow-on" earthquakes: later earthquakes within a distance of 10km and depth difference <= 10km. - If there are at least two follow-on earthquakes, then it prints out the full details of the big earthquake followed by ID, magnitude and days since the big one for each follow-on earthquake
     */
    public void findFollowOns()
    {
        UI.println("Big earthquakes and their follow-on earthquakes");
        int i = 0;
        while (i < earthquakes.size()) {
            if (earthquakes.get(i).getMagnitude() > 6) {
                bigQuakes.add(earthquakes.get(i));
            }
            i = i + 1;
        }
        int j = 0;
        while (j < bigQuakes.size()) {
            int k = 0;
            while (k < earthquakes.size()) {
                if (bigQuakes.get(j).distanceTo(earthquakes.get(k)) <= 10 && Math.abs(bigQuakes.get(j).getDepth() - earthquakes.get(k).getDepth()) <= 10 && bigQuakes.get(j).timeBetween(earthquakes.get(k)) < 0) {
                }
                k = k + 1;
            }
            j = j + 1;
        }
        UI.println("-------------------------------------");
    }

    /**
     * 
     */
    static public void main(String[] arguments)
    {
        EarthquakeAnalyser obj =  new  EarthquakeAnalyser();
    }
}
