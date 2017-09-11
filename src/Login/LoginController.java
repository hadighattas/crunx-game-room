package Login;

import MainMenu.MainMenuController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.*;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import Client.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public class LoginController implements Initializable {
    Client client1=new Client();

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton signup;

    @FXML
    private JFXButton forgotPassword;

    @FXML
    private Label message;
    @FXML
    void makeForgot(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForgotPass/ForgotPassword.fxml"));
        Parent ForgotPassword_parent = loader.load();
        Scene ForgotPassword_scene = new Scene(ForgotPassword_parent);
        Stage ForgotPassword_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ForgotPassword_stage.setScene(ForgotPassword_scene);
        ForgotPassword_stage.show();
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        System.out.print("bye");
        Platform.exit();
    }
    @FXML
    void makeLogin(ActionEvent event) throws IOException {
        String username=user.getText().trim();
        String password=pass.getText().trim();
        String result=client1.makeLogin(username, password);
        if(result.equals("Success")){
            Notifications Login=Notifications.create()
                    .title("  CRUNX").text("\n  You have successfully logged in.")
                    .graphic(null).hideAfter(Duration.seconds(10))
                    .darkStyle();
            Login.show();
            message.setText(" ");
            FXMLLoader  loader=new FXMLLoader(getClass().getResource("/MainMenu/MainMenu.fxml"));
            Parent MainMenu_parent= loader.load();
            MainMenuController mainMenu=loader.getController();
            mainMenu.setClient(client1);
            Scene MainMenu_scene= new Scene(MainMenu_parent);
            Stage MainMenu_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            MainMenu_stage.setScene(MainMenu_scene);
            mainMenu.makeRefresh();
        }
        else{
            message.setText("Wrong username or password");
        }


    }

    @FXML
    void makeSignup(ActionEvent event) throws IOException{
        Parent SignUp_parent= FXMLLoader.load(getClass().getResource("/SignUp/SignUp.fxml"));
        Scene SignUp_scene= new Scene(SignUp_parent);
        Stage SignUp_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        SignUp_stage.setScene(SignUp_scene);
        SignUp_stage.show();
    }
}

