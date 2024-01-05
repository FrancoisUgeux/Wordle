package be.he2b.atl.database.dto;

import be.he2b.atl.database.exception.DtoException;

/**
 * Game(id, userId, score, state, word)
 *
 * @author François Ugeux
 */
public class GameDto extends EntityDto<Integer> {

    private Integer userId;
    private Integer score;
    private String state;
    private String word;

    /**
     *
     * @param id
     * @param userId
     * @param score
     * @param state
     * @param word
     * @throws DtoException
     */
    public GameDto(Integer id, Integer userId, int score, String state, String word) throws DtoException {
        this(userId, score, state, word);
        this.id = id;
    }

    /**
     *
     * @param userId
     * @param score
     * @param state
     * @param word
     * @throws DtoException
     */
    public GameDto(Integer userId, Integer score, String state, String word) throws DtoException {
        if (score == null || state == null || word == null) {
            throw new DtoException("les attributs score, state et word sont obligatoires");
        }
        this.userId = userId;
        this.score = score;
        this.state = state;
        this.word = word;

    }

    public Integer getUser() {
        return userId;
    }

    public String getState() {
        return state;
    }

    public Integer getScore() {
        return score;
    }

    public String getWord() {
        return word;
    }

    public void setUser(Integer userId) {
        this.userId = userId;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "[UserDto] (" + getId() + ") " + getUser() + " " + getScore()
                + " " + getWord();
    }

}
