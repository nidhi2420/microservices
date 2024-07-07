package com.rating_service.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rating_service.service.RatingService;

import payload.RatingDto;



@RestController
@RequestMapping("/ratings")
public class RatingController {

	@Autowired
	private RatingService ratingService;

	@PutMapping("/")
	public ResponseEntity<RatingDto> create(@RequestBody RatingDto ratingDto) {
		return new ResponseEntity<RatingDto>(this.ratingService.createRating(ratingDto), HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<RatingDto>> getAllRatings() {
		return new ResponseEntity<List<RatingDto>>(this.ratingService.ratings(), HttpStatus.OK);
	}

	@GetMapping("/user/{uid}")
	public ResponseEntity<List<RatingDto>> getAllRatingsByUser(@PathVariable Long uid) {
		return new ResponseEntity<List<RatingDto>>(this.ratingService.getRatingsByUser(uid), HttpStatus.OK);
	}

	@GetMapping("/hotel/{hid}")
	public ResponseEntity<List<RatingDto>> getAllRatingsHotel(@PathVariable Long hid) {
		return new ResponseEntity<List<RatingDto>>(this.ratingService.getRatingsOfHotel(hid), HttpStatus.OK);
	}

}