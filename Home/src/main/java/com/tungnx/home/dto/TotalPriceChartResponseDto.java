package com.tungnx.home.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class TotalPriceChartResponseDto {
	private String year;
	private String month;
	private String total;

	public TotalPriceChartResponseDto(String month, String total) {
		this.month = month;
		this.total = total;
	}
}
