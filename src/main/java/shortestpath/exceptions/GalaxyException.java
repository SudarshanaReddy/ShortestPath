package shortestpath.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GalaxyException extends RuntimeException {

    public GalaxyException(String message) {
        super(message);
    }

}

