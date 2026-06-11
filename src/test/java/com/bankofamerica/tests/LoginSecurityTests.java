package com.bankofamerica.tests;

import com.bankofamerica.base.BaseTest;
import com.bankofamerica.constants.ApiConstants;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

@Epic("Bank of America Login API")
@Feature("Login - Security Scenarios")
public class LoginSecurityTests extends BaseTest {

    @Test(groups = {"regression", "security"})
    @Story("Injection attacks") @Severity(SeverityLevel.BLOCKER)
    @Description("SQL injection in username must not return 200")
    public void testSQLInjectionInUsername() {
        List<String> payloads = testDataManager.getSQLInjectionPayloads();
        assertFalse(payloads.isEmpty(), "SQL payloads must be defined");
        for (String p : payloads) {
            Response r = given().spec(requestBuilder.buildLoginRequest(p, "Test@Password1")).when().post().then().extract().response();
            assertNotEquals(r.getStatusCode(), ApiConstants.STATUS_OK, "SQL injection '" + p + "' must not return 200");
        }
    }

    @Test(groups = {"regression", "security"})
    @Story("Injection attacks") @Severity(SeverityLevel.BLOCKER)
    @Description("SQL injection in password must not return 200")
    public void testSQLInjectionInPassword() {
        for (String p : testDataManager.getSQLInjectionPayloads()) {
            Response r = given().spec(requestBuilder.buildLoginRequest("testuser001", p)).when().post().then().extract().response();
            assertNotEquals(r.getStatusCode(), ApiConstants.STATUS_OK, "SQL injection in password '" + p + "' must not return 200");
        }
    }

    @Test(groups = {"regression", "security"})
    @Story("Injection attacks") @Severity(SeverityLevel.BLOCKER)
    @Description("XSS payloads in username must not return 200")
    public void testXSSInUsername() {
        for (String p : testDataManager.getXSSPayloads()) {
            Response r = given().spec(requestBuilder.buildLoginRequest(p, "Test@Password1")).when().post().then().extract().response();
            assertNotEquals(r.getStatusCode(), ApiConstants.STATUS_OK, "XSS '" + p + "' must not return 200");
        }
    }

    @Test(groups = {"regression", "security"})
    @Story("Security headers") @Severity(SeverityLevel.CRITICAL)
    @Description("Login response must include all required security headers")
    public void testResponseContainsSecurityHeaders() {
        String[] c = testDataManager.getFirstValidCredentials();
        Response r = given().spec(requestBuilder.buildLoginRequest(c[0], c[1])).when().post().then().extract().response();
        responseValidator.validateSecurityHeaders(r);
    }

    @Test(groups = {"regression", "security"})
    @Story("Data exposure") @Severity(SeverityLevel.BLOCKER)
    @Description("Password must never appear in response body")
    public void testPasswordNotInResponse() {
        String[] c = testDataManager.getFirstValidCredentials();
        Response r = given().spec(requestBuilder.buildLoginRequest(c[0], c[1])).when().post().then().extract().response();
        assertFalse(r.getBody().asString().contains(c[1]), "Response body must not contain plain-text password");
    }

    @Test(groups = {"regression", "security"})
    @Story("Transport security") @Severity(SeverityLevel.BLOCKER)
    @Description("Base URL must use HTTPS")
    public void testLoginOverHTTPSOnly() {
        assertTrue(configManager.getBaseUrl().startsWith("https://"),
                "Base URL must use HTTPS. Got: " + configManager.getBaseUrl());
    }

    @Test(groups = {"regression", "security"})
    @Story("Rate limiting") @Severity(SeverityLevel.CRITICAL)
    @Description("Repeated failed logins should trigger 429 rate limiting")
    public void testRateLimitingAfterMultipleFailures() {
        Response last = null;
        boolean hit = false;
        for (int i = 0; i < 6; i++) {
            Response r = given().spec(requestBuilder.buildLoginRequest("testuser001", "Bad_" + i)).when().post().then().extract().response();
            last = r;
            if (r.getStatusCode() == ApiConstants.STATUS_TOO_MANY_REQUESTS) { hit = true; break; }
        }
        assertTrue(hit || (last != null && last.getStatusCode() == ApiConstants.STATUS_TOO_MANY_REQUESTS),
                "API should return 429 after repeated failed attempts");
    }
}
