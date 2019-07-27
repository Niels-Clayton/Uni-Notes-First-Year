// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2018T2, Assignment 3
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Simple 'Minesweeper' program.
 * There is a grid of cells, some of which contain a mine.
 * The user can click on a cell to either expose it or to
 * mark/unmark it.
 * <p>
 * If the user exposes a cell with a mine, they lose.
 * Otherwise, it is uncovered, and shows a number which represents the
 * number of mines in the eight cells surrounding that one.
 * If there are no mines adjacent to it, then all the unexposed cells
 * immediately adjacent to it are exposed (and and so on)
 * <p>
 * If the user marks a cell, then they cannot expose the cell,
 * (unless they unmark it first)
 * When all squares with mines are marked, and all the squares without
 * mines are exposed, the user has won.
 */
public class MineSweeper {
    
    private int ROWS = 15;
    private int COLS = 15;
    
    private static final double LEFT = 10;
    private static final double TOP = 10;
    private static final double CELL_SIZE = 20;
    
    // Fields
    private boolean marking;
    
    private Cell[][] cells;
    
    private JButton mrkButton;
    private JButton expButton;
    Color defaultColor;
    
    private int mines;
    private int spaces;
    
    /**
     * Construct a new MineSweeper object
     * and set up the GUI
     */
    private MineSweeper(){
        setupGUI();
        setMarking(false);
        makeGrid(false);
    }
    
    /**
     * setup buttons
     */
    private void setupGUI(){
        UI.setMouseListener(this::doMouse);
        UI.addButton("New Game", () -> makeGrid(true));
        this.expButton = UI.addButton("Expose", () -> setMarking(false));
        this.mrkButton = UI.addButton("Mark", () -> setMarking(true));
        UI.addButton("Mark known mines", this::markMines);
        UI.addButton("Expose all safe cells", this::exposeSafeCells);
        UI.addButton("Run AI", this::autoComplete);
        UI.addSlider("Difficulty", 1, 3, 1, this::setDifficulty);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(0.0);
    }
    
    /**
     *Change the size of the grid depending on the dufficilty
     */
    
    public void setDifficulty(double diff){
        ROWS = 15* ((int)diff);
        COLS = 15* ((int)diff);
        makeGrid(true);
    }
    
    /**
     * Respond to mouse events
     */
    private void doMouse(String action, double x, double y){
        if (action.equals("released")){
            int row = (int) ((y - TOP) / CELL_SIZE);
            int col = (int) ((x - LEFT) / CELL_SIZE);
            if (row >= 0 && row < ROWS && col >= 0 && col < COLS){
                if (marking){ mark(row, col);}
                else{ tryExpose(row, col); }
            }
        }
    }
    
    /**
     * Remember whether it is "Mark" or "Expose"
     * Change the colour of the "Mark", "Expose" buttons
     */
    private void setMarking(boolean v){
        marking = v;
        if (marking){
            mrkButton.setBackground(new Color(64, 156, 89));
            expButton.setBackground(null);
        }
        else{
            expButton.setBackground(new Color(64, 156, 89));
            mrkButton.setBackground(null);
        }
    }
    
    // Other Methods
    
    /**
     * The player has clicked on a cell to expose it
     * - if it is already exposed or marked, do nothing.
     * - if it's a mine: lose (call drawLose())
     * - otherwise expose it (call exposeCellAt)
     * then check to see if the player has won and call drawWon() if they have.
     * (This method is not recursive)
     */
    private int tryExpose(int row, int col){
        if (cells[row][col].isExposed()){return 1;}
        if (cells[row][col].hasMine() && !(cells[row][col].isMarked())){drawLose(); return -1;}
        else{exposeCellAt(row, col);}
        if (hasWon()){
            drawWin();
            for (int i = 0; i < cells.length; i++){
                for (int j = 0; j < cells[0].length; j++){
                    mark(i, j);
                }
            }
        }
        return 0;
    }
    
