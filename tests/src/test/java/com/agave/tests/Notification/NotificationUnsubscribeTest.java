package com.agave.tests.Notification;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class NotificationUnsubscribeTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void baseURISetUp(){
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
    }

    @Test(priority = 1)
    public void notificationShouldBeUnsubscribedWithProperAuthorization(){

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .when()
                 .post(baseURI + "v1/notification/unsubscribe");      
                 
        Assert.assertEquals(response.getStatusCode(), 200);         
    }  

    @Test(priority = 2)
    public void notificationShouldNotBeUnsubscribedWithoutAuthorization(){

        Response response = given()
                 .when()
                 .post(baseURI + "v1/notification/unsubscribe");      
                 
        Assert.assertEquals(response.getStatusCode(), 500);         
    }  
}
