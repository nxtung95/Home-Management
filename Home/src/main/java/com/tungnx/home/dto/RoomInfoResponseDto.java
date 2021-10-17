package com.tungnx.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoomInfoResponseDto {
	private String name;
	private String roomPrice;
	private List<StudentResponseDto> studentResponseDtoSet;
}
