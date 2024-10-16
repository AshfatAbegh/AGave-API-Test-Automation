package com.agave.tests.Profile.Update;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.TestUser;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdatePasswordTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void dbSetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles"});

        TestUser.setupUsers(1, 2, 3, 4);
        accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 1)
    public void userShouldUpdatePasswordWithProperAuthorization() {

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("currentPassword", "Asif9878");
        requestBody.put("newPassword", "Asifu9878");
        requestBody.put("reenterPassword", "Asifu9878");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(baseURI + "v1/profile/update-password");

        Assert.assertEquals(response.getStatusCode(), 200);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "成功");
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 2)
    public void userShouldNotUpdatePasswordWithoutAuthorization() {

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("currentPassword", "Asif9878");
        requestBody.put("newPassword", "Asifu9878");
        requestBody.put("reenterPassword", "Asifu9878");

        Response response = given()
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(baseURI + "v1/profile/update-password");

        Assert.assertEquals(response.getStatusCode(), 500);
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 3)
    public void passwordLengthShouldBeMinimumEightCharacter() {

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("currentPassword", "Asifu9878");
        requestBody.put("newPassword", "Asifu98");
        requestBody.put("reenterPassword", "Asifu98");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(baseURI + "v1/profile/update-password");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "パスワードは半角英数字8文字以上でなければならない。");
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 4)
    public void passwordShouldContainAtleastOneUpperCaseLetter() {

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("currentPassword", "Asifu9878");
        requestBody.put("newPassword", "asifu987");
        requestBody.put("reenterPassword", "asifu987");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(baseURI + "v1/profile/update-password");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "パスワードには少なくとも 1 つの大文字が含まれている必要があります");
    }
}
