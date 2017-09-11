package SearchMenu;

import ChatMenu.ChatMenuController;
import Client.Client;
import ContactUs.ContactUsController;
import MainMenu.MainMenuController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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

public class SearchMenuController implements Initializable {

    private Client client;
    Timeline BackgroundTask = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            makeRefresh();
        }
    }));

    public void initialize(URL url, ResourceBundle rb) {
        ListView1.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");
        searchResult.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");

        try {
            rotatingLogo.setImage(new Image(new FileInputStream("./images/output_za2R2d.gif")));
            defaultProfile.setImage(new Image(new FileInputStream("./images/user71.png")));
            home.setImage(new Image(new FileInputStream("./images/homebutton.png")));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        BackgroundTask.setCycleCount(Timeline.INDEFINITE);
        BackgroundTask.play();

    }

    public void setClient(Client client) {
        this.client = client;
        username.setText(client.username);
    }
    @FXML
    private AnchorPane ap;

    @FXML
    private ImageView rotatingLogo;

    @FXML
    private ImageView defaultProfile;

    @FXML
    private Label username;

    @FXML
    private JFXListView<Label> ListView;

    @FXML
    private JFXButton sendMessage;

    @FXML
    private ImageView home;

    @FXML
    private Label message;

    @FXML
    private JFXListView<Label> ListView1;

    @FXML
    private JFXListView<Label> searchResult;

    @FXML
    private JFXButton sendRequest;

    @FXML
    private JFXTextField mail;

    @FXML
    private JFXTextField user;


    @FXML
    private JFXButton GameButton;

    @FXML
    private JFXButton ChatButton;

    @FXML
    private JFXButton ContactUsButton;

    @FXML
    private JFXButton FriendsButton;


    @FXML
    void makeChatMenu(ActionEvent event) throws IOException{
        FXMLLoader  loader=new FXMLLoader(getClass().getResource("/ChatMenu/ChatMenu.fxml"));
        Parent ChatMenu_parent= loader.load();
        ChatMenuController ChatMenu=loader.getController();
        ChatMenu.setClient(client);
        Scene ChatMenu_scene= new Scene(ChatMenu_parent);
        Stage ChatMenu_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ChatMenu_stage.setScene(ChatMenu_scene);
        ChatMenu_stage.show();
        BackgroundTask.stop();
        ChatMenu.makeRefresh();
    }

    @FXML
    void makeContactUsMenu(ActionEvent event) throws IOException{
        FXMLLoader  loader=new FXMLLoader(getClass().getResource("/ContactUs/ContactUs.fxml"));
        Parent ContactUs_parent= loader.load();
        ContactUsController ContactUs=loader.getController();
        ContactUs.setClient(client);
        Scene ContactUs_scene= new Scene(ContactUs_parent);
        Stage ContactUs_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ContactUs_stage.setScene(ContactUs_scene);
        ContactUs_stage.show();
        BackgroundTask.stop();
        ContactUs.makeRefresh();

    }

    @FXML
    void makeGamesMenu(ActionEvent event) throws IOException {

    }

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
    void makeSearch(KeyEvent event) {
        String mailText=mail.getText();
        String userText=user.getText();
        if(userText.equals("")){
            userText="..";
        }
        if(mailText.equals("")){
            mailText="..";
        }
        String result=client.search(userText, mailText);
        try {
            String[] resultString = result.split("//");
            Integer numb = Integer.valueOf(resultString[0]);
            searchResult.getItems().clear();
            if (isNumeric(resultString[0])) {
                while (numb >= 1) {

                    Label lbl = new Label(resultString[numb]);
                    lbl.setMaxSize(400, 42);
                    lbl.setMinSize(400, 42);

                    try {
                        lbl.setStyle("-fx-background-color: transparent; -fx-text-fill: #282828; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
                        ImageView image = new ImageView(new Image(new FileInputStream("./images/useron.png")));
                        image.setPreserveRatio(true);
                        image.setFitWidth(30);
                        image.setFitHeight(30);
                        lbl.setGraphic(image);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    searchResult.getItems().add(lbl);
                    numb--;
                }
            }
        }catch (NullPointerException e){

        }



    }

    @FXML
    void makeRequest(ActionEvent event) {
        String result = client.sendFriendRequest(searchResult.getSelectionModel().getSelectedItem().getText());
        message.setText(result);
    }

    public void makeRefresh() {
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent arg0) {
                logout();
            }
        });
        getOnlineFriends();
        getReqNotif();
        getMessageNotif();

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
    void getOnlineFriends (){
        String result = client.getOnlineFriends();
        String[] onFriendList = result.split("//");
        Integer friendCount = Integer.valueOf(onFriendList[0]);
        ListView1.getItems().clear();
        if(isNumeric(onFriendList[0])){
            while (friendCount >= 1) {

                Label lbl = new Label("  " + onFriendList[friendCount]);
                lbl.setMaxSize(206, 42);
                lbl.setMinSize(206, 42);

                try {

                    lbl.setStyle("-fx-background-color: #282828; -fx-text-fill: #ffffff; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
                    ImageView image = new ImageView(new Image(new FileInputStream("./images/useron.png")));
                    image.setPreserveRatio(true);
                    image.setFitWidth(30);
                    image.setFitHeight(30);
                    lbl.setGraphic(image);

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                ListView.getItems().add(lbl);
                friendCount--;
            }
        }
    }
    void logout(){
        client.logout();
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
