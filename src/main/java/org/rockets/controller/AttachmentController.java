package org.rockets.controller;

import org.rockets.components.Attachment;
import org.rockets.components.Meeting;
import org.rockets.dbmanager.AttachmentDAO;
import org.rockets.dbmanager.MeetingDAO;

import java.sql.SQLException;
import java.util.List;

public class AttachmentController {
    private AttachmentDAO attachmentDAO;
    private MeetingDAO meetingDAO;

    public AttachmentController() {
        try {
            this.attachmentDAO = new AttachmentDAO();
            this.meetingDAO = new MeetingDAO();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createAttachmentWithMeetingIds(Attachment attachment, List<String> meetingIds) throws SQLException {
        this.attachmentDAO.createAttachment(attachment);

        for (String meetingId : meetingIds) {
            Meeting m = meetingDAO.getMeetingById(meetingId);

            if (m == null) {
                throw new SQLException("Meeting with id " + meetingId + " was not found!");
            }

            meetingDAO.addAttachmentToMeeting(meetingId, attachment.getAttachmentId().toString());
        }
    }

    public void addAttachmentToMeeting(Meeting meeting, Attachment attachment) throws SQLException {

        Attachment a = attachmentDAO.getAttachmentById(attachment.getAttachmentId().toString());

        if (a == null) {
            throw new SQLException("Attachment with id " + attachment.getAttachmentId() + " was not found!");
        }

        Meeting m = meetingDAO.getMeetingById(meeting.getMeetingId().toString());

        if (m == null) {
            throw new SQLException("Meeting with id " + meeting.getMeetingId() + " was not found!");
        }

        meetingDAO.addAttachmentToMeeting(meeting.getMeetingId().toString(), attachment.getAttachmentId().toString());
    }

    public void removeAttachmentFromMeeting(Meeting meeting, Attachment attachment) throws SQLException {
        Meeting m = meetingDAO.getMeetingById(meeting.getMeetingId().toString());

        if (m == null) {
            System.err.println("Meeting with id " + attachment.getAttachmentId() + " was not found!");
        }

        meetingDAO.removeAttachmentFromMeeting(meeting.getMeetingId().toString(), attachment.getAttachmentId().toString());
    }

    public Attachment getAttachmentById(String id) throws SQLException {
        Attachment attachment = this.attachmentDAO.getAttachmentById(id);

        if (attachment == null) {
            throw new RuntimeException("Attachment with id " + id + " was not found!");
        }

        return attachment;
    }

    public List<Attachment> getAllAttachments() throws SQLException {
        return this.attachmentDAO.getAllAttachments();
    }

    public void updateAttachment(Attachment attachment) throws SQLException {
        this.attachmentDAO.updateAttachment(attachment);
    }

    public void deleteAttachment(Attachment attachment) throws SQLException {
        this.attachmentDAO.deleteAttachment(attachment.getAttachmentId().toString());

    }
}
