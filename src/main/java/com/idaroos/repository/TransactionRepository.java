package com.idaroos.repository;


import com.idaroos.model.DatabaseConnector;
import com.idaroos.model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private final DatabaseConnector databaseConnector;

    public TransactionRepository() {
        this.databaseConnector = DatabaseConnector.getInstance();
    }

    public void saveTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions (fromaccount_id, toaccount_id, amount) VALUES (?, ?, ?)";
        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, transaction.getFromaccount_id());
            statement.setInt(2, transaction.getToaccount_id());
            statement.setInt(3, transaction.getAmount());
            statement.executeUpdate();
        }
    }


    public List<Transaction> getTransactionHistoryByAccountIdAndDates(int accountId, LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();

        String query = "SELECT * FROM transactions WHERE (fromaccount_id = ? OR toaccount_id = ?) AND created >= ? AND created <= ? ORDER BY created DESC";
        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, accountId);
            statement.setInt(2, accountId);
            statement.setTimestamp(3, Timestamp.valueOf(startDate.atStartOfDay()));
            statement.setTimestamp(4, Timestamp.valueOf(endDate.atStartOfDay().plusDays(1).minusNanos(1)));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(resultSet.getInt("id"));
                    transaction.setFromaccount_id(resultSet.getInt("fromaccount_id"));
                    transaction.setToaccount_id(resultSet.getInt("toaccount_id"));
                    transaction.setAmount(resultSet.getInt("amount"));
                    transaction.setCreated(resultSet.getTimestamp("created"));

                    transactions.add(transaction);
                }
            }
        }

        return transactions;
    }

}
