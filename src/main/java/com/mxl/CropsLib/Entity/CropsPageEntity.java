package com.mxl.CropsLib.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CropsPageEntity {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "detail")
    private String detail;

    @Column(name = "images")
    private String images;

    @Column(name = "images_cnt")
    private int images_cnt;

    @Column(name = "videos")
    private String videos;

    @Column(name = "videos_cnt")
    private int videos_cnt;



// getter and setters --------------------------------------------------------------------------------------
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getImages_cnt() {
        return images_cnt;
    }

    public void setImages_cnt(int images_cnt) {
        this.images_cnt = images_cnt;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public int getVideos_cnt() {
        return videos_cnt;
    }

    public void setVideos_cnt(int videos_cnt) {
        this.videos_cnt = videos_cnt;
    }
}
