package com.idaroos.service;

import com.idaroos.model.Account;
import com.idaroos.model.Customer;
import com.idaroos.repository.AccountRepository;
import com.idaroos.repository.CustomerRepository;


import java.sql.SQLException;

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordService passwordService;
    private final AccountRepository accountRepository;

    public CustomerService(CustomerRepository customerRepository,PasswordService passwordService, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.passwordService = passwordService;
        this.accountRepository = accountRepository;
    }

    public void createCustomerWithAccount(Customer customer, String password, Account account) throws SQLException {
        // Validera kunddata
        if (customer.getFname() == null || customer.getFname().isEmpty()) {
            throw new SQLException("Förnamn är obligatoriskt.");
        }
        if (customer.getLname() == null || customer.getLname().isEmpty()) {
            throw new SQLException("Efternamn är obligatoriskt.");
        }
      if (customer.getSs_number() == null || customer.getSs_number().isEmpty()) {
          throw new SQLException("Personnummer är obligatoriskt");
      }
        if (password == null || password.isEmpty()) {
            throw new SQLException("Lösenord är obligatoriskt.");
        }
      if(customer.getEmail() == null || customer.getEmail().isEmpty()){
          throw new SQLException("Email är obligatoriskt.");
        }
      if(customer.getPhone() == null || customer.getPhone().isEmpty()){
          throw new SQLException("Telefonnummer är obligatoriskt.");
        }
        if (account.getAccount_number() == null || account.getAccount_number().isEmpty()) {
            throw new SQLException("Kontonummer är obligatoriskt.");
        }
        if (account.getAccount_name() == null || account.getAccount_name().isEmpty()) {
            throw new SQLException("Kontonamn är obligatoriskt.");
        }
        if (account.getBalance() < 0) {
            throw new SQLException("Saldo måste vara ett positivt tal.");
        }
        // Kontrollera om kunden redan finns med samma e-postadress
        Customer existingCustomer = customerRepository.getCustomerBySsNumber(customer.getSs_number());
        if (existingCustomer != null) {
            throw new SQLException("En användare med samma personnummer finns redan. Logga in för att hantera dina konton.");
        }

        // Kontrollera om kontonumret redan är upptaget
        if (accountRepository.isAccountNumberTaken(account.getAccount_number())) {
            throw new SQLException("Kontonumret är redan upptaget. Vänligen välj ett annat kontonummer eller logga in för att hantera dina konton.");
        }

        String hashedPassword = passwordService.hash(password);
        customer.setPassword(hashedPassword);
        customerRepository.createCustomer(customer);

        System.out.println(customer.getId());
        // Skapa bankkontot och koppla till kunden
        accountRepository.createAccount(account, customer.getId());
    }


    public Customer loginHandler(String ssNumber, String password) throws SQLException {
        // Kontrollera om användaren med personnumret finns i databasen
        Customer customer = customerRepository.getCustomerBySsNumber(ssNumber);
        if (customer == null) {
            throw new SQLException("Ingen användare med det angivna personnumret hittades.");
        }

        // Kontrollera om lösenordet matchar
        if (!passwordService.verifyPassword(password, customer.getPassword())) {
            throw new SQLException("Fel lösenord.");
        }

        return customer;
    }

    public void deleteCustomer(Customer customer) throws SQLException {
        customerRepository.deleteCustomer(customer);
        accountRepository.deleteAccountsByCustomerId(customer.getId());
    }

    public void updateCustomer(Customer customer) throws SQLException {
        // Validera kunddata
        if (customer.getFname() == null || customer.getFname().isEmpty()) {
            throw new SQLException("Förnamn är obligatoriskt.");
        }
        if (customer.getLname() == null || customer.getLname().isEmpty()) {
            throw new SQLException("Efternamn är obligatoriskt.");
        }
        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new SQLException("Email är obligatoriskt.");
        }
        if (customer.getPhone() == null || customer.getPhone().isEmpty()) {
            throw new SQLException("Telefonnummer är obligatoriskt.");
        }
        if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
            throw new SQLException("Lösenord är obligatoriskt.");
        }

        customerRepository.updateCustomer(customer);
    }

    public Customer getCustomerById(int customerId) throws SQLException {
        return customerRepository.getCustomerById(customerId);
    }

}
