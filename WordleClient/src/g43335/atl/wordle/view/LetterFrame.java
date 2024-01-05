package g43335.atl.wordle.view;

import be.he2b.atl.common.model.Status;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * Represents a square in the game grid.
 *
 * @author François Ugeux
 */
public class LetterFrame extends Label {

    /**
     * Creates a square for the game grid.
     */
    public LetterFrame() {
        setAlignment(Pos.CENTER);
        setStyle(setDefaultStyle());
        setPrefWidth(60);
        setPrefHeight(60);
    }

    /**
     * Sets a letter and a status representation on the square.
     * 
     * @param letter the letter to set.
     * @param status the satus to represent.
     */
    void setLetterStyle(char letter, Status status) {
        setText("" + letter);
        if (status == Status.GOOD) {
            setStyle(setGoodStyle());
        }
        if (status == Status.WRONG_POSITION) {
            setStyle(setWrongPositionStyle());
        }
        if (status == Status.WRONG || status == null) {
            setStyle(setDefaultStyle());
        }
    }

    private String setGoodStyle() {
        return "-fx-background-color: indianred;"
                + "-fx-text-fill: white;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 20px;"
                + "-fx-border-color : white";
    }

    private String setWrongPositionStyle() {
        return "-fx-text-fill: white;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 20px;"
                + "-fx-background-color : #ffa64d;"
                + "-fx-background-radius: 50%;"
                + "-fx-border-color : white";
    }

    private String setDefaultStyle() {
        return "-fx-text-fill: white;"
                + "-fx-background-color: steelblue;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 20px;"
                + "-fx-border-color : white";
    }
}
