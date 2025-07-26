package com.mxl.CropsLib.Service;


import com.mxl.CropsLib.Entity.CropsPageEntity;
import com.mxl.CropsLib.Response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CropsPageService {
    ResponseEntity<String> addNewCropPage(CropsPageEntity cropsPageEntity);


    ResponseEntity<String> updateCropPageTitle(long cropid, String title);

    ResponseEntity<String> updateCropPageDetail(long cropid, String details);

    ResponseEntity<String> updateCropPageImage(long cropid, MultipartFile image) throws Exception;

    ResponseEntity<String> updateCropPageVideo(long cropid, MultipartFile video) throws Exception;
}
