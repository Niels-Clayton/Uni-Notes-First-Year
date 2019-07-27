// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2018T2, Assignment 3
 * Name:
 * Username:
 * ID:
 */

import java.util.*;
import ecs100.*;
import java.awt.Color;
import java.io.*;

/** Represents information about one cell in a MineSweeper map.
 *   It records
 *     its location (Row and Column)
 *     whether it has a mine or not
 *     how many cells around it have mines
 *     whether it is marked or unmarked
 *     whether it is hidden or exposed
 *   Its constructor must specify the location and
 *     whether it has a mine or not
 *   It has methods to 
 *     draw itself, (showing its state appropriately) given origin of the map.
 *     set the number of mines around it.
 *     report whether it has a mine and whether it is exposed
 *     change its state between marked and unmarked
 *     change its state to exposed
 *
 *
 *     I HAVE ATTACHED MY OWN IMAGES FOR THE MINES, AND FOR THE MARKING, THEY WERE HANDED IN WITH THE ASSIGNMENT
 */

public class Cell{
    // Fields
    private boolean mine;
    private int adjacentMines = 0;
    private boolean marked = false;
    private boolean exposed = false;

    public static final Color LIGHT_GREEN = new Color(117, 117, 118);
    public static final Color DARK_GREEN = new Color(159, 159, 159);

    // Constructors
    /** Construct a new Cell object
     */
    public Cell(boolean mine){
        this.mine = mine;
    }

    // Methods
    /** Get the number of mines adjacent to this cell  */
    public int getAdjacentMines(){
        return adjacentMines;
    }

    /** Record the number of adjacent mines */
    public void setAdjacentMines(int num){
        adjacentMines = num;
    }

    /** Does the cell contain a mine? */
    public boolean hasMine(){
        return mine;
    }

    /** Is the cell exposed already? */
    public boolean isExposed(){
        return this.exposed;
    }

    /** Is the cell currently marked? */
    public boolean isMarked(){
        return this.marked;
    }

    /** Set the cell to be exposed? */
    public void setExposed(){
        this.exposed = true;
    }

    /** Toggle the mark */
    public void toggleMark(){
        this.marked = ! this.marked;
    }

    /** Draw the cell */
    public void draw(double x, double y, double size){
        UI.setColor(LIGHT_GREEN);
        UI.fillRect(x, y, size, size);
        if (exposed){ drawExposed(x, y, size); }
        else        { drawHidden(x, y, size); }
    }

    /** Draw white outline and red number or mine */
    private void drawExposed(double x, double y, double size){
        UI.setColor(new Color(150, 150, 150));
        UI.drawRect(x, y, size, size);
        UI.setColor(new Color(200, 200, 200));
        if (mine){
            UI.drawImage("mine.png",x + size/6, y + size/6);
        }
        else if (adjacentMines > 0){
            if(adjacentMines == 1)UI.setColor(Color.BLUE);
            if(adjacentMines == 2)UI.setColor(new Color(0, 102, 0));
            if(adjacentMines == 3)UI.setColor(Color.RED);
            if(adjacentMines == 4)UI.setColor(new Color(63, 0, 102));
            if(adjacentMines == 5)UI.setColor(new Color(128, 0, 0));
            if(adjacentMines == 6)UI.setColor(new Color(64,224,208));
            if(adjacentMines == 7)UI.setColor(Color.BLACK);
            if(adjacentMines == 8)UI.setColor(Color.GRAY);
            
            UI.drawString(""+adjacentMines, x+size/2-5, y+size/2+5);
            UI.drawString(""+adjacentMines, x+size/2-4, y+size/2+5);
        }
    }

    /** Fill dark green with red mark */
    private void drawHidden(double x, double y, double size){
        UI.setColor(DARK_GREEN);
        UI.fillRect(x+1, y+1, size-2, size-2);
        if (marked){
            UI.drawImage("flag.png",x, y );
    }
    }

}
