package com.agave.tests.Notification;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class NotificationListTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void baseURISetUp(){
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
    }

    @Test(priority = 1)
    public void userShouldSeeNotificationListBySinglePageNumberAndSize(){
        
        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .queryParam("page",1)
                 .queryParam("size", 10)
                 .when()
                 .get(baseURI + "v1/notification/all");

        Assert.assertEquals(response.getStatusCode(), 200);
    }  

    @Test(priority = 2)
    public void userShouldNotSeeNotificationListWithoutPageNumber(){
        
        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .queryParam("size", 10)
                 .when()
                 .get(baseURI + "v1/notification/all");

        Assert.assertEquals(response.getStatusCode(), 500);
    }  

    @Test(priority = 3)
    public void userShouldNotSeeNotificationListWithoutPageSize(){
        
        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .queryParam("page",1)
                 .when()
                 .get(baseURI + "v1/notification/all");

        Assert.assertEquals(response.getStatusCode(), 500);
    } 

    @Test(priority = 4)
    public void userShouldNotSeeNotificationListWithoutPageNumberAndSize(){
        
        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .when()
                 .get(baseURI + "v1/notification/all");

        Assert.assertEquals(response.getStatusCode(), 500);
    } 

    @Test(priority = 5)
    public void userShouldNotSeeNotificationListWithoutAuthorization(){
        
        Response response = given()
                 .queryParam("page",1)
                 .queryParam("size", 10)
                 .when()
                 .get(baseURI + "v1/notification/all");

        Assert.assertEquals(response.getStatusCode(), 500);
    }  
}
