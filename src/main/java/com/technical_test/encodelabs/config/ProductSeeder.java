package com.technical_test.encodelabs.config;

import com.technical_test.encodelabs.common.util.LogInfo;
import com.technical_test.encodelabs.dto.Product.ProductRequestDTO;
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
   private final LogInfo log;
   private final String className = this.getClass().getName(); // DONT REPEAT YOURSELF !!!
   
   public ProductSeeder(ProductRepository productRepository, LogInfo log) {
      this.productRepository = productRepository;
      this.log = log;
   }
   
   // separamos responsabilidades
   private List<Product> getSeedProducts() {
      
      List<ProductRequestDTO> seedingProducts = List.of(
              new ProductRequestDTO("Camisa", "Camiseta de algodón color negro", new BigDecimal("19.99"), 50),
              new ProductRequestDTO("Pantalón", "Jean azul", new BigDecimal("40.00"), 10),
              new ProductRequestDTO("Zapatillas", "Zapatillas de lona blancas", new BigDecimal("15.50"), 100)
      );
      
      List<Product> products = seedingProducts.stream().map(Product::create).toList();
      
      log.logInfoAction("seeder.created", products, className);
      return products;
   }
   
   @Override
   public void run(String... args) {
      if (productRepository.count() == 0) {
         List<Product> products = getSeedProducts();
         productRepository.saveAll(products);
         
         log.logInfoAction("seeder.inserted", products, className);
         System.out.println("Seeders successfully inserted");
      } else {
         // por más que con la config actual siempre se recrea la db, si cambiaramos la
         // estrategia ya queda esto funcional (debería se algo más interesante)
         System.out.println("Seeding not necessary");
      }
   }
}
