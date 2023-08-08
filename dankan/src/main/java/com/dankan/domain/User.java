package com.dankan.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id",nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "date_id", nullable = false, columnDefinition = "int")
    private Long dateId;

    @Column(name = "name",nullable = false,length = 10,columnDefinition = "varchar")
    private String name;

    @Column(length = 16,columnDefinition = "varchar")
    private String nickname;

    @Column(nullable = false, unique = true,length = 40,columnDefinition = "varchar")
    private String email;

    @Column(nullable = false,columnDefinition = "text")
    private String profileImg;

    @Column(unique = true,length = 15,columnDefinition = "varchar")
    private String phoneNum;

    @Column(nullable = false,columnDefinition = "int")
    private Long userType;

    @Column(columnDefinition = "bool")
    private Boolean gender;

    @Column(length = 36, columnDefinition = "varchar")
    private String univEmail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    @ApiModelProperty(example = "사용자 권한 정보들")
    private List<Authority> authorities;

    public boolean hasRole(String role) {
        for(int i = 0; i < authorities.size(); i++) {
            if(authorities.get(i).getRole().equals(role)) {
                return true;
            }
        }

        return false;
    }
}
