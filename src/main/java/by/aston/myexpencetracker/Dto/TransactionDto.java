package by.aston.myexpencetracker.Dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private List<CategoryDto> categories;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDto that = (TransactionDto) o;
        return Objects.equals(description, that.description) && Objects.equals(amount, that.amount) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, amount, date);
    }
}
