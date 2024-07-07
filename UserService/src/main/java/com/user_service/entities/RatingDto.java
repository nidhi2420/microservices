package com.user_service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

	private String ratingId;
	private long userId;
	private long hotelId;
	private int rating;
	private String feedback;
	private Hotel hotel;
}
