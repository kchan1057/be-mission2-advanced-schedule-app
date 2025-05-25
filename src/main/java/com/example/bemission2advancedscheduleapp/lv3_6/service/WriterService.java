package com.example.bemission2advancedscheduleapp.lv3_6.service;

import com.example.bemission2advancedscheduleapp.lv3_6.entitiy.Writer;
import com.example.bemission2advancedscheduleapp.lv3_6.repository.WriterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class WriterService {

    private final WriterRepository writerRepository;

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public Writer getOrCreateWriter(String name, String email) {
        return writerRepository.findByNameAndEmail(name, email)
                .orElseGet(() -> writerRepository.save(new Writer(null,name,email,LocalDateTime.now(),LocalDateTime.now())));
    }

    public Writer getWriter(Long id) {
        return writerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

}
