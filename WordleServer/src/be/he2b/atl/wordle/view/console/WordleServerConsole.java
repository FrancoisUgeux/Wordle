package be.he2b.atl.wordle.view.console;

import be.he2b.atl.common.model.User;
import be.he2b.atl.common.message.Message;
import be.he2b.atl.wordle.server.model.WordleServer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordleServerConsole implements Observer {

    public static void main(String[] args) {
        try {
            WordleServer model = new WordleServer();
            WordleServerConsole console = new WordleServerConsole(model);
            model.addObserver(console);
            System.out.println("Server started");
            System.out.println("");
        } catch (IOException ex) {
            Logger.getLogger(WordleServerConsole.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }

    private final WordleServer model;

    public WordleServerConsole(WordleServer model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        updateUser();
        if (arg != null) {
            Message message = (Message) arg;
            updateMessage(message);
        }
    }

    private void updateUser() {
        System.out.println("");
        StringBuilder builder = new StringBuilder();
        builder.append("\n---- ---- Liste Users ---- ----\n");
        builder.append("Nombre d'utilisateurs connectes : ")
                .append(model.getNbConnected()).append("\n");
        builder.append("ID").append("\t");
        builder.append("IP").append("\t\t");
        builder.append("NAME").append("\n");
        for (User user : model.getUsers()) {
            builder.append(user.getId()).append("\t");
            builder.append(user.getAddress()).append("\t");
            builder.append(user.getName()).append("\n");
        }
        System.out.println(builder);
        System.out.println("");
    }

    private void updateMessage(Message message) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n---- ---- Message recu ---- ----\n");
        builder.append(LocalDateTime.now()).append(" \n");
        builder.append("Type : ").append(message.getType()).append("\n");
        builder.append("De : ").append(message.getAuthor()).append("\t");
        builder.append("Pour : ").append(message.getRecipient()).append("\n");
        builder.append("Contenu\t").append(message.getContent());
        builder.append("\n");
        System.out.println(builder);
    }
}
