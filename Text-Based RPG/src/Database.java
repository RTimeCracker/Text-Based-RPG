import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    String url = "jdbc:mysql://localhost:3306/textbasedrpg";
    String username = "root";
    String password = "";
    Connection conn;
    Statement stm;

    public void Database() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    public ResultSet fetchData(String query) throws SQLException{
        conn = DriverManager.getConnection(url,username,password);
        stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
        rs.next();

        return rs;
    }

}