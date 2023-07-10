package com.dev.ecommerce.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dev.ecommerce.entities.Product;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void should_get_all_products() {
        Product productA = new Product();
        productA.setName("ProductA");
        productA.setPrice(5.0);
        Product productB = new Product();
        productB.setName("ProductB");
        productB.setPrice(5.0);
        productA = this.entityManager.persist(productA);
        productB = this.entityManager.persist(productB);
        this.entityManager.flush();
        List<Product> expected = new ArrayList<Product>();
        expected.add(productA);
        expected.add(productB);

        List<Product> actual = this.productRepository.findAll();

        assertIterableEquals(expected, actual);
    }
    
    @Test
    public void should_get_product_by_id() {
        Product productD = new Product();
        productD.setName("ProductC");
        productD.setPrice(5.0);
        Product expected = this.entityManager.persistAndFlush(productD);

        Product actual = this.productRepository.findById(expected.getId()).get();

        assertEquals(expected, actual);
    }

    @Test
    public void should_save_product() {
        Product product = new Product();
        product.setName("ProductD");
        product.setPrice(5.0);
        Product actual = this.productRepository.save(product);

        Product expected = this.entityManager.find(Product.class, actual.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void should_delete_product() {
        Product productD = new Product();
        productD.setName("ProductE");
        productD.setPrice(5.0);
        Product product = this.entityManager.persistAndFlush(productD);

        this.productRepository.deleteById(product.getId());
        Product actual = this.entityManager.find(Product.class, product.getId());

        assertEquals(null, actual);
    }
}