package SignUp;

/**
 * Created by hadi on 4/18/2017.
 */

import com.jfoenix.controls.*;

import javafx.fxml.FXML;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import java.util.ResourceBundle;
import Client.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class SignUpController implements Initializable{
    Client client1=new Client();
    public void initialize(URL url, ResourceBundle rb){

    }
    @FXML
    private JFXTextField mail;

    @FXML
    private JFXTextField age;

    @FXML
    private JFXTextField country;

    @FXML
    private JFXTextField quest;

    @FXML
    private JFXTextField answer;

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField confPass;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private Label message;

    @FXML
    private JFXButton signUp;

    @FXML
    private JFXToggleButton gender;

    @FXML
    private ImageView checkPassword;

    @FXML
    private ImageView checkEmail;
    Boolean ValidEmail=false;
    @FXML
    void checkEmail(KeyEvent event) throws IOException{
        try {
            InternetAddress emailAddr = new InternetAddress(mail.getText().trim());
            emailAddr.validate();
            if (mail.getText().trim().contains(".")) {
                checkEmail.setImage(new Image(new FileInputStream("./images/check.png")));
                ValidEmail=true;
            }
            else{
                checkEmail.setImage(new Image(new FileInputStream("./images/x.png")));
            }
        } catch (AddressException ex) {
            checkEmail.setImage(new Image(new FileInputStream("./images/x.png")));
        }

    }

    @FXML
    void checkPassword(KeyEvent event) throws FileNotFoundException {
        if(pass.getText().equals(""))
        {
            checkPassword.setImage(null);
        }
        else if(pass.getText().trim().equals(confPass.getText().trim())){
            checkPassword.setImage(new Image(new FileInputStream("./images/check.png")));
        }
        else if ((confPass.equals(""))==false) {
            checkPassword.setImage(new Image(new FileInputStream("./images/x.png")));
        }
    }


    @FXML
    void makeSignUp(ActionEvent event) throws IOException {
        String email = mail.getText().trim();
        String age1 = age.getText().trim();
        String country1 = country.getText().trim();
        String username = user.getText().trim();
        String password = pass.getText().trim();
        Boolean genderB = gender.isSelected();
        String gender1;
        if(genderB==false){
            gender1="Male";
        }
        else{
            gender1="Female";
        }
        String question = quest.getText().trim();
        String ans = answer.getText().trim();
        String confirmP = confPass.getText().trim();
        boolean createSign = true;
        if(ValidEmail=false){
            message.setText("Email is invalid");
            createSign = false;
        }
        if (email.equals("") || age1.equals("") || country1.equals("") || username.equals("") || password.equals("") || gender1.equals("") || question.equals("") || ans.equals("") || confirmP.equals("")) {
            message.setText("Some fields are not filled");
            createSign = false;
        }
        if (!password.equals(confirmP)) {
            message.setText("Password confirmation does not match");
            createSign = false;
        }

        if (createSign == true) {
         String result=client1.makeSignUp(username, password, email, gender1, age1, country1, question, ans);
        if(result.equals("Success")){
            Notifications SignUp=Notifications.create()
                    .title("  CRUNX").text("\n  You have successfully signed up.")
                    .graphic(null).hideAfter(Duration.seconds(10))
                    .darkStyle();
            SignUp.show();
            Parent Login_parent= FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
            Scene Login_scene= new Scene(Login_parent);
            Stage Login_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Login_stage.setScene(Login_scene);
            Login_stage.show();
        }
        else{
            message.setText("Please change email or username");
        }
        }
    }


}

