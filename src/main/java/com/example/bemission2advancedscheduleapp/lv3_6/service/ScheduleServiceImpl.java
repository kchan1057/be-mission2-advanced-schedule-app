package com.example.bemission2advancedscheduleapp.lv3_6.service;

import com.example.bemission2advancedscheduleapp.lv3_6.dto.ScheduleRequestDto;
import com.example.bemission2advancedscheduleapp.lv3_6.dto.ScheduleResponseDto;
import com.example.bemission2advancedscheduleapp.lv3_6.dto.UpdateWriterRequestDto;
import com.example.bemission2advancedscheduleapp.lv3_6.entitiy.Schedule;
import com.example.bemission2advancedscheduleapp.lv3_6.entitiy.Writer;
import com.example.bemission2advancedscheduleapp.lv3_6.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final WriterService writerService;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, WriterService writerService) {
        this.scheduleRepository = scheduleRepository;
        this.writerService = writerService;
    }

    @Override
    public ScheduleResponseDto save(ScheduleRequestDto dto) {
        Writer writer = writerService.getOrCreateWriter(dto.getWriterName(), dto.getWriterEmail());
        Schedule schedule = new Schedule(null, dto.getTodo(), dto.getPassword(),
                writer.getId(), LocalDateTime.now(), LocalDateTime.now());
        return toResponse(scheduleRepository.save(schedule), writer);
    }

    @Override
    public List<ScheduleResponseDto> findAll(int page, int size) {
        return scheduleRepository.findAll(page, size);
    }

    @Override
    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
        Writer writer = writerService.getWriter(schedule.getWriterId());
        return toResponse(schedule, writer);
    }

    @Override
    public ScheduleResponseDto updateTodo(Long id, String password, String todo) {
        scheduleRepository.updateTodo(id, todo, password);
        return findById(id);
    }

    @Override
    public ScheduleResponseDto updateWriter(Long id, UpdateWriterRequestDto dto) {
        Writer writer = writerService.getOrCreateWriter(dto.getWriterName(), dto.getWriterEmail());
        scheduleRepository.updateWriter(id, writer.getId(), dto.getPassword());
        return findById(id);
    }

    @Override
    public void delete(Long id, String password) {
        scheduleRepository.delete(id, password);
    }

    private ScheduleResponseDto toResponse(Schedule schedule, Writer writer) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTodo(),
                writer.getName(),
                writer.getEmail(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
