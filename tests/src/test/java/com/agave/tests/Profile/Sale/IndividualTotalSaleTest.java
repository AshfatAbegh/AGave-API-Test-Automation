package com.agave.tests.Profile.Sale;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class IndividualTotalSaleTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();
    
    @BeforeClass
    public void baseURISetUp(){
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");
    }

    @Test(priority = 1)
    public void userShouldSeeIndividualTotalSaleWithProperAuthorization(){

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .when()
                 .get(baseURI + "v1/sales/all-info");

        Assert.assertEquals(response.getStatusCode(), 200);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("IndividualTotalSaleTestJSONSchema.json"));
    }

    @Test(priority = 2)
    public void userShouldNotSeeIndividualTotalSaleWithoutAuthorization(){

        Response response = given()
                 .when()
                 .get(baseURI + "v1/sales/all-info");

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
