package g43335.atl.wordle.model;

import be.he2b.atl.common.model.Pair;
import be.he2b.atl.common.model.Word;
import be.he2b.atl.common.model.WordStatus;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a dictionary.
 *
 * @author François Ugeux
 */
public class Dictionary {
    private final static String URL = "Dictionary.txt";

    private final URL resource;
    private List<Word> dictionary;
    private final int wordLength;

    /**
     * Creates a dictionary with words of a given length.
     *
     * @param length the length of the words the dictionary must contain.
     */
    public Dictionary(int length) {
        resource = getClass().getClassLoader().getResource(URL);
        wordLength = length;
        updateDico();
    }

    /**
     * Gives a proposition of words matching the current attempts of the player
     * in the game
     *
     * @return a list of words matching the current attempts of the player.
     */
    public List<Word> getDictionary() {
//        return new ArrayList<>(this.dictionary);
       return new ArrayList<>(this.dictionary);
    }

    /**
     * Gives the size of the dictionary.
     *
     * @return the size of the dictionary.
     */
    public int getSize() {
        return dictionary.size();
    }

    /**
     * Gives the word at a given index in the dictionary.
     *
     * @param index the position of the word in the dictionary.
     * @return a copy of the word at the given index.
     */
    public Word getWord(int index) {
        return new Word(dictionary.get(index));
    }

    /**
     * Updates the dictionary. Words are removed if they don't match letter of
     * the reference word or if they contain wrong letters (checked from a list
     * of words and the status of their letters).
     *
     * @param reference the word used for reference.
     * @param status status of the reference word letters.
     * @param words list of words used for the wrong letters check.
     * @param currentAttempt index of the last word avalaible.
     */
    public void update(Word reference, WordStatus status,
            List<Pair> words, int currentAttempt) {
        List<Word> goodWords = new ArrayList<>();
        List<Word> newDictionary = getWords(wordLength);
        for (Word word : newDictionary) {
            if (WordUtil.match(word, reference, words.get(currentAttempt).getValue())
                    && !WordUtil.containWrongLetters(word, words, currentAttempt)) {
                goodWords.add(word);
            }
        }
        dictionary = goodWords;
    }

    /**
     * Set the dictionary with default words.
     */
    public void updateDico() {
        dictionary = getWords(wordLength);
    }

    private List<Word> getWords(int length) {
        List<Word> words = new ArrayList<>();
        List<String> dictionary = readFile();
        for (String word : dictionary) {
            if (word.length() == length) {
                Word w = new Word(word);
                words.add(w);
            }
        }
        return words;
    }

    private List<String> readFile() {
        List<String> records = new ArrayList<>();

        try ( BufferedReader read = new BufferedReader(
                new InputStreamReader(resource.openStream()))) {
            String i;
            while ((i = read.readLine()) != null) {
                records.add(i);
            }
        } catch (IOException e) {
            System.err.println("Exception occurred trying to read file.");
            return null;
        }
        return records;
    }
}
