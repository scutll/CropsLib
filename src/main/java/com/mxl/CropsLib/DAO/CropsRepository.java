package com.mxl.CropsLib.DAO;

import com.mxl.CropsLib.Entity.CropsPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropsRepository extends JpaRepository<CropsPageEntity, Long> {
}
