package com.agave.tests.Admin;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.TestUser;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ActivateDeactivateAccountTest {

    @BeforeClass
    public void dbSetUp() throws SQLException{
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles"});

        TestUser.setupUsers(1, 2, 3, 4);
    }

    @Test(priority = 1)
    public void adminShouldDeactivateUserAccountWithValidId(){

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("profileId", "4");
        requestBody.put("key", "26452948404D635166546A576E5A7234753778214125442A462D4A614E6");
        requestBody.put("adminId","2");

        Response response = given()
                 .contentType(ContentType.JSON)
                 .header("Accept-Language", "jp")
                 .body(requestBody)
                 .when()
                 .post(baseURI + "v1/admin/activate-deactivate");

        Assert.assertEquals(response.getStatusCode(), 200);           
    }

    @Test(priority = 2)
    public void adminShouldNotDeactivateUserAccountWithInvalidId(){

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("profileId", "5");
        requestBody.put("key", "26452948404D635166546A576E5A7234753778214125442A462D4A614E6");
        requestBody.put("adminId","2");

        Response response = given()
                 .contentType(ContentType.JSON)
                 .header("Accept-Language", "jp")
                 .body(requestBody)
                 .when()
                 .post(baseURI + "v1/admin/activate-deactivate");

        Assert.assertEquals(response.getStatusCode(), 404);   
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "プロフィールがありません");        
    }
}
