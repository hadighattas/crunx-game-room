package Server;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Properties;

import Client.Client;
import DatabaseHandler.*;
import com.sun.org.apache.bcel.internal.generic.Select;
import sun.util.locale.provider.HostLocaleProviderAdapterImpl;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Server {
    private LinkedList<String> onlineusers=new LinkedList<String>();
    private Hashtable<String,LinkedList<String>> request=new Hashtable<>();
    private Hashtable<String,LinkedList<String>> requestNotif=new Hashtable<>();
    private Hashtable<String, Hashtable<String,LinkedList<String>>> allMessages= new Hashtable<>();
    private LinkedList<String> allMessagesg=new LinkedList<>();
    private Hashtable<String, LinkedList<String>> msgNotif= new Hashtable<>();
    private Hashtable<String,String[]> TicTacToeArray=new Hashtable<>();
    private Hashtable<String,String[]> BlackArray=new Hashtable<>();
    private Hashtable<String,LinkedList<String>> ticInv=new Hashtable<>();
    private Hashtable<String,LinkedList<String>> ticNotif=new Hashtable<>();
    private Hashtable<String,LinkedList<String>> blackInv=new Hashtable<>();
    private Hashtable<String,LinkedList<String>> blackNotif=new Hashtable<>();
    private Hashtable<String, Boolean> ticStatus=new Hashtable<>();
    private Hashtable<String, Boolean> blackStatus=new Hashtable<>();
    private Hashtable<String, Boolean> turn=new Hashtable<>();
    private Hashtable<String, Boolean> blackturn=new Hashtable<>();
    private Hashtable<String, Integer> counter=new Hashtable<>();
    private String[] a={"true", "true"};
    private Hashtable<String, String[]> stay;

    private int port = 6788;
    private ServerSocket serverSocket;
    public Server() throws ClassNotFoundException {}

    public void acceptConnections() {
        try
        {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            System.err.println("ServerSocket instantiation failure");
            e.printStackTrace();
            System.exit(0);
        }

        while (true) {
            try
            {
                Socket newConnection = serverSocket.accept();
                System.out.println("accepted connection");
                //now each client gets a threads that deals with its connection and requests //
                ServerThread st = new ServerThread(newConnection);
                new Thread(st).start();
                //now the server will continue waiting for other requests and the current user will be serviced
                // by the created thread //
            }
            catch (IOException ioe)
            {
                System.err.println("server accept failed");
            }
        }
    }

    public static void main(String args[]) {

        Server server = null;
        try {
            server = new Server();
        } catch (ClassNotFoundException e) {
            //   System.out.println("unable to load JDBC driver");
            e.printStackTrace();
            System.exit(1);
        }

        server.acceptConnections();
    }

    class ServerThread implements Runnable {

        private Socket socket;
        private BufferedReader datain;
        private DataOutputStream dataout;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        public void run()
        {
            try
            {
                datain =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
                dataout = new DataOutputStream(socket.getOutputStream());
            }
            catch (IOException e)
            {
                return;
            }
            //work


            try {
                String sql=null;
                try
                {
                    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false", "root", "root");
                    DatabaseHandler mydb=new DatabaseHandler();
                    Statement myStmt = myConn.createStatement();
                    mydb.executeQuery(myStmt, "select * from table1");
                    String input=datain.readLine();
                    System.out.println(input);
                    String []inFromUser=input.split("//");
                    if (inFromUser[0].equals("LOGIN")){

                        sql = "Select user,pass from table1 where user='"+inFromUser[1]+"' and pass='"+inFromUser[2]+"'";
                        ResultSet myRes=myStmt.executeQuery(sql);
                        if (myRes.next()) {
                            System.out.println("OK");
                            dataout.writeBytes("Success\n");
                            onlineusers.add(inFromUser[1]);
                            System.out.print(inFromUser[1]+ " is now online");
                        }
                        else {
                            dataout.writeBytes("Fail\n");
                        }

                    }
                    else if (inFromUser[0].equals("SIGNUP")){

                        sql = "insert into table1 (user, pass, email, gender, age, country, question, answer, mailvisible, agevisible, countryvisible, scoretic, scoreblack) " +
                                "values ('"+inFromUser[1]+"', '"+inFromUser[2]+"', '"+inFromUser[3]+"', '"+inFromUser[4]+"', '"+inFromUser[5]
                                +"', '"+inFromUser[6]+"', '"+inFromUser[7]+"', '"+inFromUser[8]+"', 'yes', 'yes', 'yes', '0', '0' )";
                        Boolean done=mydb.executeUpdate(myStmt,sql);
                        if(done)
                        {
                            System.out.println("User has signed up");
                            dataout.writeBytes("Success\n");
                        }
                        else{
                            dataout.writeBytes("Fail\n");
                        }
                    }
                    else if(inFromUser[0].equals("GETONFR")){
                        sql="select Friend1,Friend2 from Friends WHERE Friend1='"+inFromUser[1]+"'";
                        ResultSet myRes=myStmt.executeQuery(sql);
                        String outToUser="";
                        Integer onFriendCount=0;
                        while(myRes.next()) {

                            String Friend=myRes.getString("Friend2");
                            if(onlineusers.contains(Friend.toLowerCase())||onlineusers.contains(Friend)) {
                                onFriendCount++;
                                outToUser = outToUser + "//" +Friend;
                            }
                        }
                        outToUser=onFriendCount.toString()+outToUser;
                        dataout.writeBytes(outToUser+'\n');
                    }
                    else if(inFromUser[0].equals("GETFR")){
                        sql="select Friend1,Friend2 from Friends WHERE Friend1='"+inFromUser[1]+"'";
                        ResultSet myRes=myStmt.executeQuery(sql);
                        String outToUser="";
                        Integer onFriendCount=0;
                        while(myRes.next()) {
                            String Friend=myRes.getString("Friend2");
                            onFriendCount++;
                            outToUser = outToUser + "//" +Friend;
                        }
                        outToUser=onFriendCount.toString()+outToUser;
                        dataout.writeBytes(outToUser+'\n');
                    }
                    else if(inFromUser[0].equals("REQUEST")) {
                        sql = "SELECT Friend1, Friend2 from Friends where Friend1='" + inFromUser[1] + "' and Friend2='" + inFromUser[2] + "'";
                        ResultSet myRes = myStmt.executeQuery(sql);
                        if (myRes.next()) {
                            dataout.writeBytes("Already\n");
                        } else if (request.containsKey(inFromUser[2])) {
                            if (request.get(inFromUser[2]).contains(inFromUser[1])) {
                                dataout.writeBytes("Alreadysent\n");
                            }
                        } else {
                            sql = "select user from table1 WHERE user='" + inFromUser[2] + "'";
                            ResultSet myRes1 = myStmt.executeQuery(sql);
                            if (myRes1.next()) {
                                if (request.containsKey(inFromUser[2])) {
                                    request.get(inFromUser[2]).add(inFromUser[1]);
                                    requestNotif.get(inFromUser[2]).add(inFromUser[1]);
                                } else {
                                    request.put(inFromUser[2], new LinkedList<String>());
                                    requestNotif.put(inFromUser[2], new LinkedList<String>());
                                    request.get(inFromUser[2]).add(inFromUser[1]);
                                    requestNotif.get(inFromUser[2]).add(inFromUser[1]);
                                }
                                dataout.writeBytes("Success\n");
                            } else {
                                dataout.writeBytes("Fail\n");
                            }
                        }
                    }
                    else if(inFromUser[0].equals("GETREQ")) {
                        String response="";
                        int i=0;
                        if(!request.containsKey(inFromUser[1])){
                            dataout.writeBytes("No friend requests");
                        }
                        else {
                            while (i < request.get(inFromUser[1]).size()) {
                                response = response + "//"+request.get(inFromUser[1]).get(i);
                                i++;
                            }
                            response=String.valueOf(i)+response;
                            dataout.writeBytes(response+'\n');
                        }

                    }
                    else if(inFromUser[0].equals("ADD")){
                        sql = "insert into friends(Friend1, Friend2) values ('"+inFromUser[1]+"', '"+inFromUser[2]+"')";
                        Boolean done1=mydb.executeUpdate(myStmt,sql);
                        sql = "insert into friends(Friend1, Friend2) values ('"+inFromUser[2]+"', '"+inFromUser[1]+"')";
                        Boolean done2=mydb.executeUpdate(myStmt,sql);
                        if(done1 && done2)
                        {
                            dataout.writeBytes("Success\n");
                            request.get(inFromUser[1]).remove(inFromUser[2]);
                        }
                        else{
                            dataout.writeBytes("Fail\n");
                        }
                    }
                    else if(inFromUser[0].equals("DECLINE")){
                        request.get(inFromUser[1]).remove(inFromUser[2]);
                        if(request.get(inFromUser[1]).size()==0){
                            request.remove(inFromUser[1]);
                        }
                        dataout.writeBytes("Success");
                    }
                    else if(inFromUser[0].equals("REQNOTIF")){
                        String response="";
                        if(requestNotif.containsKey(inFromUser[1])) {
                            LinkedList<String> reqtosend = requestNotif.get(inFromUser[1]);
                            Integer reqNumber = reqtosend.size();
                            while (reqtosend.size() > 0) {
                                response = response + "//" + reqtosend.pop();
                            }
                            response = reqNumber.toString() + response;
                            requestNotif.remove(inFromUser[1]);
                            dataout.writeBytes(response + '\n');
                        }
                        else{
                            dataout.writeBytes("0"+'\n');
                        }
                    }
                    else if(inFromUser[0].equals("LOGOUT")){
                        onlineusers.remove(inFromUser[1]);
                    }
                    else if(inFromUser[0].equals("MESSAGE")){
                        if(msgNotif.containsKey(inFromUser[2])){
                            msgNotif.get(inFromUser[2]).add(inFromUser[1]);
                        }
                        else{
                            msgNotif.put(inFromUser[2],new LinkedList<String>());
                            msgNotif.get(inFromUser[2]).add(inFromUser[1]);
                        }
                        if(allMessages.containsKey(inFromUser[2])){
                            if(allMessages.get(inFromUser[2]).containsKey(inFromUser[1])){
                                allMessages.get(inFromUser[2]).get(inFromUser[1]).add(inFromUser[1]+"--"+inFromUser[3]);
                            }
                            else{
                                allMessages.get(inFromUser[2]).put(inFromUser[1], new LinkedList<>());
                                allMessages.get(inFromUser[2]).get(inFromUser[1]).add(inFromUser[1]+"--"+inFromUser[3]);
                            }
                        }
                        else if(!allMessages.containsKey(inFromUser[2])){
                            allMessages.put(inFromUser[2], new Hashtable<>());
                            allMessages.get(inFromUser[2]).put(inFromUser[1], new LinkedList<>());
                            allMessages.get(inFromUser[2]).get(inFromUser[1]).add(inFromUser[1]+"--"+inFromUser[3]);
                        }
                        if(allMessages.containsKey(inFromUser[1])){
                            if(allMessages.get(inFromUser[1]).containsKey(inFromUser[2])){
                                allMessages.get(inFromUser[1]).get(inFromUser[2]).add(inFromUser[1]+"--"+inFromUser[3]);
                            }
                            else{
                                allMessages.get(inFromUser[1]).put(inFromUser[2], new LinkedList<>());
                                allMessages.get(inFromUser[1]).get(inFromUser[2]).add(inFromUser[1]+"--"+inFromUser[3]);
                            }
                        }
                        else if(!allMessages.containsKey(inFromUser[1])){
                            allMessages.put(inFromUser[1], new Hashtable<>());
                            allMessages.get(inFromUser[1]).put(inFromUser[2], new LinkedList<>());
                            allMessages.get(inFromUser[1]).get(inFromUser[2]).add(inFromUser[1]+"--"+inFromUser[3]);
                        }

                        dataout.writeBytes("Success\n");

                    }
                    else if(inFromUser[0].equals("GETMSG")){
                        String response;
                        if(allMessages.containsKey(inFromUser[1])){
                            Hashtable<String, LinkedList<String>> HT=allMessages.get(inFromUser[1]);
                            if(HT.containsKey(inFromUser[2])){
                                LinkedList<String> LL=HT.get(inFromUser[2]);
                                Integer size=LL.size();
                                response=String.valueOf(size);
                                while(size>=1){
                                    response=response+"//"+LL.get(LL.size()-size);
                                    size--;
                                }
                            }
                            else{
                                response="NO";
                            }
                        }
                        else{
                            response="NO";

                        }
                        dataout.writeBytes(response+'\n');
                    }
                    else if(inFromUser[0].equals("SEARCH")){
                        String response="";
                        if(!inFromUser[1].equals("..") && !inFromUser[2].equals("..")) {
                            sql = "SELECT user, email, country FROM table1 WHERE user LIKE '" + inFromUser[1] + "%' and email LIKE '"
                                    + inFromUser[2] + "%'";
                        }
                        else if(inFromUser[1].equals("..")  && !inFromUser[2].equals("..")){
                            sql = "SELECT user, email, country FROM table1 WHERE email LIKE '"
                                    + inFromUser[2] + "%'";
                        }
                        else if (inFromUser[2].equals("..")&& !inFromUser[1].equals("..")){
                            sql = "SELECT user, email, country FROM table1 WHERE user LIKE '"
                                    + inFromUser[1] + "%'";
                        }
                        else if (inFromUser[1].equals("..")&& inFromUser[2].equals("..")){
                            response="No response";
                        }

                        ResultSet myRs=myStmt.executeQuery(sql);
                        Integer count=0;
                        while (myRs.next()) {
                            count++;
                            response=response+"//"+myRs.getString("user");
                        }
                        response=String.valueOf(count)+response;
                        dataout.writeBytes(response+'\n');

                    }
                    else if(inFromUser[0].equals("UPDATEINFO")){
                        sql="UPDATE table1 set country='"+inFromUser[4]+"', age='"+inFromUser[3]+
                                "', agevisible='"+inFromUser[6]+"' , mailvisible='"+inFromUser[5]
                                +"', countryvisible='"+inFromUser[7]+"' WHERE user ='"+inFromUser[1]+"'";
                        myStmt.executeUpdate(sql);
                        dataout.writeBytes("Success");
                    }
                    else if(inFromUser[0].equals("GETINFO")){
                        String response="b";
                        sql="SELECT * from table1 WHERE user='"+inFromUser[1]+"'";
                        ResultSet myRs=myStmt.executeQuery(sql);
                        myRs.next();
                        response=myRs.getString("email")+"//"+myRs.getString("age")+"//"+myRs.getString("country")+"//"+myRs.getString("mailvisible")+"//"+myRs.getString("agevisible")+"//"+myRs.getString("countryvisible")+'\n';
                        System.out.print(response);

                        dataout.writeBytes(response);
                    }
                    else if(inFromUser[0].equals("MSGNOTIF")){
                        String response="";
                        if(msgNotif.containsKey(inFromUser[1])) {
                            LinkedList<String> msgtosend = msgNotif.get(inFromUser[1]);
                            Integer msgNumber = msgtosend.size();
                            while (msgtosend.size() > 0) {
                                response = response + "//" + msgtosend.pop();
                            }
                            response = msgNumber.toString() + response;
                            msgNotif.remove(inFromUser[1]);
                            dataout.writeBytes(response + '\n');
                        }
                        else{
                            dataout.writeBytes("0"+'\n');
                        }
                    }
                    else if(inFromUser[0].equals("INVTIC")){//INVTIC USER1 USER2 GAMEID
                        if (ticInv.containsKey(inFromUser[2])) {
                            if (ticInv.get(inFromUser[2]).contains(inFromUser[1])) {
                                dataout.writeBytes("Alreadysent\n");
                            }
                            else {
                                ticInv.get(inFromUser[2]).add(inFromUser[1]+"/.."+inFromUser[3]);
                                ticNotif.get(inFromUser[2]).add(inFromUser[1]);
                                request.get(inFromUser[2]).add(inFromUser[1]+"//"+inFromUser[3]);
                            }
                        } else {

                            ticInv.put(inFromUser[2], new LinkedList<String>());
                            ticInv.get(inFromUser[2]).add(inFromUser[1]+"/.."+inFromUser[3]);
                            ticNotif.put(inFromUser[2], new LinkedList<String>());
                            ticNotif.get(inFromUser[2]).add(inFromUser[1]+"//"+inFromUser[3]);
                            ticNotif.get(inFromUser[2]).add(inFromUser[1]);
                            dataout.writeBytes("Success\n");
                        }
                    }
                    else if(inFromUser[0].equals("ACCEPTTIC")){//ACCEPTTIC gameID
                        ticStatus.put(inFromUser[1], true);
                        ticInv.get(inFromUser[2]).remove(inFromUser[3]+"/.."+inFromUser[1]);

                    }
                    else if(inFromUser[0].equals("DECLINETIC")){//DECLINETIC gameID
                        ticStatus.put(inFromUser[1], false);
                        ticInv.get(inFromUser[2]).remove(inFromUser[3]+"/.."+inFromUser[1]);
                    }
                    else if(inFromUser[0].equals("CHECKTIC")){//CHECKTIC gameID
                        if(ticStatus.containsKey(inFromUser[1])){
                            if(ticStatus.get(inFromUser[1])) {

                                dataout.writeBytes("Accepted\n");
                            }
                            else {
                                dataout.writeBytes("Declined\n");
                            }
                            ticStatus.remove(inFromUser[1]);
                        }
                        else {
                            dataout.writeBytes("Wait\n");
                        }

                    }
                    else if(inFromUser[0].equals("STARTTIC")){//STARTTIC gameID
                        if(!TicTacToeArray.containsKey(inFromUser[1])) {
                            String[] a={"1","2","3","4","5","6","7","8","9"};
                            TicTacToeArray.put(inFromUser[1], a);
                            dataout.writeBytes("X\n");
                        }
                        else{
                            dataout.writeBytes("O\n");
                        }
                        turn.put(inFromUser[1], true);

                    }
                    else if (inFromUser[0].equals("PLACETIC")){//PLACE photo loc gameID
                        String response="";
                        TicTacToeArray.get(inFromUser[3])[Integer.valueOf(inFromUser[2])]=inFromUser[1];
                        for(int i=0; i<9; i++){
                            response=response+TicTacToeArray.get(inFromUser[3])[i]+"//";
                        }
                        dataout.writeBytes(response+'\n');
                        boolean a=!turn.get(inFromUser[3]);
                        turn.put(inFromUser[3], a);
                    }
                    else if(inFromUser[0].equals("WINNERTIC")){//WINNERTIC gameID
                        String[]Array= TicTacToeArray.get(inFromUser[1]);
                        System.out.print(Array);
                        if(Array[0].equals(Array[1]) && Array[0].equals(Array[2])){
                            dataout.writeBytes("WINNER//"+Array[0]);
//                            TicTacToeArray.remove(inFromUser[1]);
                        }
                        else if(Array[3].equals(Array[4]) && Array[3].equals(Array[5])){
                            dataout.writeBytes("WINNER//"+Array[3]);
//                            TicTacToeArray.remove(inFromUser[1]);
                        }
                        else if(Array[6].equals(Array[7]) && Array[6].equals(Array[8])){
                            dataout.writeBytes("WINNER//"+Array[6]);
//                            TicTacToeArray.remove(inFromUser[1]);
                        }
                        else if(Array[0].equals(Array[4]) && Array[0].equals(Array[8])){
                            dataout.writeBytes("WINNER//"+Array[0]);
//                            TicTacToeArray.remove(inFromUser[1]);
                        }
                        else if(Array[2].equals(Array[4]) && Array[2].equals(Array[6])){
                            dataout.writeBytes("WINNER//"+Array[2]);
//                            TicTacToeArray.remove(inFromUser[1]);
                        }
                        else if(Array[0].equals(Array[3]) && Array[0].equals(Array[6])){
                            dataout.writeBytes("WINNER//"+Array[0]);
//                            TicTacToeArray.remove(inFromUser[1]);
                        }
                        else if(Array[1].equals(Array[4]) && Array[4].equals(Array[7])){
                            dataout.writeBytes("WINNER//"+Array[1]);
//                            TicTacToeArray.remove(inFromUser[1]);
                        }
                        else if(Array[2].equals(Array[5]) && Array[2].equals(Array[8])){
                            dataout.writeBytes("WINNER//"+Array[2]);
//                            TicTacToeArray.remove(inFromUser[1]);
                        }
                        else if(Array[0].equals("1")||Array[1].equals("2")||Array[2].equals("3")||Array[3].equals("4")
                                ||Array[4].equals("5")||Array[5].equals("6")||Array[6].equals("7")||Array[7].equals("8")
                                ||Array[8].equals("9")){
                            dataout.writeBytes("Notyet//1\n");
                        }
                        else{
                            dataout.writeBytes("Nowinner//1\0");
                            TicTacToeArray.remove(inFromUser[1]);
                        }

                    }
                    else if(inFromUser[0].equals("GETUSERS")){
                        String response="";
                        LinkedList<String> users=new LinkedList<>();
                        sql="SELECT *FROM table1";
                        ResultSet resultSet=myStmt.executeQuery(sql);
                        while (resultSet.next()){
                            users.add(resultSet.getString("user"));
                        }
                        sql="select Friend1,Friend2 from Friends WHERE Friend1='"+inFromUser[1]+"'";
                        ResultSet myRes=myStmt.executeQuery(sql);
                        String outToUser="";
                        while(myRes.next()) {
                            String Friend=myRes.getString("Friend2");
                            if(users.contains(Friend)){
                                users.remove(Friend);
                            }
                        }
                        users.remove(inFromUser[1]);
                        String size=Integer.toString(users.size());
                        while (users.size()>0){
                            response=response+"//"+users.pop();
                        }
                        response=size+response;
                        dataout.writeBytes(response+'\n');
                    }
                    else if(inFromUser[0].equals("GETGAMEREQ")){
                        String blackinvites="";
                        String ticinvites="";
                        if(ticInv.containsKey(inFromUser[1])) {
                            Integer ticnumb = ticInv.get(inFromUser[1]).size();
                            Integer ticcounter = ticnumb;
                            while (ticcounter > 0) {
                                ticinvites=ticinvites+"//"+ticInv.get(inFromUser[1]).get(ticnumb-1);
                                ticcounter--;
                            }
                            ticinvites=String.valueOf(ticnumb)+ticinvites;

                        }
                        if(blackInv.containsKey(inFromUser[1])) {
                            Integer blacknumb = blackInv.get(inFromUser[1]).size();
                            Integer blackcounter = blacknumb;
                            while (blackcounter > 0) {
                                blackinvites=blackinvites+"//"+blackInv.get(inFromUser[1]).get(blacknumb-1);
                                blackcounter--;
                            }
                            blackinvites=String.valueOf(blacknumb)+blackinvites;

                        }
                        if(blackinvites.equals("")){
                            blackinvites=" ";
                        }
                        if(ticinvites.equals("")){
                            ticinvites=" ";
                        }

                        dataout.writeBytes(ticinvites+"--"+blackinvites+'\n');
                    }
                    else if(inFromUser[0].equals("REFRESHTIC")){//REFRESH gameid
                        dataout.writeBytes(String.valueOf(ticStatus.get(inFromUser[1])));
                    }
                    else if(inFromUser[0].equals("GETTURNTIC")){//GETTURN gameid
                        dataout.writeBytes(Boolean.toString(turn.get(inFromUser[1]))+'\n');
                    }
                    else if(inFromUser[0].equals("GETBOARD")){
                        String response="";
                        for(int i=0; i<9; i++){
                            response=response+TicTacToeArray.get(inFromUser[1])[i]+"//";
                        }
                        dataout.writeBytes(response+'\n');
                    }
                    else if(inFromUser[0].equals("INVBLACK")){//INVTIC USER1 USER2 GAMEID
                        if (blackInv.containsKey(inFromUser[2])) {
                            if (blackInv.get(inFromUser[2]).contains(inFromUser[1])) {
                                dataout.writeBytes("Alreadysent\n");
                            }
                            else {
                                blackInv.get(inFromUser[2]).add(inFromUser[1]+"/.."+inFromUser[3]);
                                blackNotif.get(inFromUser[2]).add(inFromUser[1]);
                                blackNotif.get(inFromUser[2]).add(inFromUser[1]+"//"+inFromUser[3]);
                            }
                        } else {

                            blackInv.put(inFromUser[2], new LinkedList<String>());
                            blackInv.get(inFromUser[2]).add(inFromUser[1]+"/.."+inFromUser[3]);
                            blackNotif.put(inFromUser[2], new LinkedList<String>());
                            blackNotif.get(inFromUser[2]).add(inFromUser[1]+"//"+inFromUser[3]);
                            blackNotif.get(inFromUser[2]).add(inFromUser[1]);
                            dataout.writeBytes("Success\n");
                        }
                    }
                    else if(inFromUser[0].equals("ACCEPTBLACK")){//ACCEPTTIC gameID
                        blackStatus.put(inFromUser[1], true);
                        blackInv.get(inFromUser[2]).remove(inFromUser[3]+"/.."+inFromUser[1]);
                    }
                    else if(inFromUser[0].equals("DECLINEBLACK")){//DECLINETIC gameID
                        blackStatus.put(inFromUser[1], false);
                        blackInv.get(inFromUser[2]).remove(inFromUser[3]+"/.."+inFromUser[1]);
                    }
                    else if(inFromUser[0].equals("CHECKBLACK")){//CHECKTIC gameID
                        if(blackStatus.containsKey(inFromUser[1])){
                            if(blackStatus.get(inFromUser[1])) {

                                dataout.writeBytes("Accepted\n");
                            }
                            else {
                                dataout.writeBytes("Declined\n");
                            }
                            blackStatus.remove(inFromUser[1]);
                        }
                        else {
                            dataout.writeBytes("Wait\n");
                        }

                    }
                    else if(inFromUser[0].equals("STARTBLACK")){//STARTTIC gameID
                        if(!BlackArray.containsKey(inFromUser[1])) {
                            String[] a={"0","0","0","0","0","0","0","0","0","0"};
                            BlackArray.put(inFromUser[1], a);
                            dataout.writeBytes("X\n");
                            counter.put(inFromUser[1], 0);
                        }
                        else{
                            dataout.writeBytes("O\n");
                        }
                        blackturn.put(inFromUser[1], true);

                    }
                    else if(inFromUser[0].equals("GETCARDS")){
                        String response="";
                        for(int i=0; i<10; i++){
                            response=response+BlackArray.get(inFromUser[1])[i]+"//";
                        }
                        dataout.writeBytes(response+'\n');
                    }
                    else if(inFromUser[0].equals("UPDATEBLACK")){//UPDATE gameid  user 1 2 3
                        if(inFromUser[2].equals("X")) {
                            for (int i = 3; i <= 7; i++) {
                                BlackArray.get(inFromUser[1])[i - 3] = inFromUser[i];
                            }
                            boolean a = !blackturn.get(inFromUser[1]);
                            blackturn.put(inFromUser[1], a);
                            counter.put(inFromUser[1], counter.get(inFromUser[1]) + 1);
                        }
                        else if (inFromUser[2].equals("O")){
                            for (int i = 3; i <= 7; i++) {
                                BlackArray.get(inFromUser[1])[i+2] = inFromUser[i];
                            }
                            boolean a = !blackturn.get(inFromUser[1]);
                            blackturn.put(inFromUser[1], a);
                            counter.put(inFromUser[1], counter.get(inFromUser[1]) + 1);

                        }else if(inFromUser[2].equals("ALL")){
                            for (int i = 3; i <= 12; i++) {
                                BlackArray.get(inFromUser[1])[i - 3] = inFromUser[i];
                            }
                            boolean a = !blackturn.get(inFromUser[1]);
                            blackturn.put(inFromUser[1], a);
                            counter.put(inFromUser[1], counter.get(inFromUser[1]) + 1);
                        }
                    }
                    else if(inFromUser[0].equals("WINBLACK")){
                        if(counter.get(inFromUser[1])==8 || stay.get(inFromUser[1]).equals(a)){
                            Integer[] b={0,0,0,0,0,0,0,0,0,0,0,0,0};
                            int i=0;
                            while (i<10){
                                b[i]=Integer.valueOf(BlackArray.get(inFromUser[1])[i]);
                                i++;
                            }
                            Integer score[]={0,0,0,0,0,0,0,0,0,0,0,0,0};
                            i=0;
                            while (i<10){
                                if(b[i]==11 || b[i]==12|| b[i]==13){
                                    score[i]=10;

                                }
                                else if(b[i]==1){
                                    score[i]=11;
                                }
                                else{
                                    score[i]=b[i];
                                }
                                i++;
                            }
                            Integer sumA;
                            Integer sumB;
                            sumB=score[5]+score[6]+score[7]+score[8]+score[9];
                            sumA=score[0]+score[1]+score[2]+score[3]+score[4];
                            while(sumA>21){
                                if(score[0]==11){
                                    sumA=sumA-10;
                                    score[0]=1;

                                }else if(score[1]==11){
                                    sumA=sumA-10;
                                    score[1]=1;

                                }
                                else if(score[2]==11){
                                    sumA=sumA-10;
                                    score[2]=1;

                                }
                                else if(score[3]==11){
                                    sumA=sumA-10;
                                    score[3]=1;

                                }
                                else if(score[4]==11){
                                    sumA=sumA-10;
                                    score[3]=1;
                                }
                                else{
                                    break;
                                }
                            }
                            while(sumB>21){
                                if(score[5]==11){
                                    sumB=sumB-10;
                                    score[5]=1;

                                }else if(score[6]==11){
                                    sumB=sumB-10;
                                    score[6]=1;

                                }
                                else if(score[7]==11){
                                    sumB=sumB-10;
                                    score[7]=1;

                                }
                                else if(score[8]==11){
                                    sumB=sumB-10;
                                    score[8]=1;

                                }
                                else if(score[9]==11){
                                    sumB=sumB-10;
                                    score[9]=1;
                                }
                                else{
                                    break;
                                }
                            }
                            if(sumA==sumB){
                                dataout.writeBytes("TIE//0");
                            }
                            if(sumA<=21 && sumB>21){
                                dataout.writeBytes("WINNER//X\n");
                            }
                            else if(sumB<=21 && sumA>21){
                                dataout.writeBytes("WINNER//O\n");
                            }
                            else if(sumB<=21 && sumA<=21){
                                if (sumA>sumB){
                                    dataout.writeBytes("WINNER//X\n");
                                }else {
                                    dataout.writeBytes("WINNER//O");
                                }
                            }
                            else{
                                if (sumA<sumB){
                                    dataout.writeBytes("WINNER//X\n");
                                }else {
                                    dataout.writeBytes("WINNER//O");
                                }

                            }
                        }
                        else{
                            dataout.writeBytes("Notyet");
                        }
                    }
                    else if(inFromUser[0].equals("GETTURNBLACK")){//GETTURN gameid
                        dataout.writeBytes(Boolean.toString(blackturn.get(inFromUser[1]))+'\n');
                    }
                    else if(inFromUser[0].equals("REFRESHBLACK")){//REFRESH gameid
                        dataout.writeBytes(String.valueOf(blackStatus.get(inFromUser[1])));
                    }
                    else if(inFromUser[0].equals("MESSAGEG")){
                        allMessagesg.addFirst(inFromUser[1]+"--"+inFromUser[2]);
                    }
                    else if(inFromUser[0].equals("GETMSGG")){
                        String response="";
                        Integer counter=allMessagesg.size();
                        while (counter>0){
                            response=response+"//"+allMessagesg.get(counter-1);
                            counter--;
                        }
                        response=String.valueOf(allMessagesg.size())+response;
                        dataout.writeBytes(response+'\n');
                    }
                    else if(inFromUser[0].equals("STAY")){
                        if(inFromUser[2].equals("X")) {
                            stay.get(inFromUser[1])[0]="true";
                        }
                        if(inFromUser[2].equals("O")) {
                            stay.get(inFromUser[1])[1]="true";
                        }
                    }
                    else if(inFromUser[0].equals("SCORET")){//SCORE user
                        sql="SELECT * from table1 WHERE USER ='"+inFromUser[1]+"'";
                        ResultSet myRS1=myStmt.executeQuery(sql);
                        String v="";
                        while (myRS1.next()){
                            v=myRS1.getString("scoretic");
                        }
                        String toset=String.valueOf(Integer.parseInt(v)+1);
                        sql="UPDATE table1 SET scoretic='"+toset+"'WHERE USER ='"+inFromUser[1]+"'";
                        mydb.executeUpdate(myStmt, sql);
                    }
                    else if(inFromUser[0].equals("SCOREB")){//SCORE user
                        sql="SELECT * from" +
                                " table1 WHERE USER ='"+inFromUser[1]+"'";
                        ResultSet myRS1=myStmt.executeQuery(sql);
                        String v="";
                        while (myRS1.next()){
                            v=myRS1.getString("scoreblack");
                        }
                        String toset=String.valueOf(Integer.parseInt(v)+1);
                        sql="UPDATE table1 SET scoreblack='"+toset+"'WHERE USER ='"+inFromUser[1]+"'";
                        mydb.executeUpdate(myStmt, sql);
                    }
                    else if(inFromUser[0].equals("GETSCOREB")){//SCORE user
                        sql="SELECT * from" +
                                " table1 WHERE USER ='"+inFromUser[1]+"'";
                        ResultSet myRS1=myStmt.executeQuery(sql);
                        myRS1.next();
                        String v=myRS1.getString("scoreblack");
                        dataout.writeBytes(v+"\n");
                    }
                    else if(inFromUser[0].equals("GETSCORET")){//SCORE user
                        sql="SELECT * from table1 WHERE USER ='"+inFromUser[1]+"'";
                        ResultSet myRS1=myStmt.executeQuery(sql);
                        myRS1.next();
                        String v=myRS1.getString("scoretic");
                        dataout.writeBytes(v+"\n");
                    }
                    else if(inFromUser[0].equals("FORGOT")){
                        sql = "SELECT * FROM table1 WHERE user='"+inFromUser[1]+"'";
                        ResultSet myRes = myStmt.executeQuery(sql);
                        if(myRes.next()) {
                            dataout.writeBytes(myRes.getString("question")+'\n');
                        }
                        else{
                            dataout.writeBytes("FAIL\n");
                        }
                    }
                    else if(inFromUser[0].equals("ANSWER")){
                        sql = "SELECT * FROM table1 WHERE user='"+inFromUser[1]+"'";
                        ResultSet myRes = myStmt.executeQuery(sql);
                        if(myRes.next()) {
                            if(myRes.getString("answer").equals(inFromUser[2])){
                                dataout.writeBytes("SUCCESS\n");
                            }else{
                                dataout.writeBytes("FAIL\n");
                            }
                        }
                    }
                    else if(inFromUser[0].equals("NEWPASS")){
                        sql = "update table1 set pass = '"+inFromUser[2]+"' where user = '"+inFromUser[1]+"'";
                        myStmt.executeUpdate(sql);
                        dataout.writeBytes("SUCCESS\n");
                    }

                } catch(Exception e) {

                }
            }
            finally {
            }
//            catch (IOException k)
//            {
//                System.out.println(k);
//            }



            try
            {
                System.out.println("closing socket");
                datain.close();
                dataout.close();
                socket.close();
            }
            catch (IOException e)
            {

            }
        }
    }
//    private void sendMail() throws MessagingException{
//
//        String host = "smtp.gmail.com";
//        String password = "abcde12345";
//        String from = "testing@gmail.com";
//        String toAddress = email;
//        String filename = Environment.getExternalStorageDirectory() + "/jam.jpg";
//
//        Properties properties = System.getProperties();
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtps.auth", true);
//        properties.put("mail.smtp.starttls.enable", true);
//        Session session = Session.getInstance(properties, null);
//
//        MimeMessage message = new MimeMessage(session);
//        message.setFrom(new InternetAddress(from));
//        message.setRecipients(Message.RecipientType.TO, toAddress);
//        message.setSubject("Anti-Theft Attachment");
//
//        BodyPart messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setText(smsMessageString);
//
//        Multipart multipart = new MimeMultipart();
//        multipart.addBodyPart(messageBodyPart);
//        message.setContent(multipart);
//
//        try{
//            Transport transport = session.getTransport("smtps");
//            transport.connect(host, from, password);
//            transport.sendMessage(message, message.getAllRecipients());
//            System.out.println("Mail Sent Successfully");
//            transport.close();
//        } catch (SendFailedException sfe){
//            System.out.println(sfe);
//        }
//    }

}
