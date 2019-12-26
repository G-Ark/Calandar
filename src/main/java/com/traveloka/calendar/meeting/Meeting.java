package com.traveloka.calendar.meeting;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Meeting implements Comparable<Meeting> {
    private String roomId;

    private Date timeStart;

    private Date timeEnd;

    private List<String> invitees = new ArrayList<>();

    private List<String> attendees = new ArrayList<>();

    private String meetingId;

    private boolean isLive;

    @Override
    public int compareTo(Meeting meeting) {
        return this.getTimeStart().equals(meeting.getTimeStart())? 0 :
                (this.getTimeStart().after(meeting.getTimeStart())? 1: -1);
    }
}
