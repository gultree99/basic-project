package com.goodplatform.insa.model;

public enum Role {
	GUEST(0),
	MASTER(1),
	ADMIN(2),
	BACKBONE(3),
	PROFESSOR(4),
	STUDENT(5),
	OUTSIDER(6);

	private int role;

	private Role(int role) {
		this.role = role;
	}

	public int getValue() {
		return role;
	}
}
