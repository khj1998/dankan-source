package com.dankan.domain.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ResidencePeriod {
    @Column(name = "started_at",columnDefinition = "date")
    private LocalDate startedAt;

    @Column(name = "end_at",columnDefinition = "date")
    private LocalDate endAt;
}
