package server.mail.domain.dto;

import lombok.Builder;
import lombok.Data;
import server.mail.domain.Review;

@Data
@Builder
public class ReviewsResponse {

    private String content;
    private String username;


    public static ReviewsResponse of(Review review) {
        return ReviewsResponse.builder()
                .username(review.getUser().getName())
                .content(review.getContent())
                .build();
    }
}
