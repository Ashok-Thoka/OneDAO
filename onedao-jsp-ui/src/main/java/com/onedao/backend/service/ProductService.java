package com.onedao.backend.service;

import com.onedao.backend.dto.ProductRequest;
import com.onedao.backend.dto.ProductResponse;
import com.onedao.backend.model.Product;
import com.onedao.backend.repository.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponse create(ProductRequest request) {
        Product product = new Product();
        apply(product, request);
        return toResponse(repository.save(product));
    }

    public Page<ProductResponse> findAll(String name, String category, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(
                Sort.Direction.fromString(direction),
                validateSortField(sortBy));

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Product> spec = Specification.where(null);

        if (name != null && !name.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%"));
        }

        if (category != null && !category.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("category")), category.trim().toLowerCase()));
        }

        return repository.findAll(spec, pageable).map(this::toResponse);
    }

    public ProductResponse findById(Long id) {
        return toResponse(repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found")));
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        apply(product, request);
        return toResponse(repository.save(product));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Product not found");
        }
        repository.deleteById(id);
    }

    private void apply(Product product, ProductRequest request) {
        product.setName(request.name().trim());
        product.setDescription(request.description().trim());
        product.setPrice(request.price());
        product.setCategory(request.category().trim());
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getCategory(),
                p.getCreatedAt(),
                p.getUpdatedAt());
    }

    private String validateSortField(String sortBy) {
        return switch (sortBy) {
            case "id", "name", "price", "category", "createdAt", "updatedAt" -> sortBy;
            default -> throw new IllegalArgumentException("Invalid sortBy field");
        };
    }
}
