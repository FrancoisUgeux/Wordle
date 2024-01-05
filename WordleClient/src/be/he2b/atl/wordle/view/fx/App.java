package be.he2b.atl.wordle.view.fx;

import be.he2b.atl.wordle.model.Wordle;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author François Ugeux
 */
public class App extends Application {

    private static final String TITLE = "Client Wordle";
    private static final int MIN_WIDHT = 600;
    private static final int MIN_HEIGHT = 600;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Scene scene;
    private Wordle wordle;
    private Stage pStage; 


    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception { 
        pStage = primaryStage; 

        FXMLLoader loader = new FXMLLoader();
        URL fxmlURL = getClass().getResource("/be/he2b/atl/wordle/view/fx/login.fxml");
        loader.setLocation(fxmlURL);
        Parent parent = loader.load();

        LoginController controller = loader.getController();
        controller.setMainApp(this);

        scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Create a new wordle
     * @param host The player host.
     * @param port The port this player is connected on.
     * @param name The name of the player.
     * @param password The password if there is one.
     */
    public void createWordle(String host, int port, String name, String password) {
        try {
            wordle = new Wordle(host, port, name, password);
            loadWordleView();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }

    /**
     * Load the UI window to play wordle.
     */
    public void loadWordleView() {
        try {
            //Thread.sleep(5000); //temp to see login
            pStage.setMinWidth(MIN_WIDHT);
            pStage.setMinHeight(MIN_HEIGHT);
            pStage.setTitle(TITLE);
            new WordleView(this, pStage, wordle);
            //pStage.setScene(new Scene(wordleView));
            pStage.setOnCloseRequest((WindowEvent t1) -> {
                try {
                    wordle.quit();
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
                Platform.exit();
                System.exit(0);
            });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            try {
                wordle.quit();
            } catch (IOException ex1) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Platform.exit();
            System.exit(0);
        }
    }
}
