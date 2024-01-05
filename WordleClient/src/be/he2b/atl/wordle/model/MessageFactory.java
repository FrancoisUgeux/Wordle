package be.he2b.atl.wordle.model;

import be.he2b.atl.common.message.Message;
import be.he2b.atl.common.message.Type;
import be.he2b.atl.common.model.GameState;
import java.util.List;

class MessageFactory {

    static void build(Message message, Wordle wordleClient) {
        Type type = message.getType();
        switch (type) {
            case PROFILE:
                wordleClient.setMySelf(message.getAuthor());
                break;
            case GAMESTATE:
                wordleClient.setGameState((GameState)message.getContent());
                break;
            case NBGAMES: 
                wordleClient.setNbGames((List<Integer>)message.getContent());
            default:
                throw new IllegalArgumentException("Message type unknown " + type);
        }
    }
}
