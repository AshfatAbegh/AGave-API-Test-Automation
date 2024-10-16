package com.agave.tests.Profile.InfoAdd;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.TestUser;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AddDocumentsTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void dbSetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles", "identification_documents"});

        TestUser.setupUsers(1, 2, 3, 4);
        accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");
    }

    @Test(priority = 1)
    public void userShouldSucessfullyUploadProfileDocumentWithProperAuthorization() {

        File file = new File("assets/images.jpeg");
        File file2 = new File("assets/New.jpg");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .contentType("multipart/form-data")
                .multiPart("documents", file)
                .multiPart("documents", file2)
                .multiPart("documents", file3)
                .when()
                .post(baseURI + "v1/profile/upload-documents");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void userShouldNotUploadMoreThanThreeProfileDocument() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/New.jpg");
        File file4 = new File("assets/New.jpg");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .contentType("multipart/form-data")
                .multiPart("documents", file)
                .multiPart("documents", file2)
                .multiPart("documents", file3)
                .multiPart("documents", file4)
                .when()
                .post(baseURI + "v1/profile/upload-documents");

        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test(priority = 3)
    public void userShouldNotUploadProfileDocumentWithoutAuthorization() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/New.jpg");

        Response response = given()
                .header("Accept-Language", "jp")
                .contentType("multipart/form-data")
                .multiPart("documents", file)
                .multiPart("documents", file2)
                .multiPart("documents", file3)
                .when()
                .post(baseURI + "v1/profile/upload-documents");

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
