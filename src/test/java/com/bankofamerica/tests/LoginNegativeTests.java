package com.bankofamerica.tests;

import com.bankofamerica.base.BaseTest;
import com.bankofamerica.constants.ApiConstants;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("Bank of America Login API")
@Feature("Login - Negative Scenarios")
public class LoginNegativeTests extends BaseTest {

    @Test(groups = {"regression", "negative"})
    @Story("Invalid credentials") @Severity(SeverityLevel.CRITICAL)
    @Description("Wrong password should return 401")
    public void testInvalidPasswordReturns401() {
        String[] c = testDataManager.getFirstValidCredentials();
        Response r = given().spec(requestBuilder.buildLoginRequest(c[0], "WrongPassword@999")).when().post().then().extract().response();
        responseValidator.validateStatusCode(r, ApiConstants.STATUS_UNAUTHORIZED);
        responseValidator.validateErrorResponse(r, null);
    }

    @Test(groups = {"regression", "negative"})
    @Story("Invalid credentials") @Severity(SeverityLevel.CRITICAL)
    @Description("Non-existent username should return 401")
    public void testInvalidUsernameReturns401() {
        Response r = given().spec(requestBuilder.buildLoginRequest("nonexistent_user_xyz", "AnyPassword@123")).when().post().then().extract().response();
        responseValidator.validateStatusCode(r, ApiConstants.STATUS_UNAUTHORIZED);
    }

    @Test(groups = {"regression", "negative"})
    @Story("Validation - empty fields") @Severity(SeverityLevel.NORMAL)
    @Description("Empty username should return 400")
    public void testEmptyUsernameReturns400() {
        Response r = given().spec(requestBuilder.buildLoginRequest("", "Test@Password1")).when().post().then().extract().response();
        responseValidator.validateStatusCode(r, ApiConstants.STATUS_BAD_REQUEST);
    }

    @Test(groups = {"regression", "negative"})
    @Story("Validation - empty fields") @Severity(SeverityLevel.NORMAL)
    @Description("Empty password should return 400")
    public void testEmptyPasswordReturns400() {
        Response r = given().spec(requestBuilder.buildLoginRequest("testuser001", "")).when().post().then().extract().response();
        responseValidator.validateStatusCode(r, ApiConstants.STATUS_BAD_REQUEST);
    }

    @Test(groups = {"regression", "negative"})
    @Story("Validation - empty fields") @Severity(SeverityLevel.NORMAL)
    @Description("Both fields empty should return 400")
    public void testBothFieldsEmptyReturns400() {
        Response r = given().spec(requestBuilder.buildLoginRequest("", "")).when().post().then().extract().response();
        responseValidator.validateStatusCode(r, ApiConstants.STATUS_BAD_REQUEST);
    }

    @Test(groups = {"regression", "negative"})
    @Story("Account locked") @Severity(SeverityLevel.CRITICAL)
    @Description("Locked account should return 403")
    public void testLockedAccountReturns403() {
        String[] locked = testDataManager.getLockedCredentials();
        Response r = given().spec(requestBuilder.buildLoginRequest(locked[0], locked[1])).when().post().then().extract().response();
        responseValidator.validateStatusCode(r, ApiConstants.STATUS_FORBIDDEN);
    }

    @Test(groups = {"regression", "negative"})
    @Story("Malformed request") @Severity(SeverityLevel.NORMAL)
    @Description("Malformed JSON should return 400")
    public void testInvalidJsonBodyReturns400() {
        Response r = given().spec(requestBuilder.buildRequestWithRawBody("{\"username\": \"testuser\", \"password\":")).when().post().then().extract().response();
        responseValidator.validateStatusCode(r, ApiConstants.STATUS_BAD_REQUEST);
    }

    @Test(groups = {"regression", "negative"})
    @Story("Missing headers") @Severity(SeverityLevel.NORMAL)
    @Description("Missing Content-Type should return 415")
    public void testMissingContentTypeReturns415() {
        String[] c = testDataManager.getFirstValidCredentials();
        Response r = given().spec(requestBuilder.buildRequestWithoutContentType(c[0], c[1])).when().post().then().extract().response();
        responseValidator.validateStatusCode(r, ApiConstants.STATUS_UNSUPPORTED_MEDIA_TYPE);
    }
}
