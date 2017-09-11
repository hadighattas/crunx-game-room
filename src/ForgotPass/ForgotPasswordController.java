package ForgotPass;

import Client.Client;
import Login.LoginController;
import MainMenu.MainMenuController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable{
    private Client client=new Client();
    Timeline BackgroundTask = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            makeRefresh();
        }
    }));
    public void initialize(URL url, ResourceBundle rb){
        try{
            logo.setImage(new Image(new FileInputStream("./src/CRUNX.png")));
        }catch (FileNotFoundException e){

        }
        BackgroundTask.play();
        quest.setDisable(true);
        quest.setEditable(false);
        answer.setVisible(true);
        pass.setVisible(false);
    }
    @FXML
    private JFXTextField pass;
    @FXML
    private ImageView logo;

    @FXML
    private JFXTextField user;

    @FXML
    private JFXTextField quest;

    @FXML
    private JFXTextField answer;

    @FXML
    private JFXButton signUp;

    @FXML
    private Label message;

    @FXML
    private ImageView checkPassword;

    @FXML
    private ImageView checkEmail;

    private int z=0;
    @FXML
    void makeRefresh(){
//        client.username=user.getText();
//        String question=client.getquestion(user.getText());
//        quest.setText(question);

    }
    private String state="";
    private String state2="";
    @FXML
    void makeSignUp(ActionEvent event) throws IOException {
        if(z==0){
            client.username=user.getText();
            String question=client.getquestion(user.getText());
            quest.setText(question);
            z++;
        }
        else if(z>1 && state.equals("SUCCESS")){
            state2=client.sendpass(user.getText(), pass.getText());
            state=" ";
            if(state2.equals("SUCCESS")){
                Notifications Request = Notifications.create()
                        .title("  CRUNX").text("  Your password has been changed.")
                        .graphic(null).hideAfter(Duration.seconds(10))
                        .darkStyle();
                Request.show();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
                Parent Login_parent = loader.load();
                Scene Login_scene = new Scene(Login_parent);
                Stage Login_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Login_stage.setScene(Login_scene);
                Login_stage.show();
                BackgroundTask.stop();
            }
        }
        else if(z>0){
            state=client.sendanswer(user.getText(),answer.getText());
            z++;
            if(state.equals("SUCCESS")){
                pass.setVisible(true);
            }
            else{
                Notifications Request = Notifications.create()
                        .title("  CRUNX").text("  Wrong answer please try again.")
                        .graphic(null).hideAfter(Duration.seconds(10))
                        .darkStyle();
                Request.show();
            }
        }
    }

}
