package g43335.atl.wordle.model;

import be.he2b.atl.common.model.GameData;
import be.he2b.atl.common.model.GameState;
import be.he2b.atl.common.model.Pair;
import be.he2b.atl.common.model.Status;
import be.he2b.atl.common.model.Word;
import be.he2b.atl.common.model.WordStatus;
import be.he2b.atl.database.business.AdminModel;
import be.he2b.atl.database.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the game.
 *
 * @author François Ugeux
 */
public class Game {

    private Dictionary dictionary;
    private final Word secretWord;
    private int currentAttempt;
    private List<Pair> attempts; 
    private List<WordStatus> wordStatus; // status of the secret word
    private ScoreTable score;
    private boolean isOver = false; 
    private boolean isFound = false;
    private AdminModel db; 

    /**
     * Creates a game
     *
     */
    public Game() {
        this.dictionary = new Dictionary(GameData.getNbLetters());
        this.secretWord = selectRandomWord();
        this.currentAttempt = 0;
        this.attempts = new ArrayList<>();
        this.wordStatus = new ArrayList<>();
        this.score = new ScoreTable(GameData.getNbAttempt());
    }

    /**
     * Creates a game for test purpose.
     *
     * @param secret the word to find
     */
    public Game(Word secret) {
        this.dictionary = new Dictionary(GameData.getNbLetters());
        this.secretWord = secret;
        this.currentAttempt = 0;
        this.attempts = new ArrayList<>();
        this.wordStatus = new ArrayList<>();
        this.score = new ScoreTable(GameData.getNbAttempt());
    }

    private Word selectRandomWord() {
        return dictionary.getWord(randomNb(dictionary.getSize()));
    }

    private int randomNb(int max) {
        return (int) (Math.random() * (max + 1));
    }

    /**
     * Gives every attempts in the game and the status of its letters.
     *
     * @return every word submited and its letter's status
     */
    List<Pair> getWords() {
        return new ArrayList<>(attempts);
    }

    /**
     * Gives the status of the secret word at a given attempt.
     *
     * @param attemptNb the number of the attempt.
     * @return the status the secret word.
     */
    WordStatus getStatus(int attemptNb) {
        return new WordStatus(wordStatus.get(attemptNb));
    }

    /**
     * Gives the number of the current attempt of the player in the game.
     *
     * @return
     */
    public int getCurrentAttempt() {
        return currentAttempt;
    }

    /**
     * Gives a proposition of words matching the current attempts of the player
     * in the game
     *
     * @return a list of words matching the current attempts of the player.
     */
    public List<Word> getDictionary() {
        return dictionary.getDictionary();
    }

    /**
     * Gives the score of the player .
     *
     * @return the score of the player at the current attempt.
     */
    public int getScore() {
        return score.getScore(currentAttempt);
    }

    /**
     * Check if the length of a given word match the number of letter a word
     * must have.
     *
     * @param word the word to check.
     * @return true if the size match, else false.
     */
    boolean isGoodLength(Word word) {
        if(word.getLength() != GameData.getNbLetters()){ 
            setPenality(-1);
        }
        return word.getLength() == GameData.getNbLetters();
    }

    /**
     * Set the status of the letters of a given word comparing it to the word to
     * find. - If there's a total match (same letter on the same position) the
     * status will be GOOD. - If there's a minimal match ( same letter on
     * another position) the status will be WRONG_POSITION - Else the status
     * will be WRONG
     *
     * The new attempt is saved in the attempts of the game. The dictionary is
     * update and the score is calculated according to the attempts.
     *
     * @param other the word to check.
     */
    public void checkAttempt(Word other) {
        boolean[] isChecked = new boolean[GameData.getNbLetters()];
        WordStatus otherStatus = new WordStatus(GameData.getNbLetters());
        addStatus();
        checkGoodStatus(isChecked, other, otherStatus);
        checkWrongStatus(isChecked, other, otherStatus);
        attempts.add(new Pair(other, otherStatus));
        updateDictionary(other, currentAttempt);
        setCurrentScore();
        currentAttempt++;
        isOver = isOver(); 
    }
    
    /**
     * Set the current attempt to the previous one and updateDico the
     * dictionary.
     */
    void previousAttempt() {
        if(!isOver() && !isFound()){
            if(currentAttempt > 0){
                currentAttempt -= 1;
                attempts.remove(currentAttempt);
                wordStatus.remove(currentAttempt);
            }if(currentAttempt != 0){
                int previousAttempt = currentAttempt - 1;
                updateDictionary(attempts.get(previousAttempt).getKey(), previousAttempt);
            }else{
                dictionary.updateDico();
            }
        }
    }

