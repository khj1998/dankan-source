package com.dankan.service.univ;

import com.dankan.dto.response.univ.UnivListResponseDto;
import com.dankan.repository.UnivRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class UnivServiceImpl implements UnivService {
    private final UnivRepository univRepository;

    @Override
    @Transactional
    public List<UnivListResponseDto> findAll() {
        return univRepository.findUnivList();
    }
}
