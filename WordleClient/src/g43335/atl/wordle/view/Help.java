package g43335.atl.wordle.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 *
 * @author François Ugeux
 */
public class Help extends Button {

    private final String helpText;

    public Help() {
        this.setText("Help!");
        helpText = "Entrez un mot de 5 lettres présent dans la liste présentée a droite de l'écran"
                + " \n - Une lettre en bonne position donne 2 points"
                + " \n - Une lettre juste mais pas en bonne position donne 1 point";
        this.setOnAction((ActionEvent t) -> {
            showHelp();
        });
    }

    private void showHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("help");
        alert.setContentText(helpText);
        alert.show();
    }
}