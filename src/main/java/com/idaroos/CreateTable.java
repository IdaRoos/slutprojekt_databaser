package com.idaroos;

import com.idaroos.model.DatabaseConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    public void createTables() throws SQLException {
        DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        try (Connection connection = databaseConnector.getConnection();
             Statement statement = connection.createStatement()) {

            String customerQuery = "CREATE TABLE IF NOT EXISTS customers (id INT PRIMARY KEY AUTO_INCREMENT, ss_number VARCHAR(13), password VARCHAR(100), fname VARCHAR(100), lname VARCHAR(100), email VARCHAR(100), phone VARCHAR(20));";
            String accountQuery = "CREATE TABLE IF NOT EXISTS accounts (id INT PRIMARY KEY AUTO_INCREMENT, customer_id INT, account_number VARCHAR(15), account_name VARCHAR(50), balance INT, created DATETIME DEFAULT CURRENT_TIMESTAMP);";
            String transactionQuery = "CREATE TABLE IF NOT EXISTS transactions (id INT PRIMARY KEY AUTO_INCREMENT, toaccount_id INT, fromaccount_id INT, amount INT, created DATETIME DEFAULT CURRENT_TIMESTAMP);";

            statement.executeUpdate(customerQuery);
            statement.executeUpdate(accountQuery);
            statement.executeUpdate(transactionQuery);
        }

    }

}
