package com.agave.tests.Product.AddAndEdit;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class ProductStaticInfoTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void baseURISetUp() {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        
        accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
    }

    @Test(priority = 1)
    public void allStaticInfoOfProductShouldBeShownWithProperAuthorization() {

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .when()
                .get(baseURI + "v1/product/static-info");

        Assert.assertEquals(response.getStatusCode(), 200);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ProductStaticInfoTestJSONSchema.json"));
    }

    @Test(priority = 2)
    public void allStaticInfoOfProductShouldNotBeShownWithoutAuthorization() {

        Response response = given()
                .header("Accept-Language", "jp")

                .when()
                .get(baseURI + "v1/product/static-info");

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
