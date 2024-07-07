package com.user_service.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user_service.entities.Hotel;
import com.user_service.entities.RatingDto;
import com.user_service.entities.User;
import com.user_service.exceptions.ResourceNotFoundException;
import com.user_service.external.services.HotelService;
import com.user_service.external.services.RatingService;
import com.user_service.repository.UserRepository;
import com.user_service.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RatingService ratingService;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {

		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// Implement RATING-SERVICE getAll rating API
		List<User> users = userRepository.findAll();
		List<LinkedHashMap<String, Object>> usersRatingsMap = restTemplate
				.getForObject("http://localhost:8083/ratings/", List.class);

		// Convert the list of LinkedHashMap to List of Rating
		List<RatingDto> usersRatings = new ArrayList<>();
		if (usersRatingsMap != null) {
			for (LinkedHashMap<String, Object> map : usersRatingsMap) {
				RatingDto rating = new RatingDto();
				rating.setRatingId((String) map.get("ratingId"));
				rating.setHotelId((long) map.get("hotelId"));
				rating.setRating((Integer) map.get("rating"));
				rating.setFeedback((String) map.get("feedback"));

				// Handle userId which could be either Integer or String
				Object userIdObj = map.get("userId");
				if (userIdObj instanceof Integer) {
					rating.setUserId((long)userIdObj);
				} else if (userIdObj instanceof String) {
					rating.setUserId((long) userIdObj);
				}

				usersRatings.add(rating);
			}
		}

		// Assign ratings to users
		for (User u : users) {
			List<RatingDto> usrRatings = new ArrayList<>();
			for (RatingDto r : usersRatings) {
				if (String.valueOf(u.getUserId()).equals(r.getUserId())) {
					usrRatings.add(r);
				}
			}
			u.setRatings(usrRatings);
		}

		return users;
	}

	// getting user from database:

	/*
	 * @Override public User getUser(Long userId) { User user =
	 * userRepository.findById(userId) .orElseThrow(() -> new
	 * ResourceNotFoundException("user not found with id : " + userId));
	 * 
	 * // fetch the rating of the above user from RATING SERVICE //
	 * http://localhost:8083/ratings/user/2 Rating [] ratings =
	 * restTemplate.getForObject("http://RATING-SERVICE/ratings/user/" + userId,
	 * Rating[].class); List<Rating>ratingOfUser =Arrays.stream(ratings).toList();
	 * 
	 * // [0;39m [2m:[0;39m [{ratingId=66811569697a713d458a0a8e, userId=2, //
	 * hotelId=1, rating=3, feedback=average hotel}] logger.info("{}",
	 * ratingOfUser);
	 * 
	 * 
	 * 
	 * ratingOfUser.stream().map(rating->{ //http://localhost:8082/hotels/3
	 * ResponseEntity<Hotel> forEntity =
	 * restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId()
	 * ,Hotel.class);
	 * logger.info("the hotels are {} :",forEntity.getBody().getId()); Hotel hotel =
	 * forEntity.getBody(); rating.setHotel(hotel); return rating; }).toList();
	 * 
	 * 
	 * user.setRatings(ratingOfUser); return user; }
	 */
	
	@Override
	public User getUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user not found with id : " + userId));

		
		/*
		 * RatingDto [] ratings =
		 * restTemplate.getForObject("http://RATING-SERVICE/ratings/user/" + userId,
		 * RatingDto[].class);
		 */
		List<RatingDto>ratingOfUser = this.ratingService.getAllRatingsOfUser(userId);
		
		// [0;39m [2m:[0;39m [{ratingId=66811569697a713d458a0a8e, userId=2,
		// hotelId=1, rating=3, feedback=average hotel}]
		logger.info("{}", ratingOfUser);
		
		
		
		  ratingOfUser.stream().map(rating->{ //http://localhost:8082/hotels/3
	
		  Hotel hotel = hotelService.getHotel(rating.getHotelId());
		  rating.setHotel(hotel);
		  return rating; }).toList();
		 
		
		user.setRatings(ratingOfUser);
		return user;
	}

}
