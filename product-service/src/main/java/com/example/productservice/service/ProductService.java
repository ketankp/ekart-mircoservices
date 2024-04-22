package com.example.productservice.service;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse createProduct(ProductRequest productRequest){
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElse(null);

        Product product = Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .productDescription(productRequest.getDescription())
                .categoryId(category)
                .build();
        productRepository.save(product);
        return ProductResponse.builder()
                .name(product.getProductName())
                .id(product.getProductId())
                .description(product.getProductDescription())
                .price(product.getPrice())
                .category(product.getCategoryId().getName())
                .build();
    }

    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll().stream().map( this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getProductId())
                .name(product.getProductName())
                .description(product.getProductDescription())
                .price(product.getPrice())
                .category(product.getCategoryId().getName())
                .build();
    }
}
