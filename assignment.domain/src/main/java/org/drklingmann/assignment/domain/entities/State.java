package org.drklingmann.assignment.domain.entities;

public enum State {
	CREATED ("Created"),
	DELETED ("Deleted"),
	VERIFIED ("Verified"),
	REJECTED ("Rejected"),
	ACCEPTED ("Accepted"),
	PUBLISHED ("Published");
	
	private final String name;
	State(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
