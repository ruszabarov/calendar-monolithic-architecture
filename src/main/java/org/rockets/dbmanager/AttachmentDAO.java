package org.rockets.dbmanager;

import org.rockets.components.Attachment;
import org.rockets.components.Check;
import org.rockets.components.Participant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AttachmentDAO {
    private Connection conn;

    public AttachmentDAO(String dbUrl) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(dbUrl);
    }

    // Create a new attachment
    public void createAttachment(Attachment attachment) throws SQLException {
        if (!Check.isValidURL(attachment.getAttachmentUrl())) {
            throw new SQLException("Invalid URL format");
        }

        String sql = "INSERT INTO Attachments (AttachmentId, URL) VALUES (?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attachment.getAttachmentId().toString());
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
                        UUID.fromString(rs.getString("AttachmentId")),
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
                            UUID.fromString(rs.getString("AttachmentId")),
                            rs.getString("URL")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public List<Attachment> getAttachmentsByMeetingId(String meetingId) throws SQLException {
        String sql = "SELECT * FROM MeetingAttachment WHERE MeetingId = ?;";
        List<Attachment> attachments = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meetingId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Attachment attachment = new Attachment(
                            UUID.fromString(rs.getString("AttachmentId")),
                            rs.getString("URL")
                    );
                    attachments.add(attachment);
                }
            }
        }
        return attachments;
    }

    // Update an attachment
    public void updateAttachment(Attachment attachment) throws SQLException {
        if (!Check.isValidURL(attachment.getAttachmentUrl())) {
            throw new SQLException("Invalid URL format");
        }

        String sql = "UPDATE Attachments SET URL = ? WHERE AttachmentId = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attachment.getAttachmentUrl());
            pstmt.setString(2, attachment.getAttachmentId().toString());
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

    // Close the connection when done
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
