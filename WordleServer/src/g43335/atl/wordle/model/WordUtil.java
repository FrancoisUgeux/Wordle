package g43335.atl.wordle.model;

import be.he2b.atl.common.model.Pair;
import be.he2b.atl.common.model.Status;
import be.he2b.atl.common.model.Word;
import be.he2b.atl.common.model.WordStatus;
import java.util.List;

/**
 * Class representing a word.
 *
 * @author François Ugeux
 */
public class WordUtil {

    /**
     * Gives the character of the word at a given index.
     *
     * @param index the index of the character.
     * @return the character at the given index.
     */
    public static char getCharAt(Word word, int index) {
        return word.getCharAt(index);
    }

    /**
     * Checks if the word exists in Dictionary.
     *
     * @return true if the word exist, else false.
     * @throws IllegalArgumentException if the word is empty (length = 0).
     */
    public static boolean exist(Word word) {
        if (word.getLength() == 0) {
            throw new IllegalArgumentException("Mot vide");
        }
        Dictionary dico = new Dictionary(word.getLength());
        int i = 0;
        while (i < dico.getSize()) {
            if (dico.getWord(i).equals(word)) {
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * Checks if letters in a given word match with those of this word. The 2
     * words must have the same length. Depending of the status of the
     * characters of the given word : - if the status is GOOD, checks if the 2
     * letters are similar. - if the status is WRONG_POSITION, checks if the
     * letter of the given word is in this word.
     *
     * @param other the other word used to check.
     * @param status the status of the characters of the other word.
     * @throws IllegalArgumentException if the two words are not the same
     * length.
     * @return true if there is a match between letters, else false.
     */
    public static boolean match(Word word, Word other, WordStatus status) {
        if (word.getLength() != other.getLength()) {
            throw new IllegalArgumentException("Longueur de mots differentes");
        }
        boolean[] isChecked = new boolean[word.getLength()];
        if (!goodMatch(word, other, status, isChecked)) {
            return false;
        }
        for (int otherIndex = 0; otherIndex < word.getLength(); otherIndex++) {
            if (!wrongPositionMatch(word, other, status, isChecked, otherIndex)
                    || !wrongMatch(word, other, status, isChecked, otherIndex)) {
                return false;
            }
        }
        return true;
    }

    private static boolean goodMatch(Word word, Word other, WordStatus status, boolean[] isChecked) {
        for (int index = 0; index < word.getLength(); index++) {
            if (status.getStatus(index) == Status.GOOD) {
                if (other.getCharAt(index) != word.getCharAt(index)) {
                    return false;
                }
                isChecked[index] = true;
            }
        }
        return true;
    }

    private static boolean wrongPositionMatch(Word word, Word other, WordStatus status, boolean[] isChecked, int otherIndex) {
        if (status.getStatus(otherIndex) == Status.WRONG_POSITION) {
            boolean found = false;
            int index = 0;
            while (index < word.getLength() && !found) {
                if (other.getCharAt(otherIndex) == word.getCharAt(index)
                        && isChecked[index] == false) {
                    isChecked[index] = true;
                    found = true;
                }
                index++;
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    private static boolean wrongMatch(Word word, Word other, WordStatus status, boolean[] isChecked, int otherIndex) {
        if (status.getStatus(otherIndex) == Status.WRONG) {
            int index = 0;
            while (index < word.getLength()) {
                if (isChecked[index] == false
                        && other.getCharAt(otherIndex) == word.getCharAt(index)) {
                    return false;
                }
                index++;
            }
        }
        return true;
    }

    /**
     * Checks if the word contain a wrong letter.A letter is wrong if its status
     * is WRONG_POSITION on another word and is on the same position in this
     * word or if its status in WRONG on another word and is present in this
     * word.
     *
     * @param words the different words to check and their status.
     * @param lastWordIndex the last word index of the array of words.
     * @throws IllegalArgumentException if the two words are not the same
     * length.
     * @return true if it contains a wrong letter, else false.
     */
    public static boolean containWrongLetters(Word word, List<Pair> words, int lastWordIndex) {
        if (words.isEmpty()) {
            throw new IllegalArgumentException("no words avalaible");
        }
        int wordIndex = 0;
        while (wordIndex <= lastWordIndex) {
            int letterIndex = 0;
            while (letterIndex < word.getLength()) {
                if (word.getLength() != words.get(wordIndex).getKey().getLength()) {
                    throw new IllegalArgumentException("Longueur de mots differentes");
                }
                Pair reference = words.get(wordIndex);
                if (checkWrongPosition(word, reference, letterIndex)
                        || checkWrong(word, reference, letterIndex)) {
                    return true;
                }
                letterIndex++;
            }
            wordIndex++;
        }
        return false;
    }

    private static boolean checkWrongPosition(Word word, Pair pword, int letterIndex) {
        if (pword.getValue().getStatus(letterIndex) == Status.WRONG_POSITION) {
            if (pword.getKey().getCharAt(letterIndex)
                    == word.getCharAt(letterIndex)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkWrong(Word word, Pair pword, int letterIndex) {
        if (pword.getValue().getStatus(letterIndex) == Status.WRONG) {
            for (int toCheck = 0; toCheck < word.getLength(); toCheck++) {
                Status status = pword.getValue().getStatus(toCheck);
                if (status == Status.WRONG) {
                    if (pword.getKey().getCharAt(letterIndex) == word.getCharAt(toCheck)
                            && !isGoodLetter(word, pword, pword.getKey().getCharAt(letterIndex))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isGoodLetter(Word word, Pair pword, char c) {
        int index = 0;
        while (index < word.getLength()) {
            if (c == pword.getKey().getCharAt(index)) {
                if (pword.getValue().getStatus(index) == Status.GOOD
                        || pword.getValue().getStatus(index) == Status.WRONG_POSITION) {
                    return true;
                }
            }
            index++;
        }
        return false;
    }

}
