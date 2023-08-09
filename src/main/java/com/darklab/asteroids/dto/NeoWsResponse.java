package com.darklab.asteroids.dto;

import java.util.List;
import java.util.Map;

public class NeoWsResponse {
	private Map<String, List<NeoObject>> near_earth_objects;

	public Map<String, List<NeoObject>> getNear_earth_objects() {
		return near_earth_objects;
	}

	public void setNear_earth_objects(Map<String, List<NeoObject>> near_earth_objects) {
		this.near_earth_objects = near_earth_objects;
	}

}
