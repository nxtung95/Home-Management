package com.tungnx.home.service;

import com.tungnx.home.dto.PriceResponseDto;
import com.tungnx.home.dto.TotalElectricPriceChartDto;
import com.tungnx.home.dto.TotalPriceChartResponseDto;
import com.tungnx.home.entity.ElectricPrice;
import com.tungnx.home.entity.Price;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface PriceService {
	List<PriceResponseDto> getAllPriceFirstView(int userId, int pageNumber, int pageSize) throws Exception;

	Page<Price> searchAllPrice(int userId, int pageNumber, int pageSize, String year, String month, String status) throws Exception;

	Map<String, List<TotalPriceChartResponseDto>> getTotalPriceByYearAndMonth(int userId);

	Map<String, List<TotalElectricPriceChartDto>> getTotalElectricPriceByYearAndMonth(int userId);

	Page<ElectricPrice> searchAllElectricPrice(int userId, int pageNumber, int pageSize, String year, String month) throws Exception;
}
