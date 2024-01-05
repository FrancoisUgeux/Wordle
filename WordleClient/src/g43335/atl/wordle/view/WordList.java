package g43335.atl.wordle.view;

import be.he2b.atl.common.model.Word;
import be.he2b.atl.wordle.model.Wordle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * Represents the list of words from the dictionary.
 *
 * @author François Ugeux
 */
public class WordList extends VBox {

    private Label wordsAvalaible;
    private ListView<Word> listView;
    private Wordle wordle;

    /**
     * Creates a list of words.
     * 
     * @param wordle the game
     */
    public WordList(Wordle wordle) {
        this.wordle = wordle;
        initList();
        initCounter();
        getChildren().addAll(listView, wordsAvalaible);

    }

    private void initList() {
        ObservableList<Word> names = FXCollections.observableArrayList(
                wordle.getDictionary()
        );
        listView = new ListView<>(names);
        listView.setPrefHeight(360);
    }

    private void initCounter() {
        wordsAvalaible = new Label(
                "Nombre de mots possibles : " + wordle.getDictionary().size()
        );
        wordsAvalaible.setStyle("-fx-text-fill: white;");
    }

    /**
     * Update the list of word's display.
     */
    public void update() {
        ObservableList<Word> names = FXCollections.observableArrayList(
                wordle.getDictionary());
        listView.setItems(names);
        wordsAvalaible.setText("Nombre de mots possibles :"
                + wordle.getDictionary().size());
    }
}
