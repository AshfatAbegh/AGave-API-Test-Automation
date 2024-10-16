package com.agave.tests.Others;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactUsTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void dbSetUp(){
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"contacts"});

        accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 1)
    public void authorizedUserShouldContactAdminAboutTheirInquiries(){

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("inquiry", "SHIPPINGPROCESS");
        requestBody.put("inquiryDetails", "Need to know shipping process");
        
        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .header("Accept-Language","jp")
                 .contentType(ContentType.JSON)
                 .body(requestBody)
                 .when()
                 .post(baseURI + "v1/others/contact-us");

        Assert.assertEquals(response.getStatusCode(), 200);
    }   

    @SuppressWarnings("unchecked")
    @Test(priority = 2)
    public void unauthorizedUserShouldNotContactAdminAboutTheirInquiries(){

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("inquiry", "SHIPPINGPROCESS");
        requestBody.put("inquiryDetails", "Need to know shipping process");
        
        Response response = given()
                 .header("Accept-Language","jp")
                 .contentType(ContentType.JSON)
                 .body(requestBody)
                 .when()
                 .post(baseURI + "v1/others/contact-us");

        Assert.assertEquals(response.getStatusCode(), 500);
    } 
}
