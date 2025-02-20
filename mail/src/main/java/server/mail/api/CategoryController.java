package server.mail.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.mail.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ProductService productService;

    //카테고리 id로 제품의 이름과 가격 목록을 조회, 카테고리를 기준으로 오름차순
    @GetMapping("/{id}")
    public ResponseEntity<?> getByCategoryFromProduct(
            @PathVariable(value = "id", required = false) String name) {
        Map<String, String> category = productService.getCategory(Long.parseLong(name));
        return ResponseEntity.ok(category);
    }

    //카테고리 id로 제품의 이름과 가격 목록을 조회, 카테고리를 기준으로 오름차순
    @GetMapping("")
    public ResponseEntity<?> getByCategoryFromProductList(
            @RequestParam(value = "sort" ,required = false) String st) {
        Map<String, List<String>> stringListMap = productService.getCategorySort(st);
        return ResponseEntity.ok(stringListMap);
    }
}
