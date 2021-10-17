package com.tungnx.home.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Data
public class CommentResponseDto {
	private BigInteger num;
	private int id;
	private String comment;

	private Date createdAt;

	private int totalLike;
	private String userName;
	private int depth;
	private int ancestor;
	private String avatar;

	public CommentResponseDto(BigInteger num, int id, String comment, Date createdAt, int totalLike, String userName, int depth, int ancestor, String avatar) {
		this.num = num;
		this.id = id;
		this.comment = comment;
		this.createdAt = createdAt;
		this.totalLike = totalLike;
		this.userName = userName;
		this.depth = depth;
		this.ancestor = ancestor;
		this.avatar = avatar;
	}

	private List<CommentResponseDto> childComments;
	private int total;
}
