package com.technical_test.encodelabs.persistence.mapper;

import com.technical_test.encodelabs.model.Money;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * El mapper del VO, se encarga de mapear sus dos atrributos
 * para que los tome el ProductMapper y los asigne de forma correcta
 * El named (decorador) establece un alias que puede ser llamado por qualifiedByName
 */
@Component
public class MoneyMapper {
   
   @Named("mapToAmount")
   public BigDecimal toAmount(Money money) {
      return money.getAmount();
   }
   
   @Named("mapToCurrencyCode")
   public String toCurrencyCode(Money money) {
      return money.getCurrencyCode();
   }
   
}
