package be.he2b.atl.common.model;

import java.io.Serializable;
import java.util.Objects;
/**
 * Class representing a word.
 *
 * @author François Ugeux
 */
public class Word implements Serializable {

    private final int length; 
    private final String word;

    /**
     * Creates a word by copy
     *
     * @param word the word to copy.
     */
    public Word(Word word) {
        this.length = word.length;
        this.word = word.word;
    }

    /**
     * Creates a word. The word is created from a given String.
     *
     * @param word the String used to create the word.
     */
    public Word(String word) {
        this.length = word.length();
        this.word = word.toUpperCase();
    }

    /**
     * Gives the length of the word.
     *
     * @return the length of the word.
     */
    public int getLength() {
        return length;
    }
    
    public String getWord() {
        return word;
    }
 
    /**
     * Gives the character of the word at a given index.
     *
     * @param index the index of the character.
     * @return the character at the given index.
     */
    public char getCharAt(int index) {
        return word.charAt(index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.word);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return this.word.equals(obj.toString());
    }

    @Override
    public String toString() {
        return word;
    }
}
