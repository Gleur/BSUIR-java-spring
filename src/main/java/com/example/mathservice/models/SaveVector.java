package com.example.mathservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SaveVector {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double norm, Xproj, Yproj;

    public Long geteId() {
        return id;
    }

    public Double getNorm() {
        return norm;
    }
    public void setNorm(Double norm) {
        this.norm = norm;
    }
    public Double getXproj() {
        return Xproj;
    }
    public void setXproj(Double XProj) {
        this.Xproj = XProj;
    }
    public Double getYproj() {
        return Yproj;
    }
    public void setYproj(Double YProj) {
        this.Yproj = YProj;
    }
}
