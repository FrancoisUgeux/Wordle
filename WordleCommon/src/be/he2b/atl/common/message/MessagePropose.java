package be.he2b.atl.common.message;
/**
 * @author François Ugeux
 */

import be.he2b.atl.common.model.User;
import be.he2b.atl.common.model.Word;

public class MessagePropose implements Message {

    private final User player;
    private final Word word;

    public MessagePropose(User player, Word word) {
        this.player = player;
        this.word = word;
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
        return Type.PROPOSE;
    }

    @Override
    public Object getContent() {
        return word;
    }
}
