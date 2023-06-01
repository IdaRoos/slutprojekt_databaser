package com.idaroos.service;

import com.idaroos.model.Transaction;
import com.idaroos.repository.AccountRepository;
import com.idaroos.repository.TransactionRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository){
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public void createTransactionHistory(int fromAccountId, int toAccountId, int amount) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setFromaccount_id(fromAccountId);
        transaction.setToaccount_id(toAccountId);
        transaction.setAmount(amount);

        transactionRepository.saveTransaction(transaction);
    }

    public List<Transaction> getTransactionHistoryByAccountNumberAndDates(String accountNumber, LocalDate startDate, LocalDate endDate) throws SQLException {
        return transactionRepository.getTransactionHistoryByAccountNumberAndDates(accountNumber, startDate, endDate);
    }






}
