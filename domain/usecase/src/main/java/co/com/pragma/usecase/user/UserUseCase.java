package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.user.exception.exceptionEmailAlreadyExists;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class UserUseCase implements UserMethodsRepository {
    private final UserRepository userRepository;

    @Override
    public Mono<User> saveUser(User user) {
        List<String> errors = validateUser(user);
        if (!errors.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Dominio : Errores en la validación de datos."));
        }

        return userRepository.findByEmail(user.getEmail())
                .flatMap(existingUser ->
                        Mono.<User>error(new exceptionEmailAlreadyExists("Ya existe un usuario con ese correo electrónico."))
                )
                .switchIfEmpty(
                        userRepository.saveUser(user)
                );
    }

    /**
     * Validation of request Data User
     *
     * @param user
     * @return
     */
    private List<String> validateUser(User user) {
        List<String> errors = new ArrayList<>();

        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            errors.add("El nombre es obligatorio");
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            errors.add("El apellido es obligatorio");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !emailValidation(user.getEmail())) {
            errors.add("Error en validación de correo electrónico.");
        }
        if (user.getBaseSalary() == null || user.getBaseSalary() <= 0 || user.getBaseSalary() >= 15000000) {
            errors.add("Error en validación de Salario Base.");
        }

        return errors;
    }

    /**
     * validamos el correo Electrónico
     *
     * @param emailRequest
     * @return
     */
    private boolean emailValidation(String emailRequest) {
        final Pattern EMAIL = Pattern.compile(
                "^(?=.{1,254}$)(?=.{1,64}@)[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+)*@(?:(?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,63}$",
                Pattern.CASE_INSENSITIVE
        );
        return EMAIL.matcher(emailRequest).matches();
    }
}
