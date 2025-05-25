package com.example.bemission2advancedscheduleapp.lv3_6.controller;

import com.example.bemission2advancedscheduleapp.lv3_6.dto.ScheduleRequestDto;
import com.example.bemission2advancedscheduleapp.lv3_6.dto.ScheduleResponseDto;
import com.example.bemission2advancedscheduleapp.lv3_6.dto.UpdateWriterRequestDto;
import com.example.bemission2advancedscheduleapp.lv3_6.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> create(@Valid @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.save(dto), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(scheduleService.findAll(page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}/todo")
    public ResponseEntity<ScheduleResponseDto> updateTodo(
            @PathVariable Long id,
            @RequestParam String todo,
            @RequestParam String password
    ) {
        return new ResponseEntity<>(scheduleService.updateTodo(id, password, todo), HttpStatus.OK);
    }

    @PatchMapping("/{id}/writer")
    public ResponseEntity<ScheduleResponseDto> updateWriter(
            @PathVariable Long id,
            @Valid @RequestBody UpdateWriterRequestDto dto
    ) {
        return new ResponseEntity<>(scheduleService.updateWriter(id, dto), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam String password) {
        scheduleService.delete(id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
