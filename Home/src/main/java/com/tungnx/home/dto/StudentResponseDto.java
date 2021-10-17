package com.tungnx.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class StudentResponseDto {
	private String name;
	private String gender;
	private String phone;
	private String address;
	private Date stayDate;
}
