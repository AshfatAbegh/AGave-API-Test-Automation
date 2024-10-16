package com.agave.tests.Notification;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class GetNotificationsSettingsTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void baseURISetUp(){
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
    }

    @Test(priority = 1)
    public void userShouldGetNotificationsSettingsTestWithProperAuthorization(){

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .header("Accept-Language", "jp")
                 .when()
                 .get(baseURI + "v1/notification/get-notifications-settings");

        Assert.assertEquals(response.getStatusCode(), 200);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetNotificationsTestJSONSchema.json"));
    }

    @Test(priority = 2)
    public void userShouldNotGetNotificationsSettingsTestWithoutAuthorization(){

        Response response = given()
                 .header("Accept-Language", "jp")
                 .when()
                 .get(baseURI + "v1/notification/get-notifications-settings");

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
