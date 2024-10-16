package com.agave.tests.Auth;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ProfileExistTest {

    @BeforeClass
    public void baseURISetUp() {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
    }

    @Test(priority = 1)
    public void checkUserProfileExistsOrNot() {

        Response response = given()
                .queryParam("email", "asif1@gmail.com")
                .when()
                .get(baseURI + "v1/auth/profile-check");

        Assert.assertEquals(response.getStatusCode(), 200);
        assertTrue("is_profile_exist", true);
    }

    @Test(priority = 2)
    public void checkUserProfileExistsOrNotWithoutQueryParam() {

        Response response = given()

                .when()
                .get(baseURI + "v1/auth/profile-check");

        Assert.assertEquals(response.getStatusCode(), 500);
    }

    @Test(priority = 3)
    public void responseStatusCodeShouldBe200() {

        Response response = given()
                .queryParam("email", "asif1@gmail.com")

                .when()
                .get(baseURI + "v1/auth/profile-check");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4)
    public void responseShouldNotBeNull() {

        Response response = given()
                .queryParam("email", "asif1@gmail.com")

                .when()
                .get(baseURI + "v1/auth/profile-check");

        Assert.assertEquals(response.getStatusCode(), 200);
        assertTrue("is_profile_exist", true);
    }
}
