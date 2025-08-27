package co.com.pragma.api;

import co.com.pragma.model.user.User;
import co.com.pragma.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class Handler {

    private final UserUseCase userUseCase;

    public Mono<ServerResponse> saveUserCase(ServerRequest request) {
        return request.bodyToMono(User.class)
                .flatMap(user -> {
                    List<String> errors = validateUser(user);
                    if (!errors.isEmpty()) {
                        return ServerResponse.badRequest()
                                .bodyValue(Map.of("errors", errors));
                    }
                    return userUseCase.saveUser(user)
                            .flatMap(saved -> ServerResponse.ok().bodyValue(saved));
                });
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
