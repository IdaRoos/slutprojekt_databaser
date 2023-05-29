package com.idaroos;

import com.idaroos.repository.AccountRepository;
import com.idaroos.repository.CustomerRepository;
import com.idaroos.service.AccountService;
import com.idaroos.service.CustomerService;
import com.idaroos.service.PasswordService;
import com.idaroos.view.MainMenu;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
CreateTable createtables = new CreateTable();
        createtables.createTables();
            Scanner scanner = new Scanner(System.in);
        CustomerRepository customerRepository = new CustomerRepository();
        PasswordService passwordService = new PasswordService(customerRepository);
        AccountRepository accountRepository = new AccountRepository();

        CustomerService customerService = new CustomerService(customerRepository, passwordService, accountRepository);
AccountService accountService = new AccountService(accountRepository);

            MainMenu mainMenu = new MainMenu(customerService, accountService, passwordService, accountRepository, scanner);
        mainMenu.showMenu();

// När du är klar med användningen av scanner, stäng den
        scanner.close();
    }
}