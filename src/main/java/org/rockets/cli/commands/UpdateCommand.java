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
            // Check at least one option is provided
            if (title == null && dateTime == null && location == null && details == null &&
                    addCalendarId == null && removeCalendarId == null &&
                    addParticipantId == null && removeParticipantId == null &&
                    addAttachmentId == null && removeAttachmentId == null) {
                logger.error("At least one update option must be specified.");
                return;
            }
            try {
                MeetingController mtgController = new MeetingController("jdbc:sqlite:calendar.db");
                Meeting meeting = mtgController.getMeeting(id);
                if (title != null){
                    title = Check.limitString(title,2000);
                    meeting.setTitle(title);
                }
                if (dateTime != null) {
                    if (!Check.validateDateTime(dateTime)) {
                        logger.error("Invalid date format");
                        return;
                    }
                    meeting.setDateTime(dateTime);
                }
                if (location != null) {
                    Check.limitString(location,2000);
                    meeting.setLocation(location);
                }
                if (details != null) {
                    Check.limitString(details,10000);
                    meeting.setDetails(details);
                }
                if (addCalendarId != null) meeting.addCalendar(addCalendarId);
                if (removeCalendarId != null) meeting.removeCalendar(removeCalendarId);
                if (addParticipantId != null) {
                    Participant participant = new Participant(addParticipantId,null,null);
                    meeting.addParticipant(participant);
                }
                if (removeParticipantId != null) {
                    Participant participant = new Participant(removeParticipantId,null,null);
                    meeting.addParticipant(participant);
                    meeting.removeParticipant(participant);
                }
                if (addAttachmentId != null) {
                    Attachment attachment = new Attachment(addAttachmentId, null);
                    meeting.addAttachment(attachment);
                }
                if (removeAttachmentId != null) {
                    Attachment attachment = new Attachment(removeAttachmentId, null);
                    meeting.removeAttachment(attachment);
                }
                mtgController.updateMeeting(meeting);
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
            logger.info("Updating meeting with ID = " + id);
            // Implement update logic here
        }
    }

    // Subcommand for updating calendars
    @Command(name = "calendar", description = "Update calendar details")
    public static class UpdateCalendarCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(UpdateCalendarCommand.class);

        @Option(names = "-id", description = "Calendar ID", required = true)
        private UUID id;

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
            // Check at least one option is provided
            try {
                CalendarController calendarController = new CalendarController("jdbc:sqlite:calendar.db");
                Calendar calendar = calendarController.getCalendarById(id);

                // Check if at least one update option is specified
                if (title == null && details == null && addMeetingId == null && removeMeetingId == null) {
                    logger.error("At least one update option must be specified.");
                    return;
                }

                // Apply updates to the calendar
                if (title != null) {
                    Check.limitString(title,2000);
                    calendar.setTitle(title);
                }
                if (details != null) {
                    Check.limitString(details,10000);
                    calendar.setDetails(details);
                }
                if (addMeetingId != null) {
                    Meeting meeting = new Meeting(addMeetingId);
                    calendar.addMeeting(meeting);
                }
                if (removeMeetingId != null){
                    Meeting meeting = new Meeting(removeMeetingId);
                    calendar.removeMeeting(meeting);
                }
                logger.info("Updating calendar with ID = " + id);
                calendarController.updateCalendar(calendar);
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
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
            ParticipantController participantController = new ParticipantController("jdbc:sqlite:calendar.db");
            Participant participant = participantController.getParticipant(id);
            if (name == null && email == null ) {
                logger.error("At least one update option must be specified.");
                return;
            }
            if (name != null) {
                Check.limitString(name,600);
                participant.setName(name);
            }
            if (email != null) {
                if (!Check.isValidEmail(email)) {
                    logger.error("Invalid URL");
                    return;
                }
                participant.setEmail(email);
            }
            try {
                if (name != null) participant.setName(name);
                if (email != null) participant.setEmail(email);
                logger.info("Updating participant with ID = " + id);
                participantController.updateParticipant(participant);
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
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
            try {
                AttachmentController attachmentController = new AttachmentController("jdbc:sqlite:calendar.db");
                Attachment attachment = attachmentController.getAttachment(id);

                if (url == null) {
                    logger.error("At least one update option must be specified.");
                    return;
                }
                if (!Check.isValidURL(url)) {
                    logger.error("Invalid URL");
                    return;
                }
                attachment.setAttachmentUrl(url);
                logger.info("Updating attachment with ID = " + id);
                attachmentController.updateAttachment(attachment);
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
