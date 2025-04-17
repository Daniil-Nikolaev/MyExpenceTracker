package by.aston.myexpencetracker.Entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private int id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private List<Category> categories;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && Objects.equals(description, that.description) && Objects.equals(amount, that.amount) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, amount, date);
    }
}
