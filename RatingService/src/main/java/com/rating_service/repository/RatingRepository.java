package com.rating_service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rating_service.entities.Rating;
@Repository
public interface RatingRepository extends MongoRepository<Rating, Long> {
	
	List<Rating> findByUserId(Long uid);

	List<Rating> findByHotelId(Long hid);
}
