package org.rockets.cli.commands;

import org.rockets.cli.common.HelpOption;
import org.rockets.components.*;
import org.rockets.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;

import java.util.List;

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
                MeetingController mtgController = new MeetingController("jdbc:sqlite:calendar.db");
                if (meetingId != null) {
                    Meeting meeting = mtgController.getMeetingById(meetingId);
                    logger.info("Listing details for meeting with UUID = " + meetingId);
                    logger.info(meeting.toString());
                } else {
                    List<Meeting> meetings = mtgController.getAllMeetings();
                    logger.info("Listing all meetings.");
                    for (Meeting meeting : meetings) {
                        logger.info(meeting.toString());
                    }
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
                CalendarController calendarController = new CalendarController("jdbc:sqlite:calendar.db");
                if (calendarId != null) {
                    Calendar calendar = calendarController.getCalendarById(calendarId);
                    logger.info("Listing details for calendar with UUID = " + calendarId);
                    logger.info(calendar.toString());
                } else {
                    List<Calendar> calendars = calendarController.getAllCalendars();
                    logger.info("Listing all calendar.");
                    for (Calendar calendar : calendars) {
                        logger.info(calendar.toString());
                    }
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
            try {
                ParticipantController participantController = new ParticipantController("jdbc:sqlite:calendar.db");
                if (participantId != null) {
                    Participant participant = participantController.getParticipantById(participantId);
                    logger.info("Listing details for participant with UUID = " + participantId);
                    logger.info(participant.toString());
                } else {
                    List<Participant> participants = participantController.getAllParticipants();
                    logger.info("Listing all participants.");
                    for (Participant participant : participants) {
                        logger.info(participant.toString());
                    }
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
            try {
                AttachmentController attachmentController = new AttachmentController("jdbc:sqlite:calendar.db");
                if (attachmentId != null) {
                    Attachment attachment = attachmentController.getAttachmentById(attachmentId);
                    logger.info("Listing details for attachment with UUID = " + attachmentId);
                    logger.info(attachment.toString());
                } else {
                    List<Attachment> attachments = attachmentController.getAllAttachments();
                    logger.info("Listing all attachments.");
                    for (Attachment attachment : attachments) {
                        logger.info(attachment.toString());
                    }
                }
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }

        }
    }
}
