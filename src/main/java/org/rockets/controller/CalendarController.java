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
            calendarDAO = new CalendarDAO();
            meetingDAO = new MeetingDAO();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createCalendarWithMeetingIds(Calendar calendar, List<String> meetingIds) throws SQLException {
        calendarDAO.createCalendar(calendar);

        for (String meetingId : meetingIds) {
            Meeting meeting = meetingDAO.getMeetingById(meetingId);

            if (meeting == null) {
                throw new SQLException("Meeting with id " + meetingId + " does not exist!");
            }

            calendarDAO.addMeetingToCalendar(meetingId, calendar.getCalendarId().toString());
        }

    }

    public Calendar getCalendarById(UUID id) throws SQLException {
        Calendar calendar = calendarDAO.getCalendarById(id.toString());

        if (calendar == null) {
            throw new SQLException("Calendar with id " + id + " does not exist");
        }

        calendar.setMeetings(meetingDAO.getMeetingsByCalendarId(id.toString()));

        return calendar;
    }

    public List<Calendar> getAllCalendars() throws SQLException {
        return calendarDAO.getAllCalendars();
    }

    public void updateCalendar(Calendar calendar) throws SQLException {
        calendarDAO.updateCalendar(calendar);
    }

    public void deleteCalendar(Calendar calendar) throws SQLException {
        calendarDAO.deleteCalendar(calendar.getCalendarId().toString());
    }

    public void addMeetingToCalendar(Calendar calendar, Meeting meeting) throws SQLException {
        Calendar c = calendarDAO.getCalendarById(calendar.getCalendarId().toString());

        if (c == null) {
            throw new SQLException("No calendar with id " + calendar.getCalendarId() + " was found!");
        }

        Meeting m = meetingDAO.getMeetingById(meeting.getMeetingId().toString());

        if (m == null) {
            throw new SQLException("No meeting with id " + meeting.getMeetingId() + " was found!");
        }

        calendarDAO.addMeetingToCalendar(meeting.getMeetingId().toString(), calendar.getCalendarId().toString());
    }

    public void removeMeetingFromCalendar(Calendar calendar, Meeting meeting) throws SQLException {
        Calendar c = calendarDAO.getCalendarById(calendar.getCalendarId().toString());

        if (c == null) {
            throw new SQLException("Calendar with id " + calendar.getCalendarId() + " was not found!");
        }

        calendarDAO.removeMeetingFromCalendar(meeting.getMeetingId().toString(), calendar.getCalendarId().toString());

    }
}
