package com.dankan.domain.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OptionsId implements Serializable {
    private String codeKey;
    private Long roomId;
}
