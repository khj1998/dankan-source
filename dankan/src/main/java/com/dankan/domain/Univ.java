package com.dankan.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "univ")
public class Univ {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "univ_id",nullable = false, length = 36, columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID univId;

    @Column(nullable = false, length = 30, columnDefinition = "varchar")
    private String emailDomain;

    @Column(nullable = false, length = 20, columnDefinition = "varchar")
    private String univName;
}
