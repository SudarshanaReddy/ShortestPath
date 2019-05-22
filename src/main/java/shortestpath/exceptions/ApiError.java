package shortestpath.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
class ApiError {

    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;

}
