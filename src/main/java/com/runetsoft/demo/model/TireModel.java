package com.runetsoft.demo.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "tire")
public class TireModel {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "characteristic_id")
    private CharacteristicModel characteristic;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CharacteristicModel getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(CharacteristicModel characteristic) {
        this.characteristic = characteristic;
    }

}