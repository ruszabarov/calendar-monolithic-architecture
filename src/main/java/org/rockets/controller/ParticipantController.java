package org.rockets.controller;

import org.rockets.components.Participant;
import org.rockets.dbmanager.ParticipantDAO;

import java.sql.SQLException;
import java.util.List;

public class ParticipantController {
    private ParticipantDAO participantDAO;

    public ParticipantController() {
        try {
            this.participantDAO = new ParticipantDAO();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createParticipant(Participant participant) throws SQLException {
        participantDAO.createParticipant(participant);
    }

    public Participant getParticipantById(String id) throws SQLException {
        Participant participant = participantDAO.getParticipantById(id);

        if (participant == null) {
            throw new RuntimeException("Participant with id " + id + " was not found");
        }

        return participant;
    }

    public List<Participant> getAllParticipants() throws SQLException {
        return participantDAO.getAllParticipants();
    }

    public void updateParticipant(Participant participant) throws SQLException {
        participantDAO.updateParticipant(participant);
    }

    public void deleteParticipant(Participant participant) throws SQLException {
        participantDAO.deleteParticipant(participant.getParticipantId().toString());
    }
}
