package com.tungnx.home.service;

import com.tungnx.home.dto.PriceResponseDto;

import java.util.List;

public interface PriceService {
	List<PriceResponseDto> getAllPrice(int userId) throws Exception;
}
