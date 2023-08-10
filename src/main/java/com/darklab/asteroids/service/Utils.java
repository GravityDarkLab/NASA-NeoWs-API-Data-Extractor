package com.darklab.asteroids.service;

import java.util.*;

import com.darklab.asteroids.dto.NeoObject;
import com.darklab.asteroids.dto.NeoWsResponse;

/**
 * Utility class containing methods to extract and format data related to
 * asteroids obtained from NeoWs API.
 */
public class Utils {
	/**
	 * Extracts and formats the asteroids with the maximum and minimum diameter.
	 *
	 * @param response
	 *            The API response containing asteroid data.
	 * @return A list of formatted strings, with the first item representing the
	 *         asteroid(s) with the maximum diameter and the second item
	 *         representing the asteroid(s) with the minimum diameter.
	 */
	public static List<String> extractMaxMinDiameter(NeoWsResponse response) {
		List<NeoObject> allAsteroids = response.getNear_earth_objects().values().stream().flatMap(List::stream)
				.toList();
		OptionalDouble maxDiameterValue = allAsteroids.stream()
				.mapToDouble(asteroid -> asteroid.getEstimated_diameter().getKilometers().getEstimated_diameter_max())
				.max();
		OptionalDouble minDiameterValue = allAsteroids.stream()
				.mapToDouble(asteroid -> asteroid.getEstimated_diameter().getKilometers().getEstimated_diameter_min())
				.min();
		List<String> asteroidsWithMaxDiameter = allAsteroids.stream()
				.filter(asteroid -> asteroid.getEstimated_diameter().getKilometers()
						.getEstimated_diameter_max() == maxDiameterValue.getAsDouble())
				.map(NeoObject::getName).toList();
		List<String> asteroidsWithMinDiameter = allAsteroids.stream()
				.filter(asteroid -> asteroid.getEstimated_diameter().getKilometers()
						.getEstimated_diameter_min() == minDiameterValue.getAsDouble())
				.map(NeoObject::getName).toList();

		List<String> result = new ArrayList<>();
		result.add("Max Diameter: " + (maxDiameterValue.isPresent()
				? maxDiameterValue.getAsDouble() + " km - Asteroids: " + String.join(", ", asteroidsWithMaxDiameter)
				: "N/A"));
		result.add("Min Diameter: " + (minDiameterValue.isPresent()
				? minDiameterValue.getAsDouble() + " km - Asteroids: " + String.join(", ", asteroidsWithMinDiameter)
				: "N/A"));

		return result;
	}

	/**
	 * Extracts and formats the relative velocities of the asteroids.
	 *
	 * @param response
	 *            The API response containing asteroid data.
	 * @return A list of formatted strings where each string represents an asteroid
	 *         and its relative velocity in the format: [Asteroid Name]: [Velocity]
	 *         km/s.
	 */
	public static List<String> extractRelativeVelocity(NeoWsResponse response) {
		List<Pair<String, Double>> velocitiesWithNames = response.getNear_earth_objects().values().stream()
				.flatMap(List::stream)
				.flatMap(asteroid -> asteroid.getClose_approach_data().stream().map(
						data -> new Pair<>(asteroid.getName(), data.getRelative_velocity().getKilometers_per_second())))
				.filter(pair -> Objects.nonNull(pair.second())).sorted(Comparator.comparing(Pair::second)).toList();
		// Format each velocity value with its asteroid name
		return velocitiesWithNames.stream().map(pair -> String.format("%s: %.3f km/s", pair.first(), pair.second()))
				.toList();
	}

	/**
	 * Extracts and formats the miss distances of the asteroids.
	 *
	 * @param response
	 *            The API response containing asteroid data.
	 * @return A list of formatted strings where each string represents an asteroid
	 *         and its miss distance in the format: [Asteroid Name]: [Distance] km.
	 */
	public static List<String> extractMissDistances(NeoWsResponse response) {
		List<Pair<String, Double>> distances = response.getNear_earth_objects().values().stream().flatMap(List::stream)
				.flatMap(asteroid -> asteroid.getClose_approach_data().stream()
						.map(data -> new Pair<>(asteroid.getName(), data.getMiss_distance().getKilometers())))
				.filter(pair -> Objects.nonNull(pair.second())).sorted(Comparator.comparing(Pair::second)).toList();
		// Format each Miss distance with its asteroid name
		return distances.stream().map(pair -> String.format("%s: %.3f km", pair.first(), pair.second())).toList();
	}

}
