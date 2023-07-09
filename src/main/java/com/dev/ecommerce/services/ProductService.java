package com.dev.ecommerce.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.dev.ecommerce.dto.ProductRequestDTO;
import com.dev.ecommerce.dto.ProductResponseDTO;
import com.dev.ecommerce.entities.Product;
import com.dev.ecommerce.repositories.ProductRepository;
import com.dev.ecommerce.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponseDTO> findAllProducts() {
        List<Product> result = this.productRepository.findAll();
        return result.stream().map(e -> new ProductResponseDTO(e)).collect(Collectors.toList());
    }

    public ProductResponseDTO findProductById(Long id) {
        Optional<Product> result = this.productRepository.findById(id);
        Product product = result.orElseThrow(() -> new ResourceNotFoundException(String.format("Product id %s not found!", id))); 
        return new ProductResponseDTO(product);
    }

    public ProductResponseDTO insertProduct(ProductRequestDTO request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        Product result = this.productRepository.save(product);
        return new ProductResponseDTO(result); 
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
        try {
            Product product = this.productRepository.getReferenceById(id);
            product.setName(request.getName());
            product.setPrice(request.getPrice());

            Product result = this.productRepository.save(product);
            return new ProductResponseDTO(result);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Product id %s not found!", id)); 
        }
    }

    public void deleteProduct(Long id) {
        try {
            this.productRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(String.format("Product id %s not found!", id)); 
        }
    }
}
