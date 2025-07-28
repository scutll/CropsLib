package com.mxl.CropsLib.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "crops_page")
public class CropsPageEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "detail")
    private String detail;

    @Column(name = "images_cnt")
    private int images_cnt;

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

    public int getImages_cnt() {
        return images_cnt;
    }

    public void setImages_cnt(int imagesCnt) {
        this.images_cnt = imagesCnt;
    }

    public int getVideos_cnt() {
        return videos_cnt;
    }

    public void setVideos_cnt(int videosCnt) {
        this.videos_cnt = videosCnt;
    }
}
