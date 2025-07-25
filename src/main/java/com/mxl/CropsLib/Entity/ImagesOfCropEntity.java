package com.mxl.CropsLib.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ImagesOfCropEntity {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "image_name")
    private String image_name;

    @Column(name = "image_data")
    private byte[] image_data;



    // getter and setters --------------------------------------------------------------------------------------
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public byte[] getImage_data() {
        return image_data;
    }

    public void setImage_data(byte[] image_data) {
        this.image_data = image_data;
    }
}
