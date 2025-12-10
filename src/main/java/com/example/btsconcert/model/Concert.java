package com.example.btsconcert.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "concerts")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String venue;
    private String city;
    private LocalDateTime dateTime;
    private Double vipPrice;
    private Double generalPrice;
    private Integer vipCapacity;
    private Integer generalCapacity;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Concert() {}

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public Double getVipPrice() { return vipPrice; }
    public void setVipPrice(Double vipPrice) { this.vipPrice = vipPrice; }

    public Double getGeneralPrice() { return generalPrice; }
    public void setGeneralPrice(Double generalPrice) { this.generalPrice = generalPrice; }

    public Integer getVipCapacity() { return vipCapacity; }
    public void setVipCapacity(Integer vipCapacity) { this.vipCapacity = vipCapacity; }

    public Integer getGeneralCapacity() { return generalCapacity; }
    public void setGeneralCapacity(Integer generalCapacity) { this.generalCapacity = generalCapacity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
