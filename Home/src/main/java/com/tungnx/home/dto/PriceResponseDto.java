package com.tungnx.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponseDto {
	private Date date;
	private String roomPrice;
	private String electricPrice;
	private String waterPrice;
	private String internetPrice;
	private String garbagePrice;
	private String total;
	private String note;
}
