package server.mail.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.mail.domain.dto.ProductResponse;
import server.mail.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<?> getByProductList(
            @RequestParam(value = "sort",required = false) String sort) {
        Map<String, List<ProductResponse>> productList = productService.getProductList(sort);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByProductIdList(
            @PathVariable("id") Long st) {
        ProductResponse product = productService.getProduct(st);
        return ResponseEntity.ok(product);
    }



}
