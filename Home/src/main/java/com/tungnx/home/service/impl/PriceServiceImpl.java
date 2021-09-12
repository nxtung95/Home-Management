package com.tungnx.home.service.impl;

import com.tungnx.home.dto.PriceResponseDto;
import com.tungnx.home.entity.Price;
import com.tungnx.home.repository.PriceRepository;
import com.tungnx.home.service.PriceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceServiceImpl implements PriceService {
//	private static final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);

	@Autowired
	private PriceRepository priceRepository;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "prices")
	public List<PriceResponseDto> getAllPrice(int userId) {
		List<Price> prices = priceRepository.getAll(PageRequest.of(0, 6), userId);
		List<PriceResponseDto> priceResponseDtos = prices
				.stream()
				.map(p -> new PriceResponseDto(
						p.getDate(),
						p.getRoomPrice(),
						p.getElectricPrice(),
						p.getWaterPrice(),
						p.getInternetPrice(),
						p.getGarbagePrice(),
						p.getTotal(),
						p.getNote())
				)
				.collect(Collectors.toList());
		return priceResponseDtos;
	}
}
