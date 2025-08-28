package co.com.pragma.usecase.user.exception;

public class emailAlreadyExistsException extends RuntimeException {
    public emailAlreadyExistsException(String email) {
        super("Ya existe un usuario con el correo: " + email);
    }
}
