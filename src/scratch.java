

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class Connect {
    /**
     * Connect to a sample database
     */
    static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String SQL, k;
            String url = "jdbc:sqlite:a5.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            Statement smt = conn.createStatement();
            k = "'kidney'";
            smt.execute("insert into organs values(null, 'kidney', 'A+')");
            SQL = "select * from organs where name="+k;
            System.out.println(SQL);
            ResultSet rs = smt.executeQuery(SQL);
            while(rs.next()){
                System.out.println(rs.getInt(1) + "  " + rs.getString(2)+"  "+rs.getString(3));
            }
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String s = "A, B, C, D, E, F,G";
        String[] f = s.split("[,]");
        for (String d:f) {
            System.out.print(d);
        }
    }
}