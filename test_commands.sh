#!/bin/bash

MEETING_ID1="123e4567-e89b-12d3-a456-426614174000"
MEETING_ID2="456e7890-e12b-34d5-a678-426614174001"  # New meeting UUID
CALENDAR_ID1="987e6543-e21b-12d3-a456-426614174002"
CALENDAR_ID2="789e0123-e34b-56d7-a890-426614174003"  # New calendar UUID
PARTICIPANT_ID1="456e7890-e12b-34d5-a678-426614174004"
PARTICIPANT_ID2="789e4567-e89b-12d3-a456-426614174005"  # New participant UUID
ATTACHMENT_ID1="789e0123-e34b-56d7-a890-426614174006"
ATTACHMENT_ID2="789e0123-e34b-56d7-a890-426614174007"  # New attachment UUID

# Function to create records
create_records() {
    # Creating the first participant
    echo "java -jar ./target/calendar.jar create participant \
                  --participantId "$PARTICIPANT_ID1" \
                  --name "Alice Smith" \
                  --email "alice@example.com""
    java -jar ./target/calendar.jar create participant \
        --participantId "$PARTICIPANT_ID1" \
        --name "Alice Smith" \
        --email "alice@example.com"

    # Creating the second participant
    echo "java -jar ./target/calendar.jar create participant \
                  --participantId "$PARTICIPANT_ID2" \
                  --name "Bob Johnson" \
                  --email "bob.johnson@example.com""
    java -jar ./target/calendar.jar create participant \
        --participantId "$PARTICIPANT_ID2" \
        --name "Bob Johnson" \
        --email "bob.johnson@example.com"

    # Creating the first meeting
    echo "java -jar ./target/calendar.jar create meeting \
                  --meetingId "$MEETING_ID1" \
                  --title "Project Kickoff" \
                  --datetime "2024-09-25 10:00" \
                  --location "Conference Room A" \
                  --details "Initial project discussion with stakeholders." \
                  --participantIds "$PARTICIPANT_ID1""

    java -jar ./target/calendar.jar create meeting \
        --meetingId "$MEETING_ID1" \
        --title "Project Kickoff" \
        --datetime "2024-09-25 10:00" \
        --location "Conference Room A" \
        --details "Initial project discussion with stakeholders." \
        --participantIds "$PARTICIPANT_ID1"  # Participant 1

    # Creating the second meeting
    echo "java -jar ./target/calendar.jar create meeting \
                  --meetingId "$MEETING_ID2" \
                  --title "Project Wrap-up" \
                  --datetime "2024-09-30 10:00" \
                  --location "Conference Room B" \
                  --details "Final discussion and review of project outcomes." \
                  --participantIds "$PARTICIPANT_ID2""

    java -jar ./target/calendar.jar create meeting \
        --meetingId "$MEETING_ID2" \
        --title "Project Wrap-up" \
        --datetime "2024-09-30 10:00" \
        --location "Conference Room B" \
        --details "Final discussion and review of project outcomes." \
        --participantIds "$PARTICIPANT_ID2"  # Participant 2

    # Creating the first attachment
    echo "java -jar ./target/calendar.jar create attachment \
                  --attachmentId "$ATTACHMENT_ID1" \
                  --url "http://example.com/document1.pdf"
                  --meetingId "$MEETING_ID1""

    java -jar ./target/calendar.jar create attachment \
        --attachmentId "$ATTACHMENT_ID1" \
        --url "http://example.com/document1.pdf" \
        --meetingIds "$MEETING_ID1"

    # Creating the second attachment
    echo "java -jar ./target/calendar.jar create attachment \
                  --attachmentId "$ATTACHMENT_ID2" \
                  --url "http://example.com/document2.pdf"
                  --meetingId "$MEETING_ID2""

    java -jar ./target/calendar.jar create attachment \
        --attachmentId "$ATTACHMENT_ID2" \
        --url "http://example.com/document2.pdf" \
        --meetingIds "$MEETING_ID2"

    # Creating the first calendar
    echo "java -jar ./target/calendar.jar create calendar \
                  --calendarId "$CALENDAR_ID1" \
                  --title "Work Calendar" \
                  --details "Calendar for work-related meetings." \
                  --meetingIds "$MEETING_ID1""

    java -jar ./target/calendar.jar create calendar \
        --calendarId "$CALENDAR_ID1" \
        --title "Work Calendar" \
        --details "Calendar for work-related meetings." \
        --meetingIds "$MEETING_ID1"

    # Creating the second calendar
    echo "java -jar ./target/calendar.jar create calendar \
                  --calendarId "$CALENDAR_ID2" \
                  --title "Personal Calendar" \
                  --details "Calendar for personal events." \
                  --meetingIds "$MEETING_ID2""

    java -jar ./target/calendar.jar create calendar \
        --calendarId "$CALENDAR_ID2" \
        --title "Personal Calendar" \
        --details "Calendar for personal events." \
        --meetingIds "$MEETING_ID2"  # Include Meeting 2
}


