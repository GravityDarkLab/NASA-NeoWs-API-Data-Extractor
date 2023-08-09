package com.darklab.asteroids.dto;

import java.util.List;

public class NeoObject {
	private String id;
	private String name;
	private double absolute_magnitude_h;
	private EstimatedDiameter estimated_diameter;
	private Boolean is_potentially_hazardous_asteroid;
	private List<CloseApproachData> close_approach_data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAbsolute_magnitude_h() {
		return absolute_magnitude_h;
	}

	public void setAbsolute_magnitude_h(double absolute_magnitude_h) {
		this.absolute_magnitude_h = absolute_magnitude_h;
	}

	public EstimatedDiameter getEstimated_diameter() {
		return estimated_diameter;
	}

	public void setEstimated_diameter(EstimatedDiameter estimated_diameter) {
		this.estimated_diameter = estimated_diameter;
	}

	public Boolean getIs_potentially_hazardous_asteroid() {
		return is_potentially_hazardous_asteroid;
	}

	public void setIs_potentially_hazardous_asteroid(Boolean is_potentially_hazardous_asteroid) {
		this.is_potentially_hazardous_asteroid = is_potentially_hazardous_asteroid;
	}

	public List<CloseApproachData> getClose_approach_data() {
		return close_approach_data;
	}

	public void setClose_approach_data(List<CloseApproachData> close_approach_data) {
		this.close_approach_data = close_approach_data;
	}

	@Override
	public String toString() {
		return "NeoObject{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", absolute_magnitude_h="
				+ absolute_magnitude_h + ", estimated_diameter=" + estimated_diameter
				+ ", is_potentially_hazardous_asteroid=" + is_potentially_hazardous_asteroid + ", close_approach_data="
				+ close_approach_data + '}';
	}
}
