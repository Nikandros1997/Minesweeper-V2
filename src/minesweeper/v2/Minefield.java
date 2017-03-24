package minesweeper.v2;

import java.util.Random;

/**
 *
 * @author Nikandros
 * @version 7th of March 2017
 */
public class Minefield {
    
    private Tile[][] minefield;
    private int mines, currentMine;
    private static Minefield mf;
    
    /**
     * @return the Minefield of the current game.
     */
    public static Minefield getInstance() {
        if(mf != null) {
            return mf;
        } else {
            return null;
        }
    }
    
    /**
     * 
     * 
     * @param rows 
     * @param columns
     * @param mines maximum number of mines in the game
     */
    public Minefield(int rows, int columns, int mines) {
        if((rows < 2 || columns < 2) || (mines >= rows * columns)) {
            System.out.println("This game has not been established correctly.(Press enter to continue.)");
            return ;
        }
        
        this.minefield = new Tile[rows][columns];
        
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                minefield[i][j] = new Tile();
            }
        }
        
        this.mines = mines;
        currentMine = 0;
        
        mf = this;
    }
    
    /**
     * Randomly generates the coordinates for a mine to be planted.
     */
    public void populate() {
        Random r = new Random();
        
        while(lessMines()) {
            int x = r.nextInt(minefield.length) + 1;
            int y = r.nextInt(minefield[0].length) + 1;
            
            if(mineTile(new Coordinates(x, y))) {
                currentMine++;
            }
        }
    }
    
    /**
     * 
     * @param input the coordinates of the cell that we want to mark.
     */
    public void mark(Coordinates input) {
        int x = input.getX();
        int y = input.getY();
        
        if(checkBoundaries(input) && !minefield[x][y].isRevealed())
           minefield[x][y].changeMark();
    }
    
    /**
     * Outputs the grid for the game.
     */
    public void outputGrid() {
        if(mf != null)
            System.out.println(new Grid(minefield));
    }
    
    /**
     * Plants a mine in the field in a random cell.
     * 
     * @param input the coordinates for a specific tile.
     * @return True if mine has been planted, false otherwise.
     */
    public boolean mineTile(Coordinates input) {
        
        int x = input.getX();
        int y = input.getY();
        
        Tile tile = minefield[x][y];
        
        if(!tile.isMined() && !topLeftCorner(input)) {
            //plant the mine
            tile.mine();
            
            //increment the surroundings by one
            for(int i = x - 1; i <= x + 1; i++) {
                for(int j = y - 1; j <= y + 1; j++) {
                    if((i > -1 && j > -1) && (i < minefield.length && j < minefield[0].length)) {
                        minefield[i][j].incrementMinedNeighbours();
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    /**
     * @return true if the numbering of the mine planted is smaller than 
     * the higher limit, false otherwise.
     */
    public boolean lessMines() {
        return currentMine < mines;
    }
    
    /**
     * @param input coordinates for the specific tile
     * @return true if it is top left corner, false otherwise.
     */
    public boolean topLeftCorner(Coordinates input) {
        return input.getX() == 0 && input.getY() == 0;
    }
    
    /**
     * @param input
     * @return true if input has coordinates smaller than the length of the array.
     * @see #reveal(minesweeper.v2.Coordinates)
     */
    public boolean upperCoordinatesVerification(Coordinates input) {
        return input.getX() < minefield.length && input.getY() < minefield[0].length;
    }
    
    /**
     * 
     * @param input
     * @return true if input has coordinates greater than 0.
     * @see #reveal(minesweeper.v2.Coordinates) 
     */
    public boolean lowerCoordinatesVerification(Coordinates input) {
        return input.getX() > -1 && input.getY() > -1;
    }
    
    /**
     * 
     * @param input
     * @return false means that we have lost the game, as we clicked on a mine,
     *         true for a every other case.
     */
    public boolean reveal(Coordinates input) {
        if(checkBoundaries(input) && !minefield[input.getX()][input.getY()].isMarked() && !step(input)) {
            Console.clear();
            System.out.println("You hit mine. Game over.");
            for(int i = 0; i < minefield.length; i++)
                for(int j = 0; j < minefield[0].length; j++)
                    if(!minefield[i][j].isRevealed())
                        minefield[i][j].reveal();
            outputGrid();
            System.out.println("(Press enter to start a new game...)");
            Console.waitForEnter();
            return false;
        }
        return true;
    }
    
    /**
     * This methods does not allow to the user to do an action outside the
     * board.
     * 
     * @param input
     * @return true if input is inside the field, false otherwise
     */
    public boolean checkBoundaries(Coordinates input) {
        if(!lowerCoordinatesVerification(input) || !upperCoordinatesVerification(input)) {
            System.out.println("Be careful with the coordinates.");
            System.out.println("The board is " + minefield.length + " X " + minefield[0].length + ".");
            System.out.println("(Press enter to continue.)");
            Console.waitForEnter();
            return false;
        }
        return true;
    }
    
    /**
     * When it reveals all the 0's on the minefield, this method is used to
     * inform a loop if that was the tile clicked or initiated some actions.
     * 
     * @param input
     * @param row
     * @param col
     * @return true if that was the tile clicked, false otherwise.
     */
    public boolean centralNode(Coordinates input, int row, int col) {
        return input.getX() == col && input.getY() == row;
    }
    /**
     * This is a recursive method, which progressively tries to reveal all the
     * tiles without mines. 
     * 
     * @param input coordinates for a specific tile
     * @return 
     */
    public boolean step(Coordinates input) {
        
        int x = input.getX();
        int y = input.getY();
        
        minefield[x][y].reveal();
        
        if(minefield[x][y].isMined()) {
            return false;
        } else if(minefield[x][y].getMinedNeighbours() == 0) {
            for(int i = x - 1; i <= x + 1; i++) {
                for(int j = y - 1; j <= y + 1; j++) {
                    Coordinates inputR = new Coordinates(i + 1, j + 1);
                    if(lowerCoordinatesVerification(inputR) && upperCoordinatesVerification(inputR)) {
                        if(minefield[x][y].getMinedNeighbours() == 0) {
                            if(!minefield[i][j].isMarked() && !minefield[i][j].isMined()) {
                                if(!minefield[i][j].isRevealed())
                                    step(inputR);
                                minefield[i][j].reveal();
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    /**
     *
     * @return true if mines are correctly marked, false otherwise.
     */
    public boolean areAllMinesRevealed() {
        for(int i = 0; i < minefield.length; i++) {
            for(int j = 0; j < minefield[0].length; j++) {
                if(!minefield[i][j].isRevealed() && !minefield[i][j].isMined()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Outputs the combined arrays of <code>minefield</code> and <code>
     * minedNeighbours</code>.
     * @return the raw representation of the field
     */
    @Override
    public String toString() {
        String game = "";
        
        for(int row = 0; row < minefield.length; row++) {
            for(int column = 0; column < minefield[0].length; column++) {
                Tile tile = minefield[row][column];
                
                if(tile.isMined()) {
                    game += "* ";
                } else {
                    game += tile.getMinedNeighbours() + " ";
                }
            }
            game += "\n";
        }
        //game += "\n";
        return game;
    }
    
    /**
     * @return the number of mines in the field.
     */
    public int getMines() {
        return mines;
    }
    
    /**
     * Get the mine that has curently been planted in the field.
     * @return 
     */
    public int getCurrentMine() {
        return currentMine;
    }
    
    public Tile[][] getMinefield() {
        return minefield;
    }
    
    public Tile getTile(Coordinates input) {
        return minefield[input.getX()][input.getY()];
    }
}
