package com.bankofamerica.utils;

import com.bankofamerica.constants.ApiConstants;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class ResponseValidator {

    private static final Logger logger = LogManager.getLogger(ResponseValidator.class);

    public void validateStatusCode(Response response, int expected) {
        int actual = response.getStatusCode();
        logger.info("Status code - Expected: {}, Actual: {}", expected, actual);
        Assert.assertEquals(actual, expected,
                String.format("Status code mismatch. Expected: %d, Actual: %d. Body: %s", expected, actual, response.getBody().asString()));
    }

    public void validateLoginResponse(Response response) {
        String token = response.jsonPath().getString("accessToken");
        Assert.assertNotNull(token, "accessToken must not be null");
        Assert.assertFalse(token.isEmpty(), "accessToken must not be empty");
        Integer exp = response.jsonPath().getInt("expiresIn");
        Assert.assertNotNull(exp, "expiresIn must not be null");
        Assert.assertTrue(exp > 0, "expiresIn must be > 0");
        Assert.assertNotNull(response.jsonPath().getString("tokenType"), "tokenType must not be null");
        logger.info("Login response valid. tokenType={}, expiresIn={}", response.jsonPath().getString("tokenType"), exp);
    }

    public void validateErrorResponse(Response response, String expectedCode) {
        String code = response.jsonPath().getString("errorCode");
        Assert.assertNotNull(code, "errorCode must not be null");
        if (expectedCode != null && !expectedCode.isEmpty())
            Assert.assertEquals(code, expectedCode, "Error code mismatch");
        String msg = response.jsonPath().getString("errorMessage");
        Assert.assertNotNull(msg, "errorMessage must not be null");
        Assert.assertFalse(msg.isEmpty(), "errorMessage must not be empty");
    }

    public void validateResponseTime(Response response, long maxMs) {
        long t = response.getTime();
        logger.info("Response time: {}ms (max: {}ms)", t, maxMs);
        Assert.assertTrue(t <= maxMs, String.format("Response time %dms exceeded limit %dms", t, maxMs));
    }

    public void validateSecurityHeaders(Response response) {
        Assert.assertNotNull(response.getHeader(ApiConstants.HEADER_X_FRAME_OPTIONS), "X-Frame-Options must be present");
        Assert.assertNotNull(response.getHeader(ApiConstants.HEADER_X_XSS_PROTECTION), "X-XSS-Protection must be present");
        Assert.assertNotNull(response.getHeader(ApiConstants.HEADER_STRICT_TRANSPORT_SECURITY), "Strict-Transport-Security must be present");
        Assert.assertNotNull(response.getHeader(ApiConstants.HEADER_CONTENT_SECURITY_POLICY), "Content-Security-Policy must be present");
        logger.info("All security headers validated");
    }

    public void validatePasswordNotInResponse(Response response, String password) {
        Assert.assertFalse(response.getBody().asString().contains(password), "Password must not appear in response body");
    }

    public void validateStatusCodeNot(Response response, int excluded) {
        Assert.assertNotEquals(response.getStatusCode(), excluded, "Status code must not be " + excluded);
    }
}
