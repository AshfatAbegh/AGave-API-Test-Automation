// package com.agave.tests.Profile.Update;                        //Currently this api isn't needed. Kept it for future purpose

// import static io.restassured.RestAssured.baseURI;
// import static io.restassured.RestAssured.given;

// import java.util.HashMap;

// import org.testng.Assert;
// import org.testng.annotations.BeforeClass;
// // import org.testng.annotations.BeforeMethod;
// import org.testng.annotations.Test;

// import com.agave.tests.Profile.InfoAdd.FirstPageInfoTest;

// import io.restassured.RestAssured;
// import io.restassured.http.ContentType;
// import io.restassured.response.Response;

// public class UpdateMobileNumberTest extends FirstPageInfoTest {

//     private String authorizationCode;

//     private String getApiToken() {
//         RestAssured.baseURI = "http://agave-api-test:8080/api/";

//         Response response = given()
//                 .queryParam("app_id", "agave")
//                 .when()
//                 .get(baseURI + "v1/auth/otp/api-token")
//                 .then()
//                 .contentType("application/json")
//                 .extract()
//                 .response();

//         return response.jsonPath().getString("api_token");
//     }

//     private void shootOtp(String apiToken) {
//         HashMap<String, String> hm = new HashMap<>();
//         hm.put("app_id", "agave");
//         hm.put("api_token", apiToken);
//         hm.put("mobile", "07012345677");

//         RestAssured.baseURI = "http://agave-api-test:8080/api/";

//         given()
//                 .contentType(ContentType.JSON)
//                 .body(hm)
//                 .when()
//                 .post(baseURI + "v1/auth/otp/shoot-otp/mobile");
//     }

//     private String validateOtp(String apiToken) {
//         RestAssured.baseURI = "http://agave-api-test:8080/api/";

//         HashMap<String, String> requestBody = new HashMap<>();
//         requestBody.put("app_id", "agave");
//         requestBody.put("api_token", apiToken);
//         requestBody.put("mobile", "07012345677");
//         requestBody.put("otp", "1111");

//         return given()
//                 .contentType(ContentType.JSON)
//                 .body(requestBody)
//                 .when()
//                 .post(baseURI + "v1/auth/otp/validate-otp/mobile")
//                 .path("authorization_code");
//     }

//     @BeforeClass
//     public void authorizationCodesetup() {
//         String apiToken = getApiToken();
//         shootOtp(apiToken);
//         authorizationCode = validateOtp(apiToken);
//     }

//     @Test(priority = 1)
//     public void userShouldUpdateMobileNumberSuccessfullyWithProperAuthorization() {
//         RestAssured.baseURI = "http://agave-api-test:8080/api/";

//         HashMap<String, String> requestBody = new HashMap<>();
//         requestBody.put("mobile", "07012345677");
//         requestBody.put("authorization_code", authorizationCode);

//         Response response = given()
//                 .contentType(ContentType.JSON)
//                 .body(requestBody)
//                 .header("Authorization", "Bearer " + getAccessToken())
//                 .header("Accept-Language", "jp")
//                 .when()
//                 .put(baseURI + "v1/profile/update-mobile");

//         Assert.assertEquals(response.getStatusCode(), 200);
//     }

//     @Test(priority = 2)
//     public void userShouldNotUpdateTheSameMobileNumberWhichIsAlreadyRegistered() {
//         RestAssured.baseURI = "http://agave-api-test:8080/api/";

//         HashMap<String, String> requestBody = new HashMap<>();
//         requestBody.put("mobile", "07012345677");
//         requestBody.put("authorization_code", authorizationCode);

//         Response response = given()
//                 .contentType(ContentType.JSON)
//                 .body(requestBody)
//                 .header("Authorization", "Bearer " + getAccessToken())
//                 .header("Accept-Language", "jp")
//                 .when()
//                 .put(baseURI + "v1/profile/update-mobile");

//         Assert.assertEquals(response.getStatusCode(), 404);
//         String message = response.jsonPath().getString("message");
//         Assert.assertEquals(message, "この電話番号はすでに登録されています");
//     }

//     @Test(priority = 3)
//     public void userShouldNotUpdateMobileNumberWithoutAuthorization(){

//         RestAssured.baseURI = "http://agave-api-test:8080/api/";

//         HashMap<String, String> requestBody = new HashMap<>();
//         requestBody.put("mobile", "07012345677");
//         requestBody.put("authorization_code", authorizationCode);
    
//         Response response = given()
//                 .contentType(ContentType.JSON)
//                 .body(requestBody)
//                 .header("Accept-Language", "jp")
//                 .when()
//                 .put(baseURI + "v1/profile/update-mobile");

//         Assert.assertEquals(response.getStatusCode(), 500);
//     }

//     @Test(priority = 4)
//     public void userShouldNotUpdateInvalidMobileNumber(){

//         RestAssured.baseURI = "http://agave-api-test:8080/api/";

//         HashMap<String, String> requestBody = new HashMap<>();
//         requestBody.put("mobile", "0701234567");
//         requestBody.put("authorization_code", authorizationCode);
    
//         Response response = given()
//                 .contentType(ContentType.JSON)
//                 .body(requestBody)
//                 .header("Authorization", "Bearer " + getAccessToken())
//                 .header("Accept-Language", "jp")
//                 .when()
//                 .put(baseURI + "v1/profile/update-mobile");

//         Assert.assertEquals(response.getStatusCode(), 406);
//         String message = response.jsonPath().getString("message");
//         Assert.assertEquals(message, "無効な電話番号");
//     }

//     public String getAuthorizationCode(){
//         return authorizationCode;
//     }
// }