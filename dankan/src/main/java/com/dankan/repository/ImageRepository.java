package com.dankan.repository;

import com.dankan.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findByIdAndImageType(Long id,Long imageType);

    @Query("select i from Image i where i.imageUrl = :imageUrl "
          +"and i.id = :id "+"and i.imageType = :imageType")
    Optional<Image> findEditImage(@Param("id") Long id,
                                  @Param("imageUrl") String imageUrl,
                                  @Param("imageType") Long imageType);

    @Query("select i from Image i where i.id = :id "+
          "and i.imageType = :imageType")
    Optional<Image> findMainImage(@Param("id") Long id,@Param("imageType") Long imageType);
}
