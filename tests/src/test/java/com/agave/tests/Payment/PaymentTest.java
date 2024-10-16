package com.agave.tests.Payment;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.TestUser;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class PaymentTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();
    
    @BeforeClass
    public void dbSetUp() throws SQLException{
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles"});

        TestUser.setupUsers(1, 2, 3, 4);
        accessToken = jwtToken.generateAccessToken(2,"araf@gmail.com");
    }

    @Test(priority = 1)
    public void paymentLinkShouldBeGenerated(){

       Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .when()
                .get(baseURI + "v1/payment/create-link");

       Assert.assertEquals(response.getStatusCode(), 200);  
       response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("PaymentLinkGenerationTestJSONSchema.json"));       
    }

    // String accessToken2 = "eyJtZW1iZXJyZXN1bHQiOnsiU2l0ZUlEIjoidHNpdGUwMDA1NDkzNSIsIk1lbWJlcklEIjoiMiIsIk1lbWJlck5hbWUiOiJudWhpbjEzMTMiLCJDYXJkU2VxIjoiIiwiQ2FyZE5vIjoiMzU2NjAwKioqKioqMDQxMCIsIlJlc3VsdCI6IlNVQ0NFU1MiLCJQcm9jZXNzZGF0ZSI6IjIwMjQvMDgvMDEgMjI6NTk6NTAiLCJFcnJDb2RlIjpudWxsLCJFcnJJbmZvIjpudWxsLCJDYXJkZWRpdG5vIjoiYWdhdmVfMl8xXzE3MjI1MjA3NTM1MTIifX0=.307b32e382db2480e6faa621d65f28c6219e8bd8d071df69354229d0f4106b68";

    // @Test(priority = 2, dependsOnMethods = {"paymentLinkShouldBeGenerated"}) 
    // public void cardShouldBeSavedSuccessfully(){

    //     Response response = given()
    //              .header("Authorization", "Bearer " + accessToken)
    //              .queryParam("result", accessToken2)
    //              .header("Accept-Language", "jp")
    //              .when()
    //              .post(baseURI + "v1/payment/link/completion/profile/2");

    //     Assert.assertEquals(response.getStatusCode(), 200);
    // }

    @Test(priority = 2, dependsOnMethods = {"paymentLinkShouldBeGenerated"})
    public void cardListShouldBeSeenSuccessfullyIfAnyCardIsAlreadySaved(){

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .when()
                 .get(baseURI + "v1/payment/card-list");
   
        Assert.assertEquals(response.getStatusCode(), 200);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CardListTestJSONSchema.json"));       
    }
}
