package org.rockets.dbmanager;

import org.rockets.components.Attachment;
import org.rockets.components.Check;
import org.rockets.components.Participant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttachmentDAO {
    private final Connection conn;

    public AttachmentDAO() throws SQLException, ClassNotFoundException {
        conn = DBInitializer.getConnection();
    }

    // Create a new attachment
    public void createAttachment(Attachment attachment) throws SQLException {
        if (!Check.isValidURL(attachment.getAttachmentUrl())) {
            throw new SQLException("Invalid URL format");
        }

        String sql = "INSERT INTO Attachments (AttachmentId, URL) VALUES (?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attachment.getAttachmentId());
            pstmt.setString(2, attachment.getAttachmentUrl());
            pstmt.executeUpdate();
        }
    }

    // Read all attachments
    public List<Attachment> getAllAttachments() throws SQLException {
        String sql = "SELECT * FROM Attachments;";
        List<Attachment> attachments = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Attachment attachment = new Attachment(
                        rs.getString("AttachmentId"),
                        rs.getString("URL")
                );
                attachments.add(attachment);
            }
        }
        return attachments;
    }

    // Read a specific attachment by AttachmentId
    public Attachment getAttachmentById(String attachmentId) throws SQLException {
        String sql = "SELECT * FROM Attachments WHERE AttachmentId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attachmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Attachment(
                            rs.getString("AttachmentId"),
                            rs.getString("URL")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public List<Attachment> getAttachmentsByMeetingId(String meetingId) throws SQLException {
        String sql = "SELECT Attachments.* FROM Attachments JOIN MeetingAttachment ON Attachments.AttachmentId = MeetingAttachment.AttachmentId WHERE MeetingId = ?;";
        List<Attachment> attachments = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meetingId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Attachment attachment = new Attachment(
                            rs.getString("AttachmentId"),
                            rs.getString("URL")
                    );
                    attachments.add(attachment);
                }
            }
        }
        return attachments;
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
                            rs.getString("ParticipantId"),
                            rs.getString("Name"),
                            rs.getString("Email")
                    );
                    participants.add(participant);
                }
            }
        }
        return participants;
    }

    // Update an attachment
    public void updateAttachment(Attachment attachment) throws SQLException {
        if (!Check.isValidURL(attachment.getAttachmentUrl())) {
            throw new SQLException("Invalid URL format");
        }

        String sql = "UPDATE Attachments SET URL = ? WHERE AttachmentId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attachment.getAttachmentUrl());
            pstmt.setString(2, attachment.getAttachmentId());
            pstmt.executeUpdate();
        }
    }

    // Delete an attachment
    public void deleteAttachment(String attachmentId) throws SQLException {
        String sql = "DELETE FROM Attachments WHERE AttachmentId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attachmentId);
            pstmt.executeUpdate();
        }
    }
}
