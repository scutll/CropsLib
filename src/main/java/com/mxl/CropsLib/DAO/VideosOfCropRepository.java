package com.mxl.CropsLib.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosOfCropRepository extends JpaRepository<VideosOfCropRepository, Long> {
}
