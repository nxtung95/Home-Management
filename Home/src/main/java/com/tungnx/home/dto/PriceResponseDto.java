package com.tungnx.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PriceResponseDto {
	private Date createdAt;
	private String roomPrice;
	private String electricPrice;
	private String waterPrice;
	private String internetPrice;
	private String garbagePrice;
	private String total;
	private String note;
	private boolean status;
	private Date depositedAt;
}
