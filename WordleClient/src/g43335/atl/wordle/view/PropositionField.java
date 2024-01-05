package g43335.atl.wordle.view;

import be.he2b.atl.common.model.Word;
import be.he2b.atl.wordle.model.Wordle;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;

/**
 * Represents the field where the player submit an attempt.
 *
 * @author François Ugeux
 */
public final class PropositionField extends TextField {

    Wordle wordle;
    Prompt prompt;

    /**
     * Creates a text field.
     * 
     * @param wordle the game.
     * @param prompt the prompt where messages are displayed.
     */
    public PropositionField(Wordle wordle, Prompt prompt) {
        this.wordle = wordle;
        this.prompt = prompt;
        setAlignment(Pos.CENTER);
        setPrefHeight(40);
        setStyle("-fx-background-color: white;"
                + "-fx-text-fill: black;"
                + "-fx-font: bold 14px Verdana;");
        setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
        setEnterAction();
    }

    private void setEnterAction() {
        setOnKeyPressed(ke -> {
            KeyCode keyCode = ke.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                try {
                    Word word = new Word(getText());
                    wordle.attempt(word);
                } catch (Exception e) {
                    prompt.setErrorFrench(e.getMessage());
                }
                setText("");
                prompt.requestFocus();
            }
        });
    }

    /**
     * Disable word submissions in the field.
     */
    public void freeze() {
        setDisable(true);
    }
    
    public void unfreeze(){
        setDisable(false);
    }
}
