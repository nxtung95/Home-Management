package com.tungnx.home.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CommentResponseDto {
	private final BigInteger num;
	private final int id;
	private final String comment;

	@Temporal(TemporalType.TIMESTAMP)
	private final Date createdAt;

	private final int totalLike;
	private final String userName;
	private final int depth;
	private final int ancestor;
	private final String avatar;

	private List<CommentResponseDto> childComments;
	private int total;
}
