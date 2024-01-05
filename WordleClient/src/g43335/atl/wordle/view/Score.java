package g43335.atl.wordle.view;

import javafx.scene.control.Label;

/**
 * Represent the score prompt.
 *
 * @author François Ugeux
 */
public class Score extends Label {

    /**
     * Creates a score prompt.
     * 
     * @param score the score to display.
     */
    public Score(int score) {
        setText("Score : " + score);
        setStyle(
                "-fx-text-fill: white;"
                + "-fx-font: bold 12px Verdana;"
        );
    }

    /**
     * Update the score prompt.
     * 
     * @param score the score to display.
     */
    public void update(int score) {
        setText(" Score : " + score);
    }

}