# Function to list records
list_records() {
    echo "java -jar ./target/calendar.jar list meeting"
    java -jar ./target/calendar.jar list meeting

    echo "java -jar ./target/calendar.jar list calendar"
    java -jar ./target/calendar.jar list calendar

    echo "java -jar ./target/calendar.jar list participant"
    java -jar ./target/calendar.jar list participant

    echo "java -jar ./target/calendar.jar list attachment"
    java -jar ./target/calendar.jar list attachment
}

# Function to update records
update_records1() {
    echo "java -jar ./target/calendar.jar update meeting \
                  --id $MEETING_ID1 \
                  --title "Project Kickoff - Rescheduled" \
                  --datetime "2024-09-26 11:00" \
                  --location "Conference Room B" \
                  --details "Rescheduled initial project discussion." \
                  --add-participantId "$PARTICIPANT_ID2" \
                  --add-attachmentId "$ATTACHMENT_ID2""

    java -jar ./target/calendar.jar update meeting \
        --id $MEETING_ID1 \
        --title "Project Kickoff - Rescheduled" \
        --datetime "2024-09-26 11:00" \
        --location "Conference Room B" \
        --details "Rescheduled initial project discussion." \
        --add-participantId "$PARTICIPANT_ID2" \
        --add-attachmentId "$ATTACHMENT_ID2" \

    echo "java -jar ./target/calendar.jar update calendar \
                  --id $CALENDAR_ID1 \
                  --title "Updated Work Calendar" \
                  --details "Updated calendar for work-related meetings." \
                  --add-meetingId "$MEETING_ID2""
    java -jar ./target/calendar.jar update calendar \
        --id $CALENDAR_ID1 \
        --title "Updated Work Calendar" \
        --details "Updated calendar for work-related meetings." \
        --add-meetingId "$MEETING_ID2"

    echo "java -jar ./target/calendar.jar update participant \
                  --id $PARTICIPANT_ID1 \
                  --name "Alice Johnson" \
                  --email "alice.johnson@example.com""
    java -jar ./target/calendar.jar update participant \
        --id $PARTICIPANT_ID1 \
        --name "Alice Johnson" \
        --email "alice.johnson@example.com"

    echo "java -jar ./target/calendar.jar update attachment \
                  --id $ATTACHMENT_ID1 \
                  --url "http://example.com/document2.pdf""
    java -jar ./target/calendar.jar update attachment \
        --id $ATTACHMENT_ID1 \
        --url "http://example.com/document2.pdf"
}

