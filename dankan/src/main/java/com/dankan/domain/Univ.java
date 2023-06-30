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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "univ_id", columnDefinition = "int")
    private UUID univId;

    @Column(nullable = false, length = 30, columnDefinition = "varchar")
    private String emailDomain;

    @Column(nullable = false, length = 20, columnDefinition = "varchar")
    private String univName;
}
