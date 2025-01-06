package com.ecommerce.demo.security;

public class SecurityConstants {
    public static final byte[] JWT_SECRET_KEY = ";JSJ;F;jlsdf273I,.sdld.cB8dsX,-O0".getBytes();
    public static final int SEVEN_DAY = 604800000; // in ms
    public static final int ONE_DAY = 86400000; // in ms
    public static final int ONE_HOUR = 3600000; // in ms
    public static final int ONE_MINUTE = 60000; // in ms
    public static final int TWENTY_SECONDS = 20000; // in ms
    public static final int TEN_SECONDS = 10000; // in ms
    public static final int FIVE_SECONDS = 5000; // in ms
    // lifespan for access token
    public static final long ACCESS_LIFESPAN_IN_MS = ONE_HOUR;
    // lifespan for refresh token
    public static final long REFRESH_LIFESPAN_IN_MS = SEVEN_DAY;
}
