import java.util.List;
import java.util.UUID;

public class Meeting {
    private UUID meetingId;
    private String title;
    private String dateTime;
    private String location;
    private String details;
    private List<UUID> calendarIds;
    private List<UUID> participantIds;
    private List<UUID> attachmentIds;

    public Meeting(UUID meetingId, String title, String dateTime, String location, String details,
            List<UUID> calendarIds, List<UUID> participantIds, List<UUID> attachmentIds) {
        this.meetingId = meetingId;
        this.title = title;
        this.dateTime = dateTime;
        this.location = location;
        this.details = details;
        this.calendarIds = calendarIds;
        this.participantIds = participantIds;
        this.attachmentIds = attachmentIds;
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

    public List<UUID> getCalendarIds() {
        return calendarIds;
    }

    public void setCalendarIds(List<UUID> calendarIds) {
        this.calendarIds = calendarIds;
    }

    public List<UUID> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<UUID> participantIds) {
        this.participantIds = participantIds;
    }

    public List<UUID> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<UUID> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    // Adds CalendarId
    public void addCalendarId(UUID calendarId) {
        if (!calendarIds.contains(calendarId)) {
            calendarIds.add(calendarId);
        }
    }

    // Removes CalendarId
    public void removeCalendarId(UUID calendarId) {
        calendarIds.remove(calendarId);
    }

    // Adds ParticipantID
    public void addParticipantId(UUID participantId) {
        if (!participantIds.contains(participantId)) {
            participantIds.add(participantId);
        }
    }

    // Removes ParticipantID
    public void removeParticipantId(UUID participantId) {
        participantIds.remove(participantId);
    }

    // Adds AttachmentId
    public void addAttachmentId(UUID attachmentId) {
        if (!attachmentIds.contains(attachmentId)) {
            attachmentIds.add(attachmentId);
        }
    }

    // Removes AttachmentId
    public void removeAttachmentId(UUID attachmentId) {
        attachmentIds.remove(attachmentId);
    }
}
