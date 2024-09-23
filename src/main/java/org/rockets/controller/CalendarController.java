package org.rockets.controller;

import org.rockets.components.Calendar;
import org.rockets.components.Meeting;
import org.rockets.dbmanager.CalendarDAO;
import org.rockets.dbmanager.MeetingDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class CalendarController {
    CalendarDAO calendarDAO;

    public CalendarController(String dbUrl) {
        try {
            calendarDAO = new CalendarDAO(dbUrl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createCalendarWithMeetingIds(Calendar calendar) {
        try {
            calendarDAO.createCalendar(calendar);
            for (UUID meetingId : calendar.getMeetingIds()) {
                calendarDAO.addMeetingToCalendar(calendar.getCalendarId().toString(), meetingId.toString());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
