import java.sql.*;

public class LoadDriver {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            //handle
        }
        Connection db = null;
        try {
            db = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms?" + "user=root");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
