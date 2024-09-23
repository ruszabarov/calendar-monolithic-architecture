package org.rockets.cli.commands;

import org.rockets.cli.common.HelpOption;
import org.rockets.components.*;
import org.rockets.dbmanager.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;

@Command(
        name = "list",
        description = "Lists records for different types (Meetings, Calendars, Participants, Attachments)",
        subcommands = {
                ListCommand.ListMeetingCommand.class,
                ListCommand.ListCalendarCommand.class,
                ListCommand.ListParticipantCommand.class,
                ListCommand.ListAttachmentCommand.class
        }
)
public class ListCommand implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ListCommand.class);

    @Mixin
    private HelpOption helpOption;

    @Override
    public void run() {
        logger.info("Use one of the subcommands to list records for a specific type.");
    }

    // Subcommand for listing meetings
    @Command(name = "meeting", description = "List meetings, optionally filtered by UUID")
    public static class ListMeetingCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(ListMeetingCommand.class);

        @Option(names = "--meetingId", description = "Optional UUID of the meeting to filter")
        private String meetingId;

        @Override
        public void run() {
            try {
                MeetingDAO mtgDAO = new MeetingDAO("jdbc:sqlite:calendar.db");
                if (meetingId != null) {
                    mtgDAO.getMeetingById(meetingId);
                    logger.info("Listing details for meeting with UUID = " + meetingId);
                } else {
                    mtgDAO.getAllMeetings();
                    logger.info("Listing all meetings.");
                }
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }

    // Subcommand for listing calendars
    @Command(name = "calendar", description = "List calendars, optionally filtered by UUID")
    public static class ListCalendarCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(ListCalendarCommand.class);

        @Option(names = "--calendarId", description = "Optional UUID of the calendar to filter")
        private String calendarId;

        @Override
        public void run() {
            try {
                CalendarDAO calendarDAO = new CalendarDAO("jdbc:sqlite:calendar.db");
                if (calendarId != null) {
                    calendarDAO.getCalendarById(calendarId);
                    logger.info("Listing details for calendar with UUID = " + calendarId);
                } else {
                    calendarDAO.getAllCalendars();
                    logger.info("Listing all calendar.");
                }
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }

    // Subcommand for listing participants
    @Command(name = "participant", description = "List participants, optionally filtered by UUID")
    public static class ListParticipantCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(ListParticipantCommand.class);

        @Option(names = "--participantId", description = "Optional UUID of the participant to filter")
        private String participantId;

        @Override
        public void run() {
            // TODO: Uncomment when ParticipantDAO is implemented
            try {
                //ParticipantDAO participantDAO = new ParticipantDAO("jdbc:sqlite:calendar.db");
                if (participantId != null) {
                    //participantDAO.getParticipantById(participantId);
                    logger.info("Listing details for participant with UUID = " + participantId);
                } else {
                    //participantDAO.getAllParticipants();
                    logger.info("Listing all participants.");
                }
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }

    // Subcommand for listing attachments
    @Command(name = "attachment", description = "List attachments, optionally filtered by UUID")
    public static class ListAttachmentCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(ListAttachmentCommand.class);

        @Option(names = "--attachmentId", description = "Optional UUID of the attachment to filter")
        private String attachmentId;

        @Override
        public void run() {
            // TODO: Uncomment when AttachmentDAO is implemented
            try {
                // AttachmentDAO attachmentDAO = new AttachmentDAO("jdbc:sqlite:calendar.db");
                if (attachmentId != null) {
                    // attachmentDAO.getAttachmentById(attachmentId);
                    logger.info("Listing details for attachment with UUID = " + attachmentId);
                } else {
                    // attachmentDAO.getAllAttachments();
                    logger.info("Listing all attachments.");
                }
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }

        }
    }
}
