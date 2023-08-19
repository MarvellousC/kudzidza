package comdischem.dischem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record CustomerDTO(@Schema(defaultValue = "Joe") String firstName,
                          @Schema(defaultValue = "Green") String lastName,
                          String email,
                          String address,
                          String phone,
                          long orderId,
                          LocalDate orderDate,
                          BigDecimal totalAmount) {

}
