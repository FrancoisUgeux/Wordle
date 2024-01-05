package be.he2b.atl.wordle.model;

import be.he2b.atl.common.model.User;
import be.he2b.atl.client.AbstractClient;
import be.he2b.atl.common.message.Message;
import be.he2b.atl.common.message.MessageNewGame;
import be.he2b.atl.common.message.MessageProfile;
import be.he2b.atl.common.message.MessagePropose;
import be.he2b.atl.common.model.GameState;
import be.he2b.atl.common.model.Pair;
import be.he2b.atl.common.model.Word;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Wordle extends AbstractClient {

    private User mySelf;
    private GameState gameState;
    private List<Integer> NbGames; 

    public Wordle(String host, int port, String name, String password) throws IOException {
        super(host, port);
        openConnection();
        updateName(name);
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        Message message = (Message) msg;
        MessageFactory.build(message, this);
    }

    public void quit() throws IOException {
        closeConnection();
    }

    public void sendAttempt(String text) throws IOException {
    }

    void showMessage(Message message) {
        notifyChange(message);
    }

    private void updateName(String name) throws IOException {
        sendToServer(new MessageProfile(0, name));
    }

    public void askForNewGame() throws IOException {
        sendToServer(new MessageNewGame(getMySelf()));
    }

    private void notifyChange() {
        setChanged();
        notifyObservers();
    }

    private void notifyChange(Message message) {
        setChanged();
        notifyObservers(message);
    }

    @Override
    protected void connectionEstablished() {
        System.out.println("Client connecté");
    }

    public User getMySelf() {
        return mySelf;
    }

    void setMySelf(User user) {
        this.mySelf = user;
    }

    public GameState getGameState() {
        return gameState;
    }
   
    void setGameState(GameState gameState) {
        this.gameState = gameState;
        notifyChange();
    }
    
    void setNbGames(List<Integer> NbGames){
        this.NbGames = NbGames;
        notifyChange();
    }

    public List<Pair> getWords() {
        List<Pair> words;
        if (gameState != null) {
            words = gameState.getWords();
        } else {
            words = new ArrayList<>();
        }
        return words;
    }

    public List<Word> getDictionary() {
        List<Word> words;
        if (gameState != null) {
            words = gameState.getDictionary();
        } else {
            words = new ArrayList<>();
        }
        return words;
    }

    public int getScore() {
        return gameState != null ? gameState.getScore() : 0;
    }

    public boolean isOver() {
        return gameState != null ? gameState.isOver() : false;
    }

    public boolean isFound() {
        return gameState != null ? gameState.isFound() : false;
    }

    public void attempt(Word word)  throws IOException {
        sendToServer(new MessagePropose(getMySelf(), word));
    };
    
    public List<Integer> getNbGames(){
        return NbGames;
    }
}
