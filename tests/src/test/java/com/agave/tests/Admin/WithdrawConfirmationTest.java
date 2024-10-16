package com.agave.tests.Admin;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.TestUser;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class WithdrawConfirmationTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void dbSetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles", "withdraws"});

        TestUser.setupUsers(1, 2, 3, 4);
        accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");

        insertTestWithdrawConfirmation(1, 100, new Date().getTime(), new Date().getTime(), 1);
    }

    public void insertTestWithdrawConfirmation(int id, double requestAmount, long transactionConfirmDate, long transactionRequestDate, int profile_id) throws SQLException {
          Timestamp transactionConfirmDateTimestamp = new Timestamp(transactionConfirmDate);
          Timestamp transactionRequestDateTimestamp = new Timestamp(transactionRequestDate);

          DB.getConnection().executeUpdate("INSERT INTO `withdraws`(`id`, `requestAmount`, `transactionConfirmDate`, `transactionRequestDate`, `profile_id`) VALUES('" + id + "', '" + requestAmount + "', '" + transactionConfirmDateTimestamp + "', '" + transactionRequestDateTimestamp + "', '" + profile_id + "')");
    }

    @Test(priority = 1)
    public void userShouldMakeWithdrawRequestWithProperAuthorization() {

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("status","Successful");
        requestBody.put("id","1");
        requestBody.put("key","26452948404D635166546A576E5A7234753778214125442A462D4A614E6");
        requestBody.put("adminId","2");

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .contentType(ContentType.JSON)
                 .header("Accept-Language", "jp")
                 .body(requestBody)
                 .when()
                 .post(baseURI + "v1/admin/withdraw-confirmation");
        
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void userShouldNotMakeWithdrawRequestWithoutAuthorization() {

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("status","Successful");
        requestBody.put("id","1");
        requestBody.put("key","26452948404D635166546A576E5A7234753778214125442A462D4A614E6");
        requestBody.put("adminId","2");

        Response response = given()
                 .contentType(ContentType.JSON)
                 .header("Accept-Language", "jp")
                 .body(requestBody)
                 .when()
                 .post(baseURI + "v1/admin/withdraw-confirmation");
        
        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "トランザクションはすでに完了しています");
    }
}
