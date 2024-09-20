package org.rockets;

import org.rockets.cli.parser.CommandLineParser;
import picocli.CommandLine;

import java.sql.*;

public class Application {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        Connection connection = null;
        try {
            // Database parameters
            String url = "jdbc:sqlite:calendar.db"; // Replace with your database file path
            // Create a connection to the database
            connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
