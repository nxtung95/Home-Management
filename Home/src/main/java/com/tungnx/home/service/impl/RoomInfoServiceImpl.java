package com.tungnx.home.service.impl;

import com.tungnx.home.dto.RoomInfoResponseDto;
import com.tungnx.home.dto.StudentResponseDto;
import com.tungnx.home.entity.RoomInfo;
import com.tungnx.home.entity.Student_;
import com.tungnx.home.repository.RoomInforRepository;
import com.tungnx.home.service.RoomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomInfoServiceImpl implements RoomInfoService {
	@Autowired
	private RoomInforRepository roomInforRepository;

	@Override
	@Transactional(readOnly = true)
	public RoomInfoResponseDto getRoomInfo(int userId) {
		List<RoomInfo> roomInfos = roomInforRepository.getRoomInfor(userId);

		Optional<RoomInfoResponseDto> roomInfoResponse = roomInfos
				.stream()
				.map(roomInfo -> new RoomInfoResponseDto(
						roomInfo.getName(),
						roomInfo.getRoomPrice(),
						roomInfo.getStudents()
								.stream()
								.map(student -> new StudentResponseDto(student.getName(), student.getGender(), student.getPhone(), student.getAddress(), student.getStayDate()))
								.collect(Collectors.toList())
				))
				.findAny();
		return roomInfoResponse.get();
	}
}
