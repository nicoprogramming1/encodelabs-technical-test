package com.technical_test.encodelabs.persistence.repository;

import com.technical_test.encodelabs.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Este es el repositorio que se comunica con la DB, separando así responsabilidades
 * Hereda los métodos tradicionales del ORM como save, get, etc
 * Se definen también los custom de ser necesario como el deactivate
 */
public interface ProductJPARepository extends JpaRepository<ProductEntity, UUID> {
   
   // lista productos activos
   @Query("SELECT p FROM ProductEntity p WHERE p.active = true")
   List<ProductEntity> findActiveAll();
   
   // lista productos inactivos
   @Query("SELECT p FROM ProductEntity p WHERE p.active = false")
   List<ProductEntity> findInactiveAll();
   
   @Query("SELECT p.active FROM ProductEntity p WHERE p.id = :id")
   boolean isActive(@Param("id") UUID id);
}
