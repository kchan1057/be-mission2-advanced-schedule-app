package com.example.bemission2advancedscheduleapp.lv3_6.service;

import com.example.bemission2advancedscheduleapp.lv3_6.dto.ScheduleRequestDto;
import com.example.bemission2advancedscheduleapp.lv3_6.dto.ScheduleResponseDto;
import com.example.bemission2advancedscheduleapp.lv3_6.dto.UpdateWriterRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto save(ScheduleRequestDto dto);
    List<ScheduleResponseDto> findAll(int page, int size);
    ScheduleResponseDto findById(Long id);
    ScheduleResponseDto updateTodo(Long id, String password, String todo);
    ScheduleResponseDto updateWriter(Long id, UpdateWriterRequestDto dto);
    void delete(Long id, String password);
}