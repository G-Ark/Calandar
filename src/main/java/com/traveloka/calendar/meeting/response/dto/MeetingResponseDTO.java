package com.traveloka.calendar.meeting.response.dto;

import com.traveloka.calendar.meeting.Meeting;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MeetingResponseDTO {
    private List<Meeting> invitedMeetings = new ArrayList<>();

    private List<Meeting> acceptedMeetings = new ArrayList<>();

    private List<Meeting> pastMeetings = new ArrayList<>();
}
