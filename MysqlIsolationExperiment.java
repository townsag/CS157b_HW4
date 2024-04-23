import java.sql.*;

public class MysqlIsolationExperiment {
    
    
    public static void main(String[] args){
        Connection connection1 = null;
        Connection connection2 = null;        String dbName = "";
        String mysql_url = "jdbc:mysql://localhost:3306/";      //variable to change if you want to switch mysql url
        String mysql_user = "root";                             //mysql username, we used the default root but if you want to use your own mysql replace this variable
        String mysql_pass = "LTAndr3w";
        String url = mysql_url + dbName;



        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection1 = DriverManager.getConnection(url, mysql_user, mysql_pass);
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection2 = DriverManager.getConnection(url, mysql_user, mysql_pass);
            connection1.setAutoCommit(false);
            connection2.setAutoCommit(false);

            Statement statement1 = connection1.createStatement();
            statement1.executeUpdate("START TRANSACTION; SET TRANSACTION ISOLATION LEVEL READ COMMITTED;");
            Statement statement2 = connection2.createStatement();
            statement2.executeUpdate("INSERT INTO MY_TABLE (DATA) VALUES (1)");
            ResultSet res1 = statement1.executeQuery("SELECT * FROM MY_TABLE LIMIT 1");
            System.out.println(res1.getString("DATA"));

            statement2.executeUpdate("UPDATE MY_TABLE DATA=2 WHERE DATA=1");
            ResultSet res2 = statement1.executeQuery("SELECT * FROM MY_TABLE LIMIT 1");
            System.out.println(res2.getString("DATA"));

        } catch (Exception e){
            System.out.println("encountered an error connecting to MySQL server at"); 
        }
    }
    
}
