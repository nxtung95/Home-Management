package com.tungnx.home.repository;

import com.tungnx.home.dto.CommentResponseDto;
import com.tungnx.home.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	@Query(nativeQuery=true)
	List<CommentResponseDto> getAllComments();

	@Procedure(procedureName = "insert_comment", outputParameterName = "commentId")
	int insert(int parentLevel, String comment, int userId, Timestamp date);
}
