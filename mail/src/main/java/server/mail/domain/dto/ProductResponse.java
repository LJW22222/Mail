package server.mail.domain.dto;

import lombok.Builder;
import lombok.Data;
import server.mail.domain.Product;

@Data
@Builder
public class ProductResponse {

    private final String name;

    private final int price;



    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

}
