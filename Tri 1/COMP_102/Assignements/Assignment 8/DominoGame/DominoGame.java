// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2018T1
* Name:
* Username:
* ID:
*/

import ecs100.*;
import java.util.*;
import java.awt.Color;



/**
*  Lets a player play a simple Solitaire dominoes game.
*  Dominoes are rectangular tiles with two numbers from 0 to 6 on
*  them (shown with dots).
*  The player has a "hand" which can contain up to six dominoes.
*  They can reorder the dominoes in their hand, they can place dominoes
*  from their hand onto the table, and they can pick up more dominoes from a bag
*  to fill the gaps in their "hand".
*  The core and completion do not involve any of the matching and scoring
*  of real dominoes games.
*
*  PROGRAM DESIGN
*  The dominoes are represented by objects of the Domino class.
*  The Domino constructor will construct a new, random domino.
*  Dominos have a draw(double x, double y) method that will draw the
*  Domino on the graphics pane at the specified position.
*
*  The program has two key fields:
*    hand:  an array that can hold 6 Dominos.
*    table: an arrayList of the Dominos that have been placed on the table.
*
*  The hand should be displayed near the top of the Graphics pane with a
*   rectangular border and each domino drawn at its place in the hand.
*  Empty spaces in the hand should be represented by nulls and displayed as empty.
*
*  The user can select a position on the hand using the mouse.
*  The selected domino (or empty space) should be highlighted with
*  a border around it.
*
*  The user can use the "Left" or "Right" button to move the selected domino
*  (or the space) to the left or the right, in which case the domino is
*  swapped with the contents of the adjacent position in the hand.
*  If the selected position contains a domino, the user
*  can use the "Place" button to move the selected domino to the table.
*
*  If there are any empty positions on the hand, the user can use the
*  "Pickup" button to get a new (random) domino which will be added to
*  the hand at the leftmost empty position.
*
*  The table is represented by an ArrayList of dominos.
*  At the beginning of the game the table should be empty.
*  Dominos should be added to the end of the table.
*  The table should be displayed in rows at the top of the graphics pane.
*
*/

public class DominoGame{
    public static final int NUM_HAND = 6;    // Number of dominos in hand

    // Fields
    private Domino[] hand;            // the hand (fixed size array of Dominos)
    private ArrayList<Domino> table;  // the table (variable sized list of Dominos)

    private int selectedPos = 0;      //  selected position in the hand.
    private int clickCount = 0;

    // (You shouldn't add any more fields for core or completion)

    // constants for the layout
    public static final int HAND_LEFT = 60; // x-position of the leftmost Domino in the hand
    public static final int HAND_TOP = 5;   // y-Position of all the Dominos in the hand
    public static final int DOMINO_SPACING = 104;
    //spacing is the distance from left side of Domino to left side of next domino
    public static final int DOMINO_HEIGHT = 50;

    public static final int TABLE_LEFT = 10;
    public static final int TABLE_TOP = 120;

    /**  Constructor:
    * Initialise the hand field to have an array that will hold NUM_HAND Dominos
    * Initialise the table field to have an ArrayList of Dominos,
    * set up the GUI (call setupGUI method)
    *  restart the game
    */
    public DominoGame(){
        this.hand = new Domino[this.NUM_HAND];
        this.table = new ArrayList<Domino>();
        for(int i = 0; i < NUM_HAND; i++)
        {
            this.hand[i] = new Domino();
            Trace.println("New Domino added");
        }
        this.setupGUI();
        this.redraw();
    }

    // Set up the GUI (buttons and mouse)
    public void setupGUI(){
        UI.addButton("Pickup", this::pickup);
        UI.addButton("move Left", this::moveLeft);
        UI.addButton("move Right", this::moveRight);
        UI.addButton("Flip", this::flipDomino);
        UI.addButton("Place", this::placeDomino);
        UI.addButton("Hint", this::suggestDomino);
        UI.addButton("Restart", this::restart);
        UI.setMouseListener(this::doMouse);
    }

