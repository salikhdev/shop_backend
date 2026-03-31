package uz.salikhdev.shop_backend.exception;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.salikhdev.shop_backend.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<@NonNull ErrorResponse> handleAlreadyExistsException(Exception ex) {
        return ResponseEntity
                .status(409)
                .body(
                        ErrorResponse.builder()
                                .message(ex.getMessage())
                                .code(409)
                                .build()
                );
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<@NonNull ErrorResponse> handleNotFoundException(Exception ex) {
        return ResponseEntity
                .status(404)
                .body(
                        ErrorResponse.builder()
                                .message(ex.getMessage())
                                .code(404)
                                .build()
                );
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<@NonNull ErrorResponse> handleBadCredentialsException(Exception ex) {
        return ResponseEntity
                .status(400)
                .body(
                        ErrorResponse.builder()
                                .message(ex.getMessage())
                                .code(400)
                                .build()
                );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<@NonNull ErrorResponse> handleGenericException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(500)
                .body(
                        ErrorResponse.builder()
                                .message("An unexpected error occurred: " + ex.getMessage())
                                .code(500)
                                .build()
                );
    }

}
