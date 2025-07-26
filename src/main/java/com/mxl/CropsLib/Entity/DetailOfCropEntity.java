package com.mxl.CropsLib.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "detail_of_crop")
public class DetailOfCropEntity {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "detail")
    private String detail;

    // getter and setters --------------------------------------------------------------------------------------


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String details) {
        this.detail = details;
    }
}
