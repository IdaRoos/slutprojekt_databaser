package com.idaroos.model;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private static DatabaseConnector instance;
    private static MysqlDataSource dataSource;

    private DatabaseConnector() {
        // Privat konstruktor för att förhindra extern instansiering
    }

    public static DatabaseConnector getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnector.class) {
                if (instance == null) {
                    instance = new DatabaseConnector();
                    instance.initializeDatabase();
                }
            }
        }
        return instance;
    }

    private void initializeDatabase() {
        try {
            System.out.println("Configuring data source...");
            Properties properties = loadProperties();
            dataSource = new MysqlDataSource();
            dataSource.setUser(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
            dataSource.setUrl("jdbc:mysql://" + properties.getProperty("url") + ":" +
                    properties.getProperty("port") + "/" + properties.getProperty("database") +
                    "?serverTimezone=UTC");
            dataSource.setUseSSL(false);
            System.out.println("done!");
        } catch (SQLException | IOException e) {
            System.out.println("failed!");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public Connection getConnection() {
        try {
            System.out.println("Fetching connection to database...");
            Connection connection = dataSource.getConnection();
            System.out.println("done!");
            return connection;
        } catch (SQLException e) {
            System.out.println("failed!");
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    private Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/database.properties");
        properties.load(fileInputStream);
        fileInputStream.close();
        return properties;
    }
}
