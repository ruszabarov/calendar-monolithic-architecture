package org.rockets.dbmanager;

import org.rockets.components.Participant;
import org.rockets.components.Check;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParticipantDAO {
    private Connection conn;

    public ParticipantDAO(String dbUrl) throws SQLException, ClassNotFoundException {
        // Load the SQLite JDBC driver
        Class.forName("org.sqlite.JDBC");
        // Initialize the database connection
        conn = DriverManager.getConnection(dbUrl);
    }

    // Create a new participant with validations
    public void createParticipant(Participant participant) throws SQLException {
        // Validate email
        if (!Check.isValidEmail(participant.getEmail())) {
            throw new SQLException("Invalid email format");
        }

        // Ensure name is not longer than 600 characters
        String name = Check.limitString(participant.getName(), 600);

        String sql = "INSERT INTO Participants (ParticipantId, Name, Email) VALUES (?, ?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Check.generate(participant.getParticipantId()).toString()); // Generate UUID if null
            pstmt.setString(2, name);
            pstmt.setString(3, participant.getEmail());
            pstmt.executeUpdate();
        }
    }

    // Read all participants
    public List<Participant> getAllParticipants() throws SQLException {
        String sql = "SELECT * FROM Participants;";
        List<Participant> participants = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Participant participant = new Participant(
                        UUID.fromString(rs.getString("ParticipantId")),
                        Check.limitString(rs.getString("Name"), 600),
                        rs.getString("Email")
                );
                participants.add(participant);
            }
        }
        return participants;
    }

    // Read a specific participant by ParticipantId
    public Participant getParticipantById(String participantId) throws SQLException {
        String sql = "SELECT * FROM Participants WHERE ParticipantId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, participantId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Participant(
                            UUID.fromString(rs.getString("ParticipantId")),
                            Check.limitString(rs.getString("Name"), 600),
                            rs.getString("Email")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    // Read participants by MeetingId
    public List<Participant> getParticipantsByMeetingId(String meetingId) throws SQLException {
        String sql = "SELECT * FROM MeetingParticipant WHERE MeetingId = ?;";
        List<Participant> participants = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meetingId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Participant participant = new Participant(
                            UUID.fromString(rs.getString("ParticipantId")),
                            Check.limitString(rs.getString("Name"), 600),
                            rs.getString("Email")
                    );
                    participants.add(participant);
                }
            }
        }
        return participants;
    }

    // Update a participant with validations
    public void updateParticipant(Participant participant) throws SQLException {
        // Validate email
        if (!Check.isValidEmail(participant.getEmail())) {
            throw new SQLException("Invalid email format");
        }

        // Ensure name is not longer than 600 characters
        String name = Check.limitString(participant.getName(), 600);

        String sql = "UPDATE Participants SET Name = ?, Email = ? WHERE ParticipantId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, participant.getEmail());
            pstmt.setString(3, participant.getParticipantId().toString());
            pstmt.executeUpdate();
        }
    }

    // Delete a participant
    public void deleteParticipant(String participantId) throws SQLException {
        String sql = "DELETE FROM Participants WHERE ParticipantId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, participantId);
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
