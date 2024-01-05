package g43335.atl.wordle.view;

import be.he2b.atl.common.model.Pair;
import java.util.List;
import javafx.scene.layout.VBox;
/**
 * Represents the grid showing the attempts of the player.
 * 
 * @author François Ugeux
 */
public class GameGrid extends VBox {

    private final WordFrame[] wordFrame;
    private final int nbAttempts;
    private final int nbLetters;

    /**
     * Creates a grid for the view.
     * 
     * @param nbAttempts the number of attempts.
     * @param nbLetters the number of letters in a word.
     */
    public GameGrid(int nbAttempts, int nbLetters) {
        this.nbAttempts = nbAttempts;
        this.nbLetters = nbLetters;
        this.wordFrame = new WordFrame[nbAttempts];
        this.setStyle("-fx-background-color: steelblue;");
        initGrid();
    }

    private void initGrid() {
        for (int attempt = 0; attempt < nbAttempts; attempt++) {
            wordFrame[attempt] = new WordFrame(nbLetters);
            getChildren().add(attempt, wordFrame[attempt]);
        }
    }

    /**
     * Update the grid. Sets the letters and their status on the grid.
     * 
     * @param words the words and their status. 
     */
    public void update(List<Pair> words) {
        resetGrid();
        for (int attempt = 0; attempt < words.size(); attempt++) {
            Pair word = words.get(attempt);
            wordFrame[attempt].setLetters(word, attempt);
        }
    }

    private void resetGrid() {
        for (int attempt = 0; attempt < nbAttempts; attempt++) {
            wordFrame[attempt].resetWord();
        }
    }
}
