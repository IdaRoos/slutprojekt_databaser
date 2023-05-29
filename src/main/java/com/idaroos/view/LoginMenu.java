package com.idaroos.view;

import com.idaroos.model.Account;
import com.idaroos.model.Customer;
import com.idaroos.repository.AccountRepository;
import com.idaroos.service.AccountService;
import com.idaroos.service.CustomerService;
import com.idaroos.service.PasswordService;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginMenu {
    private final Customer loggedInCustomer;
    private final CustomerService customerService;
private final PasswordService passwordService;
    private final AccountService accountService;
    private final Scanner scanner;
    private final AccountRepository accountRepository;

    public LoginMenu(Customer loggedInCustomer, CustomerService customerService, PasswordService passwordService, AccountService accountService, AccountRepository accountRepository, Scanner scanner) {
        this.loggedInCustomer = loggedInCustomer;
        this.customerService = customerService;
        this.passwordService = passwordService;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.scanner = scanner;
    }

    public void showMenu() {
        int choice;

        do {
            System.out.println("Välkommen, " + loggedInCustomer.getFname() + " " + loggedInCustomer.getLname());
            System.out.println("Vad vill du göra?");
            System.out.println("1. Ta bort användare");
            System.out.println("2. Uppdatera användare");
            System.out.println("3. Lägg till konto");
            System.out.println("4. Ta bort konto");
            System.out.println("5. Gör transaktion");
            System.out.println("6. Visa transaktionshistorik");
            System.out.println("7. Visa kontouppgifter");
            System.out.println("8. Logga ut");
            System.out.print("Ange ditt val: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Ta bort användare");
                    System.out.println("Är du säker på att du vill ta bort användare?");
                    System.out.println("1. Ja");
                    System.out.println("2. Nej");
                    System.out.print("Ange ditt val: ");
                    int deleteChoice = scanner.nextInt();

                    if (deleteChoice == 1) {
                        try {
                            customerService.deleteCustomer(loggedInCustomer);
                            System.out.println("Användaren har tagits bort.");
                            // Avsluta inloggningen och återgå till huvudmenyn
                            return;
                        } catch (SQLException e) {
                            System.out.println("Fel vid borttagning av användare: " + e.getMessage());
                        }
                    } else if (deleteChoice == 2) {
                        System.out.println("Borttagning av användare avbruten.");
                    } else {
                        System.out.println("Ogiltigt val. Var god försök igen.");
                    }
                    break;
                case 2:
                    System.out.println("Uppdatera användare");
                    System.out.println("Vad vill du uppdatera?");
                    System.out.println("1. Förnamn");
                    System.out.println("2. Efternamn");
                    System.out.println("3. Email");
                    System.out.println("4. Telefonnummer");
                    System.out.println("5. Lösenord");
                    System.out.print("Ange ditt val: ");
                    int updateChoice = scanner.nextInt();

                    switch (updateChoice) {
                        case 1:
                            System.out.print("Ange nytt förnamn: ");
                            String newFirstName = scanner.next();
                            loggedInCustomer.setFname(newFirstName);
                            break;
                        case 2:
                            System.out.print("Ange nytt efternamn: ");
                            String newLastName = scanner.next();
                            loggedInCustomer.setLname(newLastName);
                            break;
                        case 3:
                            System.out.print("Ange ny email: ");
                            String newEmail = scanner.next();
                            loggedInCustomer.setEmail(newEmail);
                            break;
                        case 4:
                            System.out.print("Ange nytt telefonnummer: ");
                            String newPhoneNumber = scanner.next();
                            loggedInCustomer.setPhone(newPhoneNumber);
                            break;
                        case 5:
                            System.out.print("Ange nytt lösenord: ");
                            String newPassword = scanner.next();
                            loggedInCustomer.setPassword(newPassword);

                            // Uppdatera lösenordet
                            try {
                                passwordService.updatePassword(loggedInCustomer, newPassword);
                                System.out.println("Lösenordet har uppdaterats.");
                            } catch (SQLException e) {
                                System.out.println("Ett fel inträffade vid uppdatering av lösenordet: " + e.getMessage());
                            }
                            break;
                        default:
                            System.out.println("Ogiltigt val. Var god försök igen.");
                            break;
                    }

                    try {
                        customerService.updateCustomer(loggedInCustomer);
                        System.out.println("Användaruppgifterna har uppdaterats.");
                    } catch (SQLException e) {
                        System.out.println("Fel vid uppdatering av användaruppgifter: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Lägg till konto");
                    Account account = new Account();

                    System.out.print("Ange kontonummer: ");
                    account.setAccount_number(scanner.next());
                    scanner.nextLine(); // Läs in återstående nytecken

                    System.out.print("Ange kontonamn: ");
                    account.setAccount_name(scanner.nextLine());

                    System.out.print("Ange saldo: ");
                    account.setBalance(scanner.nextDouble());

                    try {
                        accountRepository.createAccount(account, loggedInCustomer.getId());
                        System.out.println("Kontot har lagts till.");
                    } catch (SQLException e) {
                        System.out.println("Fel vid skapande av konto: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Ta bort konto");
                    System.out.print("Ange kontonummer: ");
                    String accountNumber = scanner.next();

                    try {
                        accountService.deleteAccount(accountNumber, loggedInCustomer.getId());
                    } catch (SQLException e) {
                        System.out.println("Fel vid borttagning av konto: " + e.getMessage());
                    }
                    break;

                case 5:

                    break;

                case 6:
                     break;

                case 7:

                    break;

                case 8:
                    System.out.println("Loggar ut...");
                    break;
                default:
                    System.out.println("Ogiltigt val. Var god försök igen.");
            }
        } while (choice != 8);
    }
}
