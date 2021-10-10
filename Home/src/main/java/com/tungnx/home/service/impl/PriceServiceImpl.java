package com.tungnx.home.service.impl;

import com.tungnx.home.constant.Common;
import com.tungnx.home.dto.PriceResponseDto;
import com.tungnx.home.dto.TotalElectricPriceChartDto;
import com.tungnx.home.dto.TotalPriceChartResponseDto;
import com.tungnx.home.entity.Price;
import com.tungnx.home.entity.Price_;
import com.tungnx.home.entity.User;
import com.tungnx.home.entity.User_;
import com.tungnx.home.repository.PriceRepository;
import com.tungnx.home.service.PriceService;
import com.tungnx.home.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class PriceServiceImpl implements PriceService {
//	private static final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);

	@Autowired
	private PriceRepository priceRepository;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "prices")
	public List<PriceResponseDto> getAllPriceFirstView(int userId, int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("createdAt").descending());
		List<Price> prices = priceRepository.getAll(pageable, userId);
		List<PriceResponseDto> priceResponseDtos = prices
				.stream()
				.map(p -> new PriceResponseDto(
					p.getCreatedAt(),
					p.getRoomPrice(),
					p.getElectricPrice(),
					p.getWaterPrice(),
					p.getInternetPrice(),
					p.getGarbagePrice(),
					p.getTotal(),
					p.getNote(),
					p.isStatus(),
					p.getDepositedAt())
				)
				.collect(Collectors.toList());
		return priceResponseDtos;
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("searchPrices")
	public Page<Price> searchAllPrice(int userId, int pageNumber, int pageSize, String year, String month, String status) throws Exception {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("createdAt").descending());
		Specification<Price> priceSpecification = (root, query, builder) -> {
			Join<Price, User> priceUserJoin = root.join(Price_.user);
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(builder.equal(priceUserJoin.get(User_.ID), userId));

			if (StringUtils.hasLength(year)) {
				Expression<String> yearDate = builder.function("DATE_FORMAT", String.class, root.get(Price_.CREATED_AT), builder.literal("%Y"));
				predicates.add(builder.equal(yearDate, year));
			}
			if (StringUtils.hasLength(month)) {
				Expression<String> yearMonth = builder.function("DATE_FORMAT", String.class, root.get(Price_.CREATED_AT), builder.literal("%m"));
				predicates.add(builder.equal(yearMonth, month));
			}
			if (StringUtils.hasLength(status)) {
				boolean isDeposit = Common.DEPOSIT_PRICE.equals(status);
				predicates.add(builder.equal(root.get(Price_.status), isDeposit));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
		Page<Price> pricePage = priceRepository.findAll(priceSpecification, pageable);
		return pricePage;
	}

	@Override
	public Map<String, List<TotalPriceChartResponseDto>> getTotalPriceByYearAndMonth(int userId) {
		Map<String, List<TotalPriceChartResponseDto>> result = new LinkedHashMap<>();

		int currentYear = DateUtil.getYearFromDate(new Date()) + 1;
		String[] yearList = {String.valueOf(currentYear), String.valueOf(currentYear - 1), String.valueOf(currentYear - 2)};
		List<TotalPriceChartResponseDto> totalPriceCharts = priceRepository.getTotalPriceByYearAndMonth(userId, yearList);
		List<TotalPriceChartResponseDto> totalPriceChartByYear = new ArrayList<>();
		boolean isFirst = true;
		for (int i = 0; i < totalPriceCharts.size(); i++) {
			TotalPriceChartResponseDto price = totalPriceCharts.get(i);
			String year = price.getYear();
			if (isFirst) {
				result.put(year, null);
				totalPriceChartByYear.add(new TotalPriceChartResponseDto(price.getMonth(), price.getTotal()));
				isFirst = false;
				continue;
			}

			if (!result.containsKey(year)) {
				Iterator<String> iterator = result.keySet().iterator();
				String lastKey = iterator.next();
				while (iterator.hasNext()) {
					lastKey = iterator.next();
				}
				result.put(lastKey, totalPriceChartByYear);
				result.put(year, null);
				totalPriceChartByYear = new ArrayList<>();
				totalPriceChartByYear.add(new TotalPriceChartResponseDto(price.getMonth(), price.getTotal()));
			} else {
				totalPriceChartByYear.add(new TotalPriceChartResponseDto(price.getMonth(), price.getTotal()));
				if (i == totalPriceCharts.size() - 1) {
					result.put(year, totalPriceChartByYear);
				}
			}
		}
		return result;
	}

	@Override
	public Map<String, List<TotalElectricPriceChartDto>> getTotalElectricPriceByYearAndMonth(int userId) {
		Map<String, List<TotalElectricPriceChartDto>> result = new LinkedHashMap<>();

		int currentYear = DateUtil.getYearFromDate(new Date()) + 1;
		String[] yearList = {String.valueOf(currentYear), String.valueOf(currentYear - 1), String.valueOf(currentYear - 2)};
		List<TotalElectricPriceChartDto> totalPriceCharts = priceRepository.getElectricPriceByYearAndMonth(userId, yearList);

		List<TotalElectricPriceChartDto> totalPriceChartByYear = new ArrayList<>();
		boolean isFirst = true;
		for (int i = 0; i < totalPriceCharts.size(); i++) {
			TotalElectricPriceChartDto price = totalPriceCharts.get(i);
			String year = price.getYear();
			if (isFirst) {
				result.put(year, null);
				totalPriceChartByYear.add(new TotalElectricPriceChartDto(price.getMonth(), price.getElectricPrice()));
				isFirst = false;
				continue;
			}

			if (!result.containsKey(year)) {
				Iterator<String> iterator = result.keySet().iterator();
				String lastKey = iterator.next();
				while (iterator.hasNext()) {
					lastKey = iterator.next();
				}
				result.put(lastKey, totalPriceChartByYear);
				result.put(year, null);
				totalPriceChartByYear = new ArrayList<>();
				totalPriceChartByYear.add(new TotalElectricPriceChartDto(price.getMonth(), price.getElectricPrice()));
			} else {
				totalPriceChartByYear.add(new TotalElectricPriceChartDto(price.getMonth(), price.getElectricPrice()));
				if (i == totalPriceCharts.size() - 1) {
					result.put(year, totalPriceChartByYear);
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		Map<String, String> abc = new HashMap<>();
		abc.put("2021", "a");
		abc.put("2021", "b");
		abc.put("2021", "c");
		abc.forEach((k, v) -> System.out.println("Key : " + k + ", Value : " + v));
	}
}
