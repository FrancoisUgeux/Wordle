package g43335.atl.wordle.view;

import be.he2b.atl.common.model.Pair;
import be.he2b.atl.common.model.Status;
import javafx.scene.layout.HBox;

/**
 * Represent a word attempt in the game grid.
 *
 * @author François Ugeux
 */
public class WordFrame extends HBox {

    private final LetterFrame[] letters;
    private final int length;

    /**
     * Creates a word frame for the grid.
     * 
     * @param length the length of the word to display.
     */
    public WordFrame(int length) {
        this.letters = new LetterFrame[length];
        this.length = length;
        for (int index = 0; index < length; index++) {
            letters[index] = new LetterFrame();
            getChildren().add(index, letters[index]);
        }
    }

    /**
     * Sets the word in the game grid.
     * 
     * @param word the word to display.
     * @param index the index of the word in a list of word.
     */
    void setLetters(Pair word, int index) {
        for (int letter = 0; letter < length; letter++) {
            char character = word.getKey().getCharAt(letter);
            Status status = word.getValue().getStatus(letter);
            letters[letter].setLetterStyle(character, status);
        }
    }
    /**
     * Remove the word from the game grid.
     */
    void resetWord() {
        for (int letter = 0; letter < length; letter++) {
            char character = ' ';
            Status status = null;
            letters[letter].setLetterStyle(character, status);
        }
    }
}
