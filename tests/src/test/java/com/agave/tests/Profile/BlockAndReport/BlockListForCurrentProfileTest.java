// package com.agave.tests.Profile.BlockAndReport;                  //commented for whole system testing purpose

// import static io.restassured.RestAssured.baseURI;
// import static io.restassured.RestAssured.given;

// import java.sql.SQLException;

// import org.testng.Assert;
// import org.testng.annotations.BeforeClass;
// import org.testng.annotations.Test;

// import com.agave.tests.Utilities.DB;
// import com.agave.tests.Utilities.TestUser;
// import com.agave.tests.Utilities.JWT.JwtToken;

// import io.restassured.RestAssured;
// import io.restassured.module.jsv.JsonSchemaValidator;
// import io.restassured.response.Response;

// public class BlockListForCurrentProfileTest {

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

//     @Test(priority = 1)
//     public void userShouldGetBlockedUserProfileListWhichProfilesAreAlreadyBlocked() {

//         Response response = given()
//                 .header("Authorization", "Bearer " + accessToken)
//                 .when()
//                 .get(baseURI + "v1/profile/block-list");

//         Assert.assertEquals(response.getStatusCode(), 200);
//         response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("BlockListForCurrentProfileTestJSONSchema.json"));
//     }

//     @Test(priority = 2)
//     public void userShouldNotGetBlockedUserProfileListWithoutAuthorization() {

//         Response response = given()
//                 .when()
//                 .get(baseURI + "v1/profile/block-list");

//         Assert.assertEquals(response.getStatusCode(), 500);
//     }
// }
