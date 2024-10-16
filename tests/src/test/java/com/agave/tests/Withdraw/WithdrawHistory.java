package com.agave.tests.Withdraw;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.TestUser;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class WithdrawHistory {
    
    private String accessToken;
    private String accessToken2;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void dbSetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles", "withdraws"});

        TestUser.setupUsers(1, 2, 3, 4);
        accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");
        accessToken2 = jwtToken.generateAccessToken(2, "alex@gmail.com");

        insertTestWithdraws(1, 100, new Date().getTime(), new Date().getTime(), "SUCCESSFUL", "W1000001I1", 1);
        insertTestWithdraws2(2, 200, new Date().getTime(), new Date().getTime(), "PENDING", "W1000001I1", 4);
    }

    public static void insertTestWithdraws(int id, double requestAmount, long transactionConfirmDate, long transactionRequestDate, String transactionStatus, String withdrawId, int profile_id) throws SQLException{
           Timestamp transactionConfirmDateTimestamp = new Timestamp(transactionConfirmDate);
           Timestamp transactionRequestDateTimestamp = new Timestamp(transactionRequestDate);
           DB.getConnection().executeUpdate("INSERT INTO `withdraws` (`id`, `requestAmount`, `transactionConfirmDate`, `transactionRequestDate`, `transactionStatus`, `withdrawId`, `profile_id`) VALUES ('" + id + "', '" + requestAmount + "', '" + transactionConfirmDateTimestamp + "', '" + transactionRequestDateTimestamp + "', '" + transactionStatus + "', '" + withdrawId + "', '" + profile_id + "')");
    }

    public static void insertTestWithdraws2(int id, double requestAmount, long transactionConfirmDate, long transactionRequestDate, String transactionStatus, String withdrawId, int profile_id) throws SQLException{
        Timestamp transactionConfirmDateTimestamp = new Timestamp(transactionConfirmDate);
        Timestamp transactionRequestDateTimestamp = new Timestamp(transactionRequestDate);
        DB.getConnection().executeUpdate("INSERT INTO `withdraws` (`id`, `requestAmount`, `transactionConfirmDate`, `transactionRequestDate`, `transactionStatus`, `withdrawId`, `profile_id`) VALUES ('" + id + "', '" + requestAmount + "', '" + transactionConfirmDateTimestamp + "', '" + transactionRequestDateTimestamp + "', '" + transactionStatus + "', '" + withdrawId + "', '" + profile_id + "')");
 }

    @Test(priority = 1)
    public void userShouldGetWithdrawHistoryWithProperAuthorization(){

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .header("Accept-Language", "jp")
                 .when()
                 .get(baseURI + "v1/withdraw/withdraw-history");
       
        Assert.assertEquals(response.getStatusCode(), 200);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("WithdrawHistoryTestJSONSchema.json"));
    }

    @Test(priority = 2)
    public void userShouldNotGetWithdrawHistoryForPendingOrRejectedStatus(){

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken2)
                 .header("Accept-Language", "jp")
                 .when()
                 .get(baseURI + "v1/withdraw/withdraw-history");
 
        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "引き出し取引はまだ行われていません。");
    }
    
    @Test(priority = 3)
    public void userShouldNotGetWithdrawHistoryWithoutAuthorization(){

        Response response = given()
                 .header("Accept-Language", "jp")
                 .when()
                 .get(baseURI + "v1/withdraw/withdraw-history");
        
        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
