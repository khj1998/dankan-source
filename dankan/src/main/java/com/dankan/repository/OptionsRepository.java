package com.dankan.repository;

import com.dankan.domain.Options;
import com.dankan.domain.embedded.OptionsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OptionsRepository extends JpaRepository<Options, OptionsId> {
    List<Options> findByRoomId(Long roomId);

    @Query(value = "select o from Options o where o.roomId=:roomId "+
            "and o.codeKey = :codeKey")
    Optional<Options> findRoomTypeOption(@Param("roomId") Long roomId,
                                         @Param("codeKey") String codeKey);
}
