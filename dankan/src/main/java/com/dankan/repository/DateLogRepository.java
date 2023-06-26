package com.dankan.repository;

import com.dankan.domain.DateLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateLogRepository extends JpaRepository<DateLog, Long> {
}
