
/**
 * Recieves a 2D array representing the current state of the board,
 * then can find and return the possible moves a player can make based on
 * the current state of the board.
 */


public class findNextSteps {
    
    private int[][] currentState;  //2D array containing the states of each cell in the mineSweeper game
    private int ROWS; //the number of rows of the 2D array
    private int COLS; //the number of columns on the passed 2D array
    private int sugestedChange;
    
    public findNextSteps(int[][] state){
        currentState = state;
        ROWS = state.length;
        COLS = state[0].length;
    }
    
    /**
     * gets passed the position of a cell, then looks at the state of the game and returns
     * the total number of currently uncovered or marked cells around that position.
     * <p>
     * All of the try : catch's are to prevent the Array Out Of Bounds. If you were to call
     * all 8 cells that surround a specific position and that position is a corner, there are only
     * 3 avalable cells to check, and the rest will throw exceptions.
     */
    
    private int uncovCells(int row, int col, boolean mark){
        int total = 0;
        
        if (mark){
            try{
                if (currentState[row + 1][col] == -1        || currentState[row + 1][col] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row + 1][col + 1] == -1    || currentState[row + 1][col + 1] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row + 1][col - 1] == -1    || currentState[row + 1][col - 1] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row - 1][col] == -1        || currentState[row - 1][col] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row - 1][col + 1] == -1    || currentState[row - 1][col + 1] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row - 1][col - 1] == -1    || currentState[row - 1][col - 1] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row][col + 1] == -1        || currentState[row][col + 1] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row][col - 1] == -1        || currentState[row][col - 1] == -2){ total++; }
            } catch (Exception e){}
        }
        
        else{
            try{
                if (currentState[row + 1][col] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row + 1][col + 1] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row + 1][col - 1] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row - 1][col] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row - 1][col + 1] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row - 1][col - 1] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row][col + 1] == -2){ total++; }
            } catch (Exception e){}
            try{
                if (currentState[row][col - 1] == -2){ total++; }
            } catch (Exception e){}
        }
        
        return total;
        
        /**
         * The code below here is what I originally wrote before simplifying it using a try catch.
         * I am keeping it as a reminder to myself that I SHOULD NEVER USE THAT MANY IF STATEMENTS AGAIN!
         */
        //        if (row == 0 && col == 0){
        //            if (currentState[row + 1][col] == -1        || currentState[row + 1][col] == -2){ total++; }
        //            if (currentState[row + 1][col + 1] == -1    || currentState[row + 1][col + 1] == -2){ total++; }
        //            if (currentState[row][col + 1] == -1        || currentState[row][col + 1] == -2){ total++; }
        //        }
        //
        //        else if (row == (ROWS - 1) && col == (COLS - 1)){
        //            if (currentState[row - 1][col] == -1        || currentState[row - 1][col] == -2){ total++; }
        //            if (currentState[row - 1][col - 1] == -1    || currentState[row - 1][col - 1] == -2){ total++; }
        //            if (currentState[row][col - 1] == -1        || currentState[row][col - 1] == -2){ total++; }
        //        }
        //
        //        else if (row == 0){
        //            if (currentState[row + 1][col] == -1        || currentState[row + 1][col] == -2){ total++; }
        //            if (currentState[row + 1][col + 1] == -1    || currentState[row + 1][col + 1] == -2){ total++; }
        //            if (currentState[row + 1][col - 1] == -1    || currentState[row + 1][col - 1] == -2){ total++; }
        //            if (currentState[row][col - 1] == -1        || currentState[row][col - 1] == -2){ total++; }
        //            if (currentState[row][col + 1] == -1        || currentState[row][col + 1] == -2){ total++; }
        //        }
        //
        //        else if (row == (ROWS - 1)){
        //            if (currentState[row - 1][col] == -1        || currentState[row - 1][col] == -2){ total++; }
        //            if (currentState[row - 1][col + 1] == -1    || currentState[row - 1][col + 1] == -2){ total++; }
        //            if (currentState[row - 1][col - 1] == -1    || currentState[row - 1][col - 1] == -2){ total++; }
        //            if (currentState[row][col - 1] == -1        || currentState[row][col - 1] == -2){ total++; }
        //            if (currentState[row][col + 1] == -1        || currentState[row][col + 1] == -2){ total++; }
        //        }
        //
        //        else if (col == 0){
        //            if (currentState[row + 1][col + 1] == -1    || currentState[row + 1][col + 1] == -2){ total++; }
        //            if (currentState[row][col + 1] == -1        || currentState[row][col + 1] == -2){ total++; }
        //            if (currentState[row - 1][col + 1] == -1    || currentState[row - 1][col + 1] == -2){ total++; }
        //            if (currentState[row + 1][col] == -1        || currentState[row + 1][col] == -2){ total++; }
        //            if (currentState[row - 1][col] == -1        || currentState[row - 1][col] == -2){ total++; }
        //        }
        //
        //        else if (col == (COLS - 1)){
        //            if (currentState[row + 1][col - 1] == -1    || currentState[row + 1][col - 1] == -2){ total++; }
        //            if (currentState[row + 1][col] == -1        || currentState[row + 1][col] == -2){ total++; }
        //            if (currentState[row - 1][col - 1] == -1    || currentState[row - 1][col - 1] == -2){ total++; }
        //            if (currentState[row - 1][col] == -1        || currentState[row - 1][col] == -2){ total++; }
        //            if (currentState[row][col - 1] == -1        || currentState[row][col - 1] == -2){ total++; }
        //        }
        //
        //        else{
        //            if (currentState[row + 1][col] == -1        || currentState[row + 1][col] == -2){ total++; }
        //            if (currentState[row + 1][col + 1] == -1    || currentState[row + 1][col + 1] == -2){ total++; }
        //            if (currentState[row + 1][col - 1] == -1    || currentState[row + 1][col - 1] == -2){ total++; }
        //            if (currentState[row - 1][col] == -1        || currentState[row - 1][col] == -2){ total++; }
        //            if (currentState[row - 1][col + 1] == -1    || currentState[row - 1][col + 1] == -2){ total++; }
        //            if (currentState[row - 1][col - 1] == -1    || currentState[row - 1][col - 1] == -2){ total++; }
        //            if (currentState[row][col + 1] == -1        || currentState[row][col + 1] == -2){ total++; }
        //            if (currentState[row][col - 1] == -1        || currentState[row][col - 1] == -2){ total++; }
        //        }
    }
    
    /**
     * if previously found that the number of uncovered cells around a cell is equal
     * to the number of mines around it, mare each of those uncovered cells positions as a 1
     * in a new array. this array will then be used to mark each of these cells as a mine.
     */
    
    private int[][] mark(int row, int col, int[][] field){
        
        try{
            if (currentState[row + 1][col] == -1){ field[row + 1][col] = 1; sugestedChange++; }
        } catch (Exception e){}
        try{
            if (currentState[row + 1][col + 1] == -1){ field[row + 1][col + 1] = 1; sugestedChange++; }
        } catch (Exception e){}
        try{
            if (currentState[row + 1][col - 1] == -1){ field[row + 1][col - 1] = 1; sugestedChange++; }
        } catch (Exception e){}
        try{
            if (currentState[row - 1][col] == -1){ field[row - 1][col] = 1; sugestedChange++; }
        } catch (Exception e){}
        try{
            if (currentState[row - 1][col - 1] == -1){ field[row - 1][col - 1] = 1; sugestedChange++; }
        } catch (Exception e){}
        try{
            if (currentState[row - 1][col + 1] == -1){ field[row - 1][col + 1] = 1; sugestedChange++; }
        } catch (Exception e){}
        try{
            if (currentState[row][col + 1] == -1){ field[row][col + 1] = 1; sugestedChange++; }
        } catch (Exception e){}
        try{
            if (currentState[row][col - 1] == -1){ field[row][col - 1] = 1; sugestedChange++; }
        } catch (Exception e){}
        
        return field;
    }
    
    
    /**
     * find all of the currently un-exposed tiles that are safe to expose.
     * for each cell in the 2D array, retrieve the current state of that cell,
     * then check the total number of unexposed cells around that position.
     * if they are equal each of those unexposed cells must be a mine, so mare them
     * to be marked by the game, then return the array or cells to be marked.
     */
    public int[][] findMines(){
        int[][] marked = new int[ROWS][COLS];
        
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                
                int state = currentState[i][j];
                if (state > 0){
                    
                    int numCells = uncovCells(i, j, true);
                    if (numCells == state){
                        marked = mark(i, j, marked);
                        sugestedChange = 0;
                    }
                }
            }
        }
        return marked;
    }
    
    /**
     * find all of the currently un-exposed tiles that are safe to expose.
     * for each cell in the 2D array, retrieve the current state of that cell,
     * then check the total number of marked mines around that position.
     * if they are equal, each unexposed cell must be safe, so mark them
     * to be exposed by the game, then return the array
     */
    
    public int[][] uncoverCells(){
        int[][] uncover = new int[ROWS][COLS];
        
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                
                int state = currentState[i][j];
                if(state > 0){
                    
                    int safeCells = uncovCells(i, j, false);
                    if(safeCells == state){
                        uncover = mark(i, j, uncover);
                    }
                }
            }
        }
        return uncover;
    }
    
    /**
     * returns the number of changes made to the original map
     * this is then used to limit the recursion
     */
    public int changeMade(){
        int changes = sugestedChange;
        sugestedChange = 0;
        return changes;
    }
}
