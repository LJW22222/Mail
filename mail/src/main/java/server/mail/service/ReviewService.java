package server.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.mail.Repository.ReviewRepository;
import server.mail.domain.Review;
import server.mail.domain.dto.ReviewsResponse;
import server.mail.domain.dto.UserReviewsResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Map<String, List<ReviewsResponse>> getProductReviews(String id) {
        Map<String, List<ReviewsResponse>> reviewsResponse = new HashMap<>();


        Optional<List<Review>> allByProductId = reviewRepository.findAllByProductIdOrderByUserNameAsc(Long.parseLong(id));
        List<Review> reviews = allByProductId.get();

        List<ReviewsResponse> list = reviews.stream()
                .map(ReviewsResponse::of)
                .toList();

        reviewsResponse.put("reviews", list);
        return reviewsResponse;
    }

    public Map<String, List<UserReviewsResponse>> getUserReviews(String id) {
        Map<String, List<UserReviewsResponse>> reviewsResponse = new HashMap<>();


        Optional<List<Review>> allByProductId = reviewRepository.findAllByUserIdOrderByProductNameAsc(Long.parseLong(id));
        List<Review> reviews = allByProductId.get();

        List<UserReviewsResponse> list = reviews.stream()
                .map(UserReviewsResponse::of)
                .toList();

        reviewsResponse.put("reviews", list);
        return reviewsResponse;
    }

}
