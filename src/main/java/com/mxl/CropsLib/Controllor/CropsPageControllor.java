package com.mxl.CropsLib.Controllor;


import com.mxl.CropsLib.Entity.CropsPageEntity;
import com.mxl.CropsLib.Response.Response;
import com.mxl.CropsLib.Service.CropsPageService;
import com.mxl.CropsLib.Vo.CropsPageDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CropsPageControllor {

    @Autowired
    CropsPageService cropsPageService;


//    Get Mapping ---------------------------------------------------------------------------------------------
    //获取页面(名称、介绍、视频图片计数)
    @GetMapping("/cropslib/cropsPage/get/id/{cropid}")
    public ResponseEntity<CropsPageDTO> getCropPageById(@PathVariable long cropid){
        try{
            return cropsPageService.getCropPageById(cropid);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cropslib/cropsPage/get/title/{croptitle}")
    public ResponseEntity<CropsPageDTO> getCropPageByTitle(@PathVariable String croptitle){
        try{
            return cropsPageService.getCropPageByTitle(croptitle);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    //获取图片
    @GetMapping("/cropslib/cropsPage/get/images/id/{cropid}/{image_name}")
    public ResponseEntity<byte []> getCropPageImagesById(@PathVariable long cropid,
                                                         @PathVariable String image_name){
        try{
            return cropsPageService.getCropPageImageByIdAndName(cropid, image_name);
        }catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cropslib/cropsPage/get/images/title/{croptitle}/{image_name}")
    public ResponseEntity<byte []> getCropPageImagesByTitle(@PathVariable String croptitle,
                                                            @PathVariable String image_name){
        try{
            return cropsPageService.getCropPageImageByTitleAndName(croptitle, image_name);
        }catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //获取视频
    @GetMapping("/cropslib/cropsPage/get/videos/id/{cropid}/{video_name}")
    public ResponseEntity<byte []> getCropPageVideosById(@PathVariable long cropid,
                                                         @PathVariable String video_name){
        return cropsPageService.getCropPageVideosByIdAndName(cropid, video_name);
    }

    @GetMapping("/cropslib/cropsPage/get/videos/title/{croptitle}/{video_name}")
    public ResponseEntity<byte []> getCropPageVideosByTitle(@PathVariable String croptitle,
                                                            @PathVariable String video_name){
        return cropsPageService.getCropPageVideosByTitleAndName(croptitle, video_name);
    }

    //获取文字介绍
    @GetMapping("/cropslib/cropsPage/get/detail/id/{cropid}")
    public ResponseEntity<String> getCropPageDetailById(@PathVariable long cropid){
        try{
            return cropsPageService.getCropPageDetailById(cropid);
        }catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cropslib/cropsPage/get/detail/title/{croptitle}")
    public ResponseEntity<String> getCropPageDetailByTitle(@PathVariable String croptitle){
        try{
            return cropsPageService.getCropPageDetailByTitle(croptitle);
        }catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }



//    Post Mapping ---------------------------------------------------------------------------------------------

    //创建一个农作物页面
    //返回创建好的页面的id
    @PostMapping("/cropslib/cropsPage/add")
    public ResponseEntity<String> addNewCropPage(@RequestBody CropsPageEntity cropsPageEntity){
        return cropsPageService.addNewCropPage(cropsPageEntity);
    }




//    Put Mapping ---------------------------------------------------------------------------------------------

    //更新名称
    @PutMapping("/cropslib/cropsPage/update/{cropid}/title")
    public ResponseEntity<String> updateCropPageTitle(@PathVariable long cropid,
                                                @RequestParam("title") String title) {
        try {
            return cropsPageService.updateCropPageTitle(cropid, title);
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body("cropid " + cropid + " does not exist");
        }
    }

    //更新文字介绍
    @PutMapping("/cropslib/cropsPage/update/{cropid}/details")
    public ResponseEntity<String> updateCropPageDetail(@PathVariable long cropid,
                                                 @RequestParam("details") String details){
        return cropsPageService.updateCropPageDetail(cropid,details);
    }

    //添加图片
    @PutMapping("/cropslib/cropsPage/update/{cropid}/images")
    public ResponseEntity<String> updateCropPageTitle(@PathVariable long cropid,
                                                @RequestParam("image")MultipartFile image){
        try{
            return cropsPageService.updateCropPageImage(cropid,image);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("cropid " + cropid + " does not exist");
        }
    }

    //添加视频
    @PutMapping("/cropslib/cropsPage/update/{cropid}/videos")
    public ResponseEntity<String> updateCropPageVideo(@PathVariable long cropid,
                                                @RequestParam("video")MultipartFile video){
        try{
            return cropsPageService.updateCropPageVideo(cropid,video);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("cropid " + cropid + " does not exist");
        }
    }





//    Delete Mapping ---------------------------------------------------------------------------------------------
}
