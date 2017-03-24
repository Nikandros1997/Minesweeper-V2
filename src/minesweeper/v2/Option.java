package minesweeper.v2;

import java.util.Scanner;

/**
 *
 * @author Nikandros
 * @version 7th of March 2017
 */
public enum Option {
    START('S'),
    QUIT('Q') {
        
        private final String yes = choose('y');
        
        /**
         * As this method is called by default from the object, I decided to
         * override it, in order to interact with the enum without
         * overcomplicating the solution.
         * @return 
         */
        @Override
        public String toString() {
            Scanner kb = new Scanner(System.in);
            System.out.print("Press [y]es to terminate or [n]o to continue. ");
            String text = kb.next();

            char option = text.charAt(0);
            
            return choose(option);
            
        }
        
        /**
         * 
         * @param option input from the user.
         * @return QUIT if y has been chosen, UNKNOWN otherwise.
         */
        public String choose(char option) {
            if(option == 'y') {
                return "QUIT";
            } else {
                return "UNKNOWN";
            }
        }
    },
    FLAG('F'),
    REVEAL('R'),
    UNKNOWN('U');

    private char character;
    
    private Option(char character) {
        this.character = character;
        
        if(character == 'Q') {
            
        }
    }
    
    public static Option getOption(char character) {
        for(Option o : Option.values()) {
            if(character == o.getCharacter()) {
                return o;
            }
        }
        return UNKNOWN;
    }
    
    public char getCharacter() {
        return character;
    }
}
