package com.agave.tests.Profile.InfoAdd;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class FirstPageInfoTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();
   
    @BeforeMethod
    public void setUp() throws SQLException {
        accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
    }

    @Test(priority = 1)
    public void nickNameFieldShouldNotBeNull() throws SQLException{

         RestAssured.baseURI = "http://agave-api-test:8080/api/";

         File file = new File("assets/New.jpg");

        Response response = given() 
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept-Language","jp")
                    .contentType("multipart/form-data")
                    .multiPart("profile_picture",file)
                    .multiPart("self_introductory_statement", "abcd")
                    .when()
                    .post(baseURI + "v1/profile/add-info-one");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "ニックネームフィールドはnullであってはなりません");
    }

    @Test(priority = 2)
    public void nickNameShouldNotTakeOnlyNumbers(){

         RestAssured.baseURI = "http://agave-api-test:8080/api/";
  
         File file = new File("assets/New.jpg");

        Response response = given() 
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept-Language","jp")
                    .contentType("multipart/form-data")
                    .multiPart("nick_name","123456")
                    .multiPart("profile_picture",file)
                    .multiPart("self_introductory_statement", "abcd")
                    .when()
                    .post(baseURI + "v1/profile/add-info-one");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "ニックネームには数字のみを含めることはできません");
    }

    @Test(priority = 3)
    public void nickNameShouldNotTakeOnlySpecialCharacters(){

         RestAssured.baseURI = "http://agave-api-test:8080/api/";
        
         File file = new File("assets/New.jpg");

        Response response = given() 
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept-Language","jp")
                    .contentType("multipart/form-data")
                    .multiPart("nick_name","@#$%^&*")
                    .multiPart("profile_picture",file)
                    .multiPart("self_introductory_statement", "abcd")
                    .when()
                    .post(baseURI + "v1/profile/add-info-one");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "ニックネームには特殊文字のみを含めることはできません");
    }

    @Test(priority = 4)
    public void userShouldNotUploadProfileFirstPageInfoWithoutAuthorization(){  
        
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        File file = new File("assets/New.jpg");

        Response response = given() 
                    .header("Accept-Language","jp")
                    .contentType("multipart/form-data")
                    .multiPart("profile_picture", file)
                    .multiPart("nick_name","asif")
                    .multiPart("self_introductory_statement", "abcd")
                    .when()
                    .post(baseURI + "v1/profile/add-info-one");
         
        Assert.assertEquals(response.getStatusCode(), 500);
    }

    @Test(priority = 5)
    public void responseShouldShowErrorMessageWithoutFillingAnyFormData(){

        RestAssured.baseURI = "http://agave-api-test:8080/api/";
    
        Response response = given() 
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept-Language","jp")
                    .when()
                    .post(baseURI + "v1/profile/add-info-one");
             
        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message,"ニックネームフィールドはnullであってはなりません");
    }  

    @Test(priority = 6)
    public void userShouldNotAddInvalidPhoneNumber() {

        RestAssured.baseURI = "http://agave-api-test:8080/api/";
         
        File file = new File("assets/New.jpg");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .contentType("multipart/form-data")
                .multiPart("profile_picture", file)
                .multiPart("nick_name", "asif")
                .multiPart("self_introductory_statement", "abcd")
                .multiPart("phone_number", "070126980000")
                .when()
                .post(baseURI + "v1/profile/add-info-one");
           
        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "無効な電話番号");
    }

    @Test(priority = 7)
    public void userShouldSuccessfullyAddProfileFirstPageInfoByFillingAllTheField() {

        RestAssured.baseURI = "http://agave-api-test:8080/api/";
         
        File file = new File("assets/New.jpg");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .contentType("multipart/form-data")
                .multiPart("profile_picture", file)
                .multiPart("nick_name", "asif")
                .multiPart("self_introductory_statement", "abcd")
                .multiPart("phone_number", "07012698000")
                .when()
                .post(baseURI + "v1/profile/add-info-one");
           
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    public String getAccessToken(){
        return accessToken;
    }
}