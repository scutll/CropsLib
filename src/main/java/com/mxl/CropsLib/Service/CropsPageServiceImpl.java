package com.mxl.CropsLib.Service;


import com.mxl.CropsLib.DAO.CropsRepository;
import com.mxl.CropsLib.DAO.DetailsOfCropRepository;
import com.mxl.CropsLib.DAO.ImagesOfCropRepository;
import com.mxl.CropsLib.DAO.VideosOfCropRepository;
import com.mxl.CropsLib.Entity.CropsPageEntity;
import com.mxl.CropsLib.Entity.DetailOfCropEntity;
import com.mxl.CropsLib.Entity.ImagesOfCropEntity;
import com.mxl.CropsLib.Entity.VideosOfCropEntity;
import com.mxl.CropsLib.Vo.CropsPageDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

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


    private long getIdByTitle(String croptitle) throws EntityNotFoundException{
        List<CropsPageEntity> cropsPageEntityList = cropsRepository.findByTitle(croptitle);
        if(CollectionUtils.isEmpty(cropsPageEntityList))
            throw new EntityNotFoundException("Crop not found with title: " + croptitle);
        else if (cropsPageEntityList.size() > 1) {
            throw new EntityNotFoundException("Multiple crops found with same title");
        }
        return cropsPageEntityList.get(0).getId();
    }



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

    @Override
    public ResponseEntity<CropsPageDTO> getCropPageById(long cropid) {
        CropsPageEntity cropsPageEntity = cropsRepository.findById(cropid).orElseThrow(EntityNotFoundException::new);
        return ResponseEntity.ok(CropsPageDTO.ConvertIntoVo(cropsPageEntity));
    }

    @Override
    public ResponseEntity<CropsPageDTO> getCropPageByTitle(String croptitle) {
        List<CropsPageEntity> cropsPageEntityList = cropsRepository.findByTitle(croptitle);
        if(!CollectionUtils.isEmpty(cropsPageEntityList))
            return ResponseEntity.ok(CropsPageDTO.ConvertIntoVo(cropsPageEntityList.get(0)));
        else
            throw new EntityNotFoundException();

    }

    @Override
    public ResponseEntity<byte[]> getCropPageImageByIdAndName(long cropid, String imageName) {
        List<ImagesOfCropEntity> imagesOfCropEntityList = imagesOfCropRepository.findByBelongAndImagename(cropid, imageName);
        if(CollectionUtils.isEmpty(imagesOfCropEntityList))
            throw new EntityNotFoundException("Image not found with name: " + imageName + " id: " + cropid);
        else if (imagesOfCropEntityList.size() > 1) {
            throw new EntityNotFoundException("Multiple images found with same name");
        }

        ImagesOfCropEntity imagesOfCropEntity = imagesOfCropEntityList.get(0);
        String image_name = imagesOfCropEntity.getImagename();
        byte[] image_data = imagesOfCropEntity.getImage_data();


        MediaType type = image_name.toLowerCase().endsWith(".png")
                ? MediaType.IMAGE_PNG
                : MediaType.IMAGE_JPEG;
        return ResponseEntity.ok()
                .contentType(type)
                .body(image_data);
    }

    @Override
    public ResponseEntity<byte[]> getCropPageImageByTitleAndName(String croptitle, String imageName) {
        List<CropsPageEntity> cropsPageEntityList = cropsRepository.findByTitle(croptitle);
        if(CollectionUtils.isEmpty(cropsPageEntityList))
            throw new EntityNotFoundException("Crop not found with title: " + croptitle);
        else if (cropsPageEntityList.size() > 1) {
            throw new EntityNotFoundException("Multiple crops found with same title");
        }

        CropsPageEntity cropsPageEntity = cropsPageEntityList.get(0);
        long cropid = cropsPageEntity.getId();
        try{
            return getCropPageImageByIdAndName(cropid, imageName);
        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<byte[]> getCropPageVideosByIdAndName(long cropid, String videoName) {
        List<VideosOfCropEntity> videosOfCropEntityList = videosOfCropRepository.findByBelongAndVideoname(cropid, videoName);
        if(CollectionUtils.isEmpty(videosOfCropEntityList))
            throw new EntityNotFoundException("Video not found with name: " + videoName + " id: " + cropid);
        else if (videosOfCropEntityList.size() > 1) {
            throw new EntityNotFoundException("Multiple videos found with same name");
        }

        VideosOfCropEntity videosOfCropEntity = videosOfCropEntityList.get(0);
        String video_name = videosOfCropEntity.getVideoname();
        byte[] video_data = videosOfCropEntity.getVideo_data();

        String suffix = video_name.toLowerCase();
        MediaType type = suffix.endsWith(".mp4")  ? MediaType.parseMediaType("video/mp4")
                : suffix.endsWith(".avi") ? MediaType.parseMediaType("video/avi")
                : suffix.endsWith(".mov") ? MediaType.parseMediaType("video/quicktime")
                : suffix.endsWith(".mkv") ? MediaType.parseMediaType("video/x-matroska")
                : MediaType.APPLICATION_OCTET_STREAM;

        return ResponseEntity.ok().
                contentType(type).
                body(video_data);
    }

    @Override
    public ResponseEntity<byte[]> getCropPageVideosByTitleAndName(String croptitle, String videoName) {
        List<CropsPageEntity> cropsPageEntityList = cropsRepository.findByTitle(croptitle);
        if(CollectionUtils.isEmpty(cropsPageEntityList))
            throw new EntityNotFoundException("Crop not found with title: " + croptitle);
        else if (cropsPageEntityList.size() > 1) {
            throw new EntityNotFoundException("Multiple crops found with same title");
        }

        CropsPageEntity cropsPageEntity = cropsPageEntityList.get(0);
        long cropid = cropsPageEntity.getId();
        try {
            return getCropPageVideosByIdAndName(cropid, videoName);
        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> getCropPageDetailById(long cropid) {
        DetailOfCropEntity detailOfCropEntity = detailsOfCropRepository.findById(cropid);
        if(detailOfCropEntity == null)
            throw new EntityNotFoundException("Detail not found with id: " + cropid);

        return ResponseEntity.ok(detailOfCropEntity.getDetail());
        }

    @Override
    public ResponseEntity<String> getCropPageDetailByTitle(String croptitle) {
        long id = getIdByTitle(croptitle);
        try{
            return getCropPageDetailById(id);
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }


}

