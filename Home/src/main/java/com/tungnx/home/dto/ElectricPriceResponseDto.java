package com.tungnx.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ElectricPriceResponseDto {
	private Date importedDate;
	private String preElectricNum;
	private String curElectricNum;
	private String unitPrice;
	private String totalElectricNum;
	private String totalElectricPrice;
	private String note;
}
