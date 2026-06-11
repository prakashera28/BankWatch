package com.bankofamerica.constants;

public final class ApiConstants {

    private ApiConstants() {}

    public static final String BASE_URL = "https://secure.bankofamerica.com";
    public static final String BASE_URL_QA = "https://secure-qa.bankofamerica.com";
    public static final String AUTH_ENDPOINT = "/auth/login";
    public static final String TOKEN_ENDPOINT = "/auth/token";
    public static final String LOGOUT_ENDPOINT = "/auth/logout";
    public static final String REFRESH_TOKEN_ENDPOINT = "/auth/refresh";

    public static final int STATUS_OK = 200;
    public static final int STATUS_CREATED = 201;
    public static final int STATUS_NO_CONTENT = 204;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_METHOD_NOT_ALLOWED = 405;
    public static final int STATUS_CONFLICT = 409;
    public static final int STATUS_UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int STATUS_TOO_MANY_REQUESTS = 429;
    public static final int STATUS_INTERNAL_SERVER_ERROR = 500;
    public static final int STATUS_SERVICE_UNAVAILABLE = 503;

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_X_REQUEST_ID = "X-Request-ID";
    public static final String HEADER_X_CORRELATION_ID = "X-Correlation-ID";
    public static final String HEADER_X_FRAME_OPTIONS = "X-Frame-Options";
    public static final String HEADER_X_XSS_PROTECTION = "X-XSS-Protection";
    public static final String HEADER_STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
    public static final String HEADER_CONTENT_SECURITY_POLICY = "Content-Security-Policy";
    public static final String HEADER_X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";

    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String ACCEPT_JSON = "application/json";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE_BEARER = "Bearer";

    public static final String ERROR_INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String ERROR_ACCOUNT_LOCKED = "ACCOUNT_LOCKED";
    public static final String ERROR_INVALID_REQUEST = "INVALID_REQUEST";
    public static final String ERROR_RATE_LIMIT_EXCEEDED = "RATE_LIMIT_EXCEEDED";
    public static final String ERROR_UNSUPPORTED_MEDIA_TYPE = "UNSUPPORTED_MEDIA_TYPE";

    public static final long DEFAULT_RESPONSE_TIME_MS = 3000L;
    public static final long SLOW_RESPONSE_TIME_MS = 5000L;
    public static final int RATE_LIMIT_THRESHOLD = 5;
}
