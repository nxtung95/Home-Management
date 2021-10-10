package com.tungnx.home.service.impl;

import com.tungnx.home.dto.CommentResponseDto;
import com.tungnx.home.repository.CommentRepository;
import com.tungnx.home.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<CommentResponseDto> getAllComments() {
		List<CommentResponseDto> result = new ArrayList<>();
		List<CommentResponseDto> comments = commentRepository.getAllComments();
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
		int total = result
				.stream()
				.mapToInt(outer -> outer.getChildComments().size() + 1) // + thêm chính phần tử outer
				.sum();
		result = result.stream()
				.sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
				.limit(5)
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
