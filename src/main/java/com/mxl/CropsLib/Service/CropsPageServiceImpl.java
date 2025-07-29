package com.mxl.CropsLib.Service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        if(cropsPageEntity.getDetail() == null)
            cropsPageEntity.setDetail("null");

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
    public ResponseEntity<String> updateCropPageTitle(String croptitle, String title) {
        long cropid = getIdByTitle(croptitle);
        try{
            return updateCropPageTitle(cropid, title);
        }catch (IllegalArgumentException e){
            throw new EntityNotFoundException(e.getMessage());
        }
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
        String imageName = (cropsPageInDB.getImages_cnt() + 1) + ".jpg";
        cropsPageInDB.setImages_cnt(cropsPageInDB.getImages_cnt() + 1);

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
        String videoName = (cropsPageInDB.getVideos_cnt() + 1) + ".mp4";
        cropsPageInDB.setVideos_cnt(cropsPageInDB.getVideos_cnt() + 1);

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

    @Override
    public ResponseEntity<String> deleteCropPageDetailById(long cropid) {
        CropsPageEntity cropsPageEntity = cropsRepository.findById(cropid).orElseThrow(EntityNotFoundException::new);
        if(cropsPageEntity.getDetail() == null && cropsPageEntity.getDetail().equals("deleted"))
            return ResponseEntity.badRequest().body("Detail already deleted");
        cropsPageEntity.setDetail("deleted");

        DetailOfCropEntity detailOfCropEntity = detailsOfCropRepository.findById(cropid);
        detailsOfCropRepository.delete(detailOfCropEntity);

        cropsRepository.save(cropsPageEntity);

        return ResponseEntity.ok("detail delete successfully");
    }

    @Override
    public ResponseEntity<String> deleteCropPageImageByIdAndName(long cropid, String imagename) {
        CropsPageEntity cropsPageEntity = cropsRepository.findById(cropid).orElseThrow(EntityNotFoundException::new);
        if (imagename.equals("end"))
            imagename = cropsPageEntity.getImages_cnt() + ".jpg";
        System.out.println(imagename);
        List<ImagesOfCropEntity> imagesOfCropEntityList = imagesOfCropRepository.findByBelongAndImagename(cropid, imagename);
        if (CollectionUtils.isEmpty(imagesOfCropEntityList) || imagesOfCropEntityList.size() > 1)
            throw new EntityNotFoundException("Image not found with name: " + imagename + " id: " + cropid);

        ImagesOfCropEntity imagesOfCropEntity = imagesOfCropEntityList.get(0);
        int deleteNum = Integer.parseInt(imagename.substring(0, imagename.lastIndexOf('.')));

        imagesOfCropRepository.delete(imagesOfCropEntity);
        cropsPageEntity.setImages_cnt(cropsPageEntity.getImages_cnt() - 1);
        cropsRepository.save(cropsPageEntity);

        List<ImagesOfCropEntity> imagesForIncrement = imagesOfCropRepository.findByBelong(cropid);

        for (ImagesOfCropEntity image : imagesForIncrement) {
            if (Integer.parseInt(image.getImagename().substring(0, image.getImagename().lastIndexOf('.'))) > deleteNum) {
                image.setImagename((Integer.parseInt(image.getImagename().substring(0, image.getImagename().lastIndexOf('.'))) - 1) + ".jpg");
                imagesOfCropRepository.save(image);
            }
        }

        return ResponseEntity.ok("image delete successfully");
    }

    @Override
    public ResponseEntity<String> deleteCropPageVideoByIdAndName(long cropid, String videoname) {
        CropsPageEntity cropsPageEntity = cropsRepository.findById(cropid).orElseThrow(EntityNotFoundException::new);
        if (videoname.equals("end"))
            videoname = cropsPageEntity.getVideos_cnt() + ".mp4";
        List<VideosOfCropEntity> videosOfCropEntityList = videosOfCropRepository.findByBelongAndVideoname(cropid, videoname);
        if (CollectionUtils.isEmpty(videosOfCropEntityList) || videosOfCropEntityList.size() > 1)
            throw new EntityNotFoundException("Video not found with name: " + videoname + " id: " + cropid);

        VideosOfCropEntity videosOfCropEntity = videosOfCropEntityList.get(0);
        int deleteNum = Integer.parseInt(videoname.substring(0, videoname.lastIndexOf('.')));

        videosOfCropRepository.delete(videosOfCropEntity);
        cropsPageEntity.setVideos_cnt(cropsPageEntity.getVideos_cnt() - 1);
        cropsRepository.save(cropsPageEntity);

        List<VideosOfCropEntity> videosForIncrement = videosOfCropRepository.findByBelong(cropid);

        for (VideosOfCropEntity video : videosForIncrement) {
            if (Integer.parseInt(video.getVideoname().substring(0, video.getVideoname().lastIndexOf('.'))) > deleteNum) {
                video.setVideoname((Integer.parseInt(video.getVideoname().substring(0, video.getVideoname().lastIndexOf('.'))) - 1) + ".mp4");
                videosOfCropRepository.save(video);
            }
        }

        return ResponseEntity.ok("video delete successfully");
    }

    @Override
    public ResponseEntity<String> deleteCropPageImageByTitleAndName(String croptitle, String imagename) {
        try{
            long id = getIdByTitle(croptitle);
            return deleteCropPageImageByIdAndName(id, imagename);
        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException("Crop not found with title: " + croptitle);
        }
    }

    @Override
    public ResponseEntity<String> deleteCropPageVideoByTitleAndName(String croptitle, String videoname) {
        try{
            long id = getIdByTitle(croptitle);
            return deleteCropPageVideoByIdAndName(id, videoname);
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Crop not found with title: " + croptitle);
        }
    }

    @Override
    public ResponseEntity<String> addCropPageImagesById(long cropid, MultipartFile[] images, MultipartFile orderJson) throws IOException {

        if(!cropsRepository.existsById(cropid))
            throw new EntityNotFoundException("Crop not found with id: " + cropid);

        Map<String, Integer> orderMap = new ObjectMapper()
                .readValue(orderJson.getInputStream(), new TypeReference<Map<String, Integer>>() {});

        Map<String, MultipartFile> imageMap = Arrays.stream(images)
                .collect(Collectors.toMap(
                        MultipartFile::getOriginalFilename,
                        f -> f,
                        (f1, f2) -> f1)
                );

        List<MultipartFile> orderedImages = orderMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(imageMap::get)
                .filter(Objects::nonNull)
                .toList();


        CropsPageEntity cropsPageInDB = cropsRepository.findById(cropid).orElseThrow(EntityNotFoundException::new);
        for(MultipartFile image: orderedImages){
            try{
//                updateCropPageImage(cropid, image);
//                //问题出在多个图片进行上传时创建多个相同id的Entity,并进行images_cnt的修改，这是不被允许的
                ImagesOfCropEntity imageOfCropEntity = new ImagesOfCropEntity();
                imageOfCropEntity.setBelong(cropid);
                imageOfCropEntity.setImagename((cropsPageInDB.getImages_cnt() + 1) + ".jpg");
                imageOfCropEntity.setImage_data(image.getBytes());
                imagesOfCropRepository.save(imageOfCropEntity);
                cropsPageInDB.setImages_cnt(cropsPageInDB.getImages_cnt() + 1);
                cropsRepository.save(cropsPageInDB);
            }catch (Exception e){
                throw new RuntimeException("Error occurred while adding image: " + image.getOriginalFilename(), e);
            }
        }
            return ResponseEntity.ok("All images updated successfully");
    }


}



