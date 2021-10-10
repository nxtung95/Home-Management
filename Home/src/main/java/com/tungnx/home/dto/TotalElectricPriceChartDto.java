package com.tungnx.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TotalElectricPriceChartDto {
	private String year;
	private String month;
	private String electricPrice;

	public TotalElectricPriceChartDto(String month, String electricPrice) {
		this.month = month;
		this.electricPrice = electricPrice;
	}
}
