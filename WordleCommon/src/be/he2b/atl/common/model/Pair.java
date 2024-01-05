package be.he2b.atl.common.model;
/**
 * @author François Ugeux
 */

import java.io.Serializable;

public class Pair implements Serializable {
    private final Word key;
    private WordStatus value;
    
    public Pair(Word word, WordStatus status){
        key = word;
        this.value = status;
    }
    
    public Word getKey(){
        return key;
    }
    
    public WordStatus getValue(){
        return value;
    }

    public void setValue(WordStatus status) {
        this.value = status;
    }    
}
