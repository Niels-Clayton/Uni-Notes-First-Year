// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2018T2, Assignment 1
* Name:
* Username:
* ID:
*/

import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;
/**
* This class contains the main method of the program.
*
* A SlideShow object represents the slideshow application and sets up the buttons in the UI.
*
* @author pondy... The man, The myth, The legend himself <3
*/
public class SlideShow {

    public static final int GAP = 10;          // gap between images when editing

    private int showPeriod = 1;
    private int pressedImage = -1;
    public int smallSize = 100;                // size of images when editing list
    public int largeSize = 450;                // size of images during slide show
    public int columns = 6;       // Number of columns of thumbnails
    private int releasedImage = -1;
    private List<String> images = new ArrayList<String>();//  List of image file names.
    private int currentImage = -1;     // index of currently selected image.
                                       // Should always be a valid index if there are any images

    private boolean showRunning;      // flag signalling whether the slideshow is running or not


    /**
    * Constructor
    */
    public SlideShow() {
        this.setupGUI();
        UI.setColor(new Color(255, 255, 255, 128));
    }

    /**
    * Initialises the UI window, and sets up the buttons.
    */
    public void setupGUI() {
        UI.initialise();

        UI.addButton("Run show",                            this::runShow);
        UI.addButton("Edit show",                           this::editShow);
        UI.addSlider("Edit picture size", 50 ,150, 100,     this::imageSize);
        UI.addSlider("Show picture size", 300 ,800, 450,    this::showImageSize);
        UI.addSlider("Columns", 4 ,12, 6,                   this::coloumsNum);
        UI.addSlider("Period", 1 ,4, 1,                     this::showTime);
        UI.addButton("add before",                          this::addBefore);
        UI.addButton("add after",                           this::addAfter);
        UI.addButton("move left",                           this:: moveLeft);
        UI.addButton("move right",                          this:: moveRight);
        UI.addButton("move to start",                       this:: moveStart);
        UI.addButton("move to end",                         this:: moveEnd);
        UI.addButton("remove",                              this::remove);
        UI.addButton("remove all",                          this::removeAll);
        UI.addButton("reverse",                             this::reverse);
        UI.addButton("shuffle",                             this::shuffle);
        UI.addButton("Testing",                             this::setTestList);
        UI.addButton("Save",                                this::save);
        UI.addButton("Load",                                this::load);
        UI.addButton("Quit",                                UI::quit);
        UI.setKeyListener(                                  this::doKey);
        UI.setMouseListener(                                this::doMouse);
        UI.setDivider(0);
        UI.printMessage("Mouse must be over graphics pane to use the keys");

    }

    // More methods for the user interface: keys (and mouse, for challenge)
    /**
    * Interprets key presses.
    * works in both editing the list and in the slide show.
    */
    public void doKey(String key) {
        if (key.equals("Left"))                 goLeft();
        else if (key.equals("Right"))           goRight();
        else if (key.equals("Up"))              goUp();
        else if (key.equals("Down"))            goDown();
        else if (key.equals("Home"))            goStart();
        else if (key.equals("End"))             goEnd();
        else if (key.equals("Period"))          moveRight();
        else if (key.equals("Comma"))           moveLeft();
        else if (key.equals("Space"))           runShow();
        else if (key.equals("Delete")
        || (key.equals("Backspace")))           remove();
        else if (key.equalsIgnoreCase("R"))     shuffle();
    }

    // RUNNING

    /**
    * As long as the show isn't already running, and there are some
    * images to show, start the show running from the currently selected image.
    * The show should keep running indefinitely, as long as the
    * showRunning field is still true.
    * Cycles through the images, going back to the start when it gets to the end.
    * The currentImage field should always contain the index of the current image.
    */
    public void runShow(){
        if(!(images.isEmpty())){
            if(showRunning) {showRunning = false; return;}  //if the show is already running, and runShow is pressed, exit the show
            showRunning = true;
            while(showRunning){
                for(currentImage = 0; currentImage < this.images.size(); currentImage++){
                    UI.clearGraphics();
                    display();
                    UI.sleep((showPeriod*1000));
                    if(currentImage == (this.images.size()-1)){
                        currentImage = 0;
                    }
                    if(!showRunning){
                        UI.clearGraphics();
                        display();
                        return;
                    }
                }
            }
        }
    }

    /**
    * Stop the show by changing showRunning to false.
    * Redisplay the list of images, so they can be edited
    */
    public void editShow(){
        this.showRunning = false;
        UI.clearGraphics();
        display();
    }

