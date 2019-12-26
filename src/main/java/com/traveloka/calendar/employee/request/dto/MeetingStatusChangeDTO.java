package com.traveloka.calendar.employee.request.dto;

import lombok.Data;

@Data
public class MeetingStatusChangeDTO {
    private String meetingId;

    private Integer status;
    // 0 - Accepted
    // 1 - Rejected - No support yet
    // Ideally to be placed in an Enum
}
