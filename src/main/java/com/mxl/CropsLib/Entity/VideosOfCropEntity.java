package com.mxl.CropsLib.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "videos_of_crop")
public class VideosOfCropEntity {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "videoname")
    private String videoname;

    @Lob
    @Column(name = "video_data")
    private byte[] video_data;

    @Column(name = "belong")
    private long belong;


    // getter and setters --------------------------------------------------------------------------------------


    public long getBelong() {
        return belong;
    }

    public void setBelong(long belong) {
        this.belong = belong;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoName) {
        this.videoname = videoName;
    }

    public byte[] getVideo_data() {
        return video_data;
    }

    public void setVideo_data(byte[] videoData) {
        this.video_data = videoData;
    }
}
