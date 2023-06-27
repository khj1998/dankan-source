package com.dankan.domain.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDiscussion {
    @Column(name = "is_discussion",nullable = false,columnDefinition = "tinyint")
    private Long isDiscussion;

    @Column(name = "move_in_start",nullable = false,columnDefinition = "date")
    private LocalDate moveInStart;

    @Column(name = "move_in_end",nullable = false,columnDefinition = "date")
    private LocalDate moveInEnd;
}
