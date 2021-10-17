package com.tungnx.home.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class RoomInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String roomPrice;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "roomInfo", cascade = CascadeType.ALL)
	@OrderBy(Student_.STAY_DATE + " ASC")
	private List<Student> students;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
