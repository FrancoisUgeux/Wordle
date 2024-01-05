package g43335.atl.wordle.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * Represents the prompt showing messages to the player.
 *
 * @author François Ugeux
 */
public class Prompt extends Label {

    /**
     * Creates a prompt Label.
     */
    public Prompt() {
        setText("Entrez un mot de 5 lettres");
        setOpacity(100);
        setPrefSize(270, 40);
        setAlignment(Pos.CENTER);
        setStyle("-fx-text-fill: white;"
                + "-fx-font: bold 12px Verdana;");
    }

    /**
     * Display message when game is over.
     */
    public void setIsOver() {
        setText("Désolé, vous avez perdu.");
    }

    /**
     * Display message when game is won.
     */
    public void setIsFound() {
        setText("Félicitation !");
    }

    /**
     * Display error message for the user in french.
     * 
     * @param error the kind of error to display.
     */
    public void setErrorFrench(String error) {
        switch (error) {
            case "length":
                setText("Erreur : Mot de 5 lettres attendu");
                break;
            case "dictionary":
                setText("Erreur : Mot absent du dictionnaire");
                break;
            default:
                setText("Erreur inconnue");
        }
    }

    public final void reset(){
        setText("Entrez un mot de 5 lettres");
    }
}
