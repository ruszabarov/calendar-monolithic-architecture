package org.rockets.components;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Meeting {
    private UUID meetingId;
    private String title;
    private String dateTime;
    private String location;
    private String details;
    private List<Participant> participants;
    private List<Attachment> attachments;

    private List<Calendar> calendars;

    public Meeting(UUID uuid) {
        this.meetingId = uuid;
    }

    public Meeting(UUID meetingId, String title, String dateTime, String location, String details) {
        this(meetingId);
        this.title = title;
        this.dateTime = dateTime;
        this.location = location;
        this.details = details;
    }

    public UUID getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(UUID meetingId) {
        this.meetingId = meetingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    // Adds ParticipantID
    public void addParticipant(Participant participant) {
        if (participant.getParticipantId() != null && !participants.contains(participant)) {
            participants.add(participant);
        }
    }

    // Removes ParticipantID
    public void removeParticipant(Participant participant) {
        participants.remove(participant);
    }

    // Adds AttachmentId
    public void addAttachment(Attachment attachment) {
        if (!attachments.contains(attachment)) {
            attachments.add(attachment);
        }
    }

    // Removes AttachmentId
    public void removeAttachment(Attachment attachment) {
        attachments.remove(attachment);
    }

    public List<Calendar> getCalendars() {
        return this.calendars;
    }

    public void setCalendars(List<Calendar> calendars) {
        this.calendars = calendars;
    }

    public void addCalendar(Calendar calendar) {
        if (!this.calendars.contains(calendar)) {
            this.calendars.add(calendar);
        }
    }

    public void removeCalendar(Calendar calendar) {
        if (calendar != null) {
            this.calendars.remove(calendar);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(meetingId, meeting.meetingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId);
    }

}
