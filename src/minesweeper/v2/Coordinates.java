package minesweeper.v2;

/**
 *
 * @author Nikandros
 * @version 7th of March 2017
 */
public class Coordinates {
    int x;
    int y;
    
    public Coordinates(int x, int y) {
        this.x = x - 1;
        this.y = y - 1;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
