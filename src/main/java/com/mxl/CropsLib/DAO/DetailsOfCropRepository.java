package com.mxl.CropsLib.DAO;

import com.mxl.CropsLib.Entity.DetailOfCropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsOfCropRepository extends JpaRepository<DetailOfCropEntity, Long> {
}
