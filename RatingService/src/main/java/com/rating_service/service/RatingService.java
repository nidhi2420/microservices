package com.rating_service.service;

import java.util.List;

import payload.RatingDto;

public interface RatingService {
	
	RatingDto createRating(RatingDto ratingDto);

	List<RatingDto> ratings();

	List<RatingDto> getRatingsByUser(Long uid);

	List<RatingDto> getRatingsOfHotel(Long hid);
}
