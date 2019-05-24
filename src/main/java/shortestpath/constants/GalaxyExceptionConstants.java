package shortestpath.constants;

import lombok.NoArgsConstructor;


public class GalaxyExceptionConstants {

    private GalaxyExceptionConstants() {

    }
    public static final String ORIGIN_PLANET_IS_NULL = "Origin Planet is null";
    public static final String DESTINATION_PLANET_IS_NULL = "Destination Planet is null";
    public static final String SOURCE_PLANET_AND_DESTINATION_PLANET_CANNOT_BE_SAME = "Passed in source planet and destination planet cannot be same";
    public static final String GALAXY_NOT_FOUND = "Galaxy Not Found, please use the create galaxy service to create the galaxy first";
    public static final String ADD_GALAXY_EXCEPTION = "Error When Adding the galaxy";
    public static final String PLANET_CANNOT_BE_NULL = "Planet cannot be null";

}
