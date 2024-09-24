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
        } catch (SQLException | ClassNotFoundException e) {
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

    public List<Calendar> getAllCalendars() {
        try {
            return calendarDAO.getAllCalendars();
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
            System.err.println(e.getMessage());
        }

        return calendar;
    }

    public void deleteCalendar(Calendar calendar) {
        try {
            calendarDAO.deleteCalendar(calendar.getCalendarId().toString());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Calendar addMeetingToCalendar(Calendar calendar, Meeting meeting) {
        try {
            Meeting m = meetingDAO.getMeetingById(meeting.getMeetingId().toString());

            if (m == null) {
                System.err.println("No meeting with id " + meeting.getMeetingId() + " was found!");
                return calendar;
            }

            calendarDAO.addMeetingToCalendar(meeting.getMeetingId().toString(), calendar.getCalendarId().toString());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return calendar;
    }

    public Calendar removeMeetingFromCalendar(Calendar calendar, Meeting meeting) {
        try {
            Calendar c = calendarDAO.getCalendarById(calendar.getCalendarId().toString());

            if (c == null) {
                System.err.println("Calendar with id " + calendar.getCalendarId() + " was not found!");
                return calendar;
            }

            calendarDAO.removeMeetingFromCalendar(meeting.getMeetingId().toString(), calendar.getCalendarId().toString());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return calendar;
    }
}
