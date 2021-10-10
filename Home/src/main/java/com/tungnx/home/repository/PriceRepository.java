package com.tungnx.home.repository;

import com.tungnx.home.dto.PriceResponseDto;
import com.tungnx.home.dto.TotalElectricPriceChartDto;
import com.tungnx.home.dto.TotalPriceChartResponseDto;
import com.tungnx.home.entity.Price;
import com.tungnx.home.util.DateUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer>, JpaSpecificationExecutor<Price> {
	@Query("SELECT p FROM Price p INNER JOIN User u ON p.user.id = u.id WHERE u.id = :userId")
	List<Price> getAll(Pageable pageable, @Param("userId") int userId);

	@Query("SELECT new com.tungnx.home.dto.TotalPriceChartResponseDto(function('DATE_FORMAT', p.createdAt, '%Y'), function('DATE_FORMAT', p.createdAt, '%m'), p.total) FROM Price p " +
			"INNER JOIN User u ON p.user.id = u.id " +
			"WHERE u.id = :userId AND function('DATE_FORMAT', p.createdAt, '%Y') IN (:yearList)" +
			"ORDER BY p.createdAt ASC")
	List<TotalPriceChartResponseDto> getTotalPriceByYearAndMonth(@Param("userId") int userId, String[] yearList);

	@Query("SELECT new com.tungnx.home.dto.TotalElectricPriceChartDto(function('DATE_FORMAT', p.createdAt, '%Y'), function('DATE_FORMAT', p.createdAt, '%m'), p.electricPrice) FROM Price p " +
			"INNER JOIN User u ON p.user.id = u.id " +
			"WHERE u.id = :userId AND function('DATE_FORMAT', p.createdAt, '%Y') IN (:yearList)" +
			"ORDER BY p.createdAt ASC")
	List<TotalElectricPriceChartDto> getElectricPriceByYearAndMonth(@Param("userId") int userId, String[] yearList);


}
