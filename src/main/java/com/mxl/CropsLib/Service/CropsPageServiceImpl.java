package com.mxl.CropsLib.Service;


import com.mxl.CropsLib.DAO.CropsRepository;
import com.mxl.CropsLib.DAO.ImagesOfCropRepository;
import com.mxl.CropsLib.DAO.VideosOfCropRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CropsPageServiceImpl implements CropsPageService{

    @Autowired
    private CropsRepository cropsRepository;

    @Autowired
    private ImagesOfCropRepository imagesOfCropRepository;

    @Autowired
    private VideosOfCropRepository videosOfCropRepository;


}
