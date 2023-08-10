package com.darklab.asteroids.service;

import java.util.*;

import com.darklab.asteroids.dto.*;

/**
 * Utility class containing methods to extract and format data related to
 * asteroids obtained from NeoWs API.
 */
public class Utils {

	/**
	 * Extracts and formats the asteroids with the maximum and minimum estimated
	 * diameters from the provided {@link NeoWsResponse}.
	 *
	 * <p>
	 * This method operates in the following sequence:
	 * </p>
	 *
	 * <ul>
	 * <li>Flattens the list of near-earth objects from the response into a single
	 * list of asteroids.</li>
	 * <li>Computes the maximum estimated diameter of all asteroids.</li>
	 * <li>Computes the minimum estimated diameter of all asteroids.</li>
	 * <li>Filters and collects the names of asteroids with the maximum estimated
	 * diameter.</li>
	 * <li>Similarly, filters and collects the names of asteroids with the minimum
	 * estimated diameter.</li>
	 * <li>Creates a formatted list with the maximum diameter, the asteroids with
	 * that diameter, the minimum diameter, and the asteroids with that minimum
	 * diameter.</li>
	 * <li>If no diameters are available, appropriate default values ("N/A") are
	 * used.</li>
	 * <li>Returns a list of formatted strings, where the first string is related to
	 * the maximum diameter and the second string is related to the minimum
	 * diameter.</li>
	 * </ul>
	 *
	 * @param response
	 *            The {@link NeoWsResponse} containing near-earth objects data.
	 * @return A list of formatted strings representing the asteroids with the
	 *         maximum and minimum estimated diameters in kilometers, along with the
	 *         names of those asteroids.
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
	 * Extracts the maximum and minimum estimated diameters of asteroids from the
	 * provided {@link NeoWsResponse}.
	 *
	 * <p>
	 * The method performs the following steps:
	 * </p>
	 *
	 * <ul>
	 * <li>Processes and flattens the list of near-earth objects from the response
	 * into a single list of asteroids.</li>
	 * <li>Iterates over each asteroid to find the maximum estimated diameter in
	 * kilometers.</li>
	 * <li>Similarly, iterates over each asteroid to find the minimum estimated
	 * diameter in kilometers.</li>
	 * <li>If there are no diameters available (i.e., the response doesn't contain
	 * any valid asteroid data), the default values of 0.0 are set.</li>
	 * <li>Returns a {@link Diameter} object containing the found max and min
	 * estimated diameters.</li>
	 * </ul>
	 *
	 * @param response
	 *            The {@link NeoWsResponse} containing near-earth objects data.
	 * @return A {@link Diameter} object representing the maximum and minimum
	 *         estimated diameters in kilometers of all the asteroids in the
	 *         provided response.
	 */
	public static Diameter extractMaxMinDiameterJson(NeoWsResponse response) {
		List<NeoObject> allAsteroids = response.getNear_earth_objects().values().stream().flatMap(List::stream)
				.toList();
		OptionalDouble maxDiameterValue = allAsteroids.stream()
				.mapToDouble(asteroid -> asteroid.getEstimated_diameter().getKilometers().getEstimated_diameter_max())
				.max();
		OptionalDouble minDiameterValue = allAsteroids.stream()
				.mapToDouble(asteroid -> asteroid.getEstimated_diameter().getKilometers().getEstimated_diameter_min())
				.min();
		Diameter diameter = new Diameter();
		diameter.setEstimated_diameter_max(maxDiameterValue.isPresent() ? maxDiameterValue.getAsDouble() : 0.0);
		diameter.setEstimated_diameter_min(minDiameterValue.isPresent() ? minDiameterValue.getAsDouble() : 0.0);
		return diameter;
	}

