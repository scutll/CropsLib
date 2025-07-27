package com.mxl.CropsLib.DAO;

import com.mxl.CropsLib.Entity.VideosOfCropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideosOfCropRepository extends JpaRepository<VideosOfCropEntity, Long> {

    List<VideosOfCropEntity> findByVideoname(String videoname);

    List<VideosOfCropEntity> findByBelongAndVideoname(long belong, String videoname);
}
