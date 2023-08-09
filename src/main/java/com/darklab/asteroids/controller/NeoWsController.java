package com.darklab.asteroids.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

}
