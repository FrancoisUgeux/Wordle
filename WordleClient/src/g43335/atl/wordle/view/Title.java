package g43335.atl.wordle.view;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents the title of the game.
 *
 * @author François Ugeux
 */
public class Title extends Label {

    /**
     * Create a title.
     * 
     * @param title the title to display.
     */
    public Title(String title) {
        setText(title);
        setFont(Font.font("Impact", FontWeight.BOLD, 40));
        setStyle("-fx-text-fill: white;");
    }
}
