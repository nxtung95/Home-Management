package com.tungnx.home.service.impl;

import com.tungnx.home.dto.CommentResponseDto;
import com.tungnx.home.repository.CommentRepository;
import com.tungnx.home.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "comments")
	public List<CommentResponseDto> getAllComments(int limit, int offset) throws Exception {
		List<CommentResponseDto> result = new ArrayList<>();
		List<CommentResponseDto> comments = commentRepository.getAllComments();
		int total = comments.size();
		if (total <= offset) {
			throw new Exception("limit-offset over total_comment");
		}
		List<CommentResponseDto> childComments = new ArrayList<>();

		int size = comments.size();
		for (int i = 0; i < size; i++) {
			CommentResponseDto comment = comments.get(i);
			boolean isParentComment = comment.getDepth() == 0;
			if (isParentComment) {
				for (int j = i + 1; j < size; j++) {
					boolean isNextParentComment = comments.get(j).getDepth() == 0;
					if (isNextParentComment) {
						break;
					}
					childComments.add(comments.get(j));
				}
				comment.setChildComments(childComments);
				i += childComments.size();
				result.add(comment);
				childComments = new ArrayList<>();
			}
		}
		result = result.stream()
				.sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
				.skip(offset)
				.limit(limit)
				.collect(Collectors.toList());
		result.get(0).setTotal(total);
		return result;
	}

	@Override
	@Transactional
	public int insert(int parentLevel, String comment, int userId, Timestamp date) {
		return commentRepository.insert(parentLevel, comment, userId, date);
	}
}
