package be.he2b.atl.database.specification;

/**
 *
 * @author François Ugeux
 */
public class GameSpecification {

    Integer id;
    int userId;
    int score;
    String state;
    String word;
    String hardmode;

    public GameSpecification(int userId, int score, String state, String word, String hardmode) {
        this.id = 0;
        this.userId = userId;
        this.score = score;
        this.state = state;
        this.word = word;
    }

    public GameSpecification(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return userId;
    }

    public void setUser(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    
    public String getHardmode(){
        return hardmode;
    }
    
    public void setHardmode(String hardmode){
        this.hardmode = hardmode;
    }
    
    

}
