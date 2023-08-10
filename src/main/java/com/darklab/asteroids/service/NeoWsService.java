package com.darklab.asteroids.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.darklab.asteroids.dto.NeoWsResponse;

import io.micrometer.common.util.StringUtils;

@Service
public class NeoWsService {
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final long MAX_INTERVAL_DAYS = 7L;

	@Value("${neows.api.key}")
	private String apiKey;
	// + "&api_key=" + apiKey
	private static final Logger logger = LoggerFactory.getLogger(NeoWsService.class);
	private static final String BASE_URL = "https://api.nasa.gov/neo/rest/v1/feed";
	private final RestTemplate restTemplate;

	public NeoWsService() {
		this.restTemplate = new RestTemplate();
	}

	/**
	 * Fetches asteroid data from NeoWs API for a specified date range and extracts
	 * information based on a given info type.
	 * <p>
	 * The method uses the given date range to query the NeoWs API for asteroid
	 * data. The info type parameter determines what kind of information should be
	 * extracted from the fetched data:
	 * <ul>
	 * <li>maxMinDiameter: Extracts and returns the maximum and minimum diameter of
	 * asteroids.</li>
	 * <li>relativeVelocity: Extracts and returns the relative velocities of
	 * asteroids.</li>
	 * <li>missDistances: Extracts and returns the miss distances of asteroids.</li>
	 * </ul>
	 * If the info type is not recognized, it returns a list containing the string
	 * "Invalid info type".
	 * </p>
	 * <p>
	 * The method performs date validation to ensure that the date interval is
	 * within 7 days. If the date format is invalid, or if there is an error in
	 * fetching data from the API, an appropriate error message is returned in the
	 * list.
	 * </p>
	 *
	 * @param startDate
	 *            The start date of the date range in the format "yyyy-MM-dd".
	 * @param endDate
	 *            The end date of the date range in the format "yyyy-MM-dd".
	 * @param infoType
	 *            The type of information to be extracted. Can be "maxMinDiameter",
	 *            "relativeVelocity", or "missDistances".
	 * @return A list of strings containing the extracted information. In case of
	 *         errors, the list contains a relevant error message.
	 * @throws IllegalArgumentException
	 *             if the startDate or endDate is not in the expected format, or if
	 *             the date interval is more than 7 days.
	 */
	public List<String> fetchDataAndExtractInfo(String startDate, String endDate, String infoType) {
		if (!isValidDateRange(startDate, endDate)) {
			return Collections.singletonList("Invalid date format");
		}
		// 1. URL building
		String url = buildUrl(startDate, endDate);
		try {
			// 2. Fetch data
			NeoWsResponse response = restTemplate.getForObject(url, NeoWsResponse.class);

			// 3. Validation
			if (response == null || response.getNear_earth_objects() == null) {
				logger.error("No data received from NeoWs for date range {} to {}", startDate, endDate);
				return Collections.singletonList("No data available");
			}

			// 4. Logging
			response.getNear_earth_objects().keySet().forEach(date -> logger.debug("Received data for date: {}", date));

			List<String> extractedInfo = new ArrayList<>();

			if ("maxMinDiameter".equals(infoType)) {
				extractedInfo = Utils.extractMaxMinDiameter(response);
				logger.info("Fetched max and min diameter data for date range {} to {}: {}", startDate, endDate,
						extractedInfo);
			} else if ("relativeVelocity".equals(infoType)) {
				extractedInfo = Utils.extractRelativeVelocity(response);
				logger.info("Fetched relative Velocity of asteroids data for date range {} to {}: {}", startDate,
						endDate, extractedInfo);
			} else if ("missDistances".equals(infoType)) {
				extractedInfo = Utils.extractMissDistances(response);
				logger.info("Fetched relative Velocity of asteroids data for date range {} to {}: {}", startDate,
						endDate, extractedInfo);
			} else {
				return Collections.singletonList("Invalid info type");
			}

			return extractedInfo;

		} catch (RestClientException ex) {
			// 5. Exception Handling
			logger.error("Error fetching data from NeoWs for date range {} to {}. Error: {}", startDate, endDate,
					ex.getMessage());
			return Collections.singletonList("Error fetching data");
		}
	}

