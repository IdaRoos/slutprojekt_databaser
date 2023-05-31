package com.idaroos.service;
import com.idaroos.model.Account;
import com.idaroos.repository.AccountRepository;

import java.sql.SQLException;

public class AccountService {

    public final AccountRepository accountRepository;

    private final TransactionService transactionService;

    public AccountService(AccountRepository accountRepository, TransactionService transactionService){
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
    }

    public void deleteAccount(Account account, int customerId) throws SQLException {
        // Kontrollera om kontonumret tillhör rätt kund
        Account existingAccount = accountRepository.getAccountByAccountNumberAndCustomerId(account.getAccount_number(), customerId);
        if (existingAccount == null) {
            System.out.println("Kontonummer hittades inte.");
            return;
        }

        // Ta bort kontot från databasen
        accountRepository.deleteAccount(account.getAccount_number());
        System.out.println("Kontot har tagits bort.");
    }


    public void makeTransaction(int customerId, String fromAccountNumber, String toAccountNumber, int amount) throws SQLException {
        // Kontrollera om kontonumret tillhör rätt kund
        Account fromAccount = accountRepository.getAccountByAccountNumberAndCustomerId(fromAccountNumber, customerId);
        if (fromAccount == null) {
            throw new SQLException("Kontonumret för avsändaren är ogiltigt.");
        }

        Account toAccount = accountRepository.getAccountByAccountNumber(toAccountNumber);
        if (toAccount == null) {
            throw new SQLException("Kontonumret för mottagaren är ogiltigt.");
        }

        if (fromAccount.getBalance() < amount) {
            throw new SQLException("Saldo på avsändarkontot är otillräckligt.");
        }

        // Uppdatera saldon för avsändar- och mottagarkonton
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        // Spara uppdaterade konton i databasen
        accountRepository.updateAccountBalance(fromAccount);
        accountRepository.updateAccountBalance(toAccount);

        transactionService.createTransactionHistory(fromAccount.getId(), toAccount.getId(), amount);
    }
}
