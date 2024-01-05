package be.he2b.atl.common.model;

/**
 * Contains the data of the game.
 *
 * @author François Ugeux
 */
public class GameData{

    private static final int NB_LETTERS = 5;
    private static final int NB_ATTEMPT = 6;

    /**
     * Gives the number of letter a word must have in the game.
     *
     * @return
     */
    public static int getNbLetters() {
        return NB_LETTERS;
    }

    /**
     * Gives the number total of attemps a player can have in the game.
     *
     * @return
     */
    public static int getNbAttempt() {
        return NB_ATTEMPT;
    }

}