    /**
     * Expose a cell, and spread to its neighbours if safe to do so.
     * It is guaranteed that this cell is safe to expose (ie, does not have a mine).
     * If it is already exposed, we are done.
     * Otherwise expose it, and redraw it.
     * If the number of adjacent mines of this cell is 0, then
     * expose all its neighbours (which are safe to expose)
     * (and if they have no adjacent mine, expose their neighbours, and ....)
     */
    private void exposeCellAt(int row, int col){
        if (row >= ROWS || row < 0){return;}
        if (col >= COLS || col < 0){return;}
        
        if (cells[row][col].isExposed()){return;}
        if (cells[row][col].isMarked()){return;}
        
        cells[row][col].setExposed();
        cells[row][col].draw(LEFT + col * CELL_SIZE, TOP + row * CELL_SIZE, CELL_SIZE);
        spaces++;
        
        if (cells[row][col].getAdjacentMines() > 0){return;}
        
        else{
            exposeCellAt(row, col - 1);
            exposeCellAt(row, col + 1);
            exposeCellAt(row + 1, col);
            exposeCellAt(row + 1, col - 1);
            exposeCellAt(row + 1, col + 1);
            exposeCellAt(row - 1, col);
            exposeCellAt(row - 1, col - 1);
            exposeCellAt(row - 1, col + 1);
            
        }
    }
    
    
    /**
     * Mark/unmark the cell.
     * If the cell is exposed, don't do anything,
     * If it is marked, unmark it.
     * otherwise mark it and redraw.
     * (Marking cannot make the player win or lose)
     */
    private void mark(int row, int col){
        if (cells[row][col].isExposed()){return;}
        if (hasWon() && cells[row][col].isMarked()){return;}
        cells[row][col].toggleMark();
        cells[row][col].draw(LEFT + col * CELL_SIZE, TOP + row * CELL_SIZE, CELL_SIZE);
        
    }
    
    /**
     * Returns true if the player has won:
     * If all the cells without a mine have been exposed, then the player has won.
     */
    private boolean hasWon(){
        int totalSpaces = (ROWS * COLS) - mines;
        if (totalSpaces == spaces){
            return true;
        }
        else{ return false; }
    }
    
    /**
     * Construct a grid with random mines.
     */
    private void makeGrid(boolean restart){
        if (restart){
            int option = JOptionPane.showConfirmDialog(null, "Are you sure to want to restart?", "Restart", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.NO_OPTION){ return; }
        }
        
        //UI.clearGraphics();
        UI.clearPanes();
        UI.setDivider(0);
        spaces = 0;
        mines = 0;
        this.cells = new Cell[ROWS][COLS];
        for (int row = 0; row < ROWS; row++){
            double y = TOP + row * CELL_SIZE;
            for (int col = 0; col < COLS; col++){
                double x = LEFT + col * CELL_SIZE;
                boolean isMine = Math.random() < 0.10; // approx 1 in 10 cells is a mine
                this.cells[row][col] = new Cell(isMine);
                this.cells[row][col].draw(x, y, CELL_SIZE);
                if (isMine){ mines++;}
            }
        }
        // now compute the number of adjacent mines for each cell
        for (int row = 0; row < ROWS; row++){
            for (int col = 0; col < COLS; col++){
                int count = 0;
                //look at each cell in the neighbourhood.
                for (int r = Math.max(row - 1, 0); r < Math.min(row + 2, ROWS); r++){
                    for (int c = Math.max(col - 1, 0); c < Math.min(col + 2, COLS); c++){
                        if (cells[r][c].hasMine()){ count++; }
                    }
                }
                if (this.cells[row][col].hasMine()){
                    count--;  // we weren't suppose to count this cell, just the adjacent ones.
                }
                
                this.cells[row][col].setAdjacentMines(count);
            }
        }
    }
    
    /**
     * Draw a message telling the player they have won
     */
    private void drawWin(){
//                UI.setFontSize(28);
//                UI.setColor(Color.BLACK);
//                UI.drawString("You Win!", LEFT + COLS * CELL_SIZE + 20, TOP + ROWS * CELL_SIZE / 2);
//                UI.setFontSize(12);
        
        UI.setDivider(0.5);
        UI.setFontSize(80);
        UI.drawString("The pond is pleased", 50, 600);
        Pondy.pleasedGod();
        UI.setFontSize(12);
        
    }
    
    /**
     * Draw a message telling the player they have lost
     * and expose all the cells and redraw them
     */
    private void drawLose(){
        for (int row = 0; row < ROWS; row++){
            for (int col = 0; col < COLS; col++){
                cells[row][col].setExposed();
                cells[row][col].draw(LEFT + col * CELL_SIZE, TOP + row * CELL_SIZE, CELL_SIZE);
            }
        }
//                UI.setFontSize(28);
//                UI.drawString("You Lose!", LEFT + COLS * CELL_SIZE + 20, TOP + ROWS * CELL_SIZE / 2);
//                UI.setFontSize(12);

        UI.setDivider(0.5);
        UI.setFontSize(80);
        UI.drawString("The pond is angered", 50, 600);
        Pondy.angerGod();
        UI.setFontSize(12);
        
        UI.sleep(300);
        int option = JOptionPane.showConfirmDialog(null, "Do you want to restart?", "Restart", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.NO_OPTION){ return; }
        else if (option == JOptionPane.YES_OPTION){ makeGrid(false); }
    }
    
