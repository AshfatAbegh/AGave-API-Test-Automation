package com.agave.tests.Auth;

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
import com.agave.tests.Utilities.PasswordEncoder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EmailLoginTest {

    @BeforeClass
    public void dbSetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles"});

        String plainTextPassword = "Asif9878";
        String encodedPassword = PasswordEncoder.encodePassword(plainTextPassword);
        
        insertTestUser("asif1@gmail.com", encodedPassword, "false", "false", "false", "false", "false", "false", "false", 20, 5, 700, "ACTIVATE", new Date().getTime());
    }

    private void insertTestUser(String email, String password, String isFromGoogle, String notifyProductCommenters, String notifyProductPublished, String notifyProductRejected, String notifySellerProductLikes, String notifySellerProductUserComments, String notifyUserProductLikeComment, double ratedPerson, double rating, double balance, String accountStatus, long activatedDateAt) throws SQLException {
        Timestamp activatedDateAtTimestamp = new Timestamp(activatedDateAt);
        DB.getConnection().executeUpdate(
            "INSERT INTO `profiles` (`email`, `password`, `isFromGoogle`, `notifyProductCommenters`, `notifyProductPublished`, `notifyProductRejected`, `notifySellerProductLikes`, `notifySellerProductUserComments`, `notifyUserProductLikeComment`, `ratedPerson`, `rating`, `balance`, `accountStatus`, `activatedDateAt`) VALUES ('"
            + email + "', '"
            + password + "', "
            + (isFromGoogle.equals("true") ? 1 : 0) + ", "
            + (notifyProductCommenters.equals("true") ? 1 : 0) + ", "
            + (notifyProductPublished.equals("true") ? 1 : 0) + ", "
            + (notifyProductRejected.equals("true") ? 1 : 0) + ", "
            + (notifySellerProductLikes.equals("true") ? 1 : 0) + ", "
            + (notifySellerProductUserComments.equals("true") ? 1 : 0) + ", "
            + (notifyUserProductLikeComment.equals("true") ? 1 : 0) + ", '"
            + ratedPerson + "', '"
            + rating + "', '" + balance + "', '" + accountStatus + "', '" + activatedDateAtTimestamp + "')");
    }

    @Test(priority = 1)
    public void userShouldLoginUsingExistingEmail() {

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "asif1@gmail.com");
        requestBody.put("password", "Asif9878");

        Response response = given()
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/auth/login-auth-email");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void userShouldNotLoginWithoutExistingEmail() {

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "af@gmail.com");
        requestBody.put("password", "Asif9878");

        Response response = given()
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/auth/login-auth-email");

        Assert.assertEquals(response.getStatusCode(), 400);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "入力したメールアドレスが正しくありません。もう一度お試しください。");
    }

    @Test(priority = 3)
    public void userShouldNotLoginWithInvalidEmailFormat() {

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "asif.gmail.com");
        requestBody.put("password", "Asif9878");

        Response response = given()
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/auth/login-auth-email");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "入力されたメールアドレスが間違っています。もう一度やり直してください。");
    }

    @Test(priority = 4)
    public void userShouldNotLoginWithInvalidPassword() {

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "asif1@gmail.com");
        requestBody.put("password", "Asif987");

        Response response = given()
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/auth/login-auth-email");

        Assert.assertEquals(response.getStatusCode(), 400);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "入力したパスワードが正しくありません。もう一度お試しください。");
    }

    @Test(priority = 5)
    public void userShouldNotLoginWithWrongEmaiLAndPassword() {

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "af@gmail.com");
        requestBody.put("password", "Asif987");

        Response response = given()
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/auth/login-auth-email");

        Assert.assertEquals(response.getStatusCode(), 400);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "入力したメールアドレスが正しくありません。もう一度お試しください。");
    }

    @Test(priority = 6)
    public void userShouldNotLoginWithoutInsertingEmailAndPassword() {

        Response response = given()
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .when()
                .post(baseURI + "v1/auth/login-auth-email");

        Assert.assertEquals(response.getStatusCode(), 400);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "無効な JSON 形式");
    }
}
