package org.rockets.dbmanager;
import org.rockets.components.Meeting;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MeetingDAO {
    private final Connection conn;

    public MeetingDAO(String dbUrl) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(dbUrl);
    }

    // Create a new meeting
    public void createMeeting(Meeting meeting) throws SQLException {
        String sql = "INSERT INTO Meetings (MeetingId, Title, DateTime, Location, Details) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meeting.getMeetingId().toString());
            pstmt.setString(2, meeting.getTitle());
            pstmt.setString(3, meeting.getDateTime());
            pstmt.setString(4, meeting.getLocation());
            pstmt.setString(5, meeting.getDetails());
            pstmt.executeUpdate();
        }
    }

    // Read all meetings
    public List<Meeting> getAllMeetings() throws SQLException {
        String sql = "SELECT * FROM Meetings;";
        List<Meeting> meetings = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Meeting meeting = new Meeting(UUID.fromString(rs.getString("MeetingId")), rs.getString("Title"), rs.getString("DateTime"), rs.getString("Location"), rs.getString("Details"));
                meetings.add(meeting);
            }
        }
        return meetings;
    }

    // Read meetings by calendar id
    public List<Meeting> getMeetingsByCalendarId(String calendarId) throws SQLException {
        String sql = "SELECT Meetings.* FROM Meetings JOIN MeetingCalendar ON Meetings.MeetingId = MeetingCalendar.MeetingId WHERE MeetingCalendar.calendarId = ?";

        List<Meeting> meetings = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, calendarId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Meeting meeting = new Meeting(UUID.fromString(rs.getString("Meetings.MeetingId")), rs.getString("Meetings.Title"), rs.getString("Meetings.DateTime"), rs.getString("Meetings.Location"), rs.getString("Meetings.Details"));
                    meetings.add(meeting);
                }
            }
        }

        return meetings;
    }

    // Read a specific meeting
    public Meeting getMeetingById(String meetingId) throws SQLException {
        String sql = "SELECT * FROM Meetings WHERE MeetingId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meetingId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Meeting(UUID.fromString(rs.getString("MeetingId")), rs.getString("Title"), rs.getString("DateTime"), rs.getString("Location"), rs.getString("Details"));
                } else {
                    return null;
                }
            }
        }
    }

    // Update a meeting
    public void updateMeeting(Meeting meeting) throws SQLException {
        String sql = "UPDATE Meetings SET Title = ?, DateTime = ?, Location = ?, Details = ? WHERE MeetingId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meeting.getTitle());
            pstmt.setString(2, meeting.getDateTime());
            pstmt.setString(3, meeting.getLocation());
            pstmt.setString(4, meeting.getDetails());
            pstmt.setString(8, meeting.getMeetingId().toString());
            pstmt.executeUpdate();
        }
    }

    // Delete a meeting
    public void deleteMeeting(String meetingId) throws SQLException {
        String sql = "DELETE FROM Meetings WHERE MeetingId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meetingId);
            pstmt.executeUpdate();
        }
    }

    public void addParticipantToMeeting(String meetingId, String participantId) throws SQLException {
        String sql = "INSERT OR IGNORE INTO MeetingParticipant (MeetingId, ParticipantId) VALUES (?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meetingId);
            pstmt.setString(2, participantId);
            pstmt.executeUpdate();
        }
    }


    public void removeParticipantFromMeeting(String meetingId, String participantId) throws SQLException {
        String sql = "DELETE FROM MeetingParticipant WHERE MeetingId = ? AND ParticipantId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meetingId);
            pstmt.setString(2, participantId);
            pstmt.executeUpdate();
        }
    }

    public void addAttachmentToMeeting(String meetingId, String attachmentId) throws SQLException {
        String sql = "INSERT OR IGNORE INTO MeetingAttachment (MeetingId, AttachmentId) VALUES (?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meetingId);
            pstmt.setString(2, attachmentId);
            pstmt.executeUpdate();
        }
    }


    public void removeAttachmentFromMeeting(String meetingId, String attachmentId) throws SQLException {
        String sql = "DELETE FROM MeetingAttachment WHERE MeetingId = ? AND AttachmentId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meetingId);
            pstmt.setString(2, attachmentId);
            pstmt.executeUpdate();
        }
    }

    // Close the connection when done
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}

