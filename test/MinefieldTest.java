import minesweeper.v2.Coordinates;
import minesweeper.v2.Minefield;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the minesweeper class.
 *
 * @author 146688
 * @version 21 of February 2017
 */
public class MinefieldTest {
    
    public MinefieldTest() {
        
    }
    
    @Before
    public void setUp() {
        
    }
    
    @Test
    public void mineTileTest() {
        //Test for checking that a mine has been planted successfully
        Minefield mf = new Minefield(10, 7, 11);
        Coordinates input = new Coordinates(5, 5);
        
        assertEquals(mf.mineTile(input), true);
    }
    
    @Test
    public void mineTileTest2() {
        //Test for checking if it can plant a mine in cell 0,0.
        Minefield mf = new Minefield(10, 7, 11);
        Coordinates input = new Coordinates(1, 1);
                
        assertEquals(mf.mineTile(input), false);
    }
    
    @Test
    public void mineTileTest3() {
        //Test for cehcking if it can plant a mine in an already planted cell.
        Minefield mf = new Minefield(10, 7, 11);
        Coordinates input = new Coordinates(4, 4);
        
        boolean minePlanted = mf.mineTile(input);
        
        assertEquals(mf.mineTile(input), !minePlanted);
    }
    
    @Test
    public void populateTest() {
        //Test for checking that the corrent amount of mines have been planted.
        Minefield mf = new Minefield(10, 7, 100);
        mf.populate();
        assertEquals(mf.getCurrentMine(), mf.getMines());
    }
    
    @Test
    public void populateTest2() {
        //Test for checking if there is mine in cell 0, 0 after running
        //the populate
        Minefield mf = new Minefield(3, 3, 8);
        mf.populate();
        assertEquals(mf.getMinefield()[0][0].isMined(), false);
    }
    
    @Test
    public void toStringTest() {
        //Test for checking the output string when all the cells are full
        int row = 5;
        int column = 5;
        Minefield mf = new Minefield(row, column, row * column - 1);
        mf.populate();
        
        String testString = "3 * * * * \n* * * * * \n* * * * * \n* * * * * \n* * * * * \n";
        
        assertEquals(testString, mf.toString());
    }
    
    @Test
    public void toStringTest2() {
        //Test for checking the output string when all the cells are empty
        Minefield mf = new Minefield(5, 5, 0);
        mf.populate();
        
        String testString = "0 0 0 0 0 \n0 0 0 0 0 \n0 0 0 0 0 \n0 0 0 0 0 \n0 0 0 0 0 \n";
        
        assertEquals(testString, mf.toString());
    }
    
    @Test
    public void toStringTest3() {
        //Test for checking the output string when mines with specific
        //coordinates have been planted.
        Minefield mf = new Minefield(3, 3, 0);
        Coordinates input = new Coordinates(2, 2);
        Coordinates input2 = new Coordinates(3, 1);   

        
        mf.mineTile(input);
        mf.mineTile(input2);
        String testString = "1 1 1 \n2 * 1 \n* 2 1 \n";
        assertEquals(testString, mf.toString());
    }
    
    @Test
    public void markTest() {
        //check if the tile has been marked.
        Minefield mf = new Minefield(3, 3, 0);
        Coordinates input = new Coordinates(3, 3);
        
        boolean before = mf.getTile(input).isMarked();
        mf.mark(input);
        boolean after = mf.getTile(input).isMarked();
        assertEquals(true, after);
    }
    
    @Test
    public void markTest2() {
        //mark and unmark a tile
        Minefield mf = new Minefield(3, 3, 0);
        Coordinates input = new Coordinates(3, 3);
        
        boolean before = mf.getTile(input).isMarked();
        mf.mark(input);
        mf.mark(input);
        boolean theEnd = mf.getTile(input).isMarked();
        
        assertEquals(false, theEnd);
    }
    
    @Test
    public void markTest3() {
        //before and after marking a tile has to have different values.
        Minefield mf = new Minefield(3, 3, 0);
        Coordinates input = new Coordinates(3, 3);
        
        boolean before = mf.getTile(input).isMarked();
        mf.mark(input);
        boolean after = mf.getTile(input).isMarked();
        
        assertEquals(before, !after);
    }
    
    @Test
    public void areAllMinesRevealedTest() {
        //check for no mines in the field
        Minefield mf = new Minefield(3, 3, 0);
        
        assertEquals(false, mf.areAllMinesRevealed());
    }
    
    @Test
    public void areAllMinesRevealedTest2() {
        //check if all the mines has been revealed - no tile has been altered
        //after mined.
        
        Minefield mf = new Minefield(3, 3, 0);
        Coordinates input = new Coordinates(3, 3);
        Coordinates input2 = new Coordinates(1, 3);
        Coordinates input3 = new Coordinates(2, 3);
        Coordinates input4 = new Coordinates(3, 1);
        
        mf.mineTile(input);
        mf.mineTile(input2);
        mf.mineTile(input3);
        mf.mineTile(input4);
        
        assertEquals(false, mf.areAllMinesRevealed());
        //it means that there are many more unrevealed tiles.
    }
    
