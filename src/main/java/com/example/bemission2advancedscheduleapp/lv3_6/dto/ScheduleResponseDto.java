package com.example.bemission2advancedscheduleapp.lv3_6.dto;

import com.example.bemission2advancedscheduleapp.lv3_6.entitiy.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String todo;
    private String writerName;
    private String writerEmail;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
