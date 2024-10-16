package com.agave.tests.Withdraw;

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
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class WithdrawRequestTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void dbSetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles", "withdraws"});

        TestUser.setupUsers(1, 2, 3, 4);
        accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");
    }

    @Test(priority = 1)
    public void userShouldAddWithdrawRequestWithProperAmount(){
          
        HashMap<String , Integer> requestBody = new HashMap<>();
        requestBody.put("withdrawAmount", 100);

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .contentType(ContentType.JSON)
                 .header("Accept-Language", "jp")
                 .body(requestBody)
                 .when()
                 .post(baseURI + "v1/withdraw/withdraw-request");
    
        Assert.assertEquals(response.getStatusCode(), 200);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("WithdrawRequestTestJSONSchema.json"));
    }

    @Test(priority = 2)
    public void userShouldNotAddWithdrawRequestWithInvalidAmount(){
          
        HashMap<String , Integer> requestBody = new HashMap<>();
        requestBody.put("withdrawAmount", 10000);

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .contentType(ContentType.JSON)
                 .header("Accept-Language", "jp")
                 .body(requestBody)
                 .when()
                 .post(baseURI + "v1/withdraw/withdraw-request");
               
        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "同じ月に複数の引き出しを行うことはできません");
    }

    @Test(priority = 3)
    public void userShouldNotAddWithdrawRequestWithoutAuthorization(){
          
        HashMap<String , Integer> requestBody = new HashMap<>();
        requestBody.put("withdrawAmount", 100);

        Response response = given()
                 .contentType(ContentType.JSON)
                 .header("Accept-Language", "jp")
                 .body(requestBody)
                 .when()
                 .post(baseURI + "v1/withdraw/withdraw-request");
        
        Assert.assertEquals(response.getStatusCode(), 500);
    }
}











