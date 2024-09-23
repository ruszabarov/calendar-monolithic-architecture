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
            throw new RuntimeException(e);
        }
    }
}
