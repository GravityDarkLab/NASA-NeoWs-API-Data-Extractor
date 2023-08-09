package com.darklab.asteroids.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;

import com.darklab.asteroids.dto.NeoObject;
import com.darklab.asteroids.dto.NeoWsResponse;

public class Utils {
	public static List<String> extractMaxMinDiameter(NeoWsResponse response) {
		List<NeoObject> allAsteroids = response.getNear_earth_objects().values().stream().flatMap(List::stream)
				.toList();
		OptionalDouble maxDiameter = allAsteroids.stream()
				.mapToDouble(asteroid -> asteroid.getEstimated_diameter().getKilometers().getEstimated_diameter_max())
				.max();
		OptionalDouble minDiameter = allAsteroids.stream()
				.mapToDouble(asteroid -> asteroid.getEstimated_diameter().getKilometers().getEstimated_diameter_min())
				.min();
		List<String> result = new ArrayList<>();
		result.add("Max Diameter: " + (maxDiameter.isPresent() ? maxDiameter.getAsDouble() : "N/A"));
		result.add("Min Diameter: " + (minDiameter.isPresent() ? minDiameter.getAsDouble() : "N/A "));
		return result;

	}

	public static List<String> extractRelativeVelocity(NeoWsResponse response) {
		List<Double> velocities = response.getNear_earth_objects().values().stream().flatMap(List::stream)
				.map(NeoObject::getClose_approach_data).filter(Objects::nonNull).flatMap(List::stream)
				.map(data -> data.getRelative_velocity().getKilometers_per_second()).sorted().toList();

		// Format each velocity value to display only 3 digits after the decimal point
		List<String> formattedVelocities = velocities.stream().map(velocity -> String.format("%.3f km/s", velocity))
				.toList();

		return formattedVelocities;
	}

	public static List<String> extractMissDistances(NeoWsResponse response) {
		List<Double> distances = response.getNear_earth_objects().values().stream().flatMap(List::stream) // Flatten the
																											// list of
																											// asteroids
				.map(NeoObject::getClose_approach_data).filter(Objects::nonNull) // Filter out null close approach data
				.flatMap(List::stream) // Flatten the list of close approach data
				.map(data -> data.getMiss_distance().getKilometers()).sorted().toList();
		// Format each miss distance value to display only 3 digits after the decimal
		// point
		List<String> formattedDistances = distances.stream().map(distance -> String.format("%.3f km", distance))
				.toList();

		return formattedDistances;
	}

}
