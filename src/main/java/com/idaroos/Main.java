package com.idaroos;

import com.idaroos.model.Account;
import com.idaroos.model.Customer;
import com.idaroos.model.Transaction;
import com.idaroos.repository.AccountRepository;
import com.idaroos.repository.CustomerRepository;
import com.idaroos.repository.TransactionRepository;
import com.idaroos.service.AccountService;
import com.idaroos.service.CustomerService;
import com.idaroos.service.PasswordService;
import com.idaroos.service.TransactionService;
import com.idaroos.view.LoginMenu;
import com.idaroos.view.MainMenu;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        CreateTable createtables = new CreateTable();
        createtables.createTables();
        Scanner scanner = new Scanner(System.in);

        CustomerRepository customerRepository = new CustomerRepository();
        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        PasswordService passwordService = new PasswordService(customerRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, accountRepository);
        CustomerService customerService = new CustomerService(customerRepository, passwordService, accountRepository);
        AccountService accountService = new AccountService(accountRepository, transactionService);

        LoginMenu loginMenu = new LoginMenu(customerService, passwordService, accountService, transactionService, accountRepository, scanner);
        MainMenu mainMenu = new MainMenu(loginMenu, customerService, scanner);
        mainMenu.showMenu();
        scanner.close();
    }
}