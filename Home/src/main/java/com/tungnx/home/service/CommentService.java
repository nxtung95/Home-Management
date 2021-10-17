package com.tungnx.home.service;

import com.tungnx.home.dto.CommentResponseDto;

import java.sql.Timestamp;
import java.util.List;

public interface CommentService {
	List<CommentResponseDto> getAllComments(int limit, int offset) throws Exception;

	int insert(int parentLevel, String comment, int userId, Timestamp date);
}
