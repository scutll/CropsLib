package com.mxl.CropsLib.Service;


import com.mxl.CropsLib.DAO.CropsRepository;
import com.mxl.CropsLib.DAO.DetailsOfCropRepository;
import com.mxl.CropsLib.DAO.ImagesOfCropRepository;
import com.mxl.CropsLib.DAO.VideosOfCropRepository;
import com.mxl.CropsLib.Entity.CropsPageEntity;
import com.mxl.CropsLib.Entity.DetailOfCropEntity;
import com.mxl.CropsLib.Entity.ImagesOfCropEntity;
import com.mxl.CropsLib.Entity.VideosOfCropEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CropsPageServiceImpl implements CropsPageService{

    @Autowired
    private CropsRepository cropsRepository;

    @Autowired
    private ImagesOfCropRepository imagesOfCropRepository;

    @Autowired
    private VideosOfCropRepository videosOfCropRepository;

    @Autowired
    private DetailsOfCropRepository detailsOfCropRepository;


    @Override
    public ResponseEntity<String> addNewCropPage(CropsPageEntity cropsPageEntity) {
        if(cropsPageEntity.getImages() == null)
            cropsPageEntity.setImages("");
        if(cropsPageEntity.getVideos() == null)
            cropsPageEntity.setVideos("");
        if(cropsPageEntity.getDetail() == null)
            cropsPageEntity.setDetail("null");

        long images_cnt = cropsPageEntity.getImages()
                .chars().filter(c -> c == ';').count();
        cropsPageEntity.setImages_cnt((int) images_cnt);

        long videos_cnt = cropsPageEntity.getVideos()
                .chars().filter(c -> c == ';').count();
        cropsPageEntity.setVideos_cnt((int) videos_cnt);

        CropsPageEntity saved = cropsRepository.save(cropsPageEntity);

        return ResponseEntity
                .ok(saved.getId() + "");
    }

    @Override
    public ResponseEntity<String> updateCropPageTitle(long cropid, String title) {
        CropsPageEntity cropsPageInDB = cropsRepository.findById(cropid).orElseThrow(IllegalArgumentException::new);
        cropsPageInDB.setTitle(title);

        cropsRepository.save(cropsPageInDB);
        return ResponseEntity.ok("Title updated successfully: " + title);

    }

    @Override
    public ResponseEntity<String> updateCropPageDetail(long cropid, String details) {
        CropsPageEntity cropsPageInDB = cropsRepository.findById(cropid).orElseThrow(IllegalArgumentException::new);
        DetailOfCropEntity detailOfCrop = new DetailOfCropEntity();

        detailOfCrop.setId(cropid);
        detailOfCrop.setDetail(details);
        cropsPageInDB.setDetail("filled");

        detailsOfCropRepository.save(detailOfCrop);
        cropsRepository.save(cropsPageInDB);
        return ResponseEntity.ok("Details updated successfully: " + detailOfCrop.getId());
    }

    @Override
    public ResponseEntity<String> updateCropPageImage(long cropid, MultipartFile image) throws Exception{
        CropsPageEntity cropsPageInDB = cropsRepository.findById(cropid).orElseThrow(IllegalArgumentException::new);
        String imageName = cropsPageInDB.getImages_cnt() + ".jpg";
        cropsPageInDB.setImages_cnt(cropsPageInDB.getImages_cnt() + 1);
        String _images = cropsPageInDB.getImages();

        if(_images != null)
            _images += imageName + ";";
        else
            _images = imageName + ";";

        cropsPageInDB.setImages(_images);
        cropsRepository.save(cropsPageInDB);

        ImagesOfCropEntity imagesOfCrop = new ImagesOfCropEntity();
        imagesOfCrop.setBelong(cropid);
        imagesOfCrop.setImagename(imageName);
        imagesOfCrop.setImage_data(image.getBytes());

        imagesOfCropRepository.save(imagesOfCrop);
        cropsRepository.save(cropsPageInDB);

        return ResponseEntity.ok("Image updated successfully: " + imageName);
    }

    @Override
    public ResponseEntity<String> updateCropPageVideo(long cropid, MultipartFile video) throws Exception{
        CropsPageEntity cropsPageInDB = cropsRepository.findById(cropid).orElseThrow(IllegalArgumentException::new);
        String videoName = cropsPageInDB.getVideos_cnt() + ".mp4";
        cropsPageInDB.setVideos_cnt(cropsPageInDB.getVideos_cnt() + 1);
        String _videos = cropsPageInDB.getVideos();

        if(_videos != null)
            _videos += videoName + ";";
        else
            _videos = videoName + ";";

        cropsPageInDB.setVideos(_videos);
        cropsRepository.save(cropsPageInDB);

        VideosOfCropEntity videosOfCrop = new VideosOfCropEntity();
        videosOfCrop.setBelong(cropid);
        videosOfCrop.setVideoname(videoName);
        videosOfCrop.setVideo_data(video.getBytes());

        videosOfCropRepository.save(videosOfCrop);
        cropsRepository.save(cropsPageInDB);

        return ResponseEntity.ok("Video updated successfully: " + videoName);
    }


}
