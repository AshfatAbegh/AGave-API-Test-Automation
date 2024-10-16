// package com.agave.tests.Notification.Announcement;

// import static io.restassured.RestAssured.baseURI;
// import static io.restassured.RestAssured.given;

// import java.sql.SQLException;

// import org.testng.Assert;
// import org.testng.annotations.BeforeClass;
// import org.testng.annotations.Test;

// import com.agave.tests.Utilities.JWT.JwtToken;

// import io.restassured.RestAssured;
// import io.restassured.module.jsv.JsonSchemaValidator;
// import io.restassured.response.Response;

// public class AnnouncementListTest {
    
//     private String accessToken;
//     private JwtToken jwtToken = new JwtToken();

//     @BeforeClass
//     public void baseURISetUp() throws SQLException{
//         RestAssured.baseURI = "http://agave-api-test:8080/api/";
//         accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");
//     }

//     @Test(priority = 1)
//     public void userShouldSeeAllAnnouncementsListWithProperAuthorization(){

//         Response response = given()
//                  .header("Authorization", "Bearer " + accessToken)
//                  .header("Accept-Language", "jp")
//                  .queryParam("page", 0)
//                  .queryParam("size", 3)
//                  .when()
//                  .get(baseURI + "v1/announcement/all");

//         Assert.assertEquals(response.getStatusCode(), 200);
//         response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("AnnouncementListTestJSONSchema.json"));
//     }

//     @Test(priority = 2)
//     public void userShouldNotSeeAnyAnnouncementWithoutAuthorization(){

//         Response response = given()
//                  .header("Accept-Language", "jp")
//                  .queryParam("page", 0)
//                  .queryParam("size", 3)
//                  .when()
//                  .get(baseURI + "v1/announcement/all");

//         Assert.assertEquals(response.getStatusCode(), 403);
//     }
// }
