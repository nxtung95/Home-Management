package com.tungnx.home.entity;

import com.tungnx.home.composite.CompositeCommentCluster;
import lombok.Data;

import javax.persistence.*;

@Entity()
@Data
@IdClass(CompositeCommentCluster.class)
public class CommentClosure {
	@Id
	@Column(name = "ancestor", nullable = false)
	private int ancestor;

	@Id
	@Column(name = "descendant", nullable = false)
	private int descendant;

	@Column(name = "depth", nullable = false, columnDefinition = "integer default 0")
	private int depth;
}
