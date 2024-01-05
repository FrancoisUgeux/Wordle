
package g43335.atl.wordle.view;

import be.he2b.atl.wordle.model.Wordle;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 *
 * @author François Ugeux
 */
public class NewGame extends Button{
    private Wordle refToWordle;

    public NewGame(Wordle wordle) {
        this.refToWordle = wordle;
        this.setText("New game!");
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    reload();
                } catch (IOException ex) {
                    Logger.getLogger(NewGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void reload() throws IOException {
        refToWordle.askForNewGame();
    }
}