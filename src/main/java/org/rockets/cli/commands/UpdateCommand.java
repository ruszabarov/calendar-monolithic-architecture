package org.rockets.cli.commands;

import org.rockets.cli.common.HelpOption;
import org.rockets.components.*;
import org.rockets.controller.CalendarController;
import org.rockets.controller.MeetingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;

import java.util.UUID;

@Command(
        name = "update",
        description = "Updates records based on ID",
        subcommands = {
                UpdateCommand.UpdateMeetingCommand.class,
                UpdateCommand.UpdateCalendarCommand.class,
                UpdateCommand.UpdateParticipantCommand.class,
                UpdateCommand.UpdateAttachmentCommand.class
        }
)
public class UpdateCommand implements Runnable {

    private Logger logger = LoggerFactory.getLogger(UpdateCommand.class);

    @Mixin
    private HelpOption helpOption;

    @Override
    public void run() {
        logger.info("Use one of the subcommands to update a specific type of record.");
    }

    // Subcommand for updating meetings
    @Command(name = "meeting", description = "Update meeting details")
    public static class UpdateMeetingCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(UpdateMeetingCommand.class);

        @Option(names = "-id", description = "Meeting ID", required = true)
        private String id;

        @Option(names = "-title", description = "New meeting title")
        private String title;

        @Option(names = "-datetime", description = "New meeting date and time")
        private String dateTime;

        @Option(names = "-location", description = "New meeting location")
        private String location;

        @Option(names = "-details", description = "New meeting details")
        private String details;

        @Option(names = "--add-calendarId", description = "Add a Calendar ID to the meeting")
        private UUID addCalendarId;

        @Option(names = "--remove-calendarId", description = "Remove a Calendar ID from the meeting")
        private UUID removeCalendarId;

        @Option(names = "--add-participantId", description = "Add a Participant ID to the meeting")
        private UUID addParticipantId;

        @Option(names = "--remove-participantId", description = "Remove a Participant ID from the meeting")
        private UUID removeParticipantId;

        @Option(names = "--add-attachmentId", description = "Add an Attachment ID to the meeting")
        private UUID addAttachmentId;

        @Option(names = "--remove-attachmentId", description = "Remove an Attachment ID from the meeting")
        private UUID removeAttachmentId;

        @Override
        public void run() {
            // TODO: Add validations from Check.java
            // Check at least one option is provided
            if (title == null && dateTime == null && location == null && details == null &&
                    addCalendarId == null && removeCalendarId == null &&
                    addParticipantId == null && removeParticipantId == null &&
                    addAttachmentId == null && removeAttachmentId == null) {
                logger.error("At least one update option must be specified.");
                return;
            }

            MeetingController meetingController = new MeetingController("jdbc:sqlite:calendar.db");

            Meeting meeting = new Meeting(UUID.fromString(id), title, dateTime, location, details);

            meetingController.updateMeeting(meeting);

            logger.info("Updating meeting with ID = " + id);
            // Implement update logic here
        }
    }

    // Subcommand for updating calendars
    @Command(name = "calendar", description = "Update calendar details")
    public static class UpdateCalendarCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(UpdateCalendarCommand.class);

        @Option(names = "-id", description = "Calendar ID", required = true)
        private String id;

        @Option(names = "-title", description = "New calendar title")
        private String title;

        @Option(names = "-details", description = "New calendar details")
        private String details;

        @Option(names = "--add-meetingId", description = "Add a Meeting ID to the calendar")
        private UUID addMeetingId;

        @Option(names = "--remove-meetingId", description = "Remove a Meeting ID from the calendar")
        private UUID removeMeetingId;

        @Override
        public void run() {
            // TODO: Add validations from Check.java
            // Check at least one option is provided
            try {
                // Check if at least one update option is specified
                if (title == null && details == null && addMeetingId == null && removeMeetingId == null) {
                    logger.error("At least one update option must be specified.");
                    return;
                }

                CalendarController calendarController = new CalendarController("jdbc:sqlite:calendar.db");
                Calendar calendar = new Calendar(UUID.fromString(id), title, details);

                calendarController.updateCalendar(calendar);
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }


            logger.info("Updating calendar with ID = " + id);
            // Implement update logic here
        }
    }

    // Subcommand for updating participants
    @Command(name = "participant", description = "Update participant details")
    public static class UpdateParticipantCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(UpdateParticipantCommand.class);

        @Option(names = "-id", description = "Participant ID", required = true)
        private String id;

        @Option(names = "-name", description = "New participant name")
        private String name;

        @Option(names = "-email", description = "New participant email")
        private String email;

        @Override
        public void run() {
            // TODO: Add validations from Check.java
            // TODO: Uncomment when implemented
            try {
                //ParticipantDAO participantDAO = new ParticipantDAO("jdbc:sqlite:calendar.db");
                //Participant participant = participantDAO.getParticipantById(id);

                //if (name != null) participant.setName(name);
                //if (email != null) participant.setEmail(email);

                //participantDAO.updateParticipant(participant);
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }

            logger.info("Updating participant with ID = " + id);
            // Implement update logic here
        }
    }

    // Subcommand for updating attachments
    @Command(name = "attachment", description = "Update attachment details")
    public static class UpdateAttachmentCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(UpdateAttachmentCommand.class);

        @Option(names = "-id", description = "Attachment ID", required = true)
        private String id;

        @Option(names = "-url", description = "New attachment URL")
        private String url;

        @Override
        public void run() {
            // TODO: Add validations from Check.java
            // TODO: Uncomment when implemented
            if (url == null) {
                logger.error("At least one update option must be specified.");
                return;
            }

            try {
                //AttachmentDAO attachmentDAO = new AttachmentDAO("jdbc:sqlite:calendar.db");
                //Attachment attachment = attachmentDAO.getAttachmentById(id);

                //if (url != null) attachment.setUrl(url);

                //attachmentDAO.updateAttachment(attachment);
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }

            logger.info("Updating attachment with ID = " + id);
            // Implement update logic here
        }
    }
}
