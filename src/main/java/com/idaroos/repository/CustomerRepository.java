package com.idaroos.repository;

import com.idaroos.model.Customer;
import com.idaroos.model.DatabaseConnector;

import java.sql.*;

public class CustomerRepository {
    private final DatabaseConnector databaseConnector;




    public CustomerRepository() {
        this.databaseConnector = DatabaseConnector.getInstance();
    }

    public void createCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (ss_number, password, fname, lname, email, phone) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, customer.getSs_number());
            statement.setString(2, customer.getPassword());
            statement.setString(3, customer.getFname());
            statement.setString(4, customer.getLname());
            statement.setString(5, customer.getEmail());
            statement.setString(6, customer.getPhone());

            statement.executeUpdate();

            // Hämta det genererade id-värdet
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    customer.setId(generatedId);
                } else {
                    throw new SQLException("Misslyckades med att hämta det genererade id-värdet för kunden.");
                }
            }
        }
    }

    public Customer getCustomerByEmail(String email) throws SQLException {
        String query = "SELECT * FROM customers WHERE email = ?";

        try (Connection connection = DatabaseConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String ss_number = resultSet.getString("ss_number");
                String password = resultSet.getString("password");
                String fname = resultSet.getString("fname");
                String lname = resultSet.getString("lname");
                String phone = resultSet.getString("phone");
                // ... Läs in övriga kolumnvärden

                return new Customer(id, ss_number, password, fname, lname, email, phone);
            } else {
                return null; // Kund med angiven e-postadress finns inte
            }
        }
    }

    public Customer getCustomerBySsNumber(String ssNumber) throws SQLException {
        String query = "SELECT * FROM customers WHERE ss_number = ?";

        try (Connection connection = DatabaseConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ssNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String fname = resultSet.getString("fname");
                String lname = resultSet.getString("lname");
                String phone = resultSet.getString("phone");
                // ... Läs in övriga kolumnvärden

                return new Customer(id, ssNumber, password, fname, lname, email, phone);
            } else {
                return null; // Kund med angivet personnummer finns inte
            }
        }
    }


    public void deleteCustomer(Customer customer) throws SQLException {
        try (Connection connection = databaseConnector.getConnection()) {
            // Ta bort kunden
            String deleteCustomerQuery = "DELETE FROM customers WHERE id = ?";
            try (PreparedStatement deleteCustomerStatement = connection.prepareStatement(deleteCustomerQuery)) {
                deleteCustomerStatement.setInt(1, customer.getId());
                deleteCustomerStatement.executeUpdate();
            }
        }
    }


    public void updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE customers SET fname = ?, lname = ?, email = ?, phone = ?, password = ? WHERE id = ?";

        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getFname());
            statement.setString(2, customer.getLname());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPhone());
            statement.setString(5, customer.getPassword());
            statement.setInt(6, customer.getId());

            statement.executeUpdate();
        }
    }

    public Customer getCustomerById(int customerId) throws SQLException {
        String query = "SELECT * FROM customers WHERE id = ?";
        try (Connection connection = this.databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setFname(resultSet.getString("fname"));
                customer.setLname(resultSet.getString("lname"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setPassword(resultSet.getString("password"));
                return customer;
            }
        } catch (SQLException e) {
            throw new SQLException("Fel vid hämtning av kunduppgifter: " + e.getMessage());
        }
        return null;
    }
}