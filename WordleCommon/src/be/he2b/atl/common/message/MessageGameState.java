package be.he2b.atl.common.message;
/**
 * @author François Ugeux
 */

import be.he2b.atl.common.model.GameState;
import be.he2b.atl.common.model.User;

public class MessageGameState implements Message {

    private final User player;
    private final GameState gameState;

    public MessageGameState(User player, GameState gameState) {
        this.player = player;
        this.gameState = gameState;
    }

    @Override
    public User getAuthor() {
        return User.ADMIN;
    }

    @Override
    public User getRecipient() {
        return player;
    }

    @Override
    public Type getType() {
        return Type.GAMESTATE;
    }

    @Override
    public Object getContent() {
        return gameState;
    }

}