# Function to update records
update_records2() {
    echo "java -jar ./target/calendar.jar update meeting \
                  --id $MEETING_ID1 \
                  --title "Project Kickoff - Rescheduled" \
                  --datetime "1 PM" \
                  --location "Conference Room B" \
                  --details "Rescheduled initial project discussion." \
                  --remove-participantId "$PARTICIPANT_ID2" \
                  --remove-attachmentId "$ATTACHMENT_ID2""
    java -jar ./target/calendar.jar update meeting \
        --id $MEETING_ID1 \
        --title "Project Kickoff - Rescheduled" \
        --datetime "1 PM" \
        --location "Conference Room B" \
        --details "Rescheduled initial project discussion." \
        --remove-participantId "$PARTICIPANT_ID2" \
        --remove-attachmentId "$ATTACHMENT_ID2" \

    echo "java -jar ./target/calendar.jar update calendar \
                  --id $CALENDAR_ID1 \
                  --title "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit... et accumsan augue. In hac habitasse platea dictumst. Proin nec justo nec justo accumsan vehicula. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Donec eget massa nec elit efficitur dapibus vel ut arcu. Nullam ac risus eget nunc tincidunt mollis. Integer a massa vitae eros accumsan aliquam. Donec consequat ante sit amet nulla vehicula, et sollicitudin erat hendrerit. Curabitur non ante vel purus scelerisque ultricies. Nulla facilisi. Proin vitae quam ut lacus venenatis euismod. Maecenas vitae erat sed arcu volutpat tincidunt eu sit amet magna. Vivamus in volutpat mi, sed dictum nisl. Aliquam erat volutpat. Proin nec interdum nulla. Nunc vitae arcu lacinia, laoreet lacus id, tincidunt metus. Nam nec felis quam. Phasellus et tincidunt leo. Quisque et dolor nec nisl accumsan gravida. Etiam tincidunt ligula non efficitur scelerisque. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Morbi in libero nec purus consectetur varius. Sed faucibus, justo a interdum volutpat, dolor orci tincidunt augue, nec hendrerit nisi eros ut leo. In in arcu a libero aliquam fermentum. Vestibulum vehicula velit in vestibulum aliquam. Donec id magna magna. Aliquam erat volutpat. Sed ut erat purus. Intege" \
                  --details "Updated calendar for work-related meetings." \
                  --add-meetingId "$MEETING_ID2""
    java -jar ./target/calendar.jar update calendar \
        --id $CALENDAR_ID1 \
        --title "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit... et accumsan augue. In hac habitasse platea dictumst. Proin nec justo nec justo accumsan vehicula. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Donec eget massa nec elit efficitur dapibus vel ut arcu. Nullam ac risus eget nunc tincidunt mollis. Integer a massa vitae eros accumsan aliquam. Donec consequat ante sit amet nulla vehicula, et sollicitudin erat hendrerit. Curabitur non ante vel purus scelerisque ultricies. Nulla facilisi. Proin vitae quam ut lacus venenatis euismod. Maecenas vitae erat sed arcu volutpat tincidunt eu sit amet magna. Vivamus in volutpat mi, sed dictum nisl. Aliquam erat volutpat. Proin nec interdum nulla. Nunc vitae arcu lacinia, laoreet lacus id, tincidunt metus. Nam nec felis quam. Phasellus et tincidunt leo. Quisque et dolor nec nisl accumsan gravida. Etiam tincidunt ligula non efficitur scelerisque. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Morbi in libero nec purus consectetur varius. Sed faucibus, justo a interdum volutpat, dolor orci tincidunt augue, nec hendrerit nisi eros ut leo. In in arcu a libero aliquam fermentum. Vestibulum vehicula velit in vestibulum aliquam. Donec id magna magna. Aliquam erat volutpat. Sed ut erat purus. Intege" \
        --details "Updated calendar for work-related meetings." \
        --add-meetingId "$MEETING_ID2"

    echo "java -jar ./target/calendar.jar update participant \
                  --id $PARTICIPANT_ID1 \
                  --name "Alice Johnson" \
                  --email "alice.johnsonNoEmail""
    java -jar ./target/calendar.jar update participant \
        --id $PARTICIPANT_ID1 \
        --name "Alice Johnson" \
        --email "alice.johnsonNoEmail"

    echo "java -jar ./target/calendar.jar update attachment \
                  --id $ATTACHMENT_ID1 \
                  --url "example.com/document2.pdf""
    java -jar ./target/calendar.jar update attachment \
        --id $ATTACHMENT_ID1 \
        --url "example.com/document2.pdf"
}

# Function to delete records
delete_records() {
    echo "java -jar ./target/calendar.jar delete meeting --meetingId $MEETING_ID1"
    java -jar ./target/calendar.jar delete meeting --meetingId $MEETING_ID1

    echo "java -jar ./target/calendar.jar delete calendar --calendarId $CALENDAR_ID1"
    java -jar ./target/calendar.jar delete calendar --calendarId $CALENDAR_ID1

    echo "java -jar ./target/calendar.jar delete participant --participantId $PARTICIPANT_ID1"
    java -jar ./target/calendar.jar delete participant --participantId $PARTICIPANT_ID1

    echo "java -jar ./target/calendar.jar delete attachment --attachmentId $ATTACHMENT_ID1"
    java -jar ./target/calendar.jar delete attachment --attachmentId $ATTACHMENT_ID1
}

final_create() {
    echo "java -jar ./target/calendar.jar create participant --name "Teddy" --email "tpb51@fake.edu""
    java -jar ./target/calendar.jar create participant --name "Teddy" --email "tpb51@fake.edu"
}

# Main execution
create_records
list_records
update_records1
list_records
update_records2
delete_records
final_create

echo "All commands executed."

