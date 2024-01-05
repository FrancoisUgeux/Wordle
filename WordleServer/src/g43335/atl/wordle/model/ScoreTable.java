package g43335.atl.wordle.model;

import be.he2b.atl.common.model.Pair;
import be.he2b.atl.common.model.Status;

/**
 * Class representing the score table in the game Wordle. Each entry contain the
 * score for each attempt.
 *
 * @author François Ugeux
 */
public class ScoreTable {

    private int[] scores;

    /**
     * Creates a score table.
     *
     * @param nbAttempt the number of attempt in the game.
     */
    public ScoreTable(int nbAttempt) {
        this.scores = new int[nbAttempt];
    }

    /**
     * Gives the total score.
     *
     * @param nbAttempt the total number of attempts.
     * @return the total score.
     */
    public int getScore(int nbAttempt) {
        int score = 0;
        for (int attempt = 0; attempt < nbAttempt; attempt++) {
            score += this.scores[attempt];
        }
        return score;
    }

    /**
     * Sets the score for an attempt. For each GOOD status, the score is
     * increased by two and for each WRONG_POSITION status the score is
     * increased by one.
     *
     * @param attemptNb the index of the attempt to set a score.
     * @param attempt the attempt to evaluate.
     */
    void setAttemptScore(int attemptNb, Pair attempt) {
        if (scores[attemptNb] <= 0) {
            int nbLetters = attempt.getKey().getLength();
            for (int index = 0; index < nbLetters; index++) {
                if (attempt.getValue().getStatus(index) == Status.GOOD) {
                    scores[attemptNb] += 2;
                }
                if (attempt.getValue().getStatus(index) == Status.WRONG_POSITION) {
                    scores[attemptNb] += 1;
                }
            }
        }
    }

    /**
     * Sets a penality for an attempt.
     *
     * @param penality the value of the penality.
     * @param attemptNb the index of the attempt to set the penality.
     */
    void setPenality(int penality, int attemptNb) {
        if (penality < 0) {
            scores[attemptNb] += penality;
        }
    }
}
