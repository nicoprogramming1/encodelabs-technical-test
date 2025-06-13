package com.technical_test.encodelabs.dto;

import java.util.List;

/**
 * Es un record para mantener la inmutabilidad, además genera automáticamente el
 * boiler plate, siendo muy liviana pero con la seguridad de una clase
 * @param success se asigna automáticamente dependiendo si se llama success / failure
 * @param message de la operación
 * @param data con la información a presentar como un array, en caso de no devolver info se asigna []
 * @param <T> genérico, será el tipo de la data (List<ProductResponseDTO> por ej)
 */
public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
   
   public static <T> ApiResponse<List<T>> success(String message, List<T> data) {
      return new ApiResponse<>(true, message, data != null ? data : List.of());
   }
   
   public static <T> ApiResponse<List<T>> failure(String message) {
      return new ApiResponse<>(false, message, List.of());
   }
}
