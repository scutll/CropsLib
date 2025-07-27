package com.mxl.CropsLib.DAO;

import com.mxl.CropsLib.Entity.DetailOfCropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailsOfCropRepository extends JpaRepository<DetailOfCropEntity, Long> {

    DetailOfCropEntity findById(long id);
}
