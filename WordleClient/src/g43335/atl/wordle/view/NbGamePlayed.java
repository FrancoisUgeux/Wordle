package g43335.atl.wordle.view;

import javafx.scene.control.Label;

/**
 *
 * @author François Ugeux
 */
public class NbGamePlayed extends Label{
    /**
     * Creates a NbPartieJouee prompt.
     * 
     * @param easyMode the number of easy game played to display.
     */
    
    public NbGamePlayed(int easyMode) { 
        setText("Nombre de partie : " + easyMode);
        setStyle(
                "-fx-text-fill: white;"
                + "-fx-font: bold 12px Verdana;"
        );
    }

    /**
     * Update the NbgamePlayed prompt.
     * 
     * @param easyMode the number of easy game played to display.
     */
    public void update(int easyMode) {
        setText("Nombre de partie : " + easyMode); 
    }
}
