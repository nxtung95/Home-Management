package com.tungnx.home.dto;

import lombok.Data;

import java.util.Map;

@Data
public class InsertCommentResponseDto {
	private int statusCode;
	private Map<String, Object> data;
}
