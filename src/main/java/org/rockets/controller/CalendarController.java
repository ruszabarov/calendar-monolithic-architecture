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
    MeetingDAO meetingDAO;

    public CalendarController(String dbUrl) {
        try {
            calendarDAO = new CalendarDAO(dbUrl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createCalendarWithMeetingIds(Calendar calendar, List<String> meetingIds) {
        try {
            calendarDAO.createCalendar(calendar);
            for (String meetingId : meetingIds) {

                Meeting meeting = meetingDAO.getMeetingById(meetingId);

                if (meeting != null) {
                    calendarDAO.addMeetingToCalendar(calendar.getCalendarId().toString(), meetingId);
                } else {
                    System.err.println("Meeting with id " + meetingId + " does not exist!");
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Calendar getCalendarById(UUID id) {
        try {
            return calendarDAO.getCalendarById(id.toString());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public Calendar updateCalendar(Calendar calendar) {
        try {
            calendarDAO.updateCalendar(calendar);
            return calendarDAO.getCalendarById(calendar.getCalendarId().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