	/**
	 * Fetches data related to near-earth objects for the specified date range from
	 * the NeoWs service and extracts specified information based on the given info
	 * type.
	 *
	 * <p>
	 * This method performs the following operations:
	 * </p>
	 *
	 * <ul>
	 * <li>Validates the provided date range before making the request.</li>
	 * <li>Constructs the appropriate URL for the NeoWs service.</li>
	 * <li>Fetches the near-earth objects data from the NeoWs service for the
	 * specified date range.</li>
	 * <li>Logs received data for debugging and traceability.</li>
	 * <li>Based on the provided info type, extracts and returns the relevant data:
	 * <ul>
	 * <li>"maxMinDiameter": Extracts and returns the maximum and minimum diameter
	 * of the asteroids.</li>
	 * <li>"relativeVelocity": Extracts and returns the relative velocities of the
	 * asteroids.</li>
	 * <li>"missDistances": Extracts and returns the miss distances of the
	 * asteroids.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 *
	 * <p>
	 * If any error occurs during the fetch operation, such as a
	 * RestClientException, the error is logged and an empty optional is returned.
	 * Similarly, if the response from NeoWs is null or incomplete, the method
	 * returns an empty optional.
	 * </p>
	 *
	 * @param startDate
	 *            The start date of the range for which data needs to be fetched, in
	 *            "YYYY-MM-DD" format.
	 * @param endDate
	 *            The end date of the range for which data needs to be fetched, in
	 *            "YYYY-MM-DD" format.
	 * @param infoType
	 *            The type of information to extract. Acceptable values are
	 *            "maxMinDiameter", "relativeVelocity", and "missDistances".
	 * @return An {@link Optional} containing the extracted information. If no data
	 *         can be extracted or an error occurs, returns an empty
	 *         {@link Optional}.
	 */
	public Optional<Object> fetchDataAndExtractInfoJson(String startDate, String endDate, String infoType) {
		if (!isValidDateRange(startDate, endDate)) {
			return Optional.empty();
		}

		String url = buildUrl(startDate, endDate);

		try {
			NeoWsResponse response = restTemplate.getForObject(url, NeoWsResponse.class);

			if (response == null || response.getNear_earth_objects() == null) {
				logger.error("No data received from NeoWs for date range {} to {}", startDate, endDate);
				return Optional.empty();
			}

			logReceivedData(response);

			switch (infoType) {
				case "maxMinDiameter" :
					Object extractedInfo = Utils.extractMaxMinDiameterJson(response);
					logger.info("Fetched max and min diameter data for date range {} to {}: {}", startDate, endDate,
							extractedInfo);
					return Optional.of(extractedInfo);
				case "relativeVelocity" :
					extractedInfo = Utils.extractRelativeVelocityJson(response);
					logger.info("Fetched relative Velocity of asteroids data for date range {} to {}: {}", startDate,
							endDate, extractedInfo);
					return Optional.of(extractedInfo);
				case "missDistances" :
					extractedInfo = Utils.extractMissDistancesJson(response);
					logger.info("Fetched relative Velocity of asteroids data for date range {} to {}: {}", startDate,
							endDate, extractedInfo);
					return Optional.of(extractedInfo);
				default :
					return Optional.empty();
			}

		} catch (RestClientException ex) {
			logger.error("Error fetching data from NeoWs for date range {} to {}. Error: {}", startDate, endDate,
					ex.getMessage());
			return Optional.empty();
		}
	}

	private boolean isValidDateRange(String startDate, String endDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		Date parsedStartDate = new Date();
		Date parsedEndDate = new Date();

		if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
			return true;
		} else if (StringUtils.isEmpty(startDate)) {
			try {
				parsedEndDate = dateFormat.parse(endDate);
				parsedStartDate = parsedEndDate; // Set startDate to endDate
			} catch (ParseException ex) {
				logger.error("Invalid end date format '{}'. Please use {} format for dates.", endDate, DATE_FORMAT);
				return false;
			}
		} else if (StringUtils.isEmpty(endDate)) {
			try {
				parsedStartDate = dateFormat.parse(startDate);
				parsedEndDate = parsedStartDate; // Set endDate to startDate
			} catch (ParseException ex) {
				logger.error("Invalid start date format '{}'. Please use {} format for dates.", startDate, DATE_FORMAT);
				return false;
			}
		} else {
			try {
				parsedStartDate = dateFormat.parse(startDate);
				parsedEndDate = dateFormat.parse(endDate);
			} catch (ParseException ex) {
				logger.error("Invalid date format for '{}' or '{}'. Please use {} format for dates.", startDate,
						endDate, DATE_FORMAT);
				return false;
			}
		}

		long intervalMillis = parsedEndDate.getTime() - parsedStartDate.getTime();
		long maxIntervalMillis = TimeUnit.DAYS.toMillis(MAX_INTERVAL_DAYS);

		if (intervalMillis < 0 || intervalMillis > maxIntervalMillis) {
			logger.error("Date interval must be within 0 to {} days", MAX_INTERVAL_DAYS);
			return false;
		}

		return true;
	}

	private String buildUrl(String startDate, String endDate) {
		return UriComponentsBuilder.fromHttpUrl(BASE_URL).queryParam("start_date", startDate)
				.queryParam("end_date", endDate).queryParam("api_key", apiKey).toUriString();
	}

	private void logReceivedData(NeoWsResponse response) {
		response.getNear_earth_objects().keySet().forEach(date -> logger.debug("Received data for date: {}", date));
	}

}