    /**
    * Restart the game:
    *  set the table to be empty,
    *  set the hand to have no dominos
    */
    public void restart(){
        this.hand = new Domino[this.NUM_HAND];
        this.table = new ArrayList<Domino>();
        this.selectedPos = 0;
        this.clickCount = 0;
        UI.clearText();
        this.redraw();
    }

    /**
    * If there is at least one empty position on the hand, then
    * create a new random domino and put it into the first empty
    * position on the hand.
    * (needs to search along the array for an empty position.)
    */
    public void pickup(){
        for(int i = 0; i < NUM_HAND; i++)
        {
            if(this.hand[i] == (null))
            {
                this.hand[i] = new Domino();
                Trace.println("New Domino added");
                this.redraw();
                return;
            }
        }
    }

    /**
    * Draws the outline of the hand,
    * draws all the Dominos in the hand,
    * highlights the selected position in some way
    * This needs to use the constants:
    *   DOMINO_SPACING, DOMINO_HEIGHT, HAND_LEFT, HAND_TOP
    */
    public void drawHand(){
        int i = 0;
        UI.setLineWidth(4);
        UI.setColor(Color.black);
        UI.drawRect(HAND_LEFT-5, HAND_TOP-5, (DOMINO_SPACING*NUM_HAND)+5, DOMINO_HEIGHT+10);
        for(i = 0; i < NUM_HAND; i++)
        {
            if(this.hand[i] == null)
            {
            }
            else
            {
                if(this.clickCount>0){
                    UI.setColor(Color.yellow);
                    UI.drawRect(HAND_LEFT+2 + (DOMINO_SPACING*this.selectedPos)-2, HAND_TOP, (DOMINO_HEIGHT*2)+2, DOMINO_HEIGHT+1);
                }
                this.hand[i].draw(HAND_LEFT + (DOMINO_SPACING*i), HAND_TOP);
            }
        }
    }

    /**
    * Move domino from selected position on hand (if there is domino there) to the table
    * The selectedPos field contains the index of the selected domino.
    */
    public void placeDomino(){
        if(this.hand[this.selectedPos] == null)
        {
            UI.println("No Domino selected");
        }
        else{
            this.table.add(this.hand[this.selectedPos]);
            this.hand[this.selectedPos] = null;
            this.redraw();
            UI.setLineWidth(4);
            UI.eraseRect(HAND_LEFT+2 + (DOMINO_SPACING*this.selectedPos)-2, HAND_TOP, (DOMINO_HEIGHT*2)+2, DOMINO_HEIGHT+1);
        }
    }

    /**
    * Draws the list of Dominos on the table, 7 to a row
    * Note, has to wrap around to a new row when it gets to the
    * edge of the table
    * This needs to use the constants:
    *   DOMINO_SPACING, DOMINO_HEIGHT, TABLE_LEFT, TABLE_TOP
    */
    public void drawTable(){
        int i = 0;
        int rows = 0;
        int cols = 0;
        int counter = 0;
        for(rows = 0; rows < this.table.size(); rows++)
        {
            if(counter == 7)
            {
                counter = 0;
                cols++;
            }
            this.table.get(i).draw(TABLE_LEFT + (DOMINO_SPACING*counter), TABLE_TOP + ((DOMINO_HEIGHT+5)*cols));
            i++;
            counter++;
        }
    }

    /**
    * If there is a domino at the selected position in the hand,
    * flip it over.
    */
    public void flipDomino(){
        if(this.hand[this.selectedPos] == null)
        {
            UI.println("No Domino selected");
        }
        else{
            this.hand[this.selectedPos].flip();
            this.redraw();
        }
    }

    /**
    * Swap the contents of the selected position on hand with the
    * position on its left (if there is such a position)
    * and also decrement the selected position to follow the domino
    */
    public void moveLeft(){
        if(this.selectedPos == 0)
        {
            UI.println("This cant be moved left");
        }
        else
        {
            Domino select = this.hand[this.selectedPos];
            this.hand[this.selectedPos] = this.hand[this.selectedPos-1];
            this.hand[this.selectedPos-1] = select;
            this.selectedPos = this.selectedPos-1;
            this.redraw();
        }
    }

