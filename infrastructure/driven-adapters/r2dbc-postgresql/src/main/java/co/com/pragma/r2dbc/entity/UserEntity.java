package co.com.pragma.r2dbc.entity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("users")
public class UserEntity {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
    private String email;
    private Integer baseSalary;
}