package minesweeper.v2;

/**
 *
 * @author Nikandros
 * @version 7th of March 2017
 */
public class Tile {
    
    private int minedNeighbours;
    private boolean mined;
    private boolean revealed;
    private boolean marked;
    
    public Tile() {
        minedNeighbours = 0;
        mined = false;
        revealed = false;
        marked = false;
    }
    
    public void incrementMinedNeighbours() {
        minedNeighbours++;
    }
    
    /**
     * It miens the tile and it cannot be unmined.
     */
    public void mine() {
        mined = true;
    }
    
    /**
     * It reveals the tile and it cannot be unrevealed.
     */
    public void reveal() {
        revealed = true;
    }
    
    /**
     * Changes the current state of <code>marked</code> to its opposite.
     */
    public void changeMark() {
        marked = !marked;
    }
    
    //Getter methods
    public int getMinedNeighbours()  {
        return minedNeighbours;
    }
    
    public boolean isMined() {
        return mined;
    }
    
    public boolean isRevealed() {
        return revealed;
    }
    
    public boolean isMarked() {
        return marked;
    }
    
}
