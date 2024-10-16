package com.agave.tests.Withdraw;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class AllBankInfoTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();
    
    @BeforeClass
    public void dbSetUp(){

        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");;
    }

    @Test(priority = 1)
    public void userShouldGetAllBankInfoWithProperAuthorization(){

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .header("Accept-Language", "jp")
                 .contentType(ContentType.JSON)
                 .when()
                 .get(baseURI + "v1/withdraw/all-bank-info");
       
        Assert.assertEquals(response.getStatusCode(), 200);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("AllBankInfoTestJSONSchema.json"));
    }

    @Test(priority = 2)
    public void userShouldNotGetAllBankInfoWithoutAuthorization(){

        Response response = given()
                 .header("Accept-Language", "jp")
                 .contentType(ContentType.JSON)
                 .when()
                 .get(baseURI + "v1/withdraw/all-bank-info");

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
