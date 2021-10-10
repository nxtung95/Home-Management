package com.tungnx.home.entity;

import com.tungnx.home.dto.CommentResponseDto;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "comment")
@SqlResultSetMapping(
		name = "groupCommentMapping",
		classes = {
				@ConstructorResult(
						targetClass = CommentResponseDto.class,
						columns = {
								@ColumnResult(name = "num", type = BigInteger.class),
								@ColumnResult(name = "id", type = Integer.class),
								@ColumnResult(name = "comment", type = String.class),
								@ColumnResult(name = "createdAt", type = Date.class),
								@ColumnResult(name = "totalLike", type = Integer.class),
								@ColumnResult(name = "userName", type = String.class),
								@ColumnResult(name = "depth", type = Integer.class),
								@ColumnResult(name = "ancestor", type = Integer.class),
								@ColumnResult(name = "avatar", type = String.class)
						}
				)
		}
)
@NamedNativeQueries({
		@NamedNativeQuery(
				name = "Comment.getAllComments",
				query = "SELECT ROW_NUMBER() OVER(PARTITION BY cc.ancestor ORDER BY c.created_at ASC) as num, " +
						"c.id, c.comment, c.created_at as createdAt, c.total_like as totalLike, u.username as userName, cc.depth, cc.ancestor, u.avatar FROM comment c " +
						"INNER JOIN User u ON c.user_id = u.id " +
						"INNER JOIN comment_closure cc ON c.id = cc.descendant " +
						"WHERE cc.ancestor IN ( " +
						"SELECT sub_c.id FROM comment sub_c " +
						"INNER JOIN comment_closure sub_cc ON sub_c.id = sub_cc.descendant " +
						"WHERE sub_cc.ancestor = 0 " +
						"AND sub_cc.depth = 1) ",
				resultSetMapping = "groupCommentMapping"
		)
})
@Data
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "comment", nullable = false, length = 2000)
	private String comment;

	@Column(name = "total_like", columnDefinition = "Integer default 0")
	private int totalLike;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@Temporal(TemporalType.DATE)
	@Column(name = "updatedAt", nullable = false)
	private Date updatedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deletedAt", nullable = false)
	private Date deletedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
