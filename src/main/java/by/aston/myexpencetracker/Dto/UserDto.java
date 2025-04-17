package by.aston.myexpencetracker.Dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString(exclude = "password")
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String password;
    private BigDecimal balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(name, userDto.name) && Objects.equals(password, userDto.password) && Objects.equals(balance, userDto.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, balance);
    }
}
