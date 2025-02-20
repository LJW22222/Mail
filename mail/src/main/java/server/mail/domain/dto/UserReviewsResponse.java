package server.mail.domain.dto;

import lombok.Builder;
import lombok.Data;
import server.mail.domain.Review;

@Data
@Builder
public class UserReviewsResponse {

    private String content;
    private String product_name;


    public static UserReviewsResponse of(Review review) {
        return UserReviewsResponse.builder()
                .product_name(review.getProduct().getName())
                .content(review.getContent())
                .build();
    }
}
