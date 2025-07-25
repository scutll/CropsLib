package com.mxl.CropsLib.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class VideosOfCropEntity {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "video_name")
    private String video_name;

    @Column(name = "video_data")
    private byte[] video_data;




    // getter and setters --------------------------------------------------------------------------------------


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public byte[] getVideo_data() {
        return video_data;
    }

    public void setVideo_data(byte[] video_data) {
        this.video_data = video_data;
    }
}
