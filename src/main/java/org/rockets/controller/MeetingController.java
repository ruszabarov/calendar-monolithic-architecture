package org.rockets.controller;

import org.rockets.components.Attachment;
import org.rockets.components.Calendar;
import org.rockets.components.Meeting;
import org.rockets.components.Participant;
import org.rockets.dbmanager.AttachmentDAO;
import org.rockets.dbmanager.CalendarDAO;
import org.rockets.dbmanager.MeetingDAO;
import org.rockets.dbmanager.ParticipantDAO;

import java.sql.SQLException;
import java.util.List;

public class MeetingController {
    private MeetingDAO meetingDAO;
    private ParticipantDAO participantDAO;
    private AttachmentDAO attachmentDAO;
    private CalendarDAO calendarDAO;

    public MeetingController() {
        try {
            this.meetingDAO = new MeetingDAO();
            this.participantDAO = new ParticipantDAO();
            this.attachmentDAO = new AttachmentDAO();
            this.calendarDAO = new CalendarDAO();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createMeetingWithParticipants(Meeting meeting, List<String> participantIds) throws SQLException {
        meetingDAO.createMeeting(meeting);

        for (String participantId : participantIds) {
            Participant participant = participantDAO.getParticipantById(participantId);

            if (participant == null) {
                throw new SQLException("Participant with id " + participantId + " was not found!");
            }

            meetingDAO.addParticipantToMeeting(meeting.getMeetingId().toString(), participant.getParticipantId().toString());
        }
    }

    public Meeting getMeeting(String id) throws SQLException {
        Meeting meeting = meetingDAO.getMeetingById(id);

        List<Participant> participants = participantDAO.getParticipantsByMeetingId(id);
        meeting.setParticipants(participants);

        List<Attachment> attachments = attachmentDAO.getAttachmentsByMeetingId(id);
        meeting.setAttachments(attachments);

        List<Calendar> calendars = calendarDAO.getCalendarsByMeetingId(id);
        meeting.setCalendars(calendars);

        return meeting;
    }

    public List<Meeting> getAllMeetings() throws SQLException {
        return meetingDAO.getAllMeetings();
    }

    public void updateMeeting(Meeting meeting) throws SQLException {
        meetingDAO.updateMeeting(meeting);
    }

    public void deleteMeeting(Meeting meeting) throws SQLException {
        meetingDAO.deleteMeeting(meeting.getMeetingId().toString());
    }

    public void addParticipantToMeeting(Meeting meeting, Participant participant) throws SQLException {
        Participant p = participantDAO.getParticipantById(participant.getParticipantId().toString());

        if (p == null) {
            throw new SQLException("Participant with id " + participant.getParticipantId() + " was not found!");
        }

        Meeting m = meetingDAO.getMeetingById(meeting.getMeetingId().toString());

        if (m == null) {
            throw new SQLException("Meeting with id " + meeting.getMeetingId() + " was not found!");
        }

        meetingDAO.addParticipantToMeeting(meeting.getMeetingId().toString(), participant.getParticipantId().toString());
    }

    public void removeParticipantFromMeeting(Meeting meeting, Participant participant) throws SQLException {
        Meeting m = meetingDAO.getMeetingById(meeting.getMeetingId().toString());

        if (m == null) {
            throw new SQLException("Meeting with id " + meeting.getMeetingId() + " was not found!");
        }

        meetingDAO.removeParticipantFromMeeting(meeting.getMeetingId().toString(), participant.getParticipantId().toString());
    }
}
