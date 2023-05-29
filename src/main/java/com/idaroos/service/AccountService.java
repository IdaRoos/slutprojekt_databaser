package com.idaroos.service;
import com.idaroos.repository.AccountRepository;

import java.sql.SQLException;

public class AccountService {

    public final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public void deleteAccount(String accountNumber, int customerId) throws SQLException {
        // Kontrollera om kontonumret tillhör rätt kund
        String existingAccountNumber = accountRepository.getAccountByNumber(accountNumber, customerId);
        if (existingAccountNumber == null) {
            System.out.println("Kontonummer hittades inte.");
            return;
        }

        // Ta bort kontot från databasen
        accountRepository.deleteAccount(accountNumber);
        System.out.println("Kontot har tagits bort.");
    }

}
