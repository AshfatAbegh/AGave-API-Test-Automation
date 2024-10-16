package com.agave.tests.Auth;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ForgetPasswordTest extends EmailRegistrationTest {

    @BeforeMethod
    public void baseURISetUp() {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
    }

    @Test(priority = 1)
    public void userShouldChangePasswordSuccessfullyWithProperAuthorization() {

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("authorization_code", getAuthorizationCode());
        requestBody.put("password", "Asif9878");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/auth/forget-password");

        Assert.assertEquals(response.getStatusCode(), 200);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "成功");
    }

    @Test(priority = 2)
    public void userShouldNotChangePasswordWithoutAuthorization() {

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("password", "Asif9878");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/auth/forget-password");

        Assert.assertEquals(response.getStatusCode(), 400);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "ユーザーが見つかりません");
    }

    @Test(priority = 3)
    public void passwordLengthShouldBeMinimumEightCharacter() {

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("authorization_code", getAuthorizationCode());
        requestBody.put("password", "Asif987");

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Accept-Language", "jp")
                .body(requestBody)
                .when()
                .post(baseURI + "v1/auth/forget-password");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "パスワードは半角英数字8文字以上でなければならない。");
    }
}