    @Test
    public void areAllMinesRevealled3() {
        //check if all mines has been revealed - all the mines has been altered.
        //after mined.
        
        Minefield mf = new Minefield(5, 5, 0);
        Coordinates input = new Coordinates(3, 3);
        Coordinates input2 = new Coordinates(1, 3);
        Coordinates input3 = new Coordinates(2, 3);
        Coordinates input4 = new Coordinates(3, 1);
        
        mf.mineTile(input);
        mf.mineTile(input2);
        mf.mineTile(input3);
        mf.mineTile(input4);
        
        for(int i = 1; i <= mf.getMinefield().length; i++) {
            for(int j = 1; j <= mf.getMinefield()[0].length; j++) {
                if(!((mf.getTile(input)).equals(mf.getTile(new Coordinates(i, j))) ||
                    (mf.getTile(input2)).equals(mf.getTile(new Coordinates(i, j))) ||
                    (mf.getTile(input3)).equals(mf.getTile(new Coordinates(i, j))) ||
                    (mf.getTile(input4)).equals(mf.getTile(new Coordinates(i, j))))) {
                    mf.getTile(new Coordinates(i, j)).reveal();
                }
            }
        }
        
        assertEquals(true, mf.areAllMinesRevealed());
    }
    
    @Test
    public void areAllMinesRevealled4() {
        //no mines and the reveal method is run. It will reveal all the empty
        //tiles, which means that we will automatically have revealled all the
        //tiles.
        
        Minefield mf = new Minefield(5, 5, 0);
        Coordinates input = new Coordinates(3, 3);
        mf.reveal(input);
        
        assertEquals(true, mf.areAllMinesRevealed());
    }
    
    @Test
    public void lowerCoordinatesVerificationTest() {
        //test for coordinates smaller than 1
        Minefield mf = new Minefield(5, 5, 0);
        Coordinates input = new Coordinates(0, 0);
        assertEquals(false, mf.lowerCoordinatesVerification(input));
    }
    
    @Test
    public void lowerCoordinatesVerificationTest2() {
        //test for x >= 0 and y < 1
        Minefield mf = new Minefield(5, 5, 0);
        Coordinates input = new Coordinates(1, 0);
        assertEquals(false, mf.lowerCoordinatesVerification(input));
    }
    
    @Test
    public void lowerCoordinatesVerificationTest3() {
        //test for x < 1 and y >= 1
        Minefield mf = new Minefield(5, 5, 0);
        Coordinates input = new Coordinates(0, 1);
        assertEquals(false, mf.lowerCoordinatesVerification(input));
    }
    
    @Test
    public void lowerCoordinatesVerificationTest4() {
        //test for x >= 1 and y >= 1
        Minefield mf = new Minefield(5, 5, 0);
        Coordinates input = new Coordinates(1, 1);
        assertEquals(true, mf.lowerCoordinatesVerification(input));
        // it is an acceptable value
    }
    
    @Test
    public void upperCoordinatesVerificationTest() {
        //test for x > boardLength and y > boardLength
        Minefield mf = new Minefield(5, 5, 0);
        Coordinates input = new Coordinates(6, 6);
        assertEquals(true, mf.lowerCoordinatesVerification(input));
    }
    
    @Test
    public void upperCoordinatesVerificationTest2() {
        //test for x < boardLength and y > boardLength
        Minefield mf = new Minefield(5, 5, 0);
        Coordinates input = new Coordinates(5, 6);
        assertEquals(true, mf.lowerCoordinatesVerification(input));
    }
    
    @Test
    public void upperCoordinatesVerificationTest3() {
        //test for x > boardLength and y < boardLength
        Minefield mf = new Minefield(5, 5, 0);
        Coordinates input = new Coordinates(6, 5);
        assertEquals(true, mf.lowerCoordinatesVerification(input));
        // it is an acceptable value
    }
    
    @Test
    public void upperCoordinatesVerificationTest4() {
        //test for x < boardLength and y < boardLength
        Minefield mf = new Minefield(5, 5, 0);
        Coordinates input = new Coordinates(5, 5);
        assertEquals(true, mf.lowerCoordinatesVerification(input));
        // it is an acceptable value
    }
    
    @Test
    public void stepTest() {
        //
        Minefield mf = new Minefield(10, 10, 0);
        
        mf.mineTile(new Coordinates(2, 1));
        
        assertEquals(true, mf.step(new Coordinates(1, 1)));
    }
    
    @Test
    public void stepTest2() {
        Minefield mf = new Minefield(10, 10, 0);
        
        mf.mineTile(new Coordinates(2, 1));
        assertEquals(false, mf.step(new Coordinates(2, 1)));
    }
}
