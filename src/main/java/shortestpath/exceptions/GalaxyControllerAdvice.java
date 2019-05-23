package shortestpath.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GalaxyControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GalaxyException.class)
    public ResponseEntity<Object> handleGalaxyException(RuntimeException exception){

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), LocalDateTime.now());

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(GalaxyIOException.class)
    public ResponseEntity<Object> handleIOException(RuntimeException exception){

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), LocalDateTime.now());

        return buildResponseEntity(apiError);
    }



    private ResponseEntity<Object> buildResponseEntity(ApiError error){

        return new ResponseEntity<>(error, error.getStatus());

    }
}
