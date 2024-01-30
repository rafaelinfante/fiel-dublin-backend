package ie.fieldublin.security.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class SecurityConstants {

    public static final String LOGIN_URL = "/auth/login";
    public static final long JWT_EXPIRATION_TIME = 7_200_000; // 2 hours
    public static final String JWT_SECRET = "8yA6YVhB9Qo1GScKqf8ua103DW05ZUe84USsGSeVxjwgbC6j8Oajj25klNtoQNGq";
    public static final String JWT_PREFIX = "Bearer ";

}
