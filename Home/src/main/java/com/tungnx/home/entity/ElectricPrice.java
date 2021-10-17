package com.tungnx.home.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class ElectricPrice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date importedDate;

	private String preElectricNum;
	private String curElectricNum;
	private String unitPrice;
	private String totalElectricNum;
	private String totalElectricPrice;

	@Column(columnDefinition = "varchar(512) default ''")
	private String note;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
