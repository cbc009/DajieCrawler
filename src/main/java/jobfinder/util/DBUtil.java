package jobfinder.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    static Connection conn = null;

    public static Connection getConnection() {

        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?autoReconnect=true&UseUnicode=true&"
                        + "characterEncoding=utf8", "root", "123456");
                // conn =
                // DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                // "root", "123");
                // Statement statement = conn.createStatement();
                // statement.execute("INSERT INTO `test`.`bdzhidao_question` (`id`, `asker`) VALUES ('1', '������')");

            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return conn;
    }
}
