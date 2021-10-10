package com.tungnx.home.composite;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompositeCommentCluster implements Serializable {
	private int ancestor;
	private int descendant;
}
