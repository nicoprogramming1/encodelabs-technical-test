package com.technical_test.encodelabs.config;

import com.technical_test.encodelabs.model.Product;
import com.technical_test.encodelabs.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Se cargan por seeder 3 tipos de productos al inciiar la app mediante
 * CommandLineRunner. Los guardo a todos juntos mediante saveAll
 * y cada uno es creado mediante el static de Product (factory method)
 */
@Component
public class ProductSeeder implements CommandLineRunner {
   
   private final ProductRepository productRepository;
   
   public ProductSeeder(ProductRepository productRepository) {
      this.productRepository = productRepository;
   }
   
   private List<Product> getSeedProducts() {
      return List.of(
              Product.create("Camisa", "Camiseta de algodón color negro", new BigDecimal("19.99"), 50),
              Product.create("Pantalón", "Jean azul", new BigDecimal("40.00"), 10),
              Product.create("Zapatillas", "Zapatillas de lona blancas", new BigDecimal("15.50"), 100)
      );
   }
   
   @Override
   public void run(String... args) {
      if (productRepository.count() == 0) {
         List<Product> products = getSeedProducts();
         
         productRepository.saveAll(products);
         System.out.println("Productos creados por seed.");
      } else {
         System.out.println("Hay productos en DB, no se ejecuta seed.");
      }
   }
}
