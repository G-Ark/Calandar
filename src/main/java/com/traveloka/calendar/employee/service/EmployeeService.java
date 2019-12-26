package com.traveloka.calendar.employee.service;

import com.traveloka.calendar.CalendarException;
import com.traveloka.calendar.employee.Employee;
import com.traveloka.calendar.employee.request.dto.MeetingStatusChangeDTO;
import com.traveloka.calendar.meeting.Meeting;
import com.traveloka.calendar.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private List<Employee> storedEmployeeList = new ArrayList<>();
    private Map<String, Employee> employeeIdEmployeeMap = new HashMap<>();

    @Autowired
    private MeetingService meetingService;

    public Employee add(Employee employee) {
        if (!isEmployeeValid(employee)) {
            throw new CalendarException("Employee data is not valid");
        }
        storedEmployeeList.add(employee);
        employeeIdEmployeeMap = storedEmployeeList.stream()
                .collect(Collectors.toMap(Employee::getEmployeeId, Function.identity()));
        return employee;
    }

    private boolean isEmployeeValid(Employee employee) {
        List<String> employeeIdList = storedEmployeeList.stream()
                .map(Employee::getEmployeeId)
                .collect(Collectors.toList());
        if (StringUtils.isEmpty(employee.getEmployeeId())) {
            employee.setEmployeeId(UUID.randomUUID().toString());
        }
        if (employeeIdList.contains(employee.getEmployeeId())) {
            return false;
        }
        if (StringUtils.isEmpty(employee.getEmployeeName())) {
            return false;
        }
        return true;
    }

    public void addMeetingInvite(String invitee, String meetingId) {
        Employee invitedEmployee = get(invitee);
        if (invitedEmployee != null) {
            invitedEmployee.getMeetingInvites().add(meetingId);
        }
    }

    public Employee get(String employeeId) {
        return employeeIdEmployeeMap.get(employeeId);
    }

    public Employee updateMeetingStatus(String employeeId,
                                        List<MeetingStatusChangeDTO> meetingStatusList) {
        Employee employee = get(employeeId);
        if (employee == null) {
            throw new CalendarException("No employee found with given employeeId");
        }
        for (MeetingStatusChangeDTO meetingStatusChangeDTO: meetingStatusList) {
            Meeting meeting = meetingService.get(meetingStatusChangeDTO.getMeetingId());
            if (meeting == null) {
                throw new CalendarException("No such meetingId found");
            }
            meeting.getInvitees().remove(employeeId);
            meeting.getAttendees().add(employeeId);
            employee.getMeetingInvites().remove(meetingStatusChangeDTO.getMeetingId());
            employee.getMeetings().add(meetingStatusChangeDTO.getMeetingId());
        }
        return employee;
    }
}
