package com.dankan.service.univ;

import com.dankan.dto.response.univ.UnivListResponseDto;

import java.util.List;

public interface UnivService {
    public List<UnivListResponseDto> findAll();
}
