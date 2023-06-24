package com.dankan.repository;

import com.dankan.domain.Univ;
import com.dankan.dto.response.univ.UnivListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UnivRepository extends JpaRepository<Univ, UUID> {
    @Query(value = "select new com.dankan.dto.response.univ.UnivListResponseDto(univ.emailDomain, univ.univName)" +
            "from Univ univ")
    public List<UnivListResponseDto> findUnivList();

}