    /**
    * Display just the current slide if the show is running.
    * If the show is not running, display the list of images
    * (as thumbnails) highlighting the current image
    */
    public void display(){
        int count = 0;                    //total count of number pictures displayed
        int rowCount = 0;                 //Count of what row we are currently on
        int colCount = 0;                 //Count of what column we are on

        if (!showRunning){
            UI.clearGraphics();
            for(int image = 0; image < this.images.size(); image++){
                int draw_x = (GAP*(colCount+1)) + (colCount*smallSize);
                int draw_y = (GAP*(rowCount+1)) + (rowCount*smallSize);
                UI.drawImage(this.images.get(count),draw_x ,draw_y , smallSize,  smallSize);
                if(currentImage>=0 && image == currentImage){                 //If image is selected, Highlight it by
                    UI.fillRect(draw_x ,draw_y , smallSize,  smallSize);  //by drawing a transparent square overtop of it.
                }
                count++;
                colCount++;
                if(count % columns == 0){
                    rowCount++;           //Increase the row once there have been 6 colums
                    colCount = 0;         //Reset the column back to the beginning once there have been 6
                }
            }
        }
        else{
            UI.drawImage(this.images.get(currentImage), GAP, GAP, largeSize, largeSize);  //If show is running, draw image with large size.
        }
    }

    public void coloumsNum(double num){     //Edit the number of colums
        columns = (int)(num);
        display();
    }

    public void imageSize(double size){    //Edit the size of images in editing screen
        smallSize = (int)(size);
        display();
    }

    public void showImageSize(double size){  // Edit size of the display slide image
        largeSize = (int)(size);
        UI.clearGraphics();
        display();
    }

    public void showTime(double time){    //Edit the time period between images in the show
        showPeriod = (int)(time);
    }

    public void addBefore(){
        String newImage = UIFileChooser.open("Image to add");      //select new image
        if(!(images.isEmpty()) && !(showRunning)){
            images.add(currentImage, newImage);                    //add new image in current position
            display();
        }
        else if(!(showRunning)){                                   //If array is currently empty, add new image and set it to selected image
            images.add(newImage);
            currentImage = 0;
            display();
        }
    }

    public void addAfter(){
        String newImage = UIFileChooser.open("Image to add");      //select new image
        if(!(images.isEmpty()) && !(showRunning)){
            images.add(currentImage+1, newImage);                  //add new image in current position +1
            display();
        }
        else if(!(showRunning)){                                   //If array is currently empty, add new image and set it to selected image
            images.add(newImage);
            currentImage = 0;
            display();
        }
    }

    public void moveLeft(){
        if(!(images.isEmpty()) && !(showRunning)){
            int moveTo = currentImage;
            currentImage--;
            if(currentImage < 0) currentImage = (images.size()-1); // if current image is less than 0, set it to the index of
            Collections.swap(images, moveTo, currentImage);        // the end of the array. then swap.
            display();
        }
    }

    public void moveRight(){
        if(!(images.isEmpty()) && !(showRunning)){
            int moveTo = currentImage;
            currentImage++;
            if(currentImage > (images.size()-1)) currentImage = 0; // if current image is past the end of the array, set it to the index of
            Collections.swap(images, moveTo, currentImage);        // the start of the array (0). then swap.
            display();
        }
    }

    public void moveStart(){
        if(!(images.isEmpty()) && !(showRunning)){
            String selected = images.get(currentImage);
            images.remove(currentImage);                 //remove the current image from its current position in the array
            images.add(0, selected);                     //add current image to position 0 in the array
            currentImage = 0;
            display();
        }
    }

    public void moveEnd(){
        if(!(images.isEmpty()) && !(showRunning)){
            String selected = images.get(currentImage);
            images.remove(currentImage);                        //remove the current image from its current position in the array
            images.add(selected);                               //add current image to the array (this will add it to the end)
            currentImage = images.size()-1;
            display();
        }
    }

    public void remove(){
        if(!(images.isEmpty()) && !(showRunning)){
            if(currentImage >=0) images.remove(currentImage);
            if(currentImage >= images.size()) currentImage = (images.size()-1);  //If after deletion, current image is larger than array
            display();                                                           //set current image to array_size-1
        }
    }

    public void removeAll(){
        if(!(images.isEmpty()) && !(showRunning)){
            this.images.clear();                                 //clears the ArrayList
        }
        UI.clearGraphics();                                      //clears the screen
    }

    public void reverse(){
        if(!(images.isEmpty()) && !(showRunning)){
            Collections.reverse(images);                        //runs the reverse method on the ArrayList
            display();
        }
    }

