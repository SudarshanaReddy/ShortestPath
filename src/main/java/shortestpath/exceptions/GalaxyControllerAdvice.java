package shortestpath.exceptions;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GalaxyControllerAdvice extends ResponseEntityExceptionHandler {

    private ApiError apiError;

    @ExceptionHandler(GalaxyException.class)
    public ResponseEntity<Object> handleOriginPlanetInvestmentsException(RuntimeException exception, WebRequest request){

        apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), LocalDateTime.now());

        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError error){

        return new ResponseEntity<>(error, error.getStatus());

    }
}
