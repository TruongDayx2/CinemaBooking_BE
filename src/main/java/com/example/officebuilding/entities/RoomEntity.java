package com.example.officebuilding.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String rName;

    @Column(nullable = false)
    private double rPrice;

    @Column(nullable = false)
    private Integer rStatus;

    private String rDesc;
    private Timestamp rTime;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        rTime = new Timestamp(System.currentTimeMillis());
    }

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id", referencedColumnName= "id",nullable = true)
    private FloorEntity floor;
}
