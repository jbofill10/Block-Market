package me.redstery11.blockmarket.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerData {
    private SqlConnection sqlConnection = null;
    private Connection cursor = null;

    public PlayerData(){
        this.sqlConnection = SqlConnection.getInstance();
        this.cursor = this.sqlConnection.getConnection();
    }

    public void addPlayer(String uuid){
        try {

            String command = String.format("INSERT INTO playerdata (uuid, balance) VALUES ('%1$s', 0);", uuid);
            PreparedStatement statement = this.cursor.prepareStatement(command);

            if(statement.execute()){
                System.out.println("PLAYER ADDED");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void withdraw(String uuid, double amount) throws SQLException {
        String command = String.format("UPDATE playerdata SET balance = balance - %1$s WHERE uuid = '%2$s';", amount, uuid);
        PreparedStatement statement = this.cursor.prepareStatement(command);

        statement.execute();
        statement.close();
    }

    public void deposit(String uuid, double amount) throws SQLException {
        String command = String.format("UPDATE playerdata SET balance = balance + %1$s WHERE uuid = '%2$s';", amount, uuid);
        PreparedStatement statement = this.cursor.prepareStatement(command);

        statement.execute();

        statement.close();
    }

    public void updateStocks(String uuid, String stock, int shares, double spent) throws SQLException {
        String command = String.format("SELECT share FROM stocks WHERE uuid = '%1$s' AND stock = '%2$s';", uuid, stock);
        PreparedStatement statement = this.cursor.prepareStatement(command);

        ResultSet resultSet = statement.executeQuery();

        statement.close();

        if (resultSet.next()){
            String update = String.format("UPDATE stocks SET share = share + %1$s, total_spent = total_spent + %4$s WHERE uuid = '%2$s' and stock = '%3$s';", shares, uuid, stock, spent);

            PreparedStatement updateStatement = this.cursor.prepareStatement(update);

            updateStatement.execute();

            updateStatement.close();
        }else{

            String insert = String.format("INSERT INTO stocks (uuid, stock, share, total_spent) VALUES ('%1$s', '%2$s', %3$s, %4$s);", uuid, stock, shares, spent);

            PreparedStatement insertStatement = this.cursor.prepareStatement(insert);

            insertStatement.execute();

            insertStatement.close();
        }
    }

    public void updateCrypto(String uuid, String crypto, double spent, double equity) throws SQLException {
        PreparedStatement statement = this.cursor.prepareStatement(
                String.format("SELECT * FROM crypto WHERE uuid = '%1$s' AND stock = '%2$s';", uuid, crypto)
        );

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()){

            statement = this.cursor.prepareStatement(
                    String.format("UPDATE crypto SET equity = equity + %1$s, total_spent = total_spent + %2$s WHERE uuid = '%3$s' AND crypto_name = '%4$s';", equity, spent, uuid, crypto)
            );

            statement.execute();
        } else {
            statement = this.cursor.prepareStatement(
                    String.format("INSERT INTO crypto (uuid, crypto_name, equity, total_spent) VALUES ('%1$s', '%2$s', %3$s, %4$s);")
            );

            statement.execute();
        }

        statement.close();
    }

    public int getShares(String uuid, String stock) throws SQLException {
        PreparedStatement preparedStatement = this.cursor.prepareStatement(String.format("SELECT share FROM stocks WHERE uuid = '%1$s' and stock = '%2$s';", uuid, stock));

        ResultSet resultSet = preparedStatement.executeQuery();
        int shares = 0;
        if (resultSet.next())
            shares = resultSet.getInt("share");

        return shares;
    }

    public double getBalance(String uuid) throws SQLException {
        String command = String.format("SELECT balance FROM playerdata WHERE uuid='%1$s';", uuid);
        PreparedStatement statement = this.cursor.prepareStatement(command);

        ResultSet resultSet = statement.executeQuery();

        statement.close();
        resultSet.next();
        return resultSet.getDouble("balance");
    }

    public boolean playerExists(String uuid) throws SQLException {

        String command = String.format("SELECT uuid FROM playerdata WHERE uuid='%1$s';", uuid);
        PreparedStatement preparedStatement = this.cursor.prepareStatement(command);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    public boolean canAfford(String uuid, double amount) throws SQLException{
        String command = String.format("SELECT balance FROM playerdata WHERE uuid='%1$s';", uuid);
        PreparedStatement statement = this.cursor.prepareStatement(command);

        ResultSet resultSet = statement.executeQuery();

        statement.close();
        resultSet.next();
        return resultSet.getDouble("balance") > amount;
    }

    public void updateBalance(String uuid, double amount) throws SQLException {
        String command = String.format("UPDATE playerdata SET balance = %1$s WHERE uuid = '%2$s';", amount, uuid);
        PreparedStatement statement = this.cursor.prepareStatement(command);

        statement.execute();

        statement.close();
    }
}
