package infrastucture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgresService {
    private Connection con;

    public PostgresService() {
        String driver = "org.postgresql.Driver";
        String user = "postgres";
        String senha = "udesc";
        String url = "jdbc:postgresql://localhost:5432/lojaMusica";

        try {
            Class.forName(driver);
            this.con = DriverManager.getConnection(url, user, senha);
        } catch (ClassNotFoundException | SQLException ex) {
            handleError(ex);
        }
    }

    public Connection getConnection() {
        return con;
    }

    public void closeConnection(){
        try {
            this.con.close();
        } catch (SQLException ex) {
            handleError(ex);
        }
    }

    private void handleError(Exception ex) {
        Logger
            .getLogger(PostgresService.class.getName())
            .log(Level.SEVERE, null, ex);

        ex.printStackTrace();

        System.exit(1);
    }
}
