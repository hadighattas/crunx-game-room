
import java.sql.*;

public class PopulateDatabase{

    public static void main(String[] args) {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false", "root", "root");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("select * from table1");
            System.out.println("Database before deletion contained:");
            while(myRs.next()) {
                System.out.println(myRs.getString("user") + ", " + myRs.getString("pass"));
            }
            String dlt = "delete from table1";
            myStmt.executeUpdate(dlt);
            String sql = "insert into table1 (user, pass, email, gender, age, country, question, answer, mailvisible, agevisible, countryvisible, scoretic, scoreblack) " +
                    "values ('Hadi', '123', 'hsg05@mail.aub.edu.lb', 'Male', 19, 'Lebanon', 'Who is your favorite teacher?', 'Zaher Dawy', 'yes', 'yes', 'yes', '0', '0' )";
            myStmt.executeUpdate(sql);
            String sql1 = "insert into table1 (user, pass, email, gender, age, country, question, answer, mailvisible, agevisible, countryvisible, scoretic, scoreblack) " +
                    "values ('Alexandre', '456', 'ajm19@mail.aub.edu.lb', 'Male', 19, 'Lebanon', 'Who is your favorite teacher?', 'Zaher Dawy' , 'yes', 'yes', 'yes', '0', '0')";
            myStmt.executeUpdate(sql1);
            String sql2 = "insert into table1 (user, pass, email, gender, age, country, question, answer, mailvisible, agevisible, countryvisible, scoretic, scoreblack) " +
                    "values ('Pierre', '789', 'ppc01@mail.aub.edu.lb', 'Male', 19, 'Lebanon', 'Who is your favorite teacher?', 'Zaher Dawy', 'yes', 'yes', 'yes', '0', '0' )";
            myStmt.executeUpdate(sql2);
            System.out.println("Database has been populated!");
            String sql3 = "delete from friends";
            myStmt.executeUpdate(sql3);
            }
        catch (Exception exc) {
            exc.printStackTrace();
        }

    }

}