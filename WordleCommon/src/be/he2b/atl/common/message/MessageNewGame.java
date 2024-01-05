package be.he2b.atl.common.message;
/**
 * @author François Ugeux
 */

import be.he2b.atl.common.model.User;

public class MessageNewGame implements Message {

    private final User player;

    public MessageNewGame(User player) {
        this.player = player;
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
        return Type.NEWGAME;
    }

    @Override
    public Object getContent() {
        return null;
    }

}
