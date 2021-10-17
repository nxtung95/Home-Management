package com.tungnx.home.repository;

import com.tungnx.home.entity.RoomInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomInforRepository extends JpaRepository<RoomInfo, Integer> {

	@Query(value = "SELECT r " +
			"FROM RoomInfo r " +
			"JOIN FETCH r.students " +
			"INNER JOIN User u ON r.user.id = u.id " +
			"WHERE u.id = ?1 "
	)
	List<RoomInfo> getRoomInfor(int userId);
}
