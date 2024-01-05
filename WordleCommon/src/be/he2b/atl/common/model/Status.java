package be.he2b.atl.common.model;

import java.io.Serializable;

/**
 * Represents the status of a letter in Wordle. 
 * - If status is WRONG, the letter is not in the word.
 * - If status is WRONG_POSITION, the letter is in the word on another position.
 * - If status is GOOD, the letter is the good one on the good position.
 * 
 * @author François Ugeux
 */
public enum Status implements Serializable {
    WRONG,
    WRONG_POSITION,
    GOOD   
}
