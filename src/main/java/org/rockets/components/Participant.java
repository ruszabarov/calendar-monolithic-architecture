package org.rockets.components;

import java.util.Objects;
import java.util.UUID;

public class Participant {
    private UUID participantId;
    private String name;
    private String email;

    public Participant(UUID participantId, String name, String email) {
        this.participantId = participantId;
        this.name = name;
        this.email = email;
    }

    public UUID getParticipantId() { return participantId; }
    public void setParticipantId(UUID participantId) { this.participantId = participantId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant participant = (Participant) o;
        return Objects.equals(participantId, participant.getParticipantId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId);
    }
}
