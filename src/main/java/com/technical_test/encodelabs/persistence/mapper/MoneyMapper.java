package com.technical_test.encodelabs.persistence.mapper;

import com.technical_test.encodelabs.model.Money;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MoneyMapper {
   
   @Named("mapToMoney")
   public Money toMoney(BigDecimal amount) {
      return new Money(amount);
   }
   
   @Named("mapToAmount")
   public BigDecimal toAmount(Money money) {
      return money.getAmount();
   }
   
   @Named("mapToCurrencyCode")
   public String toCurrencyCode(Money money) {
      return money.getCurrencyCode();
   }
   
}
