package org.rockets.controller;

import org.rockets.components.Meeting;
import org.rockets.dbmanager.MeetingDAO;

import java.sql.SQLException;

public class MeetingController {
    private MeetingDAO meetingDAO;

    public MeetingController(String dbUrl) {
        try {
            this.meetingDAO = new MeetingDAO(dbUrl);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createMeeting(Meeting meeting) {
        try {
            meetingDAO.createMeeting(meeting);
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
}
