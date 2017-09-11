package ChatMenu;

import Client.Client;
import ContactUs.ContactUsController;
import FriendsMenu.FriendsMenuController;
import GamesMenu.GamesMenuController;
import GlobalMessage.GlobalMessageController;
import MainMenu.MainMenuController;
import PrivateMessage.PrivateMessageController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import java.io.IOException;
import java.util.ResourceBundle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public class ChatMenuController implements Initializable{
    private Client client;
    public void setClient(Client client){
        this.client=client;
        username.setText(client.username);
    }
    Timeline BackgroundTask = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            makeRefresh();
        }
    }));

    public void initialize(URL url, ResourceBundle rb) {
        ListView.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");
        allFriends.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");

        try {
            rotatingLogo.setImage(new Image(new FileInputStream("./images/output_za2R2d.gif")));
            defaultProfile.setImage(new Image(new FileInputStream("./images/user71.png")));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        BackgroundTask.setCycleCount(Timeline.INDEFINITE);
        BackgroundTask.play();

    }

    @FXML
    private AnchorPane ap;

    @FXML
    private JFXButton GameButton;

    @FXML
    private JFXButton ContactUsButton;

    @FXML
    private ImageView rotatingLogo;

    @FXML
    private JFXButton FriendsButton;

    @FXML
    private ImageView defaultProfile;

    @FXML
    private Label username;

    @FXML
    private JFXListView<Label> ListView;

    @FXML
    private JFXButton ChatButton;

    @FXML
    private JFXListView<Label> allFriends;

    @FXML
    private JFXButton GlobalChat;

    @FXML
    void makeContactUsMenu(MouseEvent event) throws IOException{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ContactUs/ContactUs.fxml"));
            Parent ContactUs_parent = loader.load();
            ContactUsController ContactUs = loader.getController();
            ContactUs.setClient(client);
            Scene ContactUs_scene = new Scene(ContactUs_parent);
            Stage ContactUs_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ContactUs_stage.setScene(ContactUs_scene);
            ContactUs_stage.show();
            BackgroundTask.stop();
            ContactUs.makeRefresh();
    }

    @FXML
    void makeFriendsMenu(MouseEvent event) throws IOException {
        FXMLLoader  loader=new FXMLLoader(getClass().getResource("/FriendsMenu/FriendsMenu.fxml"));
        Parent FriendsMenu_parent= loader.load();
        FriendsMenuController FriendsMenu=loader.getController();
        FriendsMenu.setClient(client);
        Scene FriendsMenu_scene= new Scene(FriendsMenu_parent);
        Stage FriendsMenu_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FriendsMenu_stage.setScene(FriendsMenu_scene);
        FriendsMenu_stage.show();
        BackgroundTask.stop();
        FriendsMenu.makeRefresh();
    }


    @FXML
    void makeGamesMenu(MouseEvent event) throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/GamesMenu/GamesMenu.fxml"));
        Parent GamesMenu_parent= loader.load();
        GamesMenuController GamesMenu=loader.getController();
        GamesMenu.setClient(client);
        Scene GamesMenu_scene= new Scene(GamesMenu_parent);
        Stage GamesMenu_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        GamesMenu_stage.setScene(GamesMenu_scene);
        GamesMenu_stage.show();
        BackgroundTask.stop();
        GamesMenu.makeRefresh();
    }

    @FXML
    void makeGlobalChat(ActionEvent event) throws IOException {
        toPrivate=true;
        BackgroundTask.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GlobalMessage/GlobalMessage.fxml"));
        Parent GlobalMessage_parent = loader.load();
        GlobalMessageController GlobalMessage = loader.getController();
        GlobalMessage.setClient(client);
        Scene GlobalMessage_scene = new Scene(GlobalMessage_parent);
        Stage GlobalMessage_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        GlobalMessage_stage.setScene(GlobalMessage_scene);
        GlobalMessage.makeRefresh();

    }
    boolean toPrivate=false;
    @FXML
    void makeMenu(MouseEvent event) throws IOException{
        if(!toPrivate) {
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

    }

    @FXML
    private JFXButton startPrivate;

    @FXML
    void makeStartPrivate(ActionEvent event) throws IOException{
        toPrivate=true;
        BackgroundTask.stop();
        String user2 = allFriends.getSelectionModel().getSelectedItem().getText();
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


    public void makeRefresh() {
        getOnlineFriends();
        getReqNotif();
        getFriends();
        getMessageNotif();
        Stage stage=(Stage) ap.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent arg0) {
                logout();
            }
        });
    }
    void getFriends() {
        String result = client.getFriends();
        String[] friendList = result.split("//");
        Integer friendCount = Integer.valueOf(friendList[0]);
        allFriends.getItems().clear();
        while (friendCount >= 1) {

            Label lbl = new Label(friendList[friendCount]);
            lbl.setMaxSize(180, 120);
            lbl.setMinSize(180, 120);

            try {
                lbl.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
                ImageView image = new ImageView(new Image(new FileInputStream("./images/usernoyellow40.png")));
                image.setPreserveRatio(true);
                image.setFitWidth(40);
                image.setFitHeight(40);
                lbl.setAlignment(Pos.BASELINE_LEFT);
                lbl.setGraphic(image);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            allFriends.getItems().add(lbl);
            friendCount--;
        }

    }
    void getOnlineFriends (){
        String result = client.getOnlineFriends();
        String[] onFriendList = result.split("//");
        Integer friendCount = Integer.valueOf(onFriendList[0]);
        ListView.getItems().clear();
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
    void getReqNotif(){
        String result=client.getReqNotif();

        String[] resultString = result.split("//");
        if (isNumeric(resultString[0])) {

            Integer friendCount = Integer.valueOf(resultString[0]);
            while (friendCount >= 1) {
                Notifications Request = Notifications.create()
                        .title("  CRUNX").text(" "+resultString[friendCount] + " sent you a friend request.")
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
