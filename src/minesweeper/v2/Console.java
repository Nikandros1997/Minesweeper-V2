package minesweeper.v2;

import java.util.Scanner;

/**
 *
 * @author Nikandros
 * @version 7th of March 2017
 */
public class Console {
    private static String OS = System.getProperty("os.name").toLowerCase();
    
    /**
     * Clears the console screen.
     */
    public static void clear() {
        if(isMac())
            System.out.print("\033[H\033[2J");
        else if(isWindows() || isUnix() || isSolaris()) {
            System.err.println("Clear operation is not supported for " + OS);
        }
    }
    
    /**
     * Waits for enter from the user.
     */
    public static void waitForEnter() {
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }
    
    private static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    private static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    private static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }

    private static boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }
}
