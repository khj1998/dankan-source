package com.dankan.repository;

import com.dankan.domain.ReviewReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewReportRepository extends JpaRepository<ReviewReport, Long> {
}
