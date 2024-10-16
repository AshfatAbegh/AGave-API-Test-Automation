package com.agave.tests.Auth;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EmailRegistrationTest {

        private String authorizationCode;

        private String getApiToken() {
                RestAssured.baseURI = "http://agave-api-test:8080/api/";

                Response response = given()
                                .queryParam("app_id", "agave")
                                .when()
                                .get(baseURI + "v1/auth/otp/api-token")
                                .then()
                                .contentType("application/json")
                                .extract()
                                .response();

                return response.jsonPath().getString("api_token");
        }

        private Response shootOtp(String apiToken) {
                RestAssured.baseURI = "http://agave-api-test:8080/api/";
                
                HashMap<String, String> hm = new HashMap<>();
                hm.put("app_id", "agave");
                hm.put("api_token", apiToken);
                hm.put("email", "asif1@gmail.com");
            
                Response response = given()
                        .contentType(ContentType.JSON)
                        .body(hm)
                        .when()
                        .post(baseURI + "v1/auth/otp/shoot-otp/email")
                        .then()
                        .extract()
                        .response();
            
                if (response.getStatusCode() == 429) {
                    String message = response.jsonPath().getString("message");
                    int retryAfter = response.jsonPath().getInt("retry_after");
            
                    System.out.println(message);
                    System.out.println("retry_after: " + retryAfter);
                }
            
                return response;
            }

        private String validateOtp(String apiToken) {
                RestAssured.baseURI = "http://agave-api-test:8080/api/";

                HashMap<String, String> requestBody = new HashMap<>();
                requestBody.put("app_id", "agave");
                requestBody.put("api_token", apiToken);
                requestBody.put("email", "asif1@gmail.com");
                requestBody.put("otp", "1111");

                return given()
                                .contentType(ContentType.JSON)
                                .body(requestBody)
                                .when()
                                .post(baseURI + "v1/auth/otp/validate-otp/email")
                                .path("authorization_code");
        }

        @BeforeMethod
        public void authorizationCodesetup() {
                String apiToken = getApiToken();
                shootOtp(apiToken);
                authorizationCode = validateOtp(apiToken);
        }

        @Test(priority = 1)
        public void userShouldBeRegisteredWithNewEmail() throws SQLException {

                RestAssured.baseURI = "http://agave-api-test:8080/api/";
                DB.truncateTables(new String[] {"profiles"});

                HashMap<String, String> requestBody = new HashMap<>();
                requestBody.put("email", "asif1@gmail.com");
                requestBody.put("password", "Asif9878");
                requestBody.put("authorization_code", authorizationCode);

                Response response = given()
                                .header("Accept-Language", "jp")
                                .contentType(ContentType.JSON)
                                .body(requestBody)
                                .when()
                                .post(baseURI + "v1/auth/register-auth-email")
                                .then()
                                .extract().response();

                Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test(priority = 2)
        public void userShouldNotBeRegisteredWithExistingEmail() {

                RestAssured.baseURI = "http://agave-api-test:8080/api/";

                HashMap<String, String> requestBody = new HashMap<>();
                requestBody.put("email", "asif1@gmail.com");
                requestBody.put("password", "Asif9878");
                requestBody.put("authorization_code", authorizationCode);

                Response response = given()
                                .header("Accept-Language", "jp")
                                .contentType(ContentType.JSON)
                                .body(requestBody)
                                .when()
                                .post(baseURI + "v1/auth/register-auth-email")
                                .then()
                                .extract().response();

                Assert.assertEquals(response.getStatusCode(), 400);

                String message = response.getBody().jsonPath().getString("message");
                Assert.assertEquals(message, "このメールは既に存在します");
        }

        @Test(priority = 3)
        public void userShouldNotRegisterWithWrongEmailFormat() {

                RestAssured.baseURI = "http://agave-api-test:8080/api/";

                HashMap<String, String> requestBody = new HashMap<>();
                requestBody.put("email", "asif.gmail.com");
                requestBody.put("password", "Asif9878");
                requestBody.put("authorization_code", authorizationCode);

                Response response = given()
                                .header("Accept-Language", "jp")
                                .contentType(ContentType.JSON)
                                .body(requestBody)
                                .when()
                                .post(baseURI + "v1/auth/register-auth-email")
                                .then()
                                .extract().response();

                Assert.assertEquals(response.getStatusCode(), 406);

                String message = response.getBody().jsonPath().getString("message");
                Assert.assertEquals(message, "入力されたメールアドレスが間違っています。もう一度やり直してください。");

        }

        @Test(priority = 4)
        public void passwordShouldContainAtleastOneUpperCaseLetter() {

                RestAssured.baseURI = "http://agave-api-test:8080/api/";

                HashMap<String, String> requestBody = new HashMap<>();
                requestBody.put("email", "asif1@gmail.com");
                requestBody.put("password", "asif9878");
                requestBody.put("authorization_code", authorizationCode);

                Response response = given()
                                .header("Accept-Language", "jp")
                                .contentType(ContentType.JSON)
                                .body(requestBody)
                                .when()
                                .post(baseURI + "v1/auth/register-auth-email")
                                .then()
                                .extract().response();

                Assert.assertEquals(response.getStatusCode(), 406);

                String message = response.getBody().jsonPath().getString("message");
                Assert.assertEquals(message, "パスワードには少なくとも 1 つの大文字が含まれている必要があります");
        }

        @Test(priority = 5)
        public void passwordLengthShouldBeMinimumEightCharacter() {

                RestAssured.baseURI = "http://agave-api-test:8080/api/";

                HashMap<String, String> requestBody = new HashMap<>();
                requestBody.put("email", "asif1@gmail.com");
                requestBody.put("password", "Asif987");
                requestBody.put("authorization_code", authorizationCode);

                Response response = given()
                                .header("Accept-Language", "jp")
                                .contentType(ContentType.JSON)
                                .body(requestBody)
                                .when()
                                .post(baseURI + "v1/auth/register-auth-email")
                                .then()
                                .extract().response();

                Assert.assertEquals(response.getStatusCode(), 406);
                String message = response.getBody().jsonPath().getString("message");
                Assert.assertEquals(message, "パスワードは半角英数字8文字以上でなければならない。");
        }

        public String getAuthorizationCode() {
                return authorizationCode;
        }
}