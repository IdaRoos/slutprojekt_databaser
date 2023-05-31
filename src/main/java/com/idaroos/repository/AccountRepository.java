package com.idaroos.repository;

import com.idaroos.model.Account;
import com.idaroos.model.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public void deleteAccountsByCustomerId(int customerId) throws SQLException {
        String query = "DELETE FROM accounts WHERE customer_id = ?";

        try (Connection connection = databaseConnector.getConnection();
        PreparedStatement deleteAccountsStatement = connection.prepareStatement(query)) {
                deleteAccountsStatement.setInt(1, customerId);
                deleteAccountsStatement.executeUpdate();
            }
        }


    public int getAccountIdByAccountNumber(String accountNumber) throws SQLException {
        String query = "SELECT id FROM accounts WHERE account_number = ?";
        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, accountNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new SQLException("Kontot med kontonummer " + accountNumber + " hittades inte.");
                }
            }


        }
    }

    public Account getAccountByAccountNumberAndCustomerId(String accountNumber, int customerId) throws SQLException {
        String query = "SELECT id, account_number, customer_id, balance FROM accounts WHERE account_number = ? AND customer_id = ?";
        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, accountNumber);
            statement.setInt(2, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String foundAccountNumber = resultSet.getString("account_number");
                    int foundCustomerId = resultSet.getInt("customer_id");
                    double balance = resultSet.getDouble("balance");
                    int accountId = resultSet.getInt("id");
                    return new Account(accountId, foundCustomerId, foundAccountNumber, balance);
                } else {
                    return null;
                }
            }
        }
    }

    public Account getAccountByAccountNumber(String accountNumber) throws SQLException {
        String query = "SELECT id, account_number, balance, customer_id FROM accounts WHERE account_number = ?";
        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, accountNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int accountId = resultSet.getInt("id");
                    String foundAccountNumber = resultSet.getString("account_number");
                    double balance = resultSet.getDouble("balance");
                    int customerId = resultSet.getInt("customer_id");
                    return new Account(accountId, customerId, foundAccountNumber, balance);
                }
            }
        }
        return null;
    }


    public List<Account> getAccountsByCustomerId(int customerId) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM accounts WHERE customer_id = ?";
        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setAccount_name(resultSet.getString("account_name"));
                account.setAccount_number(resultSet.getString("account_number"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setCustomer_id(resultSet.getInt("customer_id"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new SQLException("Fel vid hämtning av konton: " + e.getMessage());
        }
        return accounts;
    }

    public void updateAccountBalance(Account account) throws SQLException {
        String query = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, account.getBalance());
            statement.setString(2, account.getAccount_number());

            statement.executeUpdate();
        }
    }
}
