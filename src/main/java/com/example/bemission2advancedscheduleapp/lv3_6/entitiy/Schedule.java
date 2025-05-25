package com.example.bemission2advancedscheduleapp.lv3_6.entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String todo;
    private String password;
    private Long writerId; // FK
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
