package org.rockets;

import org.rockets.cli.parser.CommandLineParser;
import picocli.CommandLine;
import java.io.File;

import java.sql.*;

public class Application {
    public static void main(String[] args) {
        CommandLineParser parser = new CommandLineParser();
        CommandLine commandLine = new CommandLine(parser);

        int exitCode = commandLine.execute(args);
        test();

        System.exit(exitCode);
    }

    public static void test() {
        Connection connection = null;
        try {
            // Database parameters
            String url = "jdbc:sqlite:calendar.db"; // Replace with your database file path
            // Create a connection to the database
            File dbFile = new File("calendar.db");
            if (!dbFile.exists()) {
                System.out.println("File doesn't exist");
                return;
            }
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
