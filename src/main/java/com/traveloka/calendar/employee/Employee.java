package com.traveloka.calendar.employee;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Employee {
    private String employeeName;

    private String employeeId;

    private List<String> pastMeetings = new ArrayList<>();

    private List<String> meetings = new ArrayList<>();

    private List<String> meetingInvites = new ArrayList<>();
}
