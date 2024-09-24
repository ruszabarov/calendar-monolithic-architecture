#!/bin/bash

# Sample UUIDs (replace with the actual generated UUIDs)
MEETING_ID="123e4567-e89b-12d3-a456-426614174000"
CALENDAR_ID="987e6543-e21b-12d3-a456-426614174001"
PARTICIPANT_ID="456e7890-e12b-34d5-a678-426614174002"
ATTACHMENT_ID="789e0123-e34b-56d7-a890-426614174003"

# Function to create records
create_records() {
    echo "Creating Participant..."
        java -jar ./target/calendar.jar create participant \
            --participantId $PARTICIPANT_ID \
            --name "Alice Smith" \
            --email "alice@example.com"

    echo "Creating Meeting..."
    java -jar ./target/calendar.jar create meeting \
        --meetingId $MEETING_ID \
        --title "Project Kickoff" \
        --datetime "2024-09-25 10:00" \
        --location "Conference Room A" \
        --details "Initial project discussion with stakeholders." \
        --participantIds "$PARTICIPANT_ID"

    echo "Creating Calendar..."
    java -jar ./target/calendar.jar create calendar \
        --calendarId $CALENDAR_ID \
        --title "Work Calendar" \
        --details "Calendar for work-related meetings." \
        --meetingIds "$MEETING_ID"

    echo "Creating Attachment..."
    java -jar ./target/calendar.jar create attachment \
        --attachmentId $ATTACHMENT_ID \
        --url "http://example.com/document.pdf" \
        --meetingIds "$MEETING_ID"
}

# Function to list records
list_records() {
    echo "Listing Meetings..."
    java -jar ./target/calendar.jar list meeting

    echo "Listing Calendars..."
    java -jar ./target/calendar.jar list calendar

    echo "Listing Participants..."
    java -jar ./target/calendar.jar list participant

    echo "Listing Attachments..."
    java -jar ./target/calendar.jar list attachment
}

# Function to update records
update_records() {
    echo "Updating Meeting..."
    java -jar ./target/calendar.jar update meeting \
        --id $MEETING_ID \
        --title "Project Kickoff - Rescheduled" \
        --datetime "2024-09-26 11:00" \
        --location "Conference Room B" \
        --details "Rescheduled initial project discussion." \
        --add-participantId "$PARTICIPANT_ID" \
        --add-attachmentId "$ATTACHMENT_ID" \
        --add-calendarId "$CALENDAR_ID"

    echo "Updating Calendar..."
    java -jar ./target/calendar.jar update calendar \
        --id $CALENDAR_ID \
        --title "Updated Work Calendar" \
        --details "Updated calendar for work-related meetings." \
        --add-meetingId "$MEETING_ID"

    echo "Updating Participant..."
    java -jar ./target/calendar.jar update participant \
        --id $PARTICIPANT_ID \
        --name "Alice Johnson" \
        --email "alice.johnson@example.com"

    echo "Updating Attachment..."
    java -jar ./target/calendar.jar update attachment \
        --id $ATTACHMENT_ID \
        --url "http://example.com/document2.pdf"
}

# Function to delete records
delete_records() {
    echo "Deleting Meeting..."
    java -jar ./target/calendar.jar delete meeting --meetingId $MEETING_ID

    echo "Deleting Calendar..."
    java -jar ./target/calendar.jar delete calendar --calendarId $CALENDAR_ID

    echo "Deleting Participant..."
    java -jar ./target/calendar.jar delete participant --participantId $PARTICIPANT_ID

    echo "Deleting Attachment..."
    java -jar ./target/calendar.jar delete attachment --attachmentId $ATTACHMENT_ID
}

# Main execution
create_records
list_records
update_records
list_records
delete_records

echo "All commands executed."

