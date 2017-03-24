package minesweeper.v2;

/**
 *
 * @author Nikandros
 * @version 7th of March 2017
 */
public class Grid {
    
    private Tile[][] minefield;
    private String game;
    
    public Grid(Tile[][] minefield) {
        this.minefield = minefield;
        game = "";
        
        for(int row = 0, limitR = minefield.length; row < limitR; row++) {
            if(row == 0) {
                topLayer();
            } else {
                otherLayers(row);
            }
        }
    }
    
    /**
     * Creates the top layer of the board.
     */
    private void topLayer() {
        for(int column = 0, limitC = minefield[0].length; column < limitC; column++) {
            game += " ---";
        }
        game += "\n";

        for(int column = 0, limitC = minefield[0].length; column < limitC; column++) {
            
            if(column == 0) {
                game += "|";
            }
            
            game += " " + getChar(0, column) + " |";
        }
        game += "\n";

        for(int column = 0, limitC = minefield[0].length; column < limitC; column++) {
            game += " ---";
        }
        game += "\n";
    }
    
    /**
     * Creates all the layers that the <code>topLayer()</code> cannot create.
     * 
     * @param row the row, which creates at that moment.
     * @see #topLayer() 
     */
    private void otherLayers(int row) {
        for(int column = 0, limitC = minefield[0].length; column < limitC; column++) {
            
            if(column == 0) {
                game += "|";
            }
            
            game += " " + getChar(row, column) + " |";
        }
        game += "\n";

        for(int column = 0, limitC = minefield[0].length; column < limitC; column++) {
            game += " ---";
        }
        game += "\n";
    }
    
    /**
     * 
     * @param row
     * @param column
     * @return the character depending its content.
     */
    private String getChar(int row, int column) {
        String character = "";
        Tile tile = minefield[row][column];
        
        
        if(tile.isRevealed()) {
            if(!tile.isMined()) {
                if(tile.getMinedNeighbours() != 0) {
                    character += tile.getMinedNeighbours();
                } else {
                    character += "-";
                }
            } else {
                character += "*";
            }
        } else if(tile.isMarked()) {
            character += ">";

        } else {
            character += "@";
        }
        return character;
    }
    
    public String toString() {
        return game;
    }
}
