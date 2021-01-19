package me.redstery11.blockmarket.sql;

import me.redstery11.blockmarket.BlockMarket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
    private String JDBC_DRIVER;
    private String url;
    private String user;
    private String password;
    private Connection connection = null;
    private static SqlConnection instance = null;

    public SqlConnection(){
        this.JDBC_DRIVER = BlockMarket.dotenv.get("JDBC_DRIVER");
        this.url = BlockMarket.dotenv.get("SQL_HOST");
        this.user = BlockMarket.dotenv.get("SQL_USER");
        this.password = BlockMarket.dotenv.get("SQL_PASS");

        System.out.println(JDBC_DRIVER + " " + url + " " + user + " " + password);

        Connection connection = null;
        try {
            Class.forName(this.JDBC_DRIVER);
            connection = DriverManager.getConnection(this.url, this.user, this.password);
            System.out.println("Connected to PSQL");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public static SqlConnection getInstance(){
        if (instance == null){
            instance = new SqlConnection();
        }

        return instance;
    }
}
