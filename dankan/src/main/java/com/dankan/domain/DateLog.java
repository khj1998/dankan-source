package com.dankan.domain;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate createdAt;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "updated_at", nullable = false, columnDefinition = "date")
    private LocalDate updatedAt;

    @Column(name = "last_user_id", nullable = false, columnDefinition = "bigint")
    private Long lastUserId;
}
