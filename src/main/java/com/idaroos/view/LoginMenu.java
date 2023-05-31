package com.idaroos.view;

import com.idaroos.model.Account;
import com.idaroos.model.Customer;
import com.idaroos.model.Transaction;
import com.idaroos.repository.AccountRepository;
import com.idaroos.service.AccountService;
import com.idaroos.service.CustomerService;
import com.idaroos.service.PasswordService;
import com.idaroos.service.TransactionService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LoginMenu {
//    private final Customer loggedInCustomer;
    private final CustomerService customerService;
    private final PasswordService passwordService;
    private final AccountService accountService;

    private final TransactionService transactionService;
    private final Scanner scanner;
    private final AccountRepository accountRepository;


    public LoginMenu(CustomerService customerService, PasswordService passwordService, AccountService accountService, TransactionService transactionService, AccountRepository accountRepository, Scanner scanner) {
        this.customerService = customerService;
        this.passwordService = passwordService;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
        this.scanner = scanner;
    }

    public void showMenu(Customer loggedInCustomer) {
        int choice;

        do {
            System.out.println("Välkommen, " + loggedInCustomer.getFname() + " " + loggedInCustomer.getLname());
            System.out.println("Vad vill du göra?");
            System.out.println("1. Ta bort användare");
            System.out.println("2. Uppdatera användare");
            System.out.println("3. Lägg till konto");
            System.out.println("4. Ta bort konto");
            System.out.println("5. Gör transaktion");
            System.out.println("6. Visa transaktionshistorik mellan två datum");
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
                    } catch (SQLException e) {
                        System.out.println("Fel vid uppdatering av användaruppgifter: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Lägg till konto");
                    Account newAccount = new Account();

                    System.out.print("Ange kontonummer: ");
                    newAccount.setAccount_number(scanner.next());
                    scanner.nextLine(); // Läs in återstående nytecken

                    System.out.print("Ange kontonamn: ");
                    newAccount.setAccount_name(scanner.nextLine());

                    System.out.print("Ange saldo: ");
                    newAccount.setBalance(scanner.nextDouble());

                    try {
                        accountRepository.createAccount(newAccount, loggedInCustomer.getId());
                        System.out.println("Kontot har lagts till.");
                    } catch (SQLException e) {
                        System.out.println("Fel vid skapande av konto: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Ta bort konto");
                    System.out.print("Ange kontonummer: ");
                    String accountNumber = scanner.next();

                    // Skapa ett nytt Account-objekt för att hantera borttagningen av kontot
                    Account deleteAccount = new Account();
                    deleteAccount.setAccount_number(accountNumber);
                    deleteAccount.setCustomer_id(loggedInCustomer.getId());

                    try {
                        accountService.deleteAccount(deleteAccount, loggedInCustomer.getId());
                    } catch (SQLException e) {
                        System.out.println("Fel vid borttagning av konto: " + e.getMessage());
                    }
                    break;


                case 5:
                    System.out.println("Gör transaktion");
                    System.out.print("Ange kontonummer att skicka från: ");
                    String fromAccountNumber = scanner.next();

                    System.out.print("Ange kontonummer att skicka till: ");
                    String toAccountNumber = scanner.next();

                    System.out.print("Ange belopp att skicka: ");
                    int amount = scanner.nextInt();

                    try {
                        System.out.println(loggedInCustomer.getId());
                        accountService.makeTransaction(loggedInCustomer.getId(), fromAccountNumber, toAccountNumber, amount);
                        System.out.println("Transaktionen har genomförts.");
                    } catch (SQLException e) {
                        System.out.println("Fel vid transaktion: " + e.getMessage());
                    }
                    break;


                case 6:
                    System.out.println("Visa transaktionshistorik mellan två datum");
                    System.out.print("Ange kontonummer: ");
                    String transactionAccountNumber = scanner.next();
                    System.out.print("Ange startdatum (YYYY-MM-DD): ");
                    String startDateStr = scanner.next();
                    System.out.print("Ange slutdatum (YYYY-MM-DD): ");
                    String endDateStr = scanner.next();

                    LocalDate startDate = LocalDate.parse(startDateStr);
                    LocalDate endDate = LocalDate.parse(endDateStr);

                    try {
                        Account account = accountRepository.getAccountByAccountNumberAndCustomerId(transactionAccountNumber, loggedInCustomer.getId());

                        if (account != null) {
                            List<Transaction> transactions = transactionService.getTransactionHistoryByAccountNumberAndDates(transactionAccountNumber, startDate, endDate);
                            System.out.println("Transaktionshistorik för konto: " + transactionAccountNumber);
                            System.out.println("------------------");
                            for (Transaction transaction : transactions) {
                                LocalDate transactionDate = transaction.getCreated().toLocalDateTime().toLocalDate();
                                int transactionAmount = transaction.getAmount();

                                String transactionType = (transaction.getFromaccount_id() == account.getId()) ? "Skickat" : "Mottagit";
                                String formattedAmount = (transaction.getFromaccount_id() == account.getId()) ? "-" + transactionAmount : String.valueOf(transactionAmount);
                                System.out.println(transactionDate + "  " + transactionType + ": Belopp: " + formattedAmount);
                            }
                        } else {
                            System.out.println("Det angivna kontonumret tillhör inte den inloggade användaren.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Fel vid hämtning av transaktionshistorik: " + e.getMessage());
                    }
                    break;





                case 7:
                    System.out.println("Visa kontouppgifter");
                    System.out.println("Kundinformation:");
                    System.out.println("-------------");

                    try {
                        Customer customer = customerService.getCustomerById(loggedInCustomer.getId());
                        System.out.println("ID: " + customer.getId());
                        System.out.println("Förnamn: " + customer.getFname());
                        System.out.println("Efternamn: " + customer.getLname());
                        System.out.println("Email: " + customer.getEmail());
                        System.out.println("Telefonnummer: " + customer.getPhone());

                        System.out.println();
                        System.out.println("Konton:");
                        System.out.println("-------------");

                        List<Account> accounts = accountRepository.getAccountsByCustomerId(loggedInCustomer.getId());
                        for (Account accountInfo : accounts) {
                            System.out.println("Kontonamn: " + accountInfo.getAccount_name());
                            System.out.println("Kontonummer: " + accountInfo.getAccount_number());
                            System.out.println("Saldo: " + accountInfo.getBalance());
                            System.out.println();
                        }
                    } catch (SQLException e) {
                        System.out.println("Fel vid hämtning av kunduppgifter och konton: " + e.getMessage());
                    }
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
