package Blackjack;

import Client.Client;
import MainMenu.MainMenuController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BlackjackController implements Initializable{
    private Client client;
    private String user2;
    public String gameid;
    public String winner;
    public String photo;
    public Boolean canPlay;
    public String[] cards={"0","0","0","0","0","0","0","0","0","0"};
    public void setClient(Client client){
        this.client=client;
        username.setText(client.username);
    }
    public void setUser2(String user, String gameid){
        this.user2=user;
        opponent.setText(user);
        this.gameid=gameid;
    }
    public void setPhoto(String photo){
        this.photo=photo;
        started=true;
    }
    Timeline BackgroundTask = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event)  {
            try {
                makeRefresh();
            }catch (FileNotFoundException e){

            }
        }
    }));
    public void initialize(URL url, ResourceBundle rb){
        ListView1.setStyle("-fx-control-inner-background: #282828; -fx-border-radius: 5; -fx-border-color: #ffce2e; -fx-border-width: 3;");

        try {
            home.setImage(new Image(new FileInputStream("./images/home.png")));
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
    private ImageView defaultProfile;

    @FXML
    private Label username;

    @FXML
    private JFXListView<Label> ListView1;

    @FXML
    private ImageView home;

    @FXML
    private Label opponent;

    @FXML
    private ImageView c1;

    @FXML
    private ImageView c2;

    @FXML
    private ImageView c3;

    @FXML
    private ImageView c4;

    @FXML
    private ImageView c5;

    @FXML
    private ImageView o1;

    @FXML
    private ImageView o2;

    @FXML
    private ImageView o3;

    @FXML
    private ImageView o4;

    @FXML
    private ImageView o5;

    @FXML
    private JFXButton stay;

    @FXML
    private JFXButton GameButton11;

    @FXML
    private JFXButton ChatButton;

    @FXML
    private JFXButton ContactUsButton;

    @FXML
    private ImageView rotatingLogo;

    @FXML
    private JFXButton FriendsButton;

    @FXML
    private JFXButton GameButton;

    @FXML
    void makeChatMenu(ActionEvent event) {

    }

    @FXML
    void makeContactUsMenu(ActionEvent event) {

    }

    @FXML
    void makeGamesMenu(ActionEvent event) {

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
    void makehit(ActionEvent event) {
        if(canPlay && started && !stayed) {
            for (int i = 0; i < 5; i++) {
                if (cards[i].equals("0")) {
                    cards[i] = String.valueOf((int)Math.ceil(Math.random() * 13));
                    break;
                }
            }
            String[] array1;
            if (photo.equals("O")) {
                String[] newsend = {cards[5], cards[6], cards[7], cards[8], cards[9], cards[0], cards[1], cards[2], cards[3], cards[4]};
                array1 = newsend;
            } else {
                array1 = cards;
            }
            String tosend = "";
            for (int j = 9; j >= 0; j--) {
                tosend = array1[j] + "//" + tosend;
            }
            client.sendupdate(tosend, gameid, "ALL");
        }

    }
    boolean stayed=false;
    @FXML
    void makestay(ActionEvent event) {
        if(canPlay && started && !stayed) {
            if(photo.equals("X")) {
                String tosend = "";
                for (int j = 9; j >= 0; j--) {
                    tosend = cards[j] + "//" + tosend;
                }
                client.sendupdate(tosend, gameid, "ALL");
                stayed = true;
            }else{
                String[] array1;
                String[] newsend = {cards[5], cards[6], cards[7], cards[8], cards[9], cards[0], cards[1], cards[2], cards[3], cards[4]};
                array1 = newsend;
                String tosend = "";
                for (int j = 9; j >= 0; j--) {
                    tosend = array1[j] + "//" + tosend;
                }
                client.sendupdate(tosend, gameid, "ALL");
            }
        }
    }
    void sendsame() {
        if (started && !stayed) {
            if (photo.equals("X")) {
                String tosend = "";
                for (int j = 4; j >= 0; j--) {
                    tosend = cards[j] + "//" + tosend;
                }
                client.sendupdate(tosend, gameid, photo);
            } else {
                String[] news = {cards[5], cards[6], cards[7], cards[8], cards[9], cards[0], cards[1], cards[2], cards[3], cards[4]};
                String tosend = "";
                for (int j = 4; j >= 0; j--) {
                    tosend = cards[j] + "//" + tosend;
                }
                client.sendupdate(tosend, gameid, photo);
            }

        }
    }
    boolean started=false;
    boolean first=true;
    boolean getchar=false;
    public void blackgetturn() {
        String result = client.blackgetturn(gameid);
        if (result.equals("true")) {
            if (photo.equals("X")) {
                canPlay = true;
            } else {
                canPlay = false;
            }

        } else if (result.equals("false")) {
            if (photo.equals("O")) {
                canPlay = true;
            } else {
                canPlay = false;
            }
        }
    }
    boolean set=false;
    public void makeRefresh() throws FileNotFoundException{
        getOnlineFriends();
        getReqNotif();
        getMessageNotif();
        blackcheckwinner();
        blackgetturn();

        Stage stage=(Stage) ap.getScene().getWindow();
        if (started&!set){
            set=true;
            int a=(int)Math.ceil(Math.random()*13);
            int b=(int)Math.ceil(Math.random()*13);
            cards[0] = String.valueOf(a);
            cards[1] = String.valueOf(b);
            if(a==1){
                c1.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
            }
            else if(a==2){
                c1.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
            }
            else if(a==3){
                c1.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
            }
            else if(a==4){
                c1.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
            }
            else if(a==5){
                c1.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
            }
            else if(a==6){
                c1.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
            }
            else if(a==7){
                c1.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
            }
            else if(a==8){
                c1.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
            }
            else if(a==9){
                c1.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
            }
            else if(a==10){
                c1.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
            }
            else if(a==11){
                c1.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
            }
            else if(a==12){
                c1.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
            }
            else if(a==13){
                c1.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
            }

            if(b==1){
                c2.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
            }
            else if(b==2){
                c2.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
            }
            else if(b==3){
                c2.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
            }
            else if(b==4){
                c2.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
            }
            else if(b==5){
                c2.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
            }
            else if(b==6){
                c2.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
            }
            else if(b==7){
                c2.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
            }
            else if(b==8){
                c2.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
            }
            else if(b==9){
                c2.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
            }
            else if(b==10){
                c2.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
            }
            else if(b==11){
                c2.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
            }
            else if(b==12){
                c2.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
            }
            else if(b==13){
                c2.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
            }

            sendsame();

        }
        getcards();

        if(!started){
            refreshblack();
            getchar=false;
        }
        if(stayed && canPlay){
            if(photo.equals("X")) {
                String tosend = "";
                for (int j = 9; j >= 0; j--) {
                    tosend = cards[j] + "//" + tosend;
                }
                client.sendupdate(tosend, gameid, "ALL");
                stayed = true;
            }else{
                String[] array1;
                String[] newsend = {cards[5], cards[6], cards[7], cards[8], cards[9], cards[0], cards[1], cards[2], cards[3], cards[4]};
                array1 = newsend;
                String tosend = "";
                for (int j = 9; j >= 0; j--) {
                    tosend = array1[j] + "//" + tosend;
                }
                client.sendupdate(tosend, gameid, "ALL");
            }
        }
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent arg0) {
                logout();
            }
        });
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
                ListView1.getItems().add(lbl);
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
    void refreshblack(){
        String result=client.refreshblack(gameid);
        if(result.equals("true")){
            started=true;
            getchar=true;
            Notifications started = Notifications.create()
                    .title("  CRUNX").text(" "+ user2+" joined.")
                    .graphic(null).hideAfter(Duration.seconds(10))
                    .darkStyle();
            started.show();
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

    public void getcards() throws FileNotFoundException{
        String result=client.getcards(gameid);
        try {
            String[] resultString= result.split("//");

            if(photo.equals("X")) {
                cards=resultString;
                if (!cards[0].equals("0")) {
                    if(cards[0].equals("1")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
                    }
                    else if(cards[0].equals("2")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
                    }
                    else if(cards[0].equals("3")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
                    }
                    else if(cards[0].equals("4")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
                    }
                    else if(cards[0].equals("5")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
                    }
                    else if(cards[0].equals("6")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
                    }
                    else if(cards[0].equals("7")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
                    }
                    else if(cards[0].equals("8")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
                    }
                    else if(cards[0].equals("9")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
                    }
                    else if(cards[0].equals("10")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
                    }
                    else if(cards[0].equals("11")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
                    }
                    else if(cards[0].equals("12")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
                    }
                    else if(cards[0].equals("13")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
                    }

                }
                if (!cards[1].equals("0")) {
                    if(cards[1].equals("1")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
                    }
                    else if(cards[1].equals("2")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
                    }
                    else if(cards[1].equals("3")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
                    }
                    else if(cards[1].equals("4")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
                    }
                    else if(cards[1].equals("5")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
                    }
                    else if(cards[1].equals("6")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
                    }
                    else if(cards[1].equals("7")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
                    }
                    else if(cards[1].equals("8")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
                    }
                    else if(cards[1].equals("9")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
                    }
                    else if(cards[1].equals("10")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
                    }
                    else if(cards[1].equals("11")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
                    }
                    else if(cards[1].equals("12")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
                    }
                    else if(cards[1].equals("13")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
                    }
                }
                if (!cards[2].equals("0")) {
                    if(cards[2].equals("1")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
                    }
                    else if(cards[2].equals("2")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
                    }
                    else if(cards[2].equals("3")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
                    }
                    else if(cards[2].equals("4")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
                    }
                    else if(cards[2].equals("5")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
                    }
                    else if(cards[2].equals("6")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
                    }
                    else if(cards[2].equals("7")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
                    }
                    else if(cards[2].equals("8")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
                    }
                    else if(cards[2].equals("9")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
                    }
                    else if(cards[2].equals("10")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
                    }
                    else if(cards[2].equals("11")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
                    }
                    else if(cards[2].equals("12")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
                    }
                    else if(cards[2].equals("13")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
                    }
                }
                if (!cards[3].equals("0")) {
                    if(cards[3].equals("1")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
                    }
                    else if(cards[3].equals("2")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
                    }
                    else if(cards[3].equals("3")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
                    }
                    else if(cards[3].equals("4")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
                    }
                    else if(cards[3].equals("5")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
                    }
                    else if(cards[3].equals("6")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
                    }
                    else if(cards[3].equals("7")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
                    }
                    else if(cards[3].equals("8")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
                    }
                    else if(cards[3].equals("9")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
                    }
                    else if(cards[3].equals("10")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
                    }
                    else if(cards[3].equals("11")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
                    }
                    else if(cards[3].equals("12")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
                    }
                    else if(cards[3].equals("13")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
                    }
                }
                if (!cards[4].equals("0")) {
                    if(cards[4].equals("1")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
                    }
                    else if(cards[4].equals("2")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
                    }
                    else if(cards[4].equals("3")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
                    }
                    else if(cards[4].equals("4")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
                    }
                    else if(cards[4].equals("5")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
                    }
                    else if(cards[4].equals("6")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
                    }
                    else if(cards[4].equals("7")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
                    }
                    else if(cards[4].equals("8")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
                    }
                    else if(cards[4].equals("9")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
                    }
                    else if(cards[4].equals("10")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
                    }
                    else if(cards[4].equals("11")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
                    }
                    else if(cards[4].equals("12")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
                    }
                    else if(cards[4].equals("13")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
                    }
                }
                if (!cards[5].equals("0")) {
                    if(cards[5].equals("1")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
                    }
                    else if(cards[5].equals("2")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
                    }
                    else if(cards[5].equals("3")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
                    }
                    else if(cards[5].equals("4")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
                    }
                    else if(cards[5].equals("5")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
                    }
                    else if(cards[5].equals("6")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
                    }
                    else if(cards[5].equals("7")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
                    }
                    else if(cards[5].equals("8")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
                    }
                    else if(cards[5].equals("9")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
                    }
                    else if(cards[5].equals("10")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
                    }
                    else if(cards[5].equals("11")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
                    }
                    else if(cards[5].equals("12")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
                    }
                    else if(cards[5].equals("13")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
                    }
                }
                if (!cards[6].equals("0")) {
                    o2.setImage(new Image(new FileInputStream("./images/Cards/CardBack.png")));
                }
                if (!cards[7].equals("0")) {
                    o3.setImage(new Image(new FileInputStream("./images/Cards/CardBack.png")));
                }
                if (!cards[8].equals("0")) {
                    o4.setImage(new Image(new FileInputStream("./images/Cards/CardBack.png")));
                }
                if (!cards[9].equals("0")) {
                    o5.setImage(new Image(new FileInputStream("./images/Cards/CardBack.png")));
                }
            }
            else if(photo.equals("O")){



                String[] news={resultString[5],resultString[6],resultString[7],resultString[8],resultString[9],resultString[0],resultString[1],resultString[2],resultString[3],resultString[4]};
                cards=news;
                resultString=news;
                if (!resultString[0].equals("0")) {
                    if(resultString[0].equals("1")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
                    }
                    else if(resultString[0].equals("2")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
                    }
                    else if(resultString[0].equals("3")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
                    }
                    else if(resultString[0].equals("4")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
                    }
                    else if(resultString[0].equals("5")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
                    }
                    else if(resultString[0].equals("6")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
                    }
                    else if(resultString[0].equals("7")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
                    }
                    else if(resultString[0].equals("8")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
                    }
                    else if(resultString[0].equals("9")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
                    }
                    else if(resultString[0].equals("10")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
                    }
                    else if(resultString[0].equals("11")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
                    }
                    else if(resultString[0].equals("12")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
                    }
                    else if(resultString[0].equals("13")){
                        c1.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
                    }

                }
                if (!resultString[1].equals("0")) {
                    if(resultString[1].equals("1")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
                    }
                    else if(resultString[1].equals("2")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
                    }
                    else if(resultString[1].equals("3")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
                    }
                    else if(resultString[1].equals("4")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
                    }
                    else if(resultString[1].equals("5")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
                    }
                    else if(resultString[1].equals("6")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
                    }
                    else if(resultString[1].equals("7")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
                    }
                    else if(resultString[1].equals("8")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
                    }
                    else if(resultString[1].equals("9")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
                    }
                    else if(resultString[1].equals("10")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
                    }
                    else if(resultString[1].equals("11")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
                    }
                    else if(resultString[1].equals("12")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
                    }
                    else if(resultString[1].equals("13")){
                        c2.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
                    }
                }
                if (!resultString[2].equals("0")) {
                    if(resultString[2].equals("1")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
                    }
                    else if(resultString[2].equals("2")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
                    }
                    else if(resultString[2].equals("3")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
                    }
                    else if(resultString[2].equals("4")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
                    }
                    else if(resultString[2].equals("5")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
                    }
                    else if(resultString[2].equals("6")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
                    }
                    else if(resultString[2].equals("7")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
                    }
                    else if(resultString[2].equals("8")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
                    }
                    else if(resultString[2].equals("9")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
                    }
                    else if(resultString[2].equals("10")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
                    }
                    else if(resultString[2].equals("11")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
                    }
                    else if(resultString[2].equals("12")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
                    }
                    else if(resultString[2].equals("13")){
                        c3.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
                    }
                }
                if (!resultString[3].equals("0")) {
                    if(resultString[3].equals("1")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
                    }
                    else if(resultString[3].equals("2")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
                    }
                    else if(resultString[3].equals("3")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
                    }
                    else if(resultString[3].equals("4")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
                    }
                    else if(resultString[3].equals("5")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
                    }
                    else if(resultString[3].equals("6")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
                    }
                    else if(resultString[3].equals("7")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
                    }
                    else if(resultString[3].equals("8")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
                    }
                    else if(resultString[3].equals("9")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
                    }
                    else if(resultString[3].equals("10")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
                    }
                    else if(resultString[3].equals("11")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
                    }
                    else if(resultString[3].equals("12")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
                    }
                    else if(resultString[3].equals("13")){
                        c4.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
                    }
                }
                if (!resultString[4].equals("0")) {
                    if(resultString[4].equals("1")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
                    }
                    else if(resultString[4].equals("2")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
                    }
                    else if(resultString[4].equals("3")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
                    }
                    else if(resultString[4].equals("4")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
                    }
                    else if(resultString[4].equals("5")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
                    }
                    else if(resultString[4].equals("6")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
                    }
                    else if(resultString[4].equals("7")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
                    }
                    else if(resultString[4].equals("8")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
                    }
                    else if(resultString[4].equals("9")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
                    }
                    else if(resultString[4].equals("10")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
                    }
                    else if(resultString[4].equals("11")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
                    }
                    else if(resultString[4].equals("12")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
                    }
                    else if(resultString[4].equals("13")){
                        c5.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
                    }
                }
                if (!resultString[5].equals("0")) {
                    if(resultString[5].equals("1")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/CardA.png")));
                    }
                    else if(resultString[5].equals("2")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card2.png")));
                    }
                    else if(resultString[5].equals("3")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card3.png")));
                    }
                    else if(resultString[5].equals("4")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card4.png")));
                    }
                    else if(resultString[5].equals("5")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card5.png")));
                    }
                    else if(resultString[5].equals("6")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card6.png")));
                    }
                    else if(resultString[5].equals("7")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card7.png")));
                    }
                    else if(resultString[5].equals("8")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card8.png")));
                    }
                    else if(resultString[5].equals("9")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card9.png")));
                    }
                    else if(resultString[5].equals("10")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/Card10.png")));
                    }
                    else if(resultString[5].equals("11")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/CardJ.png")));
                    }
                    else if(resultString[5].equals("12")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/CardQ.png")));
                    }
                    else if(resultString[5].equals("13")){
                        o1.setImage(new Image(new FileInputStream("./images/Cards/CardK.png")));
                    }
                }
                if (!resultString[6].equals("0")) {
                    o2.setImage(new Image(new FileInputStream("./images/Cards/CardBack.png")));
                }
                if (!resultString[7].equals("0")) {
                    o3.setImage(new Image(new FileInputStream("./images/Cards/CardBack.png")));
                }
                if (!resultString[8].equals("0")) {
                    o4.setImage(new Image(new FileInputStream("./images/Cards/CardBack.png")));
                }
                if (!resultString[9].equals("0")) {
                    o5.setImage(new Image(new FileInputStream("./images/Cards/CardBack.png")));
                }

            }
        }catch (NullPointerException e){

        }
    }
    public void blackcheckwinner(){
        String result=client.checkwinblack(gameid);
        try {
            String[] resultString = result.split("//");
            if (resultString[0].equals("WINNER")) {
                if (resultString[1].equals(photo)) {
                    Notifications Request = Notifications.create()
                            .title("  CRUNX").text(client.username+" WON.")
                            .graphic(null).hideAfter(Duration.seconds(10))
                            .darkStyle();
                    Request.show();
                    winner=client.username;
                    stop();
                }
                else {

                    Notifications Request = Notifications.create()
                            .title("  CRUNX").text(client.username+" LOST.")
                            .graphic(null).hideAfter(Duration.seconds(10))
                            .darkStyle();
                    Request.show();
                    winner=user2;
                    stop();
                }
            }
            else if(resultString[0].equals("TIE")){
                Notifications Request = Notifications.create()
                        .title("  CRUNX").text(" It is a tie.")
                        .graphic(null).hideAfter(Duration.seconds(10))
                        .darkStyle();
                Request.show();

                winner="NONE";
                stop();
            }
        }catch (NullPointerException e){

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
    public void stop() {
        try {
            BackgroundTask.stop();
            Client.updatesb(winner);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu/MainMenu.fxml"));
            Parent MainMenu_parent = loader.load();
            MainMenuController mainMenu = loader.getController();
            mainMenu.setClient(client);
            Scene MainMenu_scene = new Scene(MainMenu_parent);
            Stage MainMenu_stage = (Stage) ap.getScene().getWindow();
            MainMenu_stage.setScene(MainMenu_scene);
            MainMenu_stage.show();
            BackgroundTask.stop();
            mainMenu.makeRefresh();
        }
        catch (IOException e){

        }

    }

}
