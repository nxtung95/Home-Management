package com.tungnx.home.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date date;
    private String roomPrice;
    private String electricPrice;
    private String waterPrice;
    private String internetPrice;
    private String garbagePrice;
    private String total;
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
