package org.rockets.cli.commands;

import org.rockets.cli.common.HelpOption;
import org.rockets.components.*;
import org.rockets.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Mixin;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.rockets.dbmanager.*;

@Command(
        name = "create",
        description = "Create a new record",
        subcommands = {
                CreateCommand.CreateMeetingCommand.class,
                CreateCommand.CreateCalendarCommand.class,
                CreateCommand.CreateParticipantCommand.class,
                CreateCommand.CreateAttachmentCommand.class
        }
)
public class CreateCommand implements Runnable {

    private Logger logger = LoggerFactory.getLogger(CreateCommand.class);

    @Mixin
    private HelpOption helpOption;

    @Override
    public void run() {
        logger.info("Use one of the subcommands to create a specific record type (meeting, calendar, participant, attachment).");
    }

    // Subcommand for creating meetings
    @Command(name = "meeting", description = "Create a new meeting")
    public static class CreateMeetingCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(CreateMeetingCommand.class);

        @Option(names = "--meetingId", description = "UUID for the meeting (optional)")
        private UUID meetingId;

        @Option(names = "--title", description = "Title of the meeting (up to 2000 characters)", required = true)
        private String title;

        @Option(names = "--datetime", description = "Date and time of the meeting (YYYY-MM-DD HH:MM AM/PM)", required = true)
        private String dateTime;

        @Option(names = "--location", description = "Location of the meeting (up to 2000 characters)", required = true)
        private String location;

        @Option(names = "--details", description = "Details of the meeting (up to 10000 characters)", required = true)
        private String details;

        @Option(names = "--calendarIds", description = "List of calendar IDs associated with the meeting", split = ",")
        private List<String> calendarIds;

        @Option(names = "--participantIds", description = "List of participant IDs for the meeting", split = ",", required = true)
        private List<String> participantIds;

        @Option(names = "--attachmentIds", description = "List of attachment IDs for the meeting", split = ",")
        private List<String> attachmentIds;

        @Override
        public void run() {
            try {
                if (meetingId == null) meetingId = UUID.fromString(Check.generate(null).toString());
                title = Check.limitString(title,2000);
                if (!Check.validateDateTime(dateTime)) {
                    logger.error("Invalid Date Time: " + dateTime);
                    return;
                }
                location = Check.limitString(location,2000);
                details = Check.limitString(details,10000);

                Meeting meeting = new Meeting(meetingId, title, dateTime, location, details);
                MeetingController meetingController = new MeetingController("jdbc:sqlite:calendar.db");
                meetingController.createMeetingWithParticipants(meeting, participantIds);

                System.out.println("Successfully create a meeting (" + meeting.getMeetingId() + ")");


            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }

    // Subcommand for creating calendars
    @Command(name = "calendar", description = "Create a new calendar")
    public static class CreateCalendarCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(CreateCalendarCommand.class);

        @Option(names = "--calendarId", description = "UUID for the calendar (optional)")
        private UUID calendarId;

        @Option(names = "--title", description = "Title of the calendar (up to 2000 characters)", required = true)
        private String title;

        @Option(names = "--details", description = "Details of the calendar (up to 10000 characters)", required = true)
        private String details;

        @Option(names = "--meetingIds", description = "List of meeting IDs associated with the calendar", split = ",")
        private List<String> meetingIds;

        @Override
        public void run() {

            try {
                if (calendarId == null) calendarId = UUID.fromString(Check.generate(null).toString());
                title = Check.limitString(title,2000);
                details = Check.limitString(details,10000);
                if (meetingIds == null || meetingIds.isEmpty()) {
                    logger.error("Meeting IDs is empty");
                    return;
                }
                Calendar calendar = new Calendar(calendarId, title, details);
                CalendarController calendarController = new CalendarController("jdbc:sqlite:calendar.db");
                calendarController.createCalendarWithMeetingIds(calendar, meetingIds);

                System.out.println("Successfully create a calendar (" + calendar.getCalendarId() + ")");

            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }

    // Subcommand for creating participants
    @Command(name = "participant", description = "Create a new participant")
    public static class CreateParticipantCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(CreateParticipantCommand.class);

        @Option(names = "--participantId", description = "UUID for the participant (optional)")
        private UUID participantId;

        @Option(names = "--name", description = "Name of the participant (up to 600 characters)", required = true)
        private String name;

        @Option(names = "--email", description = "Email of the participant", required = true)
        private String email;

        @Override
        public void run() {
            if (participantId == null) participantId = UUID.fromString(Check.generate(null).toString());
            name = Check.limitString(name,600);
            if (!Check.isValidEmail(email)) {
                logger.error("Invalid: " + email);
                return;
            }
            try {
                Participant participant = new Participant(participantId, name, email);
                ParticipantController participantController = new ParticipantController("jdbc:sqlite:calendar.db");
                participantController.createParticipant(participant);

                System.out.println("Successfully created participant (" + participant.getParticipantId() + ")");
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }

    // Subcommand for creating attachments
    @Command(name = "attachment", description = "Create a new attachment")
    public static class CreateAttachmentCommand implements Runnable {
        private Logger logger = LoggerFactory.getLogger(CreateAttachmentCommand.class);

        @Option(names = "--attachmentId", description = "UUID for the attachment (optional)")
        private UUID attachmentId;

        @Option(names = "--meetingId", description = "Meeting ID associated with the attachment", required = true)
        private String meetingId;

        @Option(names = "--url", description = "URL of the attachment", required = true)
        private String url;

        @Override
        public void run() {
            if (attachmentId == null) attachmentId = UUID.fromString(Check.generate(null).toString());
            if (!Check.isValidURL(url)) {
                logger.error("Invalid URL: " + url);
                return;
            }
            try {
                Attachment attachment = new Attachment(attachmentId, url);
                AttachmentController attachmentController = new AttachmentController();
                attachmentController.createAttachment(attachment);

            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
            logger.info("Creating attachment with URL = " + url + ", meetingId = " + meetingId);
        }
    }
}
