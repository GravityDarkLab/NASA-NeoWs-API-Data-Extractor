package com.darklab.asteroids.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.darklab.asteroids.dto.NeoWsResponse;

@Service
public class NeoWsService {

	@Value("${neows.api.key}")
	private String apiKey;
	// + "&api_key=" + apiKey
	private static final Logger logger = LoggerFactory.getLogger(NeoWsService.class);
	private static final String BASE_URL = "https://api.nasa.gov/neo/rest/v1/feed";
	private final RestTemplate restTemplate;

	public NeoWsService() {
		this.restTemplate = new RestTemplate();
	}

	public List<String> fetchDataAndExtractInfo(String startDate, String endDate, String infoType) {
		try {
			// 1. Date Validation
			if (!startDate.isEmpty() && !endDate.isEmpty()) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date parsedStartDate = dateFormat.parse(startDate);
				Date parsedEndDate = dateFormat.parse(endDate);

				long intervalMillis = parsedEndDate.getTime() - parsedStartDate.getTime();
				long maxIntervalMillis = 7L * 24L * 60L * 60L * 1000L; // 7 days in milliseconds
				if (intervalMillis > maxIntervalMillis) {
					return Collections.singletonList("Date interval must be within 7 days");
				}
			}
		} catch (ParseException ex) {
			logger.error("Invalid date format. Please use yyyy-MM-dd format for dates.");
			return Collections.singletonList("Invalid date format");
		}
		// 1. URL building
		String url = UriComponentsBuilder.fromHttpUrl(BASE_URL).queryParam("start_date", startDate)
				.queryParam("end_date", endDate).queryParam("api_key", apiKey).toUriString();
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

}
