package com.example.bemission2advancedscheduleapp.lv3_6.repository;

import com.example.bemission2advancedscheduleapp.lv3_6.dto.ScheduleResponseDto;
import com.example.bemission2advancedscheduleapp.lv3_6.entitiy.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);
    List<ScheduleResponseDto> findAll(int page, int size);
    Optional<Schedule> findById(Long id);
    void delete(Long id, String password);
    void updateTodo(Long id, String todo, String password);
    void updateWriter(Long id, Long writerId, String password);
}