	/**
	 * Extracts and formats the relative velocities of asteroids from the provided
	 * {@link NeoWsResponse}.
	 *
	 * <p>
	 * This method performs the following operations:
	 * </p>
	 *
	 * <ul>
	 * <li>Processes the near-earth objects from the response and flattens the
	 * list.</li>
	 * <li>For each asteroid, retrieves its close approach data to obtain the
	 * relative velocity in kilometers per second.</li>
	 * <li>Creates a pair of the asteroid's name and its relative velocity.</li>
	 * <li>Filters out any pairs where the relative velocity is null.</li>
	 * <li>Sorts the resulting list of pairs by relative velocity in ascending
	 * order.</li>
	 * <li>Formats each velocity with the corresponding asteroid's name in the
	 * format "name: velocity km/s".</li>
	 * </ul>
	 *
	 * @param response
	 *            The response containing near-earth objects data.
	 * @return A sorted list of strings where each string represents an asteroid's
	 *         name and its relative velocity in the format "name: velocity km/s".
	 *         The list is sorted in ascending order based on the relative velocity.
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
	 * Extracts the relative velocities of asteroids from the provided
	 * {@link NeoWsResponse}.
	 *
	 * <p>
	 * The method processes the near-earth objects contained in the NeoWsResponse
	 * and, for each object, fetches its close approach data to obtain the relative
	 * velocity in kilometers per second. The resulting information is then
	 * transformed into a list of {@link AsteroidVelocityDTO} objects.
	 * </p>
	 *
	 * <ul>
	 * <li>It starts by flattening the list of near-earth objects.</li>
	 * <li>For each asteroid, the method retrieves its close approach data.</li>
	 * <li>It creates a new DTO for each asteroid with the asteroid's name and its
	 * relative velocity in km/s.</li>
	 * <li>The method filters out any DTOs where the relati ve velocity is
	 * null.</li>
	 * <li>The final list is sorted by the relative velocity in ascending
	 * order.</li>
	 * </ul>
	 *
	 * @param response
	 *            The response containing near-earth objects data.
	 * @return A sorted list of {@link AsteroidVelocityDTO} objects representing the
	 *         relative velocities of asteroids. The list is sorted in ascending
	 *         order based on the relative velocity.
	 */
	public static List<AsteroidVelocityDTO> extractRelativeVelocityJson(NeoWsResponse response) {
		return response.getNear_earth_objects().values().stream().flatMap(List::stream)
				.flatMap(asteroid -> asteroid.getClose_approach_data().stream()
						.map(data -> new AsteroidVelocityDTO(asteroid.getName(),
								data.getRelative_velocity().getKilometers_per_second())))
				.filter(dto -> Objects.nonNull(dto.getVelocity()))
				.sorted(Comparator.comparing(AsteroidVelocityDTO::getVelocity)).toList();
	}

	/**
	 * Extracts and formats the miss distances of asteroids from the provided
	 * {@link NeoWsResponse}.
	 *
	 * <p>
	 * This method performs the following operations:
	 * </p>
	 *
	 * <ul>
	 * <li>Flattens the list of near-earth objects from the response into a single
	 * list of asteroids.</li>
	 * <li>For each asteroid, iterates through its close approach data to extract
	 * the miss distance in kilometers.</li>
	 * <li>Creates pairs of asteroid names with their respective miss
	 * distances.</li>
	 * <li>Filters out any pairs where the miss distance is null.</li>
	 * <li>Sorts the pairs based on the miss distance.</li>
	 * <li>Formats each miss distance along with its asteroid name.</li>
	 * <li>Returns a list of formatted strings representing each asteroid's name
	 * followed by its miss distance in kilometers.</li>
	 * </ul>
	 *
	 * @param response
	 *            The {@link NeoWsResponse} containing near-earth objects data.
	 * @return A list of formatted strings representing the miss distances of
	 *         asteroids in kilometers, each prefixed with the asteroid's name.
	 */
	public static List<String> extractMissDistances(NeoWsResponse response) {
		List<Pair<String, Double>> distances = response.getNear_earth_objects().values().stream().flatMap(List::stream)
				.flatMap(asteroid -> asteroid.getClose_approach_data().stream()
						.map(data -> new Pair<>(asteroid.getName(), data.getMiss_distance().getKilometers())))
				.filter(pair -> Objects.nonNull(pair.second())).sorted(Comparator.comparing(Pair::second)).toList();
		// Format each Miss distance with its asteroid name
		return distances.stream().map(pair -> String.format("%s: %.3f km", pair.first(), pair.second())).toList();
	}

	/**
	 * Extracts the miss distances of asteroids from the provided
	 * {@link NeoWsResponse}.
	 *
	 * <p>
	 * The method processes the near-earth objects contained in the NeoWsResponse
	 * and, for each object, fetches its close approach data to obtain the miss
	 * distance in kilometers. The resulting information is then transformed into a
	 * list of {@link AsteroidMissDistanceDTO} objects.
	 * </p>
	 *
	 * <ul>
	 * <li>It first flattens the list of near-earth objects.</li>
	 * <li>For each asteroid, the method fetches its close approach data.</li>
	 * <li>It then creates a new DTO for each asteroid containing the asteroid's
	 * name and its miss distance in kilometers.</li>
	 * <li>The method filters out any DTOs where the miss distance is null.</li>
	 * <li>The final list is sorted by the miss distance in ascending order.</li>
	 * </ul>
	 *
	 * @param response
	 *            The response containing near-earth objects data.
	 * @return A sorted list of {@link AsteroidMissDistanceDTO} objects representing
	 *         the miss distances of asteroids. The list is sorted in ascending
	 *         order based on the miss distance.
	 */
	public static List<AsteroidMissDistanceDTO> extractMissDistancesJson(NeoWsResponse response) {
		return response.getNear_earth_objects().values().stream().flatMap(List::stream)
				.flatMap(asteroid -> asteroid.getClose_approach_data().stream()
						.map(data -> new AsteroidMissDistanceDTO(asteroid.getName(),
								data.getMiss_distance().getKilometers())))
				.filter(dto -> Objects.nonNull(dto.getDistance()))
				.sorted(Comparator.comparing(AsteroidMissDistanceDTO::getDistance)).toList();
	}

}
