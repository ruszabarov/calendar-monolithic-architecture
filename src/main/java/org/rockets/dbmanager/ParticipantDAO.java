package org.rockets.dbmanager;

import org.rockets.components.Calendar;
import org.rockets.components.Participant;
import org.rockets.components.Check;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParticipantDAO {
    private final Connection conn;

    public ParticipantDAO() throws SQLException, ClassNotFoundException {
        conn = DBInitializer.getConnection();
    }

    // Create a new participant with validations
    public void createParticipant(Participant participant) throws SQLException {
        String sql = "INSERT INTO Participants (ParticipantId, Name, Email) VALUES (?, ?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, participant.getParticipantId().toString());
            pstmt.setString(2, participant.getName());
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
                        rs.getString("Name"),
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
                            rs.getString("Name"),
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
        String sql = "SELECT Participants.* FROM Participants JOIN MeetingParticipant ON Participants.ParticipantId = MeetingParticipant.ParticipantId WHERE MeetingId = ?;";

        List<Participant> participants = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meetingId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Participant participant = new Participant(
                            UUID.fromString(rs.getString("ParticipantId")),
                            rs.getString("Name"),
                            rs.getString("Email")
                    );
                    participants.add(participant);
                }
            }
        }
        return participants;
    }

    public void updateParticipant(Participant participant) throws SQLException {
        String sql = "UPDATE Participants SET Name = ?, Email = ? WHERE ParticipantId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, participant.getName());
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
