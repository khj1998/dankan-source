package com.dankan.domain;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel(value = "날짜 로그 테이블")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "date_log")
public class DateLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "int")
    private Long id;

    @Column(name = "created_at", nullable = false, columnDefinition = "date")
    private LocalDateTime createdAt;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint")
    private Long userId;

    public static DateLog of(Long userId) {
        return DateLog.builder()
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
