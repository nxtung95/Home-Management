package com.tungnx.home.dto;

import lombok.Data;

@Data
public class InsertCommentRequestDto {
	private String comment;
	private int parentLevel;
}
