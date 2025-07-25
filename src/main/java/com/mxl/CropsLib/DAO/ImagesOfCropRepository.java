package com.mxl.CropsLib.DAO;

import com.mxl.CropsLib.Entity.ImagesOfCropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesOfCropRepository extends JpaRepository<ImagesOfCropEntity, Long> {
}
