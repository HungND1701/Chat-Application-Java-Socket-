package connection;

import java.sql.SQLException;
import java.sql.Connection;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    public static DatabaseConnection getInstance(){
        if(instance == null){
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public DatabaseConnection(){
        
    }
    
    public void connectDatabase() throws SQLException {
        String server = "localhost";
        String port = "3306";
        String database = "chat_app";
        String userName = "root";
        String password = "";
        connection = java.sql.DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database, userName, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
