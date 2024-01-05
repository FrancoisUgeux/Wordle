package be.he2b.atl.wordle.server.model;

import be.he2b.atl.common.message.Message;
import be.he2b.atl.common.message.Type;
import static be.he2b.atl.common.message.Type.PROFILE;
import be.he2b.atl.common.model.Word;
import be.he2b.atl.database.exception.BusinessException;
import static be.he2b.atl.wordle.server.model.WordleServer.ID_MAPINFO;
import be.he2b.atl.server.ConnectionToClient;
import static be.he2b.atl.wordle.server.model.WordleServer.GAME_MAPINFO;
import g43335.atl.wordle.model.Game;
import java.util.ArrayList;
import java.util.List;

class MessageFactory {

    static void build(Message message, ConnectionToClient client, WordleServer wordleServer) throws BusinessException {
        Type type = message.getType();
        switch (type) {
            case PROFILE:
                int userId = wordleServer.addUserDB(message.getAuthor().getName()); 
                wordleServer.changeName(message.getAuthor(), userId); 
                int easyGame = wordleServer.countGame(userId, Boolean.FALSE);
                //int hardGame = wordleServer.countGame(userId, Boolean.TRUE);
                List<Integer> nbGames = new ArrayList<>();
                nbGames.add(easyGame); 
                //nbGames.add(hardGame);
                wordleServer.sendNbGamesToClient(client, nbGames);
                wordleServer.sendProfileToClient(client);
                break;
            case NEWGAME:
                wordleServer.sendNewGameToClient(client);
                break;
            case PROPOSE:  
                Game game = (Game) client.getInfo(GAME_MAPINFO);
                Word word = (Word) message.getContent();
                game.attempt(word);
                if(game.isOver() || game.isFound()){
                    wordleServer.addGameDB(wordleServer.getClientId(), game.getScore(), game.getState(), game.getSecret());
                }
                wordleServer.sendGameStateToClient(client);
                break;
            default:
                throw new IllegalArgumentException("Message type unknown " + type);
        }
    }
}
