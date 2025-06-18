package com.technical_test.encodelabs.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * Este es un 'Value Object' de manual, de ahora en adelante 'VO'
 * Dos VO con la misma cnfiguración son idénticos
 * Un VO es inmutable y se guarda como @embbedable en la clase a la que pertenece
 * Podría tener muchos métodos para funcionalidades deseadas como un override de equal,
 * un add o operaciones matemáticas con sus validaciones, etc..
 */
@Getter
@ToString
@Embeddable // permitimos que sea embebido en Product
public final class Money {
   
   private final BigDecimal amount;
   private final String currencyCode;
   // Voy a dejar por default el dólar
   private static final Currency DEFAULT_CURRENCY = Currency.getInstance("USD");
   
   /**
    * Este constructor vacío es para JPA y se inicialiazan las variables en null
    * porque JPA luego las pisa. Es privado porque no queremos que se utilice
    */
   private Money() {
      this.amount = null;
      this.currencyCode = null;
   }
   
   /**
    * Este es el contructor por defecto para este VO
    * los mensajes en las validaciones son hard-codeados ya que los mensajes que
    * gestiona el MessageService deberían aparecer al momento de dar la Response
    * Además no se debe inyectar un service en un VO
    * Por último podría reemplazar quizás los mensajes con simples funciones
    * por cada tipo que reciban un parámetro para personalizarlos, ejemplo:
    * (param) -> param + " must be positive"
    * Esto con motivo de permitir reutilización (DRY) en todos los escenarios posibles
    * Quizás lo ideal sería en función de la moneda, transformar el precio según
    * cotización o demás pero esto escapa a mis posibilidades actuales :)
    *
    * @param amount de tipo BigDecimal, positivo
    */
   public Money(BigDecimal amount) {
      
      Validate.notNull(amount, "Amount cannot be null");
      Validate.isInstanceOf(BigDecimal.class, amount, "Amount must be a valid BigDecimal");
      // permito que sea cero por si algo fuera gratis, pero realmente debería ser mayor a cero
      Validate.isTrue(amount.compareTo(BigDecimal.ZERO) >= 0, "Amount must be positive or zero");
      
      this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
      this.currencyCode = DEFAULT_CURRENCY.getCurrencyCode();
   }
   
}
