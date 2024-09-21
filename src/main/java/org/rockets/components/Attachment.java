package org.rockets.components;
import java.util.UUID;

public class Attachment {
    private UUID attachmentId;
    private UUID meetingId;
    private String attachmentUrl;

    public Attachment(UUID attachmentId, UUID meetingId, String attachmentUrl) {
        this.attachmentId = attachmentId;
        this.meetingId = meetingId;
        this.attachmentUrl = attachmentUrl;
    }

    public UUID getAttachmentId() { return attachmentId; }
    public void setAttachmentId(UUID attachmentId) { this.attachmentId = attachmentId; }

    public UUID getMeetingId() { return meetingId; }
    public void setMeetingId(UUID meetingId) { this.meetingId = meetingId; }

    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }
}
