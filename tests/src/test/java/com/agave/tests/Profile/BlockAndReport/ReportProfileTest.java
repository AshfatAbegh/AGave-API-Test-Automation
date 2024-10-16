package com.agave.tests.Profile.BlockAndReport;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.TestUser;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;
import java.util.HashMap;

public class ReportProfileTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void baseURISetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles","reports"});

        TestUser.setupUsers(1, 2, 3, 4);
        accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 1)
    public void producerShouldWriteReportAgainstValidId() {

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("reportedId", 3);
        requestBody.put("reason", "Fraud");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/profile/report");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 2)
    public void producerShouldNotWriteReportAgainstInvalidId() {

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("reportedId", 110);
        requestBody.put("reason", "Fraud");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/profile/report");

        Assert.assertEquals(response.getStatusCode(), 404);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "プロフィールがありません");
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 3)
    public void producerShouldNotWriteReportWithoutAuthorization() {

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("reportedId", 3);
        requestBody.put("reason", "Fraud");

        Response response = given()
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/profile/report");

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
