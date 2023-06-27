package com.dankan.domain;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ApiModel(value = "매매 게시물 엔티티")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "options")
public class Options {
    @Id
    @Column(name = "code_key",length = 16,columnDefinition = "varchar")
    private String codeKey;
    private Long value;
}
