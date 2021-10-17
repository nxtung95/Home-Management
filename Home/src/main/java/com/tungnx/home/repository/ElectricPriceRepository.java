package com.tungnx.home.repository;

import com.tungnx.home.entity.ElectricPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricPriceRepository extends JpaRepository<ElectricPrice, Integer>, JpaSpecificationExecutor<ElectricPrice> {

}