    public void shuffle(){
        if(!(images.isEmpty()) && !(showRunning)){
            Collections.shuffle(images);                       //runs the shuffle method on the ArrayList
            display();
        }
    }

    public void goLeft(){
        if(!(images.isEmpty())){
            currentImage--;                                            //decreases the current image by one
            if(currentImage < 0) currentImage = (images.size()-1);     //if current image is now < 0, set it to array_size-1
            display();
        }
    }

    public void goRight(){
        if(!(images.isEmpty())){
            currentImage++;                                            //increases the current image by one
            if(currentImage >= images.size())currentImage = 0;         //if current image is now >= array_size-1 set to 0
            display();
        }
    }

    public void goDown(){
        if(currentImage < 0) currentImage = 0;
        if(!(images.isEmpty())){
            if(currentImage + columns < images.size()){             //if adding columns dosen't excede the array_size,
                currentImage += columns;                            //increase the current image by columns
            }
            display();
        }
    }

    public void goUp(){
        if(currentImage < 0) currentImage = 0;
        if(!(images.isEmpty())){
            if(currentImage - columns < images.size()){
                if(currentImage - columns < 0){             //if taking away columns dosen't go below 0,
                    return;                                 //decrease the current image by columns
                }
                currentImage -= columns;
            }
            display();
        }
    }

    public void goStart(){
        if(!(images.isEmpty())){
            currentImage = 0;           //set currentimage to 0
            display();
        }
    }

    public void goEnd(){
        if(!(images.isEmpty())){
            currentImage = (images.size()-1);     //set currentImage to array_size-1
            display();
        }
    }

    public void moveImage(int pressed, int released){
        if(!(images.isEmpty())){
            if(released == -1 || pressed == -1) return;    //if there is no pressedImage, return
            if(released < pressed || released > pressed){  //if there is a move of image
                String selected = images.get(pressed);
                images.remove(pressed);                    //store pressed image as string, and delete it from array
                images.add(released, selected);            //add pressed image to array in the pressed index
                releasedImage = -1;
                pressedImage = -1;
                currentImage = released;
                display();
            }
            else return;
        }
    }

    public void doMouse(String action, double x, double y){
        if(action.equals("pressed")){
            currentImage = index(x,y);                          //find the index of the pressed image
            display();
            pressedImage = currentImage;
        }
        if(action.equals("released")){
            releasedImage = index(x,y);                         //find index of released image
            moveImage(pressedImage, releasedImage);             //move pressed image to released position
        }
    }

    public int index(double x, double y){                    //returns the index of the given x and y cordinates
        int index = ((y_click(y) * columns)+ x_click(x));
        if (index < ((y_click(y)+1)*(columns)) && index < images.size()) return index;
        else return -1;
    }

    public int x_click(double x){
        int click = ((int)(x/(smallSize + GAP)));             //returns the column that the x value is in
        return click;
    }

    public int y_click(double y){
        int click = ((int)(y/(smallSize + GAP)));             //returns the column that the y value is in
        return click;
    }

    public void save(){
        try{
            PrintStream out = new PrintStream(new File(UI.askString("File name?")));
            for(String image : images){
                out.println(image);                          //print out the array of file names to a new file
            }
            out.close();
        }
        catch(Exception e){}
    }

    public void load(){
        try{
            Scanner scan = new Scanner(new File(UIFileChooser.open()));
            while(scan.hasNext()){
                images.add(scan.nextLine());         //read a file, and add each line ofr the file to the images array
            }
            display();
        }
        catch(Exception e){/*im fucking gay*/}
    }

    /**
    * A method that adds a bunch of names to the list of images, for testing.
    */
    public void setTestList(){
        if (showRunning) return;
        String[] names = new String[] {"Atmosphere.jpg", "BachalpseeFlowers.jpg",
                                       "BoraBora.jpg", "Branch.jpg", "DesertHills.jpg",
                                       "DropsOfDew.jpg", "Earth_Apollo17.jpg",
                                       "Frame.jpg", "Galunggung.jpg", "HopetounFalls.jpg",
                                       "Palma.jpg", "Sky.jpg", "SoapBubble.jpg",
                                       "Sunrise.jpg", "Winter.jpg"};

        this.images.clear();
        for(String name : names){
            images.add(name);
        }
        this.showRunning = false;
        UI.clearGraphics();
        Trace.println(this.images.size());
        display();
    }

    public static void main(String[] args) {
        new SlideShow();
    }
}
