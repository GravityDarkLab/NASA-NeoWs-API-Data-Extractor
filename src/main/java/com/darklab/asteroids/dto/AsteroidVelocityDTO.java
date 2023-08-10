package com.darklab.asteroids.dto;

public class AsteroidVelocityDTO {
	private String name;
	private Double velocity;

	public AsteroidVelocityDTO(String name, Double velocity) {
		this.name = name;
		this.velocity = velocity;
	}

	@Override
	public String toString() {
		return "AsteroidVelocityDTO{" + "name='" + name + '\'' + ", velocity=" + velocity + '}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getVelocity() {
		return velocity;
	}

	public void setVelocity(Double velocity) {
		this.velocity = velocity;
	}
}
