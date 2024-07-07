package payload;

import java.util.Date;



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
	    private Long userId;
		private Long hotelId;
		private int rating;
		private String feedback;
		
	
}
