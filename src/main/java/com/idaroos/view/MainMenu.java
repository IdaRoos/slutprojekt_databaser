package com.idaroos.view;

import com.idaroos.model.Account;
import com.idaroos.model.Customer;
import com.idaroos.repository.AccountRepository;
import com.idaroos.service.AccountService;
import com.idaroos.service.CustomerService;
import com.idaroos.service.PasswordService;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    private final CustomerService customerService;
    private final AccountService accountService;

    private final PasswordService passwordService;
    private final AccountRepository accountRepository;

    private final Scanner scanner;

    public MainMenu(CustomerService customerService,AccountService accountService, PasswordService passwordService, AccountRepository accountRepository, Scanner scanner) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.passwordService = passwordService;
        this.accountRepository = accountRepository;
        this.scanner = scanner;
    }

    public void showMenu() {
        int choice;

        do {
            System.out.println("Hej! Vad vill du göra?");
            System.out.println("1. Logga in");
            System.out.println("2. Skapa användare");
            System.out.println("3. Avsluta");
            System.out.print("Ange ditt val: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Logga in");
                    System.out.print("Ange personnummer (yyyyMMdd-xxxx): ");
                    String ssNumber = scanner.next();
                    System.out.print("Ange lösenord: ");
                    String password = scanner.next();

                    try {
                        Customer loggedInCustomer = customerService.loginHandler(ssNumber, password);
                        LoginMenu loginMenu = new LoginMenu(loggedInCustomer, customerService, passwordService, accountService, accountRepository, scanner);
                        loginMenu.showMenu();
                    } catch (SQLException e) {
                        System.out.println("Fel vid inloggning: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Skapa användare");
                    Customer newCustomer = new Customer();

                    // Läs in användardata från användaren
                    System.out.print("Ange Förnamn: ");
                    newCustomer.setFname(scanner.next());

                    System.out.print("Ange Efternamn: ");
                    newCustomer.setLname(scanner.next());

                    System.out.print("Ange Personnummer (yyyyMMdd-xxxx): ");
                    newCustomer.setSs_number(scanner.next());

                    System.out.print("Ange Lösenord: ");
                   password = scanner.next();
                    scanner.nextLine();

                    System.out.print("Ange Email: ");
                    newCustomer.setEmail(scanner.nextLine());

                    System.out.print("Ange Telefonnummer: ");
                    newCustomer.setPhone(scanner.next());

                    Account account = new Account();

                    // Läs in kontodata från användaren
                    System.out.print("Ange Kontonummer: ");
                    account.setAccount_number(scanner.next());
                    scanner.nextLine(); // Läs in återstående nytecken

                    System.out.print("Ange Kontonamn: ");
                    account.setAccount_name(scanner.nextLine());

                    System.out.print("Ange Saldo: ");
                    account.setBalance(scanner.nextDouble());

                    try {
                        customerService.createCustomerWithAccount(newCustomer, password, account);
                        System.out.println("Användare skapad!");
                    } catch (SQLException e) {
                        System.out.println("Fel vid skapande av användare: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Avslutar...");
                    break;
                default:
                    System.out.println("Ogiltigt val. Var god försök igen.");
            }
        } while (choice != 3);
    }
}
