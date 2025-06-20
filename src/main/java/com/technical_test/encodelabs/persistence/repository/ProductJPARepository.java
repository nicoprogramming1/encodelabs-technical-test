package com.technical_test.encodelabs.persistence.repository;

import com.technical_test.encodelabs.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Este es el repositorio que se comunica con la DB, separando así responsabilidades
 * Hereda los métodos tradicionales del ORM como save, get, etc
 * Se definen también los custom de ser necesario como los que hay a continuación
 * ->
 * Al final los find de listas inactivas o activas o hacer una separación según estas
 * necesidades de negocio no fueron implementadas, sólo el finAll general
 * Pero es muy similar y aquí incluso dejo como sería la query
 */
public interface ProductJPARepository extends JpaRepository<ProductEntity, UUID> {
   
   // lista productos activos
   @Query("SELECT p FROM ProductEntity p WHERE p.isActive = true")
   List<ProductEntity> findActiveAll();
   
   // lista productos inactivos
   @Query("SELECT p FROM ProductEntity p WHERE p.isActive = false")
   List<ProductEntity> findInactiveAll();
   
   boolean existsByName(String name);  // JPA infiere la query
   
   @Query("SELECT p.isActive FROM ProductEntity p WHERE p.id = :id")
   boolean isActive(@Param("id") UUID id);
}
