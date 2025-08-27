package co.com.pragma.api.dto;

import java.util.Date;

public record UserDTO(String firstName,
                      String lastName,
                      Date birthDate,
                      String address,
                      String phoneNumber,
                      String email,
                      Integer baseSalary)  {
}
