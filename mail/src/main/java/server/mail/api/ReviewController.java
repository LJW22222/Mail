package server.mail.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.mail.domain.dto.ReviewsResponse;
import server.mail.domain.dto.UserReviewsResponse;
import server.mail.service.ReviewService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getByCategoryFromProduct(@PathVariable(value = "id") String id,
                                                      @RequestParam(value = "page",required = false) String start,
                                                      @RequestParam(value = "page",required = false) String end) {

        Map<String, List<ReviewsResponse>> productReviews = reviewService.getProductReviews(id);
        return ResponseEntity.ok(productReviews);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getByCategoryFromUser(@PathVariable(value = "id") String id,
                                                   @RequestParam(value = "page",required = false) String start,
                                                   @RequestParam(value = "page",required = false) String end) {

        Map<String, List<UserReviewsResponse>> userReviews = reviewService.getUserReviews(id);
        return ResponseEntity.ok(userReviews);
    }


}
