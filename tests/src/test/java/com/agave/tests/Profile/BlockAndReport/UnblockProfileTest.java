// package com.agave.tests.Profile.BlockAndReport;              //commented for whole system testing purpose

// import static io.restassured.RestAssured.baseURI;
// import static io.restassured.RestAssured.given;

// import java.sql.SQLException;
// import java.util.HashMap;

// import org.testng.Assert;
// import org.testng.annotations.BeforeClass;
// import org.testng.annotations.Test;

// import com.agave.tests.Utilities.DB;
// import com.agave.tests.Utilities.TestUser;
// import com.agave.tests.Utilities.JWT.JwtToken;

// import io.restassured.RestAssured;
// import io.restassured.http.ContentType;
// import io.restassured.response.Response;

// public class UnblockProfileTest {

//     private String accessToken;
//     private JwtToken jwtToken = new JwtToken();

//     @BeforeClass
//     public void dbSetUp() throws SQLException {
//         RestAssured.baseURI = "http://agave-api-test:8080/api/";
//         DB.truncateTables(new String[] {"profiles", "blocked_users"});

//         TestUser.setupUsers(1, 2, 3, 4);
//         accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");

//         insertBlockedUser(4, 1);
//     }

//     private void insertBlockedUser(int blocked_id, int blocker_id) throws SQLException {
//         DB.getConnection().executeUpdate("INSERT INTO `blocked_users` (`blocked_id`, `blocker_id`) VALUES ('"+ blocked_id + "', '" + blocker_id + "')");
//     }

//     @SuppressWarnings("unchecked")
//     @Test(priority = 1)
//     public void userShouldUnblockProfileWithValidId() {

//         @SuppressWarnings("rawtypes")
//         HashMap requestBody = new HashMap<>();
//         requestBody.put("unBlockedId", 4);

//         Response response = given()
//                 .header("Authorization", "Bearer " + accessToken)
//                 .header("Accept-Language", "jp")
//                 .contentType(ContentType.JSON)
//                 .body(requestBody)
//                 .when()
//                 .post(baseURI + "v1/profile/unblock");

//         Assert.assertEquals(response.getStatusCode(), 200);
//     }

//     @SuppressWarnings("unchecked")
//     @Test(priority = 2)
//     public void userShouldNotUnblockProfileWithInvalidId() {

//         @SuppressWarnings("rawtypes")
//         HashMap requestBody = new HashMap<>();
//         requestBody.put("unBlockedId", 5);

//         Response response = given()
//                 .header("Authorization", "Bearer " + accessToken)
//                 .header("Accept-Language", "jp")
//                 .contentType(ContentType.JSON)
//                 .body(requestBody)
//                 .when()
//                 .post(baseURI + "v1/profile/unblock");

//         Assert.assertEquals(response.getStatusCode(), 404);
//         String message = response.jsonPath().getString("message");
//         Assert.assertEquals(message, "ユーザーはブロックされていません");
//     }

//     @SuppressWarnings("unchecked")
//     @Test(priority = 3)
//     public void userShouldNotUnblockProfileWithoutAuthorization() {

//         @SuppressWarnings("rawtypes")
//         HashMap requestBody = new HashMap<>();
//         requestBody.put("unBlockedId", 4);

//         Response response = given()
//                 .header("Accept-Language", "jp")
//                 .contentType(ContentType.JSON)
//                 .body(requestBody)
//                 .when()
//                 .post(baseURI + "v1/profile/unblock");

//         Assert.assertEquals(response.getStatusCode(), 500);
//     }
// }
