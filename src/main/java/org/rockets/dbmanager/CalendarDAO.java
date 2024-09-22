package org.rockets.dbmanager;

import org.rockets.components.Calendar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CalendarDAO {
    private final Connection conn;

    public CalendarDAO(String dbUrl) throws SQLException, ClassNotFoundException {
        // Load the SQLite JDBC driver
        Class.forName("org.sqlite.JDBC");
        // Initialize the database connection
        conn = DriverManager.getConnection(dbUrl);
    }

    // Create a new calendar
    public void createCalendar(Calendar calendar) throws SQLException {
        String sql = "INSERT INTO Calendars (CalendarId, Title, Details) VALUES (?, ?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, calendar.getCalendarId().toString());
            pstmt.setString(2, calendar.getTitle());
            pstmt.setString(3, calendar.getDetails());
            pstmt.executeUpdate();
        }
    }

    // Read all calendars
    public List<Calendar> getAllCalendars() throws SQLException {
        String sql = "SELECT * FROM Calendars;";
        List<Calendar> calendars = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Calendar calendar = new Calendar(UUID.fromString(rs.getString("CalendarId")), rs.getString("Title"), rs.getString("CalendarDetails"));
                calendars.add(calendar);
            }
        }
        return calendars;
    }

    // Read a specific calendar
    public Calendar getCalendarById(String calendarId) throws SQLException {
        String sql = "SELECT * FROM Calendars WHERE CalendarId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, calendarId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Calendar(UUID.fromString(rs.getString("CalendarId")), rs.getString("Title"), rs.getString("CalendarDetails"));
                } else {
                    return null;
                }
            }
        }
    }

    public void updateCalendar(String calendarId, String title, String details) throws SQLException {
        String sql = "UPDATE Calendars SET Title = ?, Details = ? WHERE CalendarId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, details);
            pstmt.setString(3, calendarId);
            pstmt.executeUpdate();
        }
    }

    // Delete a calendar
    public void deleteCalendar(String calendarId) throws SQLException {
        String sql = "DELETE FROM Calendars WHERE CalendarId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, calendarId);
            pstmt.executeUpdate();
        }
        // Deletion of associations and triggers should handle other cleanups
    }

    // Add a meeting to a calendar
    public void addMeetingToCalendar(String meetingId, String calendarId) throws SQLException {
        String sql = "INSERT OR IGNORE INTO MeetingCalendar (MeetingId, CalendarId) VALUES (?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meetingId);
            pstmt.setString(2, calendarId);
            pstmt.executeUpdate();
        }
    }

    // Remove a meeting from a calendar
    public void removeMeetingFromCalendar(String meetingId, String calendarId) throws SQLException {
        String sql = "DELETE FROM MeetingCalendar WHERE MeetingId = ? AND CalendarId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meetingId);
            pstmt.setString(2, calendarId);
            pstmt.executeUpdate();
        }
    }

    // Get meeting IDs associated with a calendar
    private List<String> getMeetingIdsByCalendarId(String calendarId) throws SQLException {
        String sql = "SELECT MeetingId FROM MeetingCalendar WHERE CalendarId = ?;";
        List<String> meetingIds = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, calendarId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    meetingIds.add(rs.getString("MeetingId"));
                }
            }
        }
        return meetingIds;
    }

}
