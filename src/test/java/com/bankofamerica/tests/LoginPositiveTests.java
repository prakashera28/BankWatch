package com.bankofamerica.tests;

import com.bankofamerica.base.BaseTest;
import com.bankofamerica.constants.ApiConstants;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

@Epic("Bank of America Login API")
@Feature("Login - Positive Scenarios")
public class LoginPositiveTests extends BaseTest {

    @Test(groups = {"smoke", "regression"})
    @Story("Valid user login") @Severity(SeverityLevel.BLOCKER)
    @Description("POST valid credentials and assert 200 OK with access token in response")
    public void testValidLoginReturnsToken() {
        String[] c = testDataManager.getFirstValidCredentials();
        Response r = given().spec(requestBuilder.buildLoginRequest(c[0], c[1])).when().post().then().extract().response();
        responseValidator.validateStatusCode(r, ApiConstants.STATUS_OK);
        responseValidator.validateLoginResponse(r);
    }

    @Test(groups = {"smoke", "regression"})
    @Story("Valid user login") @Severity(SeverityLevel.CRITICAL)
    @Description("Validate all expected fields are present in login response")
    public void testValidLoginResponseStructure() {
        String[] c = testDataManager.getFirstValidCredentials();
        Response r = given().spec(requestBuilder.buildLoginRequest(c[0], c[1])).when().post().then().extract().response();
        responseValidator.validateStatusCode(r, ApiConstants.STATUS_OK);
        String body = r.getBody().asString();
        assertTrue(body.contains("accessToken") || body.contains("access_token"), "accessToken missing from response");
        assertTrue(body.contains("tokenType") || body.contains("token_type"), "tokenType missing from response");
        assertTrue(body.contains("expiresIn") || body.contains("expires_in"), "expiresIn missing from response");
    }

    @Test(groups = {"regression"})
    @Story("Remember Me feature") @Severity(SeverityLevel.NORMAL)
    @Description("Login with rememberMe=true and assert refreshToken is present")
    public void testRememberMeTrue() {
        String[] c = testDataManager.getFirstValidCredentials();
        Response r = given().spec(requestBuilder.buildLoginRequest(c[0], c[1], true)).when().post().then().extract().response();
        responseValidator.validateStatusCode(r, ApiConstants.STATUS_OK);
        String body = r.getBody().asString();
        assertTrue(body.contains("refreshToken") || body.contains("refresh_token"), "refreshToken missing for rememberMe=true");
    }

    @Test(groups = {"smoke", "regression"})
    @Story("Performance") @Severity(SeverityLevel.NORMAL)
    @Description("Assert login API responds within 3000ms")
    public void testValidLoginResponseTime() {
        String[] c = testDataManager.getFirstValidCredentials();
        Response r = given().spec(requestBuilder.buildLoginRequest(c[0], c[1])).when().post().then().extract().response();
        responseValidator.validateResponseTime(r, 3000);
    }

    @Test(groups = {"regression"}, dataProvider = "validUsersDataProvider")
    @Story("Valid user login") @Severity(SeverityLevel.NORMAL)
    @Description("Login with multiple valid users from test data")
    public void testLoginWithDifferentValidUsers(String username, String password) {
        Response r = given().spec(requestBuilder.buildLoginRequest(username, password)).when().post().then().extract().response();
        responseValidator.validateStatusCode(r, ApiConstants.STATUS_OK);
        responseValidator.validateLoginResponse(r);
    }

    @DataProvider(name = "validUsersDataProvider")
    public Object[][] validUsersDataProvider() {
        String[][] creds = testDataManager.getValidCredentials();
        Object[][] data = new Object[creds.length][2];
        for (int i = 0; i < creds.length; i++) { data[i][0] = creds[i][0]; data[i][1] = creds[i][1]; }
        return data;
    }
}
