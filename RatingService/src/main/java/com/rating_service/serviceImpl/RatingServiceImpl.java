package com.rating_service.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.bouncycastle.pqc.jcajce.provider.Rainbow;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.rating_service.entities.Rating;
import com.rating_service.exception.ResourceNotFoundException;
import com.rating_service.repository.RatingRepository;
import com.rating_service.service.RatingService;

import jakarta.annotation.PostConstruct;
import payload.RatingDto;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepository ratingRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	
	
	private Rating ratingDtoTORating(RatingDto ratingDto) {
		return this.modelMapper.map(ratingDto, Rating.class);
	}

	private RatingDto ratingTORatingDto(Rating rating) {
		return this.modelMapper.map(rating, RatingDto.class);
	}



	@Override
	public RatingDto createRating(RatingDto ratingDto) {
		Rating rating = ratingDtoTORating(ratingDto);
		rating.setAddedTime(new Date());
		this.ratingRepo.save(rating);
		return ratingTORatingDto(rating);
	}

	@Override
	public List<RatingDto> ratings() { 
		List<Rating> ratings = this.ratingRepo.findAll();
		List<RatingDto> ratingsDto = ratings.stream().map(this::ratingTORatingDto).collect(Collectors.toList());
		return ratingsDto;
	}

	@Override
	public List<RatingDto> getRatingsByUser(Long uid) {
		List<Rating> ratings = this.ratingRepo.findByUserId(uid);
		if (ratings.size() == 0)
			throw new ResourceNotFoundException("user", "id", uid);
		
		List<RatingDto> ratingDtos = ratings.stream().map(this::ratingTORatingDto).collect(Collectors.toList());
		return ratingDtos;
	}

	@Override
	public List<RatingDto> getRatingsOfHotel(Long hid) {
		List<Rating> ratings = this.ratingRepo.findByHotelId(hid);
		if (ratings.size() == 0)
			throw new ResourceNotFoundException("Hotel", "id", hid);
		List<RatingDto> ratingDtos = ratings.stream().map(this::ratingTORatingDto).collect(Collectors.toList());
	
		return ratingDtos;
	}

}
