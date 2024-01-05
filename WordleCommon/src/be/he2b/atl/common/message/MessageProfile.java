package be.he2b.atl.common.message;

import be.he2b.atl.common.model.User;

public class MessageProfile implements Message {

    private final User player;

    public MessageProfile(int id, String name) {
        player = new User(id,name);
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
        return Type.PROFILE;
    }

    @Override
    public Object getContent() {
        return player;
    }

}
