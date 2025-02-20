package server.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.mail.Repository.CategoryRepository;
import server.mail.Repository.ProductRepository;
import server.mail.domain.Category;
import server.mail.domain.Product;
import server.mail.domain.dto.ProductResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    //1번
    public Map<String, List<ProductResponse>> getProductList(String st) {
        System.out.println(st);
        Map<String, List<ProductResponse>> productList = new HashMap<>();
        Optional<List<Product>> allOrder = Optional.empty();
        if (st == null || st.equals("id")) {
            allOrder = productRepository.findAllOrderById();
        } else if(st.equals("name")){
            allOrder = productRepository.findAllOrderByName();
        } else if (st.equals("price")) {
            allOrder = productRepository.findAllOrderByPrice();
        }
        List<Product> products = allOrder.get();

        List<ProductResponse> list = products.stream()
                .map(ProductResponse::of)
                .toList();

        productList.put("products", list);

        return productList;
    }

    //2번
    public ProductResponse getProduct(Long id) {
        Optional<Product> allById = productRepository.findAllById(id);

        Product product = allById.get();
        return ProductResponse.of(product);
    }


    //3번 뭐임 대체
//    public Map<String, List<ProductResponse>> getProductListFromCategory(String id) {
//        Map<String, List<ProductResponse>> productList = new HashMap<>();
//
//        Optional<List<Product>> byCategoryId = productRepository.findByCategory_IdOrderByCategory_IdAsc(id);
//        List<Product> products = byCategoryId.get();
//
//        List<ProductResponse> list = products.stream()
//                .map(ProductResponse::of)
//                .toList();
//
//        productList.put("products", list);
//        return productList;
//    }

    //4번
    public Map<String, List<String>> getCategorySort(String sort) {
        Map<String, List<String>> productList = new HashMap<>();
        Optional<List<Category>> categoryList = Optional.empty();
        if (sort == null || sort.equals("id")) {
            categoryList = categoryRepository.findCategoryListNoSort();
        } else if(sort.equals("name")){
            categoryList = categoryRepository.findAllCategoryByName();
        }
        List<Category> categories = categoryList.get();
        List<String> list = categories.stream()
                .map(Category::getName).toList();
        productList.put("products", list);
        return productList;
    }

    //5번
    public Map<String, String> getCategory(Long id) {
        Map<String, String> categoryResponse = new HashMap<>();
        Optional<String> category = categoryRepository.findCategory(id);

        String s = category.get();
        categoryResponse.put("name", s);
        return categoryResponse;
    }

}
