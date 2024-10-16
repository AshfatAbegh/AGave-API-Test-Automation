package com.agave.tests.Notification;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateNotificationsSettingsTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void baseURISetUp(){
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 1)
    public void userShouldUpdateNotificationsWithProperKeyAndValue(){

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("key","productPublished");
        requestBody.put("value","true");

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .header("Accept-Language", "jp")
                 .contentType(ContentType.JSON)    
                 .body(requestBody)
                 .when()   
                 .put(baseURI + "v1/notification/update-notifications-settings");

        Assert.assertEquals(response.getStatusCode(),200);
    }  
    
    @Test(priority = 2)
    public void userShouldNotUpdateNotificationsWithoutProperKeyAndValue(){

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .header("Accept-Language", "jp")
                 .contentType(ContentType.JSON)    
                 .when()   
                 .put(baseURI + "v1/notification/update-notifications-settings");

        Assert.assertEquals(response.getStatusCode(),400);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "無効な JSON 形式");
    }  

    @SuppressWarnings("unchecked")
    @Test(priority = 3)
    public void userShouldNotUpdateNotificationsWithoutAuthorization(){

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("key","productPublished");
        requestBody.put("value","true");

        Response response = given()
                 .header("Accept-Language", "jp")
                 .contentType(ContentType.JSON)    
                 .body(requestBody)
                 .when()   
                 .put(baseURI + "v1/notification/update-notifications-settings");

        Assert.assertEquals(response.getStatusCode(), 500);
    }  
}
