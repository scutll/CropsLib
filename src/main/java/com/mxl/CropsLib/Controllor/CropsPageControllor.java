package com.mxl.CropsLib.Controllor;


import com.mxl.CropsLib.Entity.CropsPageEntity;
import com.mxl.CropsLib.Response.Response;
import com.mxl.CropsLib.Service.CropsPageService;
import com.mxl.CropsLib.Vo.CropsPageDTO;
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
//    //获取页面(名称、介绍、视频图片计数)
//    @GetMapping("/cropslib/cropsPage/get/id/{cropid}")
//    public Response<CropsPageDTO> getCropPageById(@PathVariable long cropid){
//        return cropsPageService.getCropPageById(cropid);
//    }
//
//    @GetMapping("/cropslib/cropsPage/get/title/{croptitle}")
//    public Response<CropsPageDTO> getCropPageByTitle(@PathVariable String croptitle){
//        return cropsPageService.getCropPageByTitle(croptitle);
//    }
//
//    //获取图片
//    @GetMapping("/cropslib/cropsPage/get/images/id/{cropid}")
//    public ResponseEntity<byte []> getCropPageImagesById(@PathVariable long cropid){
//        return cropsPageService.getCropPageImagesById(cropid);
//    }
//
//    @GetMapping("/cropslib/cropsPage/get/images/title/{croptitle}")
//    public ResponseEntity<byte []> getCropPageImagesByTitle(@PathVariable String croptitle) {
//        return cropsPageService.getCropPageImagesByTitle(croptitle);
//    }
//
//    //获取视频
//    @GetMapping("/cropslib/cropsPage/get/videos/id/{cropid}")
//    public ResponseEntity<byte []> getCropPageVideosById(@PathVariable long cropid){
//        return cropsPageService.getCropPageVideosById(cropid);
//    }
//
//    @GetMapping("/cropslib/cropsPage/get/videos/title/{croptitle}")
//    public ResponseEntity<byte []> getCropPageVideosByTitle(@PathVariable String croptitle){
//        return cropsPageService.getCropPageVideosByTitle(croptitle);
//    }
//
//

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
