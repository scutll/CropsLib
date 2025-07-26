package com.mxl.CropsLib.Vo;

import com.mxl.CropsLib.Entity.CropsPageEntity;

public class CropsPageDTO {

    private long id;

    private String title;

    private int imagesCnt;

    private int videosCnt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImagesCnt() {
        return imagesCnt;
    }

    public void setImagesCnt(int imagesCnt) {
        this.imagesCnt = imagesCnt;
    }

    public int getVideosCnt() {
        return videosCnt;
    }

    public void setVideosCnt(int videosCnt) {
        this.videosCnt = videosCnt;
    }

    public static CropsPageDTO build(CropsPageEntity cropsPageEntity) {
        CropsPageDTO cropsPageDTO = new CropsPageDTO();
        cropsPageDTO.setId(cropsPageEntity.getId());
        cropsPageDTO.setTitle(cropsPageEntity.getTitle());
        cropsPageDTO.setImagesCnt(cropsPageEntity.getImages_cnt());
        cropsPageDTO.setVideosCnt(cropsPageEntity.getVideos_cnt());
        return cropsPageDTO;
    }
}
