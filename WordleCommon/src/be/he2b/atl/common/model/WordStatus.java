package be.he2b.atl.common.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class represent an array of status. It is used to give the status of a
 * word's letters.
 *
 * @author François Ugeux
 */
public class WordStatus implements Serializable {

    Status[] letterStatus;
    int size;

    /**
     * Create a default array of status.
     *
     * @param length the size of the array (corresponding to a word length)
     */
    public WordStatus(int length) {
        size = length;
        letterStatus = new Status[length];
        for (int i = 0; i < length; i++) {
            letterStatus[i] = Status.WRONG;

        }
    }

    /**
     * Creates a word status from an array of status.
     * 
     * @param status the array of status .
     */
    public WordStatus(Status[] status) {
        size = status.length;
        letterStatus = new Status[size];
        for (int letter = 0; letter < size; letter++) {
            setStatus(status[letter], letter);
        }
    }

    /**
     * Create a status array by copy.
     *
     * @param other the WordStatus to copy.
     */
    public WordStatus(WordStatus other) {
        size = other.size;
        letterStatus = new Status[size];
        for (int letter = 0; letter < size; letter++) {
            setStatus(other.getStatus(letter), letter);
        }
    }

    /**
     * Gives the size of the status array.
     *
     * @return the size of the status array.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the status at a given index.
     *
     * @param status the status to set.
     * @param index the index where to set the status.
     */
    public void setStatus(Status status, int index) {
        letterStatus[index] = status;
    }

    /**
     * Gives the status at a given index.
     *
     * @param index the index of the status to get.
     * @return the status at a given index.
     */
    public Status getStatus(int index) {
        return letterStatus[index];
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.letterStatus);
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
        final WordStatus other = (WordStatus) obj;
        if (!Arrays.deepEquals(this.letterStatus, other.letterStatus)) {
            return false;
        }
        return true;
    }
}
