package com.traveloka.calendar.employee.controller;

import com.traveloka.calendar.employee.Employee;
import com.traveloka.calendar.employee.request.dto.MeetingStatusChangeDTO;
import com.traveloka.calendar.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/api/v1/employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.add(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
    }

    @PostMapping("/api/v1/employee/{employeeId}")
    public ResponseEntity<Employee> addEmployee(@PathVariable String employeeId,
                                                @RequestBody List<MeetingStatusChangeDTO> meetingStatusList) {
        Employee savedEmployee = employeeService.updateMeetingStatus(employeeId, meetingStatusList);
        return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
    }
}
