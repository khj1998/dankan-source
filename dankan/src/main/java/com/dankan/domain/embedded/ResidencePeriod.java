package com.dankan.domain.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ResidencePeriod {
    @Column(name = "started_at",nullable = false,columnDefinition = "date")
    private Date startedAt;

    @Column(name = "end_at",nullable = false,columnDefinition = "date")
    private Date endAt;
}
