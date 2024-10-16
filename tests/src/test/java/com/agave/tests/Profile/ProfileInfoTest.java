package com.agave.tests.Profile;

import static io.restassured.RestAssured.baseURI;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ProfileInfoTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void baseURISetUp() {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        
        accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
    }

    @Test(priority = 1)
    public void userShouldGetIndividualProfileInfoWithProperAuthorization() {

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(baseURI + "v1/profile/info");

        Assert.assertEquals(response.getStatusCode(), 200);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ProfileInfoTestJSONSchema.json"));
    }

    @Test(priority = 2)
    public void userShouldNotGetIndividualProfileInfoWithoutAuthorization() {

        Response response = given()

                .when()
                .get(baseURI + "v1/profile/info");

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
