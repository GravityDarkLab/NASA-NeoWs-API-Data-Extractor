package com.darklab.asteroids.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
		List<String> result = neoWsService.fetchDataAndExtractInfo(start_date, end_date, "maxMinDiameter");

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("result", result);

		return modelAndView;
	}
	@GetMapping("/listRelativeVelocity")
	public ModelAndView listRelativeVelocity(@RequestParam String start_date, @RequestParam String end_date) {
		List<String> result = neoWsService.fetchDataAndExtractInfo(start_date, end_date, "relativeVelocity");

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("result", result);

		return modelAndView;
	}
	@GetMapping("/listMissDistance")
	public ModelAndView listMissDistance(@RequestParam String start_date, @RequestParam String end_date) {
		List<String> result = neoWsService.fetchDataAndExtractInfo(start_date, end_date, "missDistances");
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("result", result);

		return modelAndView;
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleRuntimeException(RuntimeException e) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.error("Unexpected error occurred", e);
		return "Internal Server Error";
	}

}
