package be.he2b.atl.wordle.server.model;

import be.he2b.atl.common.model.User;
import be.he2b.atl.common.message.Message;
import be.he2b.atl.common.message.MessageGameState;
import be.he2b.atl.common.message.MessageNbGames;
import be.he2b.atl.common.message.MessageProfile;
import be.he2b.atl.common.model.GameState;
import be.he2b.atl.database.business.AdminModel;
import be.he2b.atl.database.exception.BusinessException;
import be.he2b.atl.server.AbstractServer;
import be.he2b.atl.server.ConnectionToClient;
import g43335.atl.wordle.model.Game;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordleServer extends AbstractServer {

    private static final int PORT = 12_345;
    static final String ID_MAPINFO = "ID";
    static final String GAME_MAPINFO = "GAME";
    private final AdminModel db;

    private static InetAddress getLocalAddress() {
        try {
            Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
            while (b.hasMoreElements()) {
                for (InterfaceAddress f : b.nextElement().getInterfaceAddresses()) {
                    if (f.getAddress().isSiteLocalAddress()) {
                        return f.getAddress();
                    }
                }
            }
        } catch (SocketException e) {
            Logger.getLogger(WordleServer.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    private int clientId;

    private final Users users;

    public WordleServer() throws IOException {
        super(PORT);
        users = new Users();
        clientId = 0;
        this.listen();
        db = new AdminModel();
    }

    final synchronized int getNextId() {
        clientId++;
        return clientId;
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        Message message = (Message) msg;
        try {
            MessageFactory.build(message, client, this);
        } catch (BusinessException ex) {
            Logger.getLogger(WordleServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        notifyChange(message);
    }

    public void quit() throws IOException {
        this.stopListening();
        this.close();
    }

    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        super.clientDisconnected(client);
        int userId = (int) client.getInfo(ID_MAPINFO);
        users.remove(userId);
        notifyChange();
    }

    @Override
    protected synchronized void clientConnected(ConnectionToClient client) {
        super.clientConnected(client);
        int userId = users.add(getNextId(), client.getName(), client.getInetAddress());
        client.setInfo(ID_MAPINFO, userId);
        notifyChange();
    }

    @Override
    protected synchronized void clientException(ConnectionToClient client, Throwable exception) {
        super.clientException(client, exception);
        try {
            if (client.isConnected()) {
                client.sendToClient(new IllegalArgumentException("Message illisible " + exception.getMessage()));
            }
        } catch (IOException ex) {
            Logger.getLogger(WordleServer.class.getName()).log(Level.SEVERE, "Impossible d envoyer erreur au client", ex);
        }
    }

    void notifyChange() {
        setChanged();
        notifyObservers();
    }

    void notifyChange(Message message) {
        setChanged();
        notifyObservers(message);
    }

    public Users getUsers() {
        return users;
    }

    public String getIP() {
        if (getLocalAddress() == null) {
            return "Unknown";
        }
        return getLocalAddress().getHostAddress();
    }

    public int getNbConnected() {
        return getNumberOfClients();
    }

    void sendToClient(Message message, User recipient) {
        sendToClient(message, recipient.getId());
    }

    void sendNewGameToClient(ConnectionToClient client) {
        Game game = new Game();
        client.setInfo(GAME_MAPINFO, game);
        GameState gameState = game.getGameState();
        User player = users.getUser((int) client.getInfo(ID_MAPINFO));
        MessageGameState message = new MessageGameState(player, gameState);
        sendToClient(message, client);
    }
    
    void sendGameStateToClient(ConnectionToClient client){
        Game game = (Game) client.getInfo(GAME_MAPINFO);
        GameState gameState = game.getGameState();
        User player = users.getUser((int) client.getInfo(ID_MAPINFO));
        MessageGameState message = new MessageGameState(player, gameState);
        sendToClient(message, client);
    }
    
    void sendNbGamesToClient(ConnectionToClient client, List<Integer> nbGames){ 
        User player = users.getUser((int) client.getInfo(ID_MAPINFO));
        MessageNbGames message = new MessageNbGames(player ,nbGames);
        sendToClient(message, client);
    }
    
    void sendProfileToClient(ConnectionToClient client) {
        try {
            int id = (int) client.getInfo(ID_MAPINFO);
            User player = users.getUser(id);

        if (player != null) {
            MessageProfile message = new MessageProfile(player.getId(), player.getName());
            sendToClient(message, client);
        } else {
            Logger.getLogger(WordleServer.class.getName()).log(Level.WARNING, "Failed to retrieve user with ID: {0}", id);
        }
        } catch (ClassCastException e) {
           Logger.getLogger(WordleServer.class.getName()).log(Level.SEVERE, "Failed to cast client info to integer", e);
        }
    }   

    void sendToClient(Message message, ConnectionToClient client) {
        if (client != null) {
            try {
                client.sendToClient(message);
            } catch (Exception ex) {
            }
        }
    }

    void sendToClient(Message message, int clientId) {
        ConnectionToClient client = getConnectionToClient(clientId);
        sendToClient(message, client);
    }

    ConnectionToClient getConnectionToClient(int clientId) {
        ConnectionToClient client = null;
        List<Thread> clientThreadList = getClientConnections();
        int index = 0;
        boolean find = false;
        while (index < clientThreadList.size() && !find) {
            client = (ConnectionToClient) clientThreadList.get(index);
            int id = (int) client.getInfo(ID_MAPINFO);
            if (id == clientId) {
                find = true;
            }
            index++;
        }
        return client;
    }

    void changeName(User author, int userId) {
        users.changeName(author.getName(), userId);
    }
    int addUserDB(String user) throws BusinessException{
         clientId = db.addUser(user);
         return clientId;
    }
    
    int countGame(int userId, Boolean difficulty) throws BusinessException{
        return db.countGameByDifficulty(userId, difficulty);
    }
    
    //String getUserDB(int id) throws BusinessException{
    //    String userName = db.getUser(id).getName();
    //    return userName;
    //}
    
    void addGameDB(int userId, int score, String state, String word) throws BusinessException{
        db.addGame(userId, score, state, word);
    }

    public int getClientId() {
        return clientId;
    }
}
