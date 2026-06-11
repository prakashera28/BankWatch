package com.bankofamerica.base;

import com.bankofamerica.config.ConfigManager;
import com.bankofamerica.utils.RequestBuilder;
import com.bankofamerica.utils.ResponseValidator;
import com.bankofamerica.utils.TestDataManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    protected RequestBuilder requestBuilder;
    protected ResponseValidator responseValidator;
    protected TestDataManager testDataManager;
    protected ConfigManager configManager;

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() {
        logger.info("=== Initializing BankOfAmerica API Automation Suite ===");
        configManager = ConfigManager.getInstance();
        requestBuilder = new RequestBuilder();
        responseValidator = new ResponseValidator();
        testDataManager = TestDataManager.getInstance();
        RestAssured.baseURI = configManager.getBaseUrl();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.filters(new AllureRestAssured());
        RestAssured.config = RestAssuredConfig.config().httpClient(
                HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", configManager.getTimeoutSeconds() * 1000)
                        .setParam("http.socket.timeout", configManager.getTimeoutSeconds() * 1000));
        logger.info("Base URL: {} | Suite setup complete.", configManager.getBaseUrl());
    }

    @AfterSuite(alwaysRun = true)
    public void suiteCleanup() {
        logger.info("=== BankOfAmerica API Automation Suite Complete ===");
        RestAssured.reset();
    }

    @BeforeMethod(alwaysRun = true)
    public void testSetup(Method method) {
        logger.info(">>> Starting test: {}", method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void testCleanup(ITestResult result) {
        String status = switch (result.getStatus()) {
            case ITestResult.SUCCESS -> "PASSED";
            case ITestResult.FAILURE -> "FAILED";
            case ITestResult.SKIP -> "SKIPPED";
            default -> "UNKNOWN";
        };
        logger.info("<<< Test [{}] - {}", result.getName(), status);
        if (result.getStatus() == ITestResult.FAILURE && result.getThrowable() != null)
            logger.error("Failure: {}", result.getThrowable().getMessage());
    }
}
