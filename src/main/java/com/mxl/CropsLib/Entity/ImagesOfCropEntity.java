package com.mxl.CropsLib.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "images_of_crop")
public class ImagesOfCropEntity {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "imagename")
    private String imagename;

    @Lob
    @Column(name = "image_data")
    private byte[] image_data;

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

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imageName) {
        this.imagename = imageName;
    }

    public byte[] getImage_data() {
        return image_data;
    }

    public void setImage_data(byte[] imageData) {
        this.image_data = imageData;
    }
}
