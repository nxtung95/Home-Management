package com.tungnx.home.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String roomPrice;
    private String electricPrice;
    private String waterPrice;
    private String internetPrice;
    private String garbagePrice;
    private String total;
    private String note;
    private boolean status;

    @Temporal(TemporalType.DATE)
    @Column(name = "deposited_at")
    private Date depositedAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at", nullable = false)
    private Date deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
