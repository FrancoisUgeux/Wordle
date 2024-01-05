package be.he2b.atl.wordle.view.fx;

import be.he2b.atl.common.model.GameData;
import be.he2b.atl.common.model.Pair;
import be.he2b.atl.wordle.model.Wordle;
import g43335.atl.wordle.view.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX WordleView / wordleView for Wordle.
 */
public class WordleView extends VBox implements Observer {

    private final Stage primaryStage;
    private HBox mainField;
    private VBox gameField;
    private VBox infoField;
    private Label title;
    private GameGrid gamePanel;
    private PropositionField proposition;
    private Prompt prompt;
    private Score score;
    private NbGamePlayed gamesPlayed;
    private Help help;
    private NewGame newGame; 
    private WordList dictionary;

    private final Wordle wordle;
    private final App app;

    private static final int HEIGHT = 600;
    private static final int WIDTH = 600;

    public WordleView(App app, Stage primaryStage, Wordle wordle) throws IOException {
        this.app = app;
        this.primaryStage = primaryStage;
        this.wordle = wordle;
        setScene(primaryStage);
        wordle.addObserver(this);
        wordle.askForNewGame();
    }

    private void setScene(Stage primaryStage) {
        VBox root = new VBox();
        mainField = new HBox();
        setBackground();
        initGameField();
        initInfoField();
        root.getChildren().add(mainField);
        mainField.getChildren().addAll(gameField, infoField);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setBackground() {
        URL imgURL = getClass().getClassLoader().getResource("background.jpg");
        mainField.setBackground(new Background(new BackgroundImage(
                new Image(imgURL.toString(), 0, 0, false, false),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(
                        Side.LEFT, 0, true,
                        Side.BOTTOM, 0, true),
                new BackgroundSize(
                        BackgroundSize.AUTO,
                        BackgroundSize.AUTO,
                        true, true, false, true)
        )));
    }

    private void initGameField() {
        gameField = new VBox();
        gameField.setAlignment(Pos.CENTER);
        gameField.setPadding(new Insets(20));
        gameField.setSpacing(20);
        initTitle();
        initGamePanel();
        initPrompt();
        initPropositionField();
        gameField.getChildren().addAll(title, gamePanel, proposition, prompt);
    }

    private void initTitle() {
        title = new Title("Wordle");
    }

    private void initGamePanel() {
        gamePanel = new GameGrid(GameData.getNbAttempt(), GameData.getNbLetters());
    }

    private void initPrompt() {
        prompt = new Prompt();
    }

    private void initPropositionField() {
        proposition = new PropositionField(wordle, prompt);
    }

    private void initInfoField() {
        infoField = new VBox();
        infoField.setAlignment(Pos.CENTER);
        infoField.setPadding(new Insets(20));
        infoField.setSpacing(20);
        initDictionary();
        initScore();
        initNbGamePlayed(); 
        initHelp();
        initNewGame(wordle);
        infoField.getChildren().addAll(score, dictionary, help, newGame, gamesPlayed);
    }

    private void initScore() {
        score = new Score(wordle.getScore());
    }

    private void initDictionary() {
        dictionary = new WordList(wordle);
    }
    
    private void initHelp(){
        help = new Help();
    }
    
    private void initNewGame(Wordle wordle){
        newGame = new NewGame(wordle);
        newGame.setOnAction(e -> {
            try {
                newGameRefresh();
            } catch (IOException ex) {
                Logger.getLogger(WordleView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void initNbGamePlayed(){
        gamesPlayed = new NbGamePlayed(0);
    }
    
    private void newGameRefresh() throws IOException{
        proposition.unfreeze();
        prompt.reset();
        newGame.reload();
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(() -> {
            updateGameGrid();
            updateGameStatus();
            updateScore();
            updateDictionary();
            updateNbGamePlayed(wordle.getNbGames()); 
        });
    }

    private void updateGameGrid() {
        List<Pair> words = wordle.getWords();
        gamePanel.update(words);
    }

    private void updateDictionary() {
        dictionary.update();
    }

    private void updateGameStatus() {
        if (wordle.isOver()) {
            prompt.setIsOver();
            proposition.freeze();
        }
        if (wordle.isFound()) {
            prompt.setIsFound();
            proposition.freeze();
        }
    }
    
    private void updateNbGamePlayed(List<Integer> NbGames){
        if(NbGames == null){
            gamesPlayed.update(0);
        }else{
            gamesPlayed.update(NbGames.get(0));
        }
        
    }

    private void updateScore() {
        score.update(wordle.getScore());
    }

}
