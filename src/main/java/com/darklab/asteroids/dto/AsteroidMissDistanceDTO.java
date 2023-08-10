package com.darklab.asteroids.dto;

public class AsteroidMissDistanceDTO {
	private String name;
	private Double distance;

	public AsteroidMissDistanceDTO(String name, Double distance) {
		this.name = name;
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "AsteroidMissDistanceDTO{" + "name='" + name + '\'' + ", distance=" + distance + '}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
}