    private void updateDictionary(Word word, int attemptNb) {
        dictionary.update(
                word,
                wordStatus.get(attemptNb),
                attempts,
                attemptNb
        );
    }

    private void addStatus() {
        if (currentAttempt == 0) {
            wordStatus.add(new WordStatus(GameData.getNbLetters()));
        } else {
            int previousAttempt = currentAttempt - 1;
            WordStatus newStatus = new WordStatus(wordStatus.get(previousAttempt));
            wordStatus.add(newStatus);
        }
    }

    private void setStatus(int index, Status status) {
        this.wordStatus.get(currentAttempt).setStatus(status, index);

    }

    private void checkGoodStatus(boolean[] isChecked, Word other, WordStatus status) {
        int nbGoodLetters = 0;
        for (int index = 0; index < GameData.getNbLetters(); index++) {
            if (other.getCharAt(index) == secretWord.getCharAt(index)) {
                status.setStatus(Status.GOOD, index);
                setStatus(index, Status.GOOD);
                isChecked[index] = true;
                nbGoodLetters++; 
            }
        }
        isFound = nbGoodLetters == GameData.getNbLetters(); 
    }

    private void checkWrongStatus(boolean[] isChecked, Word other, WordStatus status) {
        for (int otherIndex = 0; otherIndex < GameData.getNbLetters(); otherIndex++) {
            int index = 0;
            boolean found = false;
            while (index < GameData.getNbLetters() && !found) {
                if (other.getCharAt(otherIndex) == secretWord.getCharAt(index)
                        && isChecked[index] == false
                        && status.getStatus(otherIndex) != Status.GOOD) {
                    status.setStatus(Status.WRONG_POSITION, otherIndex);
                    setStatus(index, Status.WRONG_POSITION);
                    isChecked[index] = true;
                    found = true;
                } else if (status.getStatus(otherIndex) == null) {
                    status.setStatus(Status.WRONG, otherIndex);
                }
                index++;
            }
        }
    }

    /**
     * Check if the number of attempt have been reach.
     *
     * @return true if the number of attempt is reach, else false.
     */
    public boolean isOver() {
        return this.currentAttempt >= GameData.getNbAttempt();
    }

    /**
     * Check if the secret word is found.
     *
     * @return true if the word is found, else false.
     */
    public boolean isFound() {
        return secretWord.equals(attempts.get(currentAttempt - 1).getKey());
    }

    /**
     * Sets the score of the current attempt.
     */
    private void setCurrentScore() {
        score.setAttemptScore(currentAttempt, attempts.get(currentAttempt));
    }

    /**
     * Sets a penality for an attempt.
     *
     * @param penality
     */
    void setPenality(int penality) {
        score.setPenality(penality, currentAttempt);
    }

    public void attempt(Word word) {
        System.out.println("ATTEMPT  = " + word);
        if (getCurrentAttempt() >= GameData.getNbAttempt()) {
            throw new IllegalArgumentException("all attempts used : " + getCurrentAttempt()); 
        }
        if (!isGoodLength(word)) {
            setPenality(-1);
            throw new IllegalArgumentException("length"); 
        }
        if (!exist(word)) {
            setPenality(-1); 
            throw new IllegalArgumentException("dictionary");
        }
        checkAttempt(word); //aro
    }

    /**
     * Checks if the word exists in Dictionary.
     *
     * @param word
     * @return true if the word exist, else false.
     * @throws IllegalArgumentException if the word is empty (length = 0).
     */
    public boolean exist(Word word) {
        if (word.getLength() == 0) {
            throw new IllegalArgumentException("Mot vide");
        }
        Dictionary dico = new Dictionary(word.getLength());
        int i = 0;
        while (i < dico.getSize()) {
            if (dico.getWord(i).equals(word)) { //this au lieu de word?
                return true;
            }
            i++;
        }
        setPenality(-1);
        return false;
    }

    public GameState getGameState() {
        GameState gameState = new GameState(dictionary.getDictionary(), attempts,
                getScore(), isOver, isFound);
        return gameState;
    }
    
    public String getSecret(){ 
        return secretWord.toString();
    }
    
    public String getState(){
        if(isFound)
            return "trouvé";
        else
            return "perdu";
    }
}
