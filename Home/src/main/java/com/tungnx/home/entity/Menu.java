package com.tungnx.home.entity;

import javax.persistence.*;

@Entity
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = true)
	private int parentId;

	@Column(nullable = false)
	private String name;
}
