package org.rockets.components;

import java.util.Objects;
import java.util.UUID;

public class Attachment {
    private UUID attachmentId;
    private String attachmentUrl;

    public Attachment(UUID attachmentId, UUID meetingId, String attachmentUrl) {
        this.attachmentId = attachmentId;
        this.attachmentUrl = attachmentUrl;
    }

    public UUID getAttachmentId() { return attachmentId; }
    public void setAttachmentId(UUID attachmentId) { this.attachmentId = attachmentId; }

    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment attachment = (Attachment) o;
        return Objects.equals(attachmentId, attachment.getAttachmentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attachmentId);
    }
}
