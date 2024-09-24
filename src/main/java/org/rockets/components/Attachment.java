package org.rockets.components;

import java.util.Objects;
import java.util.UUID;

public class Attachment {
    private final UUID attachmentId;
    private String attachmentUrl;

    public Attachment(UUID attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Attachment(UUID attachmentId, String attachmentUrl) {
        this(attachmentId);
        this.attachmentUrl = attachmentUrl;
    }

    public UUID getAttachmentId() {
        return attachmentId;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

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

    @Override
    public String toString() {
        return "(Attachment) id: " + getAttachmentId() + " | url: " + getAttachmentUrl();
    }
}
