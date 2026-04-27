package com.example.easypark.repository;

import com.example.easypark.entity.Entry;
import com.example.easypark.entity.EntryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    List<Entry> findByStatus(EntryStatus status);
}