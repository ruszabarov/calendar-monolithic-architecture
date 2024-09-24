package org.rockets.cli.commands;

import org.rockets.cli.common.HelpOption;
import org.rockets.components.*;
import org.rockets.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;

import java.util.UUID;

@Command(
        name = "delete",
        description = "Deletes a record by UUID",
        subcommands = {
                DeleteCommand.DeleteMeetingCommand.class,
                DeleteCommand.DeleteCalendarCommand.class,
                DeleteCommand.DeleteParticipantCommand.class,
                DeleteCommand.DeleteAttachmentCommand.class
        }
)
public class DeleteCommand implements Runnable {

    private Logger logger = LoggerFactory.getLogger(DeleteCommand.class);

    @Mixin
    private HelpOption helpOption;

    @Override
    public void run() {
        logger.info("Use one of the subcommands to delete a specific record type (meeting, calendar, participant, attachment).");
    }

    // Subcommand for deleting meetings
    @Command(name = "meeting", description = "Delete a meeting by its UUID")
    public static class DeleteMeetingCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(DeleteMeetingCommand.class);

        @Option(names = "--meetingId", description = "UUID of the meeting to delete", required = true)
        private String meetingId;

        @Override
        public void run() {
            try {
                MeetingController mtgController = new MeetingController("jdbc:sqlite:calendar.db");
                mtgController.deleteMeeting(meetingId);
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
            logger.info("Deleting meeting with UUID = " + meetingId);
        }
    }

    // Subcommand for deleting calendars
    @Command(name = "calendar", description = "Delete a calendar by its UUID")
    public static class DeleteCalendarCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(DeleteCalendarCommand.class);

        @Option(names = "--calendarId", description = "UUID of the calendar to delete", required = true)
        private String calendarId;

        @Override
        public void run() {
            try {
                CalendarController calendarController = new CalendarController("jdbc:sqlite:calendar.db");
                calendarController.deleteCalendar(calendarId);
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
            logger.info("Deleting calendar with UUID = " + calendarId);
        }
    }

    // Subcommand for deleting participants
    @Command(name = "participant", description = "Delete a participant by its UUID")
    public static class DeleteParticipantCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(DeleteParticipantCommand.class);

        @Option(names = "--participantId", description = "UUID of the participant to delete", required = true)
        private String participantId;

        @Override
        public void run() {
            try {
                ParticipantController participantController = new Participant("jdbc:sqlite:calendar.db");
                participantController.deleteParticipant(participantId);
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
            logger.info("Deleting participant with UUID = " + participantId);
        }
    }

    // Subcommand for deleting attachments
    @Command(name = "attachment", description = "Delete an attachment by its UUID")
    public static class DeleteAttachmentCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(DeleteAttachmentCommand.class);

        @Option(names = "--attachmentId", description = "UUID of the attachment to delete", required = true)
        private String attachmentId;

        @Override
        public void run() {
            try {
                AttachmentController attachmentController = new AttachmentController("jdbc:sqlite:calendar.db");
                attachmentController.deleteParticipant(attachmentId);
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
            logger.info("Deleting attachment with UUID = " + attachmentId);
        }
    }
}

