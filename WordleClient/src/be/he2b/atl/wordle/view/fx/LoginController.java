
package be.he2b.atl.wordle.view.fx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author François Ugeux
 */
public class LoginController {

    @FXML
    private TextField TF_host;
    @FXML
    private TextField TF_port;
    @FXML
    private TextField TF_name;
    
    private App mainApp;
    @FXML
    private Button BT_Login;
    @FXML
    private TextField TF_password;
    
    public void setMainApp(App mainApp){
        this.mainApp = mainApp;
    }
    
    @FXML
    public void tryToLogin(){
        String host = TF_host.getText();
        int port = Integer.parseInt(TF_port.getText());
        String name = TF_name.getText();
        String password = TF_password.getText();
        mainApp.createWordle(host, port, name, password);
        
    }

}
