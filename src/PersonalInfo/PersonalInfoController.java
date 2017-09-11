package PersonalInfo;

import Client.Client;
import MainMenu.MainMenuController;
import PrivateMessage.PrivateMessageController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXToggleButton;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonalInfoController implements Initializable{

    private Client client;
    private String tosee;

    Timeline BackgroundTask = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            makeRefresh();
        }
    }));
    public void setClient(Client client) {
        this.client = client;
        username1.setText(client.username);
    }
    public void setTosee(String user){
        this.tosee=user;
        username.setText(user);
        scoreb.setText(client.getscoreb(tosee));
        scoret.setText(client.getscoret(tosee));
    }


    public void initialize(URL url, ResourceBundle rb) {
        email.setVisible(true);
        username.setVisible(true);
        country.setVisible(true);
        age.setVisible(true);


        try {
            defaultProfile.setImage(new Image(new FileInputStream("./images/user71.png")));
            home.setImage(new Image(new FileInputStream("./images/homebutton.png")));


        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        BackgroundTask.setCycleCount(Timeline.INDEFINITE);
        BackgroundTask.play();

    }
    @FXML
    private AnchorPane ap;
    @FXML
    private Label scoret;
    @FXML
    private Label scoreb;

    @FXML
    private ImageView defaultProfile;

    @FXML
    private JFXTextArea username;

    @FXML
    private Label username1;
    @FXML
    private JFXTextArea email;

    @FXML
    private JFXToggleButton emailVisible;

    @FXML
    private JFXTextArea age;

    @FXML
    private JFXTextArea country;

    @FXML
    private JFXToggleButton countryVisible;

    @FXML
    private JFXToggleButton ageVisible;

    @FXML
    private JFXButton update;

    @FXML
    private ImageView home;

    @FXML
    void sendMessage(ActionEvent event) throws IOException{
        BackgroundTask.stop();
        String user2=username.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PrivateMessage/PrivateMessage.fxml"));
        Parent PrivateMessage_parent = loader.load();
        PrivateMessageController privateMessage = loader.getController();
        privateMessage.setClient(client);
        privateMessage.setClient2(user2);
        Scene PrivateMessage_scene = new Scene(PrivateMessage_parent);
        Stage PrivateMessage_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        PrivateMessage_stage.setScene(PrivateMessage_scene);
        privateMessage.makeRefresh();
    }
    @FXML
    private JFXButton message;

    @FXML
    void makeMenu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu/MainMenu.fxml"));
        Parent MainMenu_parent = loader.load();
        MainMenuController mainMenu = loader.getController();
        mainMenu.setClient(client);
        Scene MainMenu_scene = new Scene(MainMenu_parent);
        Stage MainMenu_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu_stage.setScene(MainMenu_scene);
        MainMenu_stage.show();
        BackgroundTask.stop();
        mainMenu.makeRefresh();
    }

    @FXML
    void makeUpdate(ActionEvent event) {

        String usernameText=username.getText();
        String mailText=email.getText();
        String countryText=country.getText();
        String ageText=age.getText();
        String emailVis;
        String ageVis;
        String countryVis;

        if(ageVisible.isSelected()){
            ageVis="yes";
        }
        else {
            ageVis="no";
        }
        if(countryVisible.isSelected()){
            countryVis="yes";
        }
        else {
            countryVis="no";
        }
        if(emailVisible.isSelected()){
            emailVis="yes";
        }
        else {
            emailVis="no";
        }
        String result=client.updateInfo(mailText, usernameText, countryText, ageText, emailVis, ageVis, countryVis);

    }
    public void makeRefresh() {
        Stage stage = (Stage) ap.getScene().getWindow();
        if(client.username.equals(tosee)){
            message.setVisible(false);
        }
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent arg0) {
                logout();
            }
        });
        getInfo(tosee);
        getReqNotif();
        getMessageNotif();
    }
    void getReqNotif(){
        String result=client.getReqNotif();

        String[] resultString = result.split("//");
        if (isNumeric(resultString[0])) {

            Integer friendCount = Integer.valueOf(resultString[0]);
            while (friendCount >= 1) {
                Notifications Request = Notifications.create()
                        .title("  CRUNX").text(resultString[friendCount] + " sent you a friend request.")
                        .graphic(null).hideAfter(Duration.seconds(10))
                        .darkStyle();
                Request.show();
                friendCount--;
            }
        }

    }
    void logout(){
        client.logout();
    }
    void getInfo(String user){
        if(client.username.equals(tosee)) {
            email.setEditable(false);
            username.setEditable(false);
            country.setEditable(true);
            age.setEditable(true);
            update.setVisible(true);
            ageVisible.setVisible(true);
            countryVisible.setVisible(true);
            emailVisible.setVisible(true);
        }
        else{
            email.setEditable(false);
            username.setEditable(false);
            country.setEditable(false);
            age.setEditable(false);
            update.setVisible(false);
            ageVisible.setVisible(false);
            countryVisible.setVisible(false);
            emailVisible.setVisible(false);
        }
       String result=client.getInfo(user);
       String[] resultString=result.split("//");
       if(client.username.equals(user)){
           email.setText(resultString[0]);
           age.setText(resultString[1]);
           country.setText(resultString[2]);
           if(resultString[3].equals("no")){
               emailVisible.setSelected(false);
           }
           else{
               emailVisible.setSelected(true);
           }
           if(resultString[4].equals("no")){
               ageVisible.setSelected(false);
           }
           else{
               ageVisible.setSelected(true);
           }
           if(resultString[5].equals("no")){
               countryVisible.setSelected(false);
           }
           else{
               countryVisible.setSelected(true);
           }

       }
       else{

            if(resultString[3].equals("yes")){
                email.setVisible(true);
                email.setText(resultString[0]);
            }
            else{
                email.setVisible(false);
            }
            if(resultString[4].equals("yes")){
                age.setVisible(true);
                age.setText(resultString[1]);
            }
            else{
                age.setVisible(false);
            }
            if(resultString[5].equals("yes")){
                country.setVisible(true);
                country.setText(resultString[2]);
            }
            else{
                country.setVisible(false);
            }

        }

    }
    void getMessageNotif(){
        String result =client.getMessageNotif();
        String[] resultString = result.split("//");
        if (isNumeric(resultString[0])) {

            Integer messageCount = Integer.valueOf(resultString[0]);
            while (messageCount >= 1) {
                Notifications Request = Notifications.create()
                        .title("  CRUNX").text(" "+resultString[messageCount] + " sent you a message.")
                        .graphic(null).hideAfter(Duration.seconds(10))
                        .darkStyle();
                Request.show();
                messageCount--;
            }
        }

    }
    public static boolean isNumeric(String str) {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

}
