package org.rockets;

import org.rockets.cli.parser.CommandLineParser;
import org.rockets.dbmanager.DBInitializer;
import picocli.CommandLine;
import java.io.File;

import java.sql.*;

public class Application {
    public static void main(String[] args) {
        try {
            DBInitializer.initializeDatabase("calendar.db");

            CommandLineParser parser = new CommandLineParser();
            CommandLine commandLine = new CommandLine(parser);

            int exitCode = commandLine.execute(args);

            System.exit(exitCode);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBInitializer.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
