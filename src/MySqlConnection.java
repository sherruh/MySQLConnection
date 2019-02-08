import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public class MySqlConnection {
    private final String url = "jdbc:mysql://localhost:3306/lesson1?useUnicode=yes&characterEncoding=UTF-8";
    private final String user="shtashev";
    private final String password="1q2w3e4r";

    private Connection con;

    public void initConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        //Создаём соединение
        con = DriverManager.getConnection(url, user, password);
        System.out.println("Соединение установлено");
    }

    public Connection getCon() throws SQLException, ClassNotFoundException {
        if (this.con==null){
            this.initConnection();
        }
        return this.con;
    }
}
