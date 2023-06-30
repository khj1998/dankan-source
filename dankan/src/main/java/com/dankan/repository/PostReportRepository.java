package com.dankan.repository;

import com.dankan.domain.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostReportRepository extends JpaRepository<PostReport, Long> {
}
