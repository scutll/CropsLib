package com.mxl.CropsLib.Service;


import com.mxl.CropsLib.Entity.CropsPageEntity;
import com.mxl.CropsLib.Vo.CropsPageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CropsPageService {
    ResponseEntity<String> addNewCropPage(CropsPageEntity cropsPageEntity);


    ResponseEntity<String> updateCropPageTitle(long cropid, String title);

    ResponseEntity<String> updateCropPageTitle(String croptitle, String title);

    ResponseEntity<String> updateCropPageDetail(long cropid, String details);

    ResponseEntity<String> updateCropPageImage(long cropid, MultipartFile image) throws Exception;

    ResponseEntity<String> updateCropPageVideo(long cropid, MultipartFile video) throws Exception;

    ResponseEntity<CropsPageDTO> getCropPageById(long cropid);

    ResponseEntity<CropsPageDTO> getCropPageByTitle(String croptitle);

    ResponseEntity<byte[]> getCropPageImageByIdAndName(long cropid, String imageName);


    ResponseEntity<byte[]> getCropPageImageByTitleAndName(String croptitle, String imageName);

    ResponseEntity<byte[]> getCropPageVideosByIdAndName(long cropid, String videoName);

    ResponseEntity<byte[]> getCropPageVideosByTitleAndName(String croptitle, String videoName);

    ResponseEntity<String> getCropPageDetailById(long cropid);

    ResponseEntity<String> getCropPageDetailByTitle(String croptitle);

    ResponseEntity<String> deleteCropPageDetailById(long cropid);

    ResponseEntity<String> deleteCropPageImageByIdAndName(long cropid, String imagename);

    ResponseEntity<String> deleteCropPageVideoByIdAndName(long cropid, String videoname);

    ResponseEntity<String> deleteCropPageImageByTitleAndName(String croptitle, String imagename);

    ResponseEntity<String> deleteCropPageVideoByTitleAndName(String croptitle, String videoname);

    ResponseEntity<String> addCropPageImagesById(long cropid, MultipartFile[] images, MultipartFile orderJson) throws IOException;
}
