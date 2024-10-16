package com.agave.tests.Profile.Update;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.agave.tests.Profile.InfoAdd.FirstPageInfoTest;
import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.TestUser;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateEmailTest extends FirstPageInfoTest{

    private String authorizationCode;

    private String getApiToken() {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        Response response = given()
                .queryParam("app_id", "agave")
                .when()
                .get(baseURI + "v1/auth/otp/api-token")
                .then()
                .contentType("application/json")
                .extract()
                .response();

        return response.jsonPath().getString("api_token");
    }

    private void shootOtp(String apiToken) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("app_id", "agave");
        hm.put("api_token", apiToken);
        hm.put("email", "asif1@gmail.com");

        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        given()
                .contentType(ContentType.JSON)
                .body(hm)
                .when()
                .post(baseURI + "v1/auth/otp/shoot-otp/email");
    }

    private String validateOtp(String apiToken) {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("app_id", "agave");
        requestBody.put("api_token", apiToken);
        requestBody.put("email", "asif1@gmail.com");
        requestBody.put("otp", "1111");

        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/auth/otp/validate-otp/email")
                .path("authorization_code");
    }

    @BeforeMethod
    public void authorizationCodesetup() throws SQLException {

        String apiToken = getApiToken();
        shootOtp(apiToken);
        authorizationCode = validateOtp(apiToken);

        DB.truncateTables(new String[] {"profiles"});
        TestUser.setupUsers(1, 2, 3, 4);
    }

    @Test(priority = 1)
    public void userShouldUpdateEmailSuccessfullyWithProperAuthorization() {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "asifrr@gmail.com");
        requestBody.put("authorizationCode", authorizationCode);

        Response response = given()
                .header("Authorization", "Bearer " + getAccessToken())
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(baseURI + "v1/profile/update-email");
        
        Assert.assertEquals(response.getStatusCode(), 200);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "成功");
    }

    @Test(priority = 2)
    public void userShouldNotUpdateWrongEmailFormat() {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "asifrr.gmail.com");
        requestBody.put("authorizationCode", authorizationCode);

        Response response = given()
                .header("Authorization", "Bearer " + getAccessToken())
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(baseURI + "v1/profile/update-email");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "入力されたメールアドレスが間違っています。もう一度やり直してください。");
    }

    @Test(priority = 3)
    public void userShouldNotUpdateEmailWithoutAuthorization() {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "asifrr@gmail.com");
        requestBody.put("authorizationCode", authorizationCode);

        Response response = given()
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(baseURI + "v1/profile/update-email");

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}