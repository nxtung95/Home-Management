package com.tungnx.home.repository;

import com.tungnx.home.entity.Price;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PriceRepository extends CrudRepository<Price, Integer> {
	@Query("SELECT p FROM Price p INNER JOIN User u ON p.user.id = u.id WHERE u.id = :userId ORDER BY p.date DESC")
	List<Price> getAll(Pageable pageable, @Param("userId") int userId);
}
