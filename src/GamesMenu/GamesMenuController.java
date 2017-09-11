package GamesMenu;


import Blackjack.BlackjackController;
import BlackjackMenu.BlackjackMenuController;
import ChatMenu.ChatMenuController;
import Client.Client;
import ContactUs.ContactUsController;
import FriendsMenu.FriendsMenuController;
import MainMenu.MainMenuController;
import TicTacMenu.TicTacMenuController;
import TicTacToe.TicTacToeController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class GamesMenuController implements Initializable{

    private Client client;
    public void setClient(Client client){
        this.client=client;
        username.setText(client.username);
    }
    public Hashtable<String, Hashtable<String , LinkedList<String>>> saved=new Hashtable<>();

    Timeline BackgroundTask = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            makeRefresh();
        }
    }));
    public void initialize(URL url, ResourceBundle rb){
        ListView.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");
        BackgroundTask.setCycleCount(Timeline.INDEFINITE);
        BackgroundTask.play();
        try {
            rotatingLogo.setImage(new Image(new FileInputStream("./images/output_za2R2d.gif")));
            defaultProfile.setImage(new Image(new FileInputStream("./images/user71.png")));
            logob.setImage(new Image(new FileInputStream("./images/bj-logo.png")));
            logot.setImage(new Image(new FileInputStream("./images/tictactoe.png")));

        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private AnchorPane ap;
    @FXML
    private ImageView logot;
    @FXML
    private ImageView logob;

    @FXML
    private JFXButton GameButton;

    @FXML
    private JFXButton FriendsButton;

    @FXML
    private JFXButton ChatButton;

    @FXML
    private ImageView rotatingLogo;

    @FXML
    private ImageView defaultProfile;

    @FXML
    private Label username;

    @FXML
    private JFXListView<Label > ListView;

    @FXML
    private Label message;

    @FXML
    private JFXButton tiTacToe;

    @FXML
    private JFXButton blackjack;

    @FXML
    private JFXButton ContactUsButton;

    @FXML
    void makeBlackJack(ActionEvent event) throws IOException{
        changingScene=true;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/BlackjackMenu/BlackjackMenu.fxml"));
        Parent BlackjackMenu_parent= loader.load();
        BlackjackMenuController BlackjackMenu=loader.getController();
        BlackjackMenu.setClient(client);
        Scene BlackjackMenu_scene= new Scene(BlackjackMenu_parent);
        Stage BlackjackMenu_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        BlackjackMenu_stage.setScene(BlackjackMenu_scene);
        BlackjackMenu_stage.show();
        BackgroundTask.stop();
        BlackjackMenu.makeRefresh();
    }

    @FXML
    void makeChatMenu(MouseEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/ChatMenu/ChatMenu.fxml"));
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
    void makeGamesMenu(MouseEvent event) {

    }
    @FXML
    void makeContactUs(MouseEvent event) throws IOException{
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
    boolean changingScene=false;
    @FXML
    void makeMenu(MouseEvent event) throws IOException {
        if(!changingScene) {
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
    private JFXButton declineButton;

    @FXML
    private JFXButton acceptButton;

    @FXML
    private JFXListView<Label> requests;

    @FXML
    void makeAcceptGame(ActionEvent event) throws IOException{
        changingScene=true;
        String game=requests.getSelectionModel().getSelectedItem().getText();
        String[]gamestring=game.split(" ");
        if(gamestring[0].equals("TICTACTOE")) {
            String gameID = saved.get(gamestring[0]).get(gamestring[1]).pop();
            client.accepttic(gameID, gamestring[1]);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicTacToe/TicTacToe.fxml"));
            Parent TicTacToe_parent = loader.load();
            TicTacToeController TicTacToe = loader.getController();
            TicTacToe.setClient(client);
            TicTacToe.setUser2(gamestring[1], gameID);
            TicTacToe.setPhoto(client.starttic(gameID));
            Scene TicTacToe_scene = new Scene(TicTacToe_parent);
            Stage TicTacToe_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            TicTacToe_stage.setScene(TicTacToe_scene);
            TicTacToe_stage.show();
            BackgroundTask.stop();
            TicTacToe.makeRefresh();
        }
        else{
            changingScene=true;
            String gameid = saved.get(gamestring[0]).get(gamestring[1]).pop();
            client.acceptblack(gameid, gamestring[1]);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Blackjack/Blackjack.fxml"));
            Parent Blackjack_parent = loader.load();
            BlackjackController Blackjack = loader.getController();
            Blackjack.setClient(client);
            Blackjack.setUser2(gamestring[1], gameid);
            Blackjack.setPhoto(client.startblack(gameid));
            Scene Blackjack_scene = new Scene(Blackjack_parent);
            Stage Blackjack_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Blackjack_stage.setScene(Blackjack_scene);
            Blackjack_stage.show();
            BackgroundTask.stop();
            Blackjack.makeRefresh();

        }
    }

    @FXML
    void makeDeclineGame(ActionEvent event) {
        String game=requests.getSelectionModel().getSelectedItem().getText();
        String[]gamestring=game.split(" ");
        if (gamestring[0].equals("TICTACTOE")) {
            client.declinetic(saved.get(gamestring[0]).get(gamestring[1]).pop(), gamestring[1]);
        }
        else {
            client.declineblack(saved.get(gamestring[0]).get(gamestring[1]).pop(), gamestring[1]);
        }
    }

    @FXML
    void makeTicTacToe(ActionEvent event) throws IOException {
        changingScene=true;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/TicTacMenu/TicTacMenu.fxml"));
        Parent TicTacMenu_parent= loader.load();
        TicTacMenuController TicTacMenu=loader.getController();
        TicTacMenu.setClient(client);
        Scene TicTacMenu_scene= new Scene(TicTacMenu_parent);
        Stage TicTacMenu_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        TicTacMenu_stage.setScene(TicTacMenu_scene);
        TicTacMenu_stage.show();
        BackgroundTask.stop();
        TicTacMenu.makeRefresh();
    }
    public void makeRefresh() {
        getOnlineFriends();
        getReqNotif();
        getMessageNotif();
        getGameReq();
        Stage stage=(Stage) ap.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent arg0) {
                logout();
            }
        });
    }
    void logout(){
        client.logout();
    }
    void getOnlineFriends (){
        String result = client.getOnlineFriends();
        System.out.println(result);
        String[] resultString = result.split("//");
        if(isNumeric(resultString[0])) {

            Integer friendCount = Integer.valueOf(resultString[0]);
            ListView.getItems().clear();

            while (friendCount >= 1) {

                Label lbl = new Label("  "+resultString[friendCount]);
                lbl.setMaxSize(206, 42);
                lbl.setMinSize(206, 42);

                try {

                    lbl.setStyle("-fx-background-color: #282828; -fx-text-fill: White; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
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
    void getGameReq(){
        String invites=client.getInvites();
        try{
            String[]game12=invites.split("--");
            String[]ticinvites=game12[0].split("//");
            String[]blackinvites=game12[1].split("//");
            requests.getItems().clear();
            if(isNumeric(ticinvites[0])) {
                Integer ticcount = Integer.valueOf(ticinvites[0]);
                String[]toSave=ticinvites[ticcount].split("/..");
                while (ticcount >= 1) {

                    if(saved.containsKey("TICTACTOE")) {
                        if(saved.get("TICTACTOE").containsKey(toSave[0])){
                            if(!saved.get("TICTACTOE").get(toSave[0]).contains(toSave[1])) {
                                saved.get("TICTACTOE").get(toSave[0]).add(toSave[1]);
                            }
                        }
                        else{
                            LinkedList<String> a=new LinkedList<>();
                            a.add(toSave[1]);
                            saved.get("TICTACTOE").put(toSave[0],a);
                        }
                    }else{
                        Hashtable<String, LinkedList<String>> entry = new Hashtable<>();
                        LinkedList<String> a = new LinkedList<String>();
                        a.add(toSave[1]);
                        entry.put(toSave[0], a);
                        saved.put("TICTACTOE", entry);
                    }

                    Label lbl = new Label("TICTACTOE"+" "+toSave[0]);
                    lbl.setMaxSize(206, 42);
                    lbl.setMinSize(206, 42);
                    lbl.setStyle("-fx-background-color: transparent; -fx-text-fill: #282828; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
                    requests.getItems().add(lbl);
                    ticcount--;
                }
            }
            if(isNumeric(blackinvites[0])) {

                Integer blackcount = Integer.valueOf(blackinvites[0]);
                String[]toSave=blackinvites[blackcount].split("/..");
                while (blackcount >= 1) {
                    if(saved.containsKey("BLACKJACK")){
                        if(saved.get("BLACKJACK").contains(toSave[0])){
                            if(!saved.get("BLACKJACK").get(toSave[0]).contains(toSave[1])) {
                                saved.get("BLACKJACK").get(toSave[0]).add(toSave[1]);
                            }
                        }
                        else{
                            Hashtable<String,LinkedList<String>> entry=new Hashtable<>();
                            LinkedList<String> a=new LinkedList<>();
                            a.add(toSave[1]);
                            entry.put(toSave[0], a);
                            saved.put("BLACKJACK", entry);
                        }
                    }
                    else{
                        Hashtable<String, LinkedList<String>> entry = new Hashtable<>();
                        LinkedList<String> a = new LinkedList<String>();
                        a.add(toSave[1]);
                        entry.put(toSave[0], a);
                        saved.put("BLACKJACK", entry);
                    }

                    Label lbl = new Label("BLACKJACK"+" "+toSave[0]);
                    lbl.setMaxSize(206, 42);
                    lbl.setMinSize(206, 42);
                    lbl.setStyle("-fx-background-color: transparent; -fx-text-fill: #282828; -fx-border-radius: 15; -fx-border-width:3; -fx-font-size: 17;");
                    requests.getItems().add(lbl);
                    blackcount--;
                }

            }

        }catch (NullPointerException e){
            requests.getItems().clear();
        }
        catch (ArrayIndexOutOfBoundsException e){
            requests.getItems().clear();
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
