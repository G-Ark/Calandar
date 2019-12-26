package com.traveloka.calendar.meeting.controller;

import com.traveloka.calendar.meeting.Meeting;
import com.traveloka.calendar.meeting.response.dto.MeetingResponseDTO;
import com.traveloka.calendar.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @PostMapping("/api/v1/meeting")
    public ResponseEntity<Meeting> addMeeting(@RequestBody Meeting meeting) {
        Meeting savedMeeting = meetingService.add(meeting);
        return new ResponseEntity<>(savedMeeting, HttpStatus.OK);
    }

    @GetMapping("/api/v1/meeting")
    public ResponseEntity<MeetingResponseDTO> addMeeting(@RequestParam String employeeId) {
        MeetingResponseDTO meetingsList = meetingService.findMeetingsForEmployee(employeeId);
        return new ResponseEntity<>(meetingsList, HttpStatus.OK);
    }

}
