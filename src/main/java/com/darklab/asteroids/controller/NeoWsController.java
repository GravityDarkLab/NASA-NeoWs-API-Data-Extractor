package com.darklab.asteroids.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.darklab.asteroids.dto.Diameter;
import com.darklab.asteroids.service.NeoWsService;

@RestController
public class NeoWsController {

	@Autowired
	private NeoWsService neoWsService;

	@GetMapping("/")
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@GetMapping("/listMaxMinDiameter")
	public ModelAndView listMaxMinDiameter(@RequestParam String start_date, @RequestParam String end_date) {
		Optional<Object> result = fetchDataAndExtract(start_date, end_date, "maxMinDiameter", false);
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("result", result.orElseGet(ArrayList::new));
		return modelAndView;
	}

	@GetMapping("/listMaxMinDiameterJson")
	public ResponseEntity<?> listMaxMinDiameterJson(@RequestParam String start_date, @RequestParam String end_date) {
		return handleResponse(fetchDataAndExtract(start_date, end_date, "maxMinDiameter", true));
	}

	@GetMapping("/listRelativeVelocity")
	public ModelAndView listRelativeVelocity(@RequestParam String start_date, @RequestParam String end_date) {
		Optional<Object> result = fetchDataAndExtract(start_date, end_date, "relativeVelocity", false);
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("result", result.orElseGet(ArrayList::new));
		return modelAndView;
	}

	@GetMapping("/listRelativeVelocityJson")
	public ResponseEntity<?> listRelativeVelocityJson(@RequestParam String start_date, @RequestParam String end_date) {
		return handleResponse(fetchDataAndExtract(start_date, end_date, "relativeVelocity", true));
	}

	@GetMapping("/listMissDistance")
	public ModelAndView listMissDistance(@RequestParam String start_date, @RequestParam String end_date) {
		Optional<Object> result = fetchDataAndExtract(start_date, end_date, "missDistances", false);
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("result", result.orElseGet(ArrayList::new));
		return modelAndView;
	}

	@GetMapping("/listMissDistanceJson")
	public ResponseEntity<?> listMissDistanceJson(@RequestParam String start_date, @RequestParam String end_date) {
		return handleResponse(fetchDataAndExtract(start_date, end_date, "missDistances", true));
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleRuntimeException(RuntimeException e) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.error("Unexpected error occurred", e);
		return "Internal Server Error";
	}

	private Optional<Object> fetchDataAndExtract(String start_date, String end_date, String infoType, Boolean isJson) {
		if (isJson) {
			return neoWsService.fetchDataAndExtractInfoJson(start_date, end_date, infoType);
		}
		List<String> result = neoWsService.fetchDataAndExtractInfo(start_date, end_date, infoType);
		return Optional.ofNullable(result);
	}
	private ResponseEntity<?> handleResponse(Optional<Object> optionalResult) {
		if (optionalResult.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No data available for the given date range.");
		}

		Object result = optionalResult.get();

		if (result instanceof List<?>) {
			return ResponseEntity.ok(result);
		}
		if (result instanceof String) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		}
		if (result instanceof Diameter) { // Specific to Diameter, may be removed if not needed in future methods
			return ResponseEntity.ok(result);
		}
		// For any other unexpected data types or errors
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
	}

}
