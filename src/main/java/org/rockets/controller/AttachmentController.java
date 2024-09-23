package org.rockets.controller;

import org.rockets.components.Attachment;
import org.rockets.dbmanager.AttachmentDAO;
import java.sql.SQLException;

public class AttachmentController {
    private AttachmentDAO attachmentDAO;

    public AttachmentController(String dbUrl) {
        try {
            this.attachmentDAO = new AttachmentDAO(dbUrl);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createAttachment(Attachment attachment) {
        try {
            this.attachmentDAO.createAttachment(attachment);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Attachment getAttachment(String id) {
        try {
            this.attachmentDAO.getAttachmentById(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public Attachment updateAttachment(Attachment attachment) {
        try {
            this.attachmentDAO.updateAttachment(attachment);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return attachment;
    }

    public void deleteAttachment(Attachment attachment) {
        try {
            this.attachmentDAO.deleteAttachment(attachment.getAttachmentId().toString());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
