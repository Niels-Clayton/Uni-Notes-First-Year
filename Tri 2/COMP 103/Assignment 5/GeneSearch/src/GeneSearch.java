// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2018T2, Assignment 5
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.util.*;
import java.io.*;


/** GeneSearch   */

public class GeneSearch{

    
    private List<Character> data;    // the genome data to search in
    private List<Character> pattern; // the pattern to search for
    private String patternString;         // the pattern to search for (as a String)
    private int maxErrors = 1;            // number of mismatching characters allowed
    private Map<String, ArrayList<Integer>> keyMap;
    /**
     * Construct a new GeneSearch object
     */
    public GeneSearch(){
        setupGUI();
        loadData();
    }

    /**
     * Initialise the interface
     */
    public void setupGUI(){
        UI.addTextField("Search Pattern", this::setSearchPattern);
        UI.addButton("ExactSearch", this::exactSearch);
        UI.addButton("Approx Search", this::approximateSearch);
        UI.addSlider("# mismatches allowed", 1, 5, maxErrors,
                     (double n)->{maxErrors = (int)n;});
        UI.addButton("Load map", this::loadMap);
        UI.addButton("Search with map", this::exactSearchMap);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }



    public void setSearchPattern(String v){
        patternString = v.toUpperCase();
        pattern = new ArrayList<Character>();
        for (int i=0; i<v.length(); i++){
            pattern.add(patternString.charAt(i));
        }
        UI.println("Search pattern is now "+ pattern);
    }

    /**
     * Search for all occurrences of the pattern in the data,
     * reporting the position of each occurrence and the total number of matches
     */
    public void exactSearch(){
        if (pattern==null){UI.println("No pattern");return;}
        UI.println("===================\nExact searching for "+patternString);
        long time = System.currentTimeMillis();

        
        int totalMatches = 0;
        for(int i = 0; i < data.size() - pattern.size(); i++){
            int matchingChar = 0;
            for (int j = 0; j < pattern.size(); j++){
                if(pattern.get(j) == data.get(i+j)){
                    matchingChar++;
                }
                else break;
            }
            if(matchingChar == patternString.length()){
                totalMatches++;
                UI.println("Found at "+i+" : "+pattern);
            }
        }
        UI.printf("%d exact matches found in %.3f seconds\n",
                totalMatches, (double)(System.currentTimeMillis()- time)/1000);
    }


    /**
     * Search for all approximate occurrences of the pattern in the data,
     * (pattern is the same as a sub sequence of the data except for at most
     *  maxErrors characters that differ.)
     * Reports the position and data sequence of each occurrence and
     *  the total number of matches
     */    
    public void approximateSearch(){
        if (pattern==null){UI.println("No pattern"); return;}
        UI.println("===================");
        UI.printf("searching for %s, %d mismatches allowed\n", patternString, maxErrors);
        long time = System.currentTimeMillis();
        System.gc();

        int totalMatches = 0;
        for(int i = 0; i < data.size() - pattern.size(); i++){
            int matchingChar = 0;
            int errors = 0;
            for (int j = 0; j < pattern.size(); j++){
                if(pattern.get(j) == data.get(i+j)){
                    matchingChar++;
                }
                else{
                    errors++;
                    if(errors > maxErrors) break;
                }
            }
            if(matchingChar >= patternString.length()-maxErrors){
                totalMatches++;
                UI.println("Found at "+i+" : "+pattern);
            }
        }
        UI.printf("%d matches with at most %d errors found in %.3f seconds\n",
                totalMatches, maxErrors, (double)(System.currentTimeMillis()- time)/1000);

    }


    /**
     * Load gene data from file into ArrayList of characters
     */
    public void loadData(){
        data = new ArrayList<Character>(1000000);
        try{
            Scanner sc = new Scanner(new File("acetobacter_pasteurianus.txt"));
            while (sc.hasNext()){
                String line = sc.nextLine();
                for (int i=0; i<line.length(); i++){
                    data.add(line.charAt(i));
                }
            }
            sc.close();
            UI.println("read "+data.size()+" letters");
        }
        catch(IOException e){UI.println("Fail: " + e);}
    }
    
    /** Load the map of all possible combinations for a 5 letter string of A,T,C,G.
     * the key of the map will be a 5 letter string, and then we will search for each occurrence of the string
     */
    public void loadMap(){
        keyMap = new HashMap<>();
        try{
            Scanner scan = new Scanner(new File("GeneCombinations.txt"));
            int count = 0;
            while(scan.hasNext()){
                String key = scan.nextLine();
    
                String keyString = key.toUpperCase();
                ArrayList <Character> keyList = new ArrayList<Character>();
                for (int i=0; i<keyString.length(); i++){
                    keyList.add(keyString.charAt(i));
                }
                ArrayList<Integer> locations = exactMapSearch(keyList);
                
                keyMap.put(key, locations);
                UI.println(count);
                count++;
            }
            UI.println("done");
        }
        catch (IOException e){UI.println("Well.... you tried?");}
    }
    
    
    /** Returns an arraylist of all the locations that a given key occurs within the data */
    public ArrayList<Integer> exactMapSearch(ArrayList<Character> key){
        if (key==null) return null;
        System.gc();
        
        ArrayList<Integer> matches = new ArrayList<>();
        for(int i = 0; i < data.size() - key.size(); i++){
            int matchingChar = 0;
            for (int j = 0; j < key.size(); j++){
                if(key.get(j) == data.get(i+j)){
                    matchingChar++;
                }
                else break;
            }
            if(matchingChar == key.size()){
                matches.add(i);
            }
        }
        return matches;
    }
    
    /** Search for the exact value givenen in the string using the map. */
    public void exactSearchMap(){
        if (pattern==null){UI.println("No pattern"); return;}
        UI.println("===================");
        UI.printf("searching for %s, using a map\n", patternString);
        
        long time = System.currentTimeMillis();
        String key = patternString.substring(0,5);      //turn the pattern into a substring that can be used to search the map
        ArrayList<Integer> locations = keyMap.get(key); //get all the locations of the given key
        
        int totalMatches = 0;
        for(Integer i : locations){
            int matching = 0;
            int count = 0;
            for (int j = i; j < pattern.size()+i; j++){ //if each element of the sting is the same
                if(data.get(j) == pattern.get(count)){
                    matching++;
                }
                else break;
                count++;
            }
            if(matching == pattern.size()){
                totalMatches++;
                UI.println("Found at "+i+" : "+pattern);
            }
        }
        UI.printf("%d exact matches found in %.3f seconds/n",
                totalMatches, (double)(System.currentTimeMillis()- time)/1000);
    }

    public static void main(String[] arguments){
        new GeneSearch();
    }        
    

}
