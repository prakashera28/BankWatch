package com.bankofamerica.utils;

import com.bankofamerica.config.ConfigManager;
import com.bankofamerica.constants.ApiConstants;
import com.bankofamerica.models.request.LoginRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class RequestBuilder {

    private static final Logger logger = LogManager.getLogger(RequestBuilder.class);
    private final ConfigManager config = ConfigManager.getInstance();

    public RequestSpecification buildLoginRequest(String username, String password) {
        LoginRequest body = LoginRequest.builder().username(username).password(password).rememberMe(false).clientId("web-client").build();
        logger.info("Building login request for user: {}", username);
        return base().setBody(body).build();
    }

    public RequestSpecification buildLoginRequest(String username, String password, boolean rememberMe) {
        LoginRequest body = LoginRequest.builder().username(username).password(password).rememberMe(rememberMe).clientId("web-client").build();
        return base().setBody(body).build();
    }

    public RequestSpecification buildLoginRequest(LoginRequest body) {
        return base().setBody(body).build();
    }

    public RequestSpecification buildTokenRequest(String authCode) {
        String body = String.format("{\"grant_type\":\"authorization_code\",\"code\":\"%s\",\"client_id\":\"web-client\"}", authCode);
        return new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl()).setBasePath(config.getTokenEndpoint())
                .setContentType(ContentType.JSON)
                .addHeader(ApiConstants.HEADER_ACCEPT, ApiConstants.ACCEPT_JSON)
                .addHeader(ApiConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString())
                .setBody(body).build();
    }

    public RequestSpecification buildRequestWithoutContentType(String username, String password) {
        LoginRequest body = LoginRequest.builder().username(username).password(password).build();
        return new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl()).setBasePath(config.getAuthEndpoint())
                .addHeader(ApiConstants.HEADER_ACCEPT, ApiConstants.ACCEPT_JSON)
                .addHeader(ApiConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString())
                .setBody(body).build();
    }

    public RequestSpecification buildRequestWithRawBody(String rawBody) {
        return new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl()).setBasePath(config.getAuthEndpoint())
                .setContentType(ContentType.JSON)
                .addHeader(ApiConstants.HEADER_ACCEPT, ApiConstants.ACCEPT_JSON)
                .addHeader(ApiConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString())
                .setBody(rawBody).build();
    }

    private RequestSpecBuilder base() {
        return new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl()).setBasePath(config.getAuthEndpoint())
                .setContentType(ContentType.JSON)
                .addHeader(ApiConstants.HEADER_ACCEPT, ApiConstants.ACCEPT_JSON)
                .addHeader(ApiConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString());
    }
}
