// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2018T2, Assignment 6
 * Name:
 * Username:
 * ID:
 */

import ecs100.UI;
import java.awt.*;
import java.util.*;

/**
 * Search for a path to the goal in a maze.
 * The maze consists of a graph of Cells:
 *  Each cell has a collection of neighbouring cells.
 *  Each cell can be "visited" and it will remember that it has been visited
 *  A Cell is Iterable, so that you can iterate through its neighbour cells with
 *    for(Cell neigh : cell){....
 *
 * The maze has a goal cell (in the bottom right corner)
 * The user can click on a cell, and the program will search for a path
 * from that cell to the goal.
 * 
 * Every cell that is looked at during the search is coloured  yellow, and then,
 * if the cell turns out to be on a dead end, it is coloured red.
 */

public class MazeSearch {

    public static final int DELAY = 20;

    private Maze maze;
    private String search = "first";   // "first", "all", or "shortest"
    private int pathCount = 0;

    
    public MazeSearch() {
        setupGui();
        makeMaze(10);
    }
        
    public void setupGui(){
        UI.addTextField("Maze Size", (String v)->{makeMaze(Integer.parseInt(v));});
        UI.setMouseListener(this::doMouse);
        UI.addButton("First path",    ()->{search="first";});
        UI.addButton("All paths",     ()->{search="all";});
        UI.addButton("Shortest path", ()->{search="shortest";});
        UI.addButton("Quit", UI::quit);
    }

    /**
     * Creates a new maze and draws it .
     */
    public void makeMaze(int size){
        maze = new Maze(size);
        maze.draw();
    }

    /**
     * Clicking the mouse on a cell should make the program
     * search for a path from the clicked cell to the goal.
     */
    public void doMouse(String action, double x, double y){
        if (action.equals("released")){
            UI.clearText();
            maze.reset();
            maze.draw();
            pathCount = 0;
            Cell start = maze.getCellAt(x, y);
            if (search=="first"){
                exploreCell(start);
                start.draw(Color.GREEN);
            }
            else if (search=="all"){
                exploreCellAll(start);
            }
            if (search=="shortest"){
                start.draw(Color.GREEN);
                exploreCellShortest(start);
            }
        }
    }

    /**
     * Search for a path from a cell to the goal.
     * Return true if we got to the goal via this cell (and don't
     *  search for any more paths).
     * Return false if there is not a path via this cell.
     * 
     * If the cell is the goal, then we have found a path - return true.
     * If the cell is already visited, then abandon this path - return false.
     * Otherwise,
     *  Mark the cell as visited, and colour it yellow (and sleep for a short time)
     *  Recursively try exploring from the cell's neighbouring cells, returning true
     *   if a neighbour leads to the goal
     *  If no neighbour leads to a goal,
     *    colour the cell red (to signal failure)
     *    abandon the path - return false.
     */
    public boolean exploreCell(Cell cell) {
        UI.sleep(10);
        if (cell == maze.getGoal()) {
            cell.draw(Color.CYAN);   // to indicate finding the goal
            return true;
        }
        if(cell.isVisited()){
            return false;
        }
        cell.visit();
        cell.draw(Color.yellow);
        for(Cell c : cell){
            if(exploreCell(c)){
                return true;
            }
        }
        cell.draw(Color.red);
        return false;
    }


    /** COMPLETION
     * Search for all paths from a cell,
     * If we reach the goal, then we have found a complete path,
     *  so pause for 1 second
     * Otherwise,
     *  visit the cell, and colour it yellow
     *  Recursively explore from the cell's neighbours, 
     *  unvisit the cell and colour it white.
     * 
     */
    public void exploreCellAll(Cell cell) {
        if (cell == maze.getGoal()){
            cell.draw(Color.CYAN);   // to indicate finding the goal
            UI.sleep(500);
            return;
        }
        if(cell.isVisited()){
            return;
        }
        cell.visit();
        cell.draw(Color.yellow);
        for(Cell c : cell){
            exploreCellAll(c);
            //UI.sleep(5);
        }
        cell.unvisit();
        cell.draw(Color.white);
    }

    
    /** CHALLENGE
     * Search for shortest path from a cell,
     * Use Breadth first search.
     */
    public void exploreCellShortest(Cell start) {
        Queue<Cell> visited = new LinkedList<>();
        Queue<Cell> cameFrom = new LinkedList<>();
        
        visited.offer(start);
        cameFrom.offer(start);

        while(visited.size() != 0){
            Cell check = visited.poll();
            check.cellBefore = cameFrom.poll();
            check.visit();
    
            if(check == maze.getGoal()){
                start.cellBefore = null;
                drawShortest(check);
                return;
            }
            else{
                for(Cell c : check){
                    if(!c.isVisited()){
                        cameFrom.offer(check);
                        visited.offer(c);
                    }
                }
            }
        }
    }
    
    public void drawShortest(Cell start){
        start.draw(Color.cyan);
        maze.getGoal().draw(Color.GREEN);
        if(start.cellBefore != null){
            UI.sleep(50);
            drawShortest(start.cellBefore);
        }
    }
    


    public static void main(String[] args) {
        new MazeSearch();
    }
}

