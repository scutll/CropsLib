package com.mxl.CropsLib.DAO;

import com.mxl.CropsLib.Entity.CropsPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropsRepository extends JpaRepository<CropsPageEntity, Long> {

    List<CropsPageEntity> findByTitle(String title);
}
