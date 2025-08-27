package co.com.pragma.usecase.user.exception;

public class exceptionEmailAlreadyExists extends RuntimeException {
    public exceptionEmailAlreadyExists(String email) {
        super("Ya existe un usuario con el correo: " + email);
    }
}
