package com.user_service.external.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user_service.entities.RatingDto;


@FeignClient(name = "RATING-SERVICE")
public interface RatingService {
	
	@GetMapping("ratings/user/{userId}")
	List<RatingDto> getAllRatingsOfUser(@PathVariable Long userId);

}
