package com.example.bemission2advancedscheduleapp.lv3_6.repository;

import com.example.bemission2advancedscheduleapp.lv3_6.entitiy.Writer;

import java.util.Optional;

public interface WriterRepository {
    Optional<Writer> findByNameAndEmail(String name, String email);
    Writer save(Writer writer);
    Optional<Writer> findById(Long id);
}
