import java.util.UUID;

public class Participant {
    private UUID participantId;
    private UUID meetingId;
    private String name;
    private String email;

    public Participant(UUID participantId, UUID meetingId, String name, String email) {
        this.participantId = participantId;
        this.meetingId = meetingId;
        this.name = name;
        this.email = email;
    }

    public UUID getParticipantId() { return participantId; }
    public void setParticipantId(UUID participantId) { this.participantId = participantId; }

    public UUID getMeetingId() { return meetingId; }
    public void setMeetingId(UUID meetingId) { this.meetingId = meetingId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