    /**
     * Returns a 2D array where each state on the map as been replaced by an int value
     * -  Marked = -2
     * -  Not Exposed = -1
     * -  exposed with no adjacent mines = 0
     * -  exposed with adjacent mines = number of adjacent mines
     */
    
    public int[][] returnMapState(){
        
        int[][] mapState = new int[ROWS][COLS];  //2D array of same size as the game
        
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                
                Cell currentCell = cells[i][j];
                
                if (!(currentCell.isExposed()) && !(currentCell.isMarked())){
                    mapState[i][j] = -1; // UnExposed cell
                }
                else if (!(currentCell.isExposed()) && currentCell.isMarked()){
                    mapState[i][j] = -2; // Marked cell
                }
                else if (currentCell.isExposed() && (currentCell.getAdjacentMines() == 0)){
                    mapState[i][j] = 0; // exposed cell with noe surrounding mines
                }
                else if (currentCell.isExposed()){
                    mapState[i][j] = currentCell.getAdjacentMines();
                }
            }
        }
        return mapState;
    }
    
    /**
     * Takes a 2D array that contains the state of each cell in the game
     * if it is marked, uncovered, exposed, and if exposed, their adjacent mines.
     * Passes this array to the findNextSteps class, which calcualtes the position
     * if all of the currently known mines. returns this again as a new array filled
     * only with 1's and 0's, where each one is a mine that can be marked.
     */
    
    private int markMines(){
        int moveMade = 0;
        int[][] state = returnMapState(); //2D array cntaining the state of the game
        findNextSteps next = new findNextSteps(state);
        int[][] minesToMark = next.findMines(); // Creates a 2D array of 1's and 0's, where 1's are mines
        
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                if (minesToMark[i][j] == 1){
                    if (!(cells[i][j].isMarked())){
                        cells[i][j].toggleMark();  //marks each known mine
                        cells[i][j].draw(LEFT + j * CELL_SIZE, TOP + i * CELL_SIZE, CELL_SIZE);
                        moveMade++;
                    }
                }
            }
        }
        return moveMade;
    }
    
    /**
     * Takes a 2D array that contains the state of each cell in the game
     * if it is marked, uncovered, exposed, and if exposed, their adjacent mines.
     * Passes this array to the findNextSteps class, which calcualtes the position
     * if all of the currently known safe squares by looking at the positions of the flags.
     * returns this again as a new array filled only with 1's and 0's,
     * where each one is a safe cell that can be uncovered.
     */
    
    private void exposeSafeCells(){
        int[][] state = returnMapState();
        findNextSteps next = new findNextSteps(state);
        int[][] safeToUncover = next.uncoverCells();
        
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                if (safeToUncover[i][j] == 1){
                    if(tryExpose(i, j) == -1){
                        return;
                    }
                }
            }
        }
        int changes = next.changeMade();
        if (!(changes == 0)){
            exposeSafeCells();
        }
    }
    
    /**
     * if there is no possible move, randomly selects one of the avalable cells and uncovers it
     * Once there is a possible move, mark all the known cells to have mines, then recursively
     * call the expose safe cells method till no changes are made anymore, then mark mines again.
     * Repeat this till the game is won.
     */
    
    private void autoComplete(){
        while (!hasWon()){
            while (markMines() == 0){
    
                int option = JOptionPane.showConfirmDialog(null, "No certain moves, make a random one?", "Make random move", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.NO_OPTION){ return; }
                else if (option == JOptionPane.YES_OPTION){
                    int randCol = (int) (Math.random() * COLS);
                    int randRow = (int) (Math.random() * ROWS);
                    int exposed = tryExpose(randRow, randCol);
                    if (exposed == 1 && (!hasWon())){
                        
                        ArrayList<int[]> possibleMoves = new ArrayList();
                        
                        for (int i = 0; i < ROWS; i++){
                            for (int j = 0; j < COLS; j++){
                                if(!(cells[i][j].isExposed()) && !(cells[i][j].isMarked())){
                                    int[] move = {i, j};
                                    possibleMoves.add(move);
                                }
                            }
                        }
                        int selection = (int)(Math.random()*possibleMoves.size());
                        int row = possibleMoves.get(selection)[0];
                        int col = possibleMoves.get(selection)[1];
                        tryExpose(row, col);
                    }
                }
            }
            markMines();
            UI.sleep(500);
            exposeSafeCells();
            UI.sleep(500);
        }
    }
    
    
    // Main
    public static void main(String[] arguments){
        new MineSweeper();
    }
}
