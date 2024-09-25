package org.rockets.cli.commands;

import org.rockets.cli.common.HelpOption;
import org.rockets.components.*;
import org.rockets.controller.AttachmentController;
import org.rockets.controller.CalendarController;
import org.rockets.controller.MeetingController;
import org.rockets.controller.ParticipantController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;

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

        @Option(names = "--id", description = "Meeting ID", required = true)
        private String id;

        @Option(names = "--title", description = "New meeting title")
        private String title;

        @Option(names = "--datetime", description = "New meeting date and time")
        private String dateTime;

        @Option(names = "--location", description = "New meeting location")
        private String location;

        @Option(names = "--details", description = "New meeting details")
        private String details;

        @Option(names = "--add-calendarId", description = "Add a Calendar ID to the meeting")
        private String addCalendarId;

        @Option(names = "--remove-calendarId", description = "Remove a Calendar ID from the meeting")
        private String removeCalendarId;

        @Option(names = "--add-participantId", description = "Add a Participant ID to the meeting")
        private String addParticipantId;

        @Option(names = "--remove-participantId", description = "Remove a Participant ID from the meeting")
        private String removeParticipantId;

        @Option(names = "--add-attachmentId", description = "Add an Attachment ID to the meeting")
        private String addAttachmentId;

        @Option(names = "--remove-attachmentId", description = "Remove an Attachment ID from the meeting")
        private String removeAttachmentId;

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
                MeetingController mtgController = new MeetingController();
                AttachmentController attachmentController = new AttachmentController();
                CalendarController calendarController = new CalendarController();

                Meeting meeting = mtgController.getMeetingById(id);

                if (title != null) {
                    title = Check.limitString(title, 2000);
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
                    Check.limitString(location, 2000);
                    meeting.setLocation(location);
                }
                if (details != null) {
                    Check.limitString(details, 10000);
                    meeting.setDetails(details);
                }
                if (addCalendarId != null) {
                    Calendar calendar = new Calendar(addCalendarId);
                    calendarController.addMeetingToCalendar(calendar, meeting);
                }
                if (removeCalendarId != null) {
                    Calendar calendar = new Calendar(removeCalendarId);
                    calendarController.removeMeetingFromCalendar(calendar, meeting);
                }
                if (addParticipantId != null) {
                    Participant participant = new Participant(addParticipantId);
                    mtgController.addParticipantToMeeting(meeting, participant);
                }
                if (removeParticipantId != null) {
                    Participant participant = new Participant(removeParticipantId);
                    mtgController.removeParticipantFromMeeting(meeting, participant);
                }
                if (addAttachmentId != null) {
                    Attachment attachment = new Attachment(addAttachmentId);
                    attachmentController.addAttachmentToMeeting(meeting, attachment);
                }
                if (removeAttachmentId != null) {
                    Attachment attachment = new Attachment(removeAttachmentId);
                    attachmentController.removeAttachmentFromMeeting(meeting, attachment);
                }

                mtgController.updateMeeting(meeting);
                System.out.println("Successfully updated meeting (" + id + ")");
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }

    // Subcommand for updating calendars
    @Command(name = "calendar", description = "Update calendar details")
    public static class UpdateCalendarCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(UpdateCalendarCommand.class);

        @Option(names = "--id", description = "Calendar ID", required = true)
        private String id;

        @Option(names = "--title", description = "New calendar title")
        private String title;

        @Option(names = "--details", description = "New calendar details")
        private String details;

        @Option(names = "--add-meetingId", description = "Add a Meeting ID to the calendar")
        private String addMeetingId;

        @Option(names = "--remove-meetingId", description = "Remove a Meeting ID from the calendar")
        private String removeMeetingId;

        @Override
        public void run() {
            // Check at least one option is provided
            try {
                CalendarController calendarController = new CalendarController();
                Calendar calendar = calendarController.getCalendarById(id);

                // Check if at least one update option is specified
                if (title == null && details == null && addMeetingId == null && removeMeetingId == null) {
                    logger.error("At least one update option must be specified.");
                    return;
                }

                // Apply updates to the calendar
                if (title != null) {
                    Check.limitString(title, 2000);
                    calendar.setTitle(title);
                }
                if (details != null) {
                    Check.limitString(details, 10000);
                    calendar.setDetails(details);
                }
                if (addMeetingId != null) {
                    Meeting meeting = new Meeting(addMeetingId);
                    calendarController.addMeetingToCalendar(calendar, meeting);
                }
                if (removeMeetingId != null) {
                    Meeting meeting = new Meeting(removeMeetingId);
                    calendarController.removeMeetingFromCalendar(calendar, meeting);
                }
                calendarController.updateCalendar(calendar);
                System.out.println("Successfully updated calendar (" + id + ")");

            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }

    // Subcommand for updating participants
    @Command(name = "participant", description = "Update participant details")
    public static class UpdateParticipantCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(UpdateParticipantCommand.class);

        @Option(names = "--id", description = "Participant ID", required = true)
        private String id;

        @Option(names = "--name", description = "New participant name")
        private String name;

        @Option(names = "--email", description = "New participant email")
        private String email;

        @Override
        public void run() {
            try {
                ParticipantController participantController = new ParticipantController();
                Participant participant = participantController.getParticipantById(id);

                if (name == null && email == null) {
                    logger.error("At least one update option must be specified.");
                    return;
                }
                if (name != null) {
                    Check.limitString(name, 600);
                    participant.setName(name);
                }
                if (email != null) {
                    if (!Check.isValidEmail(email)) {
                        logger.error("Invalid Email");
                        return;
                    }
                    participant.setEmail(email);
                }

                participantController.updateParticipant(participant);
                System.out.println("Successfully updated participant (" + id + ")");
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }

        }
    }

    // Subcommand for updating attachments
    @Command(name = "attachment", description = "Update attachment details")
    public static class UpdateAttachmentCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(UpdateAttachmentCommand.class);

        @Option(names = "--id", description = "Attachment ID", required = true)
        private String id;

        @Option(names = "--url", description = "New attachment URL")
        private String url;

        @Override
        public void run() {
            try {
                AttachmentController attachmentController = new AttachmentController();
                Attachment attachment = attachmentController.getAttachmentById(id);

                if (url == null) {
                    logger.error("At least one update option must be specified.");
                    return;
                }
                if (!Check.isValidURL(url)) {
                    logger.error("Invalid URL");
                    return;
                }

                attachment.setAttachmentUrl(url);

                attachmentController.updateAttachment(attachment);
                System.out.println("Successfully updated attachment (" + id + ")");
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
