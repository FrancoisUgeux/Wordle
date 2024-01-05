package be.he2b.atl.common.message;

import be.he2b.atl.common.model.User;
import java.util.List;

/**
 *
 * @author François Ugeux
 */
public class MessageNbGames implements Message{

    private final User player;
    private final List<Integer> difficulty;

    public MessageNbGames(User player, List<Integer> difficulty) {
        this.player = player;
        this.difficulty = difficulty;
    }

    @Override
    public User getAuthor() {
        return player;
    }

    @Override
    public User getRecipient() {
        return User.ADMIN;
    }

    @Override
    public Type getType() {
        return Type.NBGAMES;
    }

    @Override
    public Object getContent() {
        return difficulty;
    }
    
}
