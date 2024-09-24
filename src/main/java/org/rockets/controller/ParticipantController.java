package org.rockets.controller;

import org.rockets.components.Participant;
import org.rockets.dbmanager.ParticipantDAO;

import java.sql.SQLException;
import java.util.List;

public class ParticipantController {
    private ParticipantDAO participantDAO;

    public ParticipantController(String dbUrl) {
        try {
            this.participantDAO = new ParticipantDAO();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createParticipant(Participant participant) throws SQLException {
        participantDAO.createParticipant(participant);
    }

    public Participant getParticipant(String id) throws SQLException {
        return participantDAO.getParticipantById(id);
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
