package com.idaroos.view;

import com.idaroos.model.Account;
import com.idaroos.model.Customer;
import com.idaroos.service.CustomerService;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    private final LoginMenu loginMenu;
    private final CustomerService customerService;
    private final Scanner scanner;

    public MainMenu(LoginMenu loginMenu, CustomerService customerService, Scanner scanner) {
        this.loginMenu = loginMenu;
        this.customerService = customerService;
        this.scanner = scanner;
    }

    public void showMenu() {
        int choice = 0;

        do {
            System.out.println("Hej! Vad vill du göra?");
            System.out.println("1. Logga in");
            System.out.println("2. Skapa användare");
            System.out.println("3. Avsluta");
            System.out.print("Ange ditt val: ");

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ogiltig inmatning. Var god försök igen.");
                scanner.nextLine(); // Töm inmatningsbufferten
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Logga in");
                    System.out.print("Ange personnummer (yyyyMMdd-xxxx): ");
                    String ssNumber = scanner.next();
                    System.out.print("Ange lösenord: ");
                    String password = scanner.next();

                    try {
                        Customer loggedInCustomer = customerService.loginHandler(ssNumber, password);
                        loginMenu.showMenu(loggedInCustomer);
                    } catch (SQLException e) {
                        System.out.println("Fel vid inloggning: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Skapa användare");
                    Customer newCustomer = new Customer();


                    System.out.print("Ange Förnamn: ");
                    String newFirstName = scanner.next();

                    if (customerService.isValidName(newFirstName)) {
                        newCustomer.setFname(newFirstName);

                        System.out.print("Ange Efternamn: ");
                        String newLastName = scanner.next();

                        if (customerService.isValidName(newLastName)) {
                            newCustomer.setLname(newLastName);

                            System.out.print("Ange Personnummer (yyyyMMdd-xxxx): ");
                            String newSsNumber = scanner.next();

                            if (customerService.isValidSsNumber(newSsNumber)) {
                                newCustomer.setSs_number(newSsNumber);

                                System.out.print("Ange Lösenord: ");
                                password = scanner.next();
                                scanner.nextLine();

                                System.out.print("Ange Email: ");
                                newCustomer.setEmail(scanner.nextLine());

                                System.out.print("Ange Telefonnummer: ");
                                String newPhoneNumber = scanner.next();

                                if (customerService.isValidNumber(newPhoneNumber)) {
                                    newCustomer.setPhone(newPhoneNumber);

                                    Account account = new Account();

                                    System.out.print("Ange Kontonummer: ");
                                    String newAccountNumber = scanner.next();

                                    if (customerService.isValidNumber(newAccountNumber)) {
                                        account.setAccount_number(newAccountNumber);
                                        scanner.nextLine();

                                        System.out.print("Ange Kontonamn: ");
                                        account.setAccount_name(scanner.nextLine());

                                        System.out.print("Ange Saldo: ");
                                        String newBalance = scanner.next();


                                        if (customerService.isValidNumber(newBalance)) {
                                            account.setBalance(Double.parseDouble(newBalance));

                                            try {
                                                customerService.createCustomerWithAccount(newCustomer, password, account);
                                                System.out.println("Användare skapad!");
                                            } catch (SQLException e) {
                                                System.out.println("Fel vid skapande av användare: " + e.getMessage());
                                            }
                                        } else {
                                            System.out.println("Ogiltig inmatning för saldo. Var god ange ett giltigt nummer.");
                                        }
                                    } else {
                                        System.out.println("Ogiltig inmatning för kontonummer. Var god ange endast siffror.");
                                    }
                                } else {
                                    System.out.println("Ogiltig inmatning för telefonnummer. Var god ange endast siffror.");
                                }
                            } else {
                                System.out.println("Ogiltig inmatning för personnummer. Var god ange formatet 'YYYYMMDD-xxxx'.");
                            }
                        } else {
                            System.out.println("Ogiltig inmatning för efternamn. Var god ange endast bokstäver.");
                        }
                    } else {
                        System.out.println("Ogiltig inmatning för förnamn. Var god ange endast bokstäver.");
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
