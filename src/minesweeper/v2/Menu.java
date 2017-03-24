package minesweeper.v2;

import java.util.Scanner;

/**
 *
 * @author Nikandros
 * @version 7th of March 2017
 */
public final class Menu {
    private Minefield mf;
    private String previousEntry;
    private Scanner kb;
    
    public Menu() {
        kb = new Scanner(System.in);
        previousEntry = "";
        Option option = null;
        boolean stop = false;
        
        do {
            if(option != null && !option.name().equals("QUIT"))
                menu(option);
            Console.clear();
            System.out.print(menuToString());
            
            if((option = input()) == Option.QUIT) {
                stop = option.name().equals(Option.QUIT.toString());
            }
            
        } while(!stop);
        
        System.out.println("You terminated the game");
    }
    
    /**
     * This represents the main menu of the game.
     * 
     * @param option 
     */
    public void menu(Option option) {
        switch(option) {
            case START: if(setup())
                            play();
                break;
            default: System.out.println("This is not an option. (Press enter to continue.)");
                     Console.waitForEnter();
                break;
        }
    }
    
    /**
     * 
     * @return true if the game has been successfully set up, false otherwise.
     */
    public boolean setup() {
        //You can change these variables to change size of the board or the
        //amount of mines on the field.
        int columns = 10;
        int rows = 7;
        int mines = 15;
        
        mf = new Minefield(rows, columns, mines);
        if(mf.getInstance() == null) {
            Console.waitForEnter();
            return false;
        }
        mf.populate();
        return true;
    }
    
    /**
     * This is the main routine for the game. This methods helps to interact with
     * the <code>Minefield</code>.
     */
    public void play() {
        Option option = null;
        boolean stop = false;
        do {
            if(option != null && !option.name().equals("QUIT"))
                if(game(option)) {
                    break;
                }
            whilePlaying();
            System.out.print("Gameplay: ");
            if((option = input()) == Option.QUIT) {
                stop = option.name().equals(Option.QUIT.toString());
            }
        } while(!stop);
    }
    
    /**
     * It contains the menu during the gameplay.
     * 
     * @param option
     * @return true if <code>play()</code> has to stop for any reason, false otherwise.
     * @see #play() 
     */
    public boolean game(Option option) {
        switch(option) {
            case FLAG: mf.mark(coordinatesInput(Option.FLAG.getCharacter()));
                break;
            case REVEAL: if(mf.reveal(coordinatesInput(Option.REVEAL.getCharacter()))) {
                            if(mf.areAllMinesRevealed()) {
                               whilePlaying();
                               System.out.println("YOU WON... (Press enter to start a new game.)");
                               System.out.println();
                               Console.waitForEnter();
                               return true;
                            }
                         } else {
                             return true;
                         }
                break;
            default: System.out.println("This is not an option. (Press enter to continue.)");
                     Console.waitForEnter();
                break;
        }
        return false;
    }
    
    /**
     * This methods verifies that no unsuitable input has been taken for the
     * Coordinates.
     * 
     * @param option if the letter from the enum <code>Option</code> of the 
     *               option selected from the player.
     * @return the coordinates of tile.
     */
    public Coordinates coordinatesInput(char option) {
        
        whilePlaying();
        
        StringBuffer sb = new StringBuffer();
        sb.append("Coordinates: ").append(option).append(" [");
        System.out.print(sb.toString());
        String xText = "";
        String yText = "";
        boolean repeat = false;
        
        do {
            xText = kb.next();
            
            if(repeat = checkForLetters(xText)) {
                whilePlaying();
                System.out.print(sb.toString());
            }
        } while(repeat);
        int x = Integer.parseInt(xText);
        sb.append(x).append(", ");
        whilePlaying();
        System.out.print(sb.toString());
        
        repeat = false;
        do {
            yText = kb.next();
            
            if(repeat = checkForLetters(yText)) {
                whilePlaying();
                System.out.print(sb.toString());
            }
        } while(repeat);
        int y = Integer.parseInt(yText);
        sb.append(y).append("]");
        previousEntry = sb.toString();
        
        return new Coordinates(x, y);
    }
    
    /**
     * 
     * @param text
     * @return true if there are letters, false otherwise.
     */
    public boolean checkForLetters(String text) {
        
        for(int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if(character < '0' || character > '9') {
                System.out.println("You can type just numbers in this field.");
                return true;
            } 
        }
        return false;
    }
    
    /**
     * During the gameplay this pattern can be seen multiple times and I have
     * put it in a method to make it simpler to write.
     */
    public void whilePlaying() {
        Console.clear();
        if(!previousEntry.equals(""))
            System.out.println(previousEntry);
        mf.outputGrid();
    }
    
    /**
     * Gets the Option from the enum <code>Option</code>.
     * 
     * @return 
     * @see Option
     */
    public Option input() {
        String option = kb.next();
        
        char character = option.charAt(0);
        
        if(character >= 'a' && character <= 'z') {
            return Option.getOption((char) (character - 32));
        }
        
        return Option.getOption(character);
    }
    
    /**
     * 
     * @return the main menu of the game.
     */
    public String menuToString() {
        String menu = "====== MENU ======" + "\n";
              menu += " [S] - Start Game " + "\n";
              menu += " [Q] - Quit " + "\n";
              menu += "Option: ";
        
        return menu;
    }
}
