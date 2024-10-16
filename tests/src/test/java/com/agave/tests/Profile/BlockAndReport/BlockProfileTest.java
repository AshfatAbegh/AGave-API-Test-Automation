// package com.agave.tests.Profile.BlockAndReport;                    //commented for whole system testing purpose

// import org.testng.Assert;
// import org.testng.annotations.BeforeClass;
// import org.testng.annotations.Test;

// import com.agave.tests.Utilities.DB;
// import com.agave.tests.Utilities.TestUser;
// import com.agave.tests.Utilities.JWT.JwtToken;

// import io.restassured.RestAssured;
// import io.restassured.http.ContentType;
// import io.restassured.response.Response;
// import static io.restassured.RestAssured.baseURI;
// import static io.restassured.RestAssured.given;

// import java.sql.SQLException;
// import java.util.HashMap;

// public class BlockProfileTest {

//     private String accessToken;
//     private JwtToken jwtToken = new JwtToken();

//     @BeforeClass
//     public void baseURISetUp() throws SQLException {
//         RestAssured.baseURI = "http://agave-api-test:8080/api/";
//         DB.truncateTables(new String[] {"profiles", "blocked_users"});

//         TestUser.setupUsers(1, 2, 3, 4);
//         accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");
//     }

//     @SuppressWarnings({ "unchecked", "rawtypes" })
//     @Test(priority = 1)
//     public void producerShouldBlockUserWithValidId() {

//         HashMap requestBody = new HashMap();
//         requestBody.put("blockedId", 3);

//         Response response = given()
//                 .header("Authorization", "Bearer " + accessToken)
//                 .header("Accept-Language", "jp")
//                 .contentType(ContentType.JSON)
//                 .body(requestBody)
//                 .when()
//                 .post(baseURI + "v1/profile/block");

//         Assert.assertEquals(response.getStatusCode(), 200);
//     }

//     @SuppressWarnings({ "unchecked", "rawtypes" })
//     @Test(priority = 2)
//     public void producerShouldNotBlockAUserWhoIsAlreadyBlocked() {

//         HashMap requestBody = new HashMap();
//         requestBody.put("blockedId", 3);

//         Response response = given()
//                 .header("Authorization", "Bearer " + accessToken)
//                 .header("Accept-Language", "jp")
//                 .contentType(ContentType.JSON)
//                 .body(requestBody)
//                 .when()
//                 .post(baseURI + "v1/profile/block");

//         Assert.assertEquals(response.getStatusCode(), 404);
//         String message = response.jsonPath().getString("message");
//         Assert.assertEquals(message, "ユーザーはすでにブロックされています");
//     }

//     @SuppressWarnings({ "unchecked", "rawtypes" })
//     @Test(priority = 3)
//     public void producerShouldNotBlockUserWithInvalidId() {

//         HashMap requestBody = new HashMap();
//         requestBody.put("blockedId", 5);
        
//         Response response = given()
//                 .header("Authorization", "Bearer " + accessToken)
//                 .header("Accept-Language", "jp")
//                 .contentType(ContentType.JSON)
//                 .body(requestBody)
//                 .when()
//                 .post(baseURI + "v1/profile/block");

//         Assert.assertEquals(response.getStatusCode(), 404);
//         String message = response.jsonPath().getString("message");
//         Assert.assertEquals(message, "プロフィールがありません");
//     }

//     @SuppressWarnings({ "unchecked", "rawtypes" })
//     @Test(priority = 4)
//     public void producerShouldNotBlockThyself() {

//         HashMap requestBody = new HashMap();
//         requestBody.put("blockedId", 1);
        
//         Response response = given()
//                 .header("Authorization", "Bearer " + accessToken)
//                 .header("Accept-Language", "jp")
//                 .contentType(ContentType.JSON)
//                 .body(requestBody)
//                 .when()
//                 .post(baseURI + "v1/profile/block");

//         Assert.assertEquals(response.getStatusCode(), 404);
//         String message = response.jsonPath().getString("message");
//         Assert.assertEquals(message, "自分自身をブロックできません");
//     }

//     @SuppressWarnings({ "unchecked", "rawtypes" })
//     @Test(priority = 5)
//     public void producerShouldNotBlockUserWithoutAuthorization() {

//         HashMap requestBody = new HashMap();
//         requestBody.put("blockedId", 3);
        
//         Response response = given()
//                 .header("Accept-Language", "jp")
//                 .contentType(ContentType.JSON)
//                 .body(requestBody)
//                 .when()
//                 .post(baseURI + "v1/profile/block");

//         Assert.assertEquals(response.getStatusCode(), 500);
//     }
// }
