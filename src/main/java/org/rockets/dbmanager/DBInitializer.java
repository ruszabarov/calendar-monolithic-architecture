package org.rockets.dbmanager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    private static Connection conn = null;

    public static void initializeDatabase(String dbFilePath) throws SQLException, ClassNotFoundException {

        boolean dbExists = new File(dbFilePath).exists();

        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);

        if (dbExists) {
            System.out.println("Database file found successfully.");
            return;
        }


        // Initialize the schema if necessary
        try (Statement stmt = conn.createStatement()) {
            String schemaSQL = "CREATE TABLE MeetingAttachment ( " +
                    "MeetingId TEXT NOT NULL, " +
                    "AttachmentId TEXT NOT NULL, " +
                    "PRIMARY KEY (MeetingId, AttachmentId), " +
                    "FOREIGN KEY (MeetingId) REFERENCES Meetings(MeetingId) ON DELETE CASCADE, " +
                    "FOREIGN KEY (AttachmentId) REFERENCES Attachments(AttachmentId) ON DELETE CASCADE); " +

                    "CREATE TABLE IF NOT EXISTS Meetings ( " +
                    "MeetingId TEXT PRIMARY KEY, " +
                    "Title TEXT NOT NULL CHECK (length(Title) <= 2000), " +
                    "DateTime TEXT NOT NULL, " +
                    "Location TEXT CHECK (length(Location) <= 2000), " +
                    "Details TEXT CHECK (length(Details) <= 10000)); " +

                    "CREATE TABLE IF NOT EXISTS Calendars ( " +
                    "CalendarId TEXT PRIMARY KEY, " +
                    "Title TEXT NOT NULL CHECK (length(Title) <= 2000), " +
                    "Details TEXT CHECK (length(Details) <= 10000)); " +

                    "CREATE TABLE IF NOT EXISTS Participants ( " +
                    "ParticipantId TEXT PRIMARY KEY, " +
                    "Name TEXT NOT NULL CHECK (length(Name) <= 600), " +
                    "Email TEXT NOT NULL UNIQUE CHECK (Email LIKE '%_@_%._%')); " +

                    "CREATE TABLE IF NOT EXISTS Attachments ( " +
                    "AttachmentId TEXT PRIMARY KEY, " +
                    "URL TEXT NOT NULL); " +

                    "CREATE TABLE MeetingCalendar ( " +
                    "MeetingId TEXT NOT NULL, " +
                    "CalendarId TEXT NOT NULL, " +
                    "PRIMARY KEY (MeetingId, CalendarId), " +
                    "FOREIGN KEY (MeetingId) REFERENCES Meetings(MeetingId) ON DELETE CASCADE, " +
                    "FOREIGN KEY (CalendarId) REFERENCES Calendars(CalendarId) ON DELETE CASCADE); " +

                    "CREATE TABLE MeetingParticipant ( " +
                    "MeetingId TEXT NOT NULL, " +
                    "ParticipantId TEXT NOT NULL, " +
                    "PRIMARY KEY (MeetingId, ParticipantId), " +
                    "FOREIGN KEY (MeetingId) REFERENCES Meetings(MeetingId) ON DELETE CASCADE, " +
                    "FOREIGN KEY (ParticipantId) REFERENCES Participants(ParticipantId) ON DELETE CASCADE); " +

                    "CREATE TRIGGER delete_calendar_if_no_meetings " +
                    "AFTER DELETE ON MeetingCalendar " +
                    "FOR EACH ROW " +
                    "BEGIN " +
                    "DELETE FROM Calendars " +
                    "WHERE CalendarId = OLD.CalendarId " +
                    "AND NOT EXISTS (SELECT 1 FROM MeetingCalendar WHERE CalendarId = OLD.CalendarId); " +
                    "END; " +

                    "CREATE TRIGGER delete_meeting_if_no_participants " +
                    "AFTER DELETE ON MeetingParticipant " +
                    "FOR EACH ROW " +
                    "BEGIN " +
                    "DELETE FROM Meetings " +
                    "WHERE MeetingId = OLD.MeetingId " +
                    "AND NOT EXISTS (SELECT 1 FROM MeetingParticipant WHERE MeetingId = OLD.MeetingId); " +
                    "END;";
            stmt.executeUpdate(schemaSQL);

            System.out.println("Database schema initialized successfully.");
        }
    }

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Connection not initialized. Please initialize the database.");
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
