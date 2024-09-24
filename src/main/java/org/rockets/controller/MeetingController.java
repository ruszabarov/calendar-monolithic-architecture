package org.rockets.controller;

import org.rockets.components.Attachment;
import org.rockets.components.Meeting;
import org.rockets.components.Participant;
import org.rockets.dbmanager.AttachmentDAO;
import org.rockets.dbmanager.MeetingDAO;
import org.rockets.dbmanager.ParticipantDAO;

import java.sql.SQLException;
import java.util.List;

public class MeetingController {
    private MeetingDAO meetingDAO;
    private ParticipantDAO participantDAO;
    private AttachmentDAO attachmentDAO;

    public MeetingController(String dbUrl) {
        try {
            this.meetingDAO = new MeetingDAO(dbUrl);
            this.participantDAO = new ParticipantDAO(dbUrl);
            this.attachmentDAO = new AttachmentDAO(dbUrl);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createMeetingWithParticipants(Meeting meeting, List<String> participantIds) {
        try {
            meetingDAO.createMeeting(meeting);

            for (String participantId : participantIds) {
                Participant participant = participantDAO.getParticipantById(participantId);

                if (participant == null) {
                    System.err.println("Participant with id " + participantId + " was not found!");
                } else {
                    meetingDAO.addParticipantToMeeting(meeting.getMeetingId().toString(), participant.getParticipantId().toString());
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Meeting getMeeting(String id) {
        try {
            return meetingDAO.getMeetingById(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public Meeting updateMeeting(Meeting meeting) {
        try {
            meetingDAO.updateMeeting(meeting);
            return meetingDAO.getMeetingById(meeting.getMeetingId().toString());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return meeting;
    }

    public void deleteMeeting(Meeting meeting) {
        try {
            meetingDAO.deleteMeeting(meeting.getMeetingId().toString());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Meeting addParticipantToMeeting(Meeting meeting, Participant participant) {
        try {
            Participant p = participantDAO.getParticipantById(participant.getParticipantId().toString());

            if (p == null) {
                System.err.println("Participant with id " + participant.getParticipantId() + " was not found!");
                return meeting;
            }

            Meeting m = meetingDAO.getMeetingById(meeting.getMeetingId().toString());

            if (m == null) {
                System.err.println("Meeting with id " + meeting.getMeetingId() + " was not found!");
                return meeting;
            }

            meetingDAO.addParticipantToMeeting(meeting.getMeetingId().toString(), participant.getParticipantId().toString());
            return meetingDAO.getMeetingById(meeting.getMeetingId().toString());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return meeting;
    }

    public Meeting removeParticipantFromMeeting(Meeting meeting, Participant participant) {
        try {
            Meeting m = meetingDAO.getMeetingById(meeting.getMeetingId().toString());

            if (m == null) {
                System.err.println("Meeting with id " + meeting.getMeetingId() + " was not found!");
                return meeting;
            }

            meetingDAO.removeParticipantFromMeeting(meeting.getMeetingId().toString(), participant.getParticipantId().toString());

            return meetingDAO.getMeetingById(meeting.getMeetingId().toString());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return meeting;
    }

    public Meeting addAttachmentToMeeting(Meeting meeting, Attachment attachment) {
        try {
            Attachment a = attachmentDAO.getAttachmentById(attachment.getAttachmentId().toString());

            if (a == null) {
                System.err.println("Attachment with id " + attachment.getAttachmentId() + " was not found!");
                return meeting;
            }

            Meeting m = meetingDAO.getMeetingById(meeting.getMeetingId().toString());

            if (m == null) {
                System.err.println("Meeting with id " + meeting.getMeetingId() + " was not found!");
                return meeting;
            }

            meetingDAO.addAttachmentToMeeting(meeting.getMeetingId().toString(), attachment.getAttachmentId().toString());
            return meetingDAO.getMeetingById(meeting.getMeetingId().toString());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return meeting;
    }

    public Meeting removeAttachmentFromMeeting(Meeting meeting, Attachment attachment) {
        try {
            Meeting m = meetingDAO.getMeetingById(meeting.getMeetingId().toString());

            if (m == null) {
                System.err.println("Meeting with id " + attachment.getAttachmentId() + " was not found!");
                return meeting;
            }

            meetingDAO.removeAttachmentFromMeeting(meeting.getMeetingId().toString(), attachment.getAttachmentId().toString());
            return meetingDAO.getMeetingById(meeting.getMeetingId().toString());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return meeting;
    }
}