    /**
    * Swap the contents of the selected position on hand with the
    *  position on its right (if there is such a position)
    *  and also increment the selected position to follow the domino
    */
    public void moveRight(){
        if(this.selectedPos == 5)
        {
            UI.println("This cant be moved right");
        }
        else
        {
            Domino select = this.hand[this.selectedPos];
            this.hand[this.selectedPos] = this.hand[this.selectedPos+1];
            this.hand[this.selectedPos+1] = select;
            this.selectedPos = this.selectedPos+1;
            this.redraw();
        }
        this.redraw();
    }

    /**
    * If the table is empty, only a double (left and right the same) can be suggested.
    * If the table is not empty, see if one domino has a number that matches the right
    *    number of the last domino on the table.
    */
    public void suggestDomino(){
        int sugestions = 0;
        if(this.table.size() == 0)
        {
            for(int i = 0; i < NUM_HAND; i++)
            {
                if(this.hand[i] == null)
                {
                }
                else if(this.hand[i].getRight() == this.hand[i].getLeft())
                {
                    UI.setColor(Color.green);
                    UI.setLineWidth(4);
                    //UI.eraseRect(HAND_LEFT+2 + (DOMINO_SPACING*this.selectedPos)-2, HAND_TOP, (DOMINO_HEIGHT*2)+2, DOMINO_HEIGHT+1);
                    UI.drawRect(HAND_LEFT+2 + (DOMINO_SPACING*i)-2, HAND_TOP, (DOMINO_HEIGHT*2)+2, DOMINO_HEIGHT+1);
                    sugestions++;
                    this.selectedPos = i;
                }
            }
        }
        else{
            int tabNum = this.table.get(this.table.size()-1).getRight();
            for(int i = 0; i < NUM_HAND; i++)
            {
                if(this.hand[i] == null)
                {
                }
                else if(this.hand[i].getRight() == tabNum || this.hand[i].getLeft() == tabNum)
                {
                    UI.setColor(Color.green);
                    UI.setLineWidth(4);
                    //UI.eraseRect(HAND_LEFT+2 + (DOMINO_SPACING*this.selectedPos)-2, HAND_TOP, (DOMINO_HEIGHT*2)+2, DOMINO_HEIGHT+1);
                    UI.drawRect(HAND_LEFT+2 + (DOMINO_SPACING*i)-2, HAND_TOP, (DOMINO_HEIGHT*2)+2, DOMINO_HEIGHT+1);
                    sugestions++;
                    this.selectedPos = i;
                }
            }
        }
        if(sugestions > 0){
            UI.clearText();
            UI.printf("these are the possible moves");
        }
        else{
            UI.clearText();
            UI.printf("there are no possible moves");
        }
    }

    /** ---------- The code below is already written for you ---------- **/

    /** Allows the user to select a position in the hand using the mouse.
    * If the mouse is released over the hand, then sets  selectedPos
    * to be the index into the hand array.
    * Redraws the hand and table */
    public void doMouse(String action, double x, double y){
        if (action.equals("released")){
            if (y >= HAND_TOP && y <= HAND_TOP+DOMINO_HEIGHT &&
            x >= HAND_LEFT && x <= HAND_LEFT + NUM_HAND*DOMINO_SPACING) {
                this.selectedPos = (int) ((x-HAND_LEFT)/DOMINO_SPACING);
                UI.clearText();UI.printf("selected Domino %d\n ",(this.selectedPos+1));
                this.clickCount++;
                this.redraw();
            }
        }
    }

    /**
    *  Redraw the table and the hand.
    */
    public void redraw(){
        UI.clearGraphics();
        this.drawHand();
        this.drawTable();
    }

    public static void main(String[] args){
        DominoGame obj = new DominoGame();

    }

}
