import java.util.List;
import java.util.UUID;

public class Calendar {
    private UUID calendarId;
    private String title;
    private String details;
    private List<UUID> meetingIds;

    public Calendar(UUID calendarId, String title, String details, List<UUID> meetingIds) {
        this.calendarId = calendarId;
        this.title = title;
        this.details = details;
        this.meetingIds = meetingIds;
    }

    public UUID getCalendarId() { return calendarId; }
    public void setCalendarId(UUID calendarId) { this.calendarId = calendarId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public List<UUID> getMeetingIds() { return meetingIds; }
    public void setMeetingIds(List<UUID> meetingIds) { this.meetingIds = meetingIds; }
}
