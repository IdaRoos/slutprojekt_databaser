package com.idaroos.repository;

import com.idaroos.model.Account;
import com.idaroos.model.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository {
    private DatabaseConnector databaseConnector;

    public AccountRepository() {
        this.databaseConnector = DatabaseConnector.getInstance();
    }

    public void createAccount(Account account, int customerId) throws SQLException {

        if (isAccountNumberTaken(account.getAccount_number())) {
            throw new SQLException("Kontonumret är redan upptaget. Vänligen välj ett annat kontonummer.");
        }

        String query = "INSERT INTO accounts (account_number, account_name, balance, customer_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, account.getAccount_number());
            statement.setString(2, account.getAccount_name());
            statement.setDouble(3, account.getBalance());
            statement.setInt(4, customerId);

            statement.executeUpdate();
        }

    }

    public boolean isAccountNumberTaken(String accountNumber) throws SQLException {
        String query = "SELECT COUNT(*) FROM accounts WHERE account_number = ?";

        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            int count = resultSet.getInt(1);
            return count > 0;
        }
    }




    public void deleteAccount(String accountNumber) throws SQLException {
        String query = "DELETE FROM accounts WHERE account_number = ?";
        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, accountNumber);
            statement.executeUpdate();
        }
    }

    public String getAccountByNumber(String accountNumber, int customerId) throws SQLException {
        String query = "SELECT account_number FROM accounts WHERE account_number = ? AND customer_id = ?";
        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, accountNumber);
            statement.setInt(2, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("account_number");
                }
            }
        }
        return null;
    }




}
