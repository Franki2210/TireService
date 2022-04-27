package com.runetsoft.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "characteristic")
public class CharacteristicModel {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "design")
    private String design;

    @Column(name = "diameter")
    private Integer diameter;

    @Column(name = "load_index")
    private Integer loadIndex;

    @Column(name = "speed_index")
    private String speedIndex;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "run_flat")
    private String runFlat;

    @Column(name = "tube_type")
    private String tubeType;

    @Column(name = "season")
    private String season;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(Integer diameter) {
        this.diameter = diameter;
    }

    public Integer getLoadIndex() {
        return loadIndex;
    }

    public void setLoadIndex(Integer loadIndex) {
        this.loadIndex = loadIndex;
    }

    public String getSpeedIndex() {
        return speedIndex;
    }

    public void setSpeedIndex(String speedIndex) {
        this.speedIndex = speedIndex;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getRunFlat() {
        return runFlat;
    }

    public void setRunFlat(String runFlat) {
        this.runFlat = runFlat;
    }

    public String getTubeType() {
        return tubeType;
    }

    public void setTubeType(String tubeType) {
        this.tubeType = tubeType;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

}