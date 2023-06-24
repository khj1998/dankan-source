package com.dankan.domain.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomOption {
    @Column(name = "elevator_option",nullable = false,columnDefinition = "tinyint")
    private Long elevatorOption;

    @Column(name = "room_options",nullable = false,length = 120,columnDefinition = "varchar")
    private String roomOptions;

    @Column(name = "room_etc_options",nullable = false,length = 120,columnDefinition = "varchar")
    private String roomEtcOptions;
}
