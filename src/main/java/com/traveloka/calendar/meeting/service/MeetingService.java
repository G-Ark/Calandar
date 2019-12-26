package com.traveloka.calendar.meeting.service;

import com.traveloka.calendar.CalendarException;
import com.traveloka.calendar.employee.Employee;
import com.traveloka.calendar.employee.service.EmployeeService;
import com.traveloka.calendar.meeting.Meeting;
import com.traveloka.calendar.meeting.response.dto.MeetingResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MeetingService {
    private List<Meeting> meetingList = new ArrayList<>();

    private Map<String, Meeting> meetingIdMeetingMapping = new HashMap<>();

    @Autowired
    private EmployeeService employeeService;

    public Meeting add(Meeting meeting) {
        if (!isValidMeeting(meeting)) {
            throw new CalendarException("Meeting is not valid");
        }
        meetingList.add(meeting);
        meetingIdMeetingMapping = meetingList.stream()
                .collect(Collectors.toMap(Meeting::getMeetingId, Function.identity()));
        for (String invitee :meeting.getInvitees()) {
            employeeService.addMeetingInvite(invitee, meeting.getMeetingId());
        }
        // TODO: Add this to all the employees as well.
        return meeting;
    }

    private boolean isValidMeeting(Meeting meeting) {
        if (CollectionUtils.isEmpty(meeting.getInvitees())) {
            return false;
        }
        if (StringUtils.isEmpty(meeting.getMeetingId())) {
            meeting.setMeetingId(UUID.randomUUID().toString());
        }
        return meeting.getTimeStart() != null && meeting.getTimeEnd() != null;
    }

    public MeetingResponseDTO findMeetingsForEmployee(String employeeId) {
        Employee employee = employeeService.get(employeeId);
        MeetingResponseDTO meetingResponseDTO = new MeetingResponseDTO();
        for (String meetingId: employee.getMeetingInvites()) {
            Meeting meeting = get(meetingId);
            if (meeting !=null) {
                if (meeting.getTimeEnd().after(new Date())) {
                    meetingResponseDTO.getInvitedMeetings().add(meeting);
                } else {
                    meetingResponseDTO.getPastMeetings().add(meeting);
                }
            }
        }
        for (String meetingId: employee.getMeetings()) {
            Meeting meeting = get(meetingId);
            if (meeting != null) {
                if (meeting.getTimeEnd().after(new Date())) {
                    meetingResponseDTO.getAcceptedMeetings().add(meeting);
                } else {
                    meetingResponseDTO.getPastMeetings().add(meeting);
                }
            }
        }
        Collections.sort(meetingResponseDTO.getInvitedMeetings());
        Collections.sort(meetingResponseDTO.getPastMeetings());
        Collections.sort(meetingResponseDTO.getAcceptedMeetings());
        return meetingResponseDTO;
    }

    public Meeting get(String meetingId) {
        return meetingIdMeetingMapping.get(meetingId);
    }
}
