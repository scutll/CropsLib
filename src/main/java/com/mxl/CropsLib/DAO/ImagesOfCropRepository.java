package com.mxl.CropsLib.DAO;

import com.mxl.CropsLib.Entity.ImagesOfCropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesOfCropRepository extends JpaRepository<ImagesOfCropEntity, Long> {

    List<ImagesOfCropEntity> findByImagename(String imagename);

    List<ImagesOfCropEntity> findByBelongAndImagename(long belong, String imagename);

    List<ImagesOfCropEntity> findByBelong(long cropid);
}
