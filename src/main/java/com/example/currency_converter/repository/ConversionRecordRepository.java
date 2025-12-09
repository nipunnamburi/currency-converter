package com.example.currency_converter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.currency_converter.model.ConversionRecord;

public interface ConversionRecordRepository extends JpaRepository<ConversionRecord, Long> {

    List<ConversionRecord> findByUsernameOrderByCreatedAtDesc(String username);
}
