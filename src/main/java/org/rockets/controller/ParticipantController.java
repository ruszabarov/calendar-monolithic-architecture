package org.rockets.controller;

import org.rockets.components.Participant;
import org.rockets.dbmanager.MeetingDAO;
import org.rockets.dbmanager.ParticipantDAO;

import java.sql.SQLException;

public class ParticipantController {
    private ParticipantDAO participantDAO;

    public ParticipantController(String dbUrl) {
        try {
            this.participantDAO = new ParticipantDAO(dbUrl);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createParticipant(Participant participant) {
        try {
            participantDAO.createParticipant(participant);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Participant getParticipant(String id) {
        try {
            return participantDAO.getParticipantById(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Participant updateParticipant(Participant participant) {
        try {
            participantDAO.updateParticipant(participant);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return participant;
    }

    public void deleteParticipant(Participant participant) {
        try {
            participantDAO.deleteParticipant(participant.getParticipantId().toString());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
