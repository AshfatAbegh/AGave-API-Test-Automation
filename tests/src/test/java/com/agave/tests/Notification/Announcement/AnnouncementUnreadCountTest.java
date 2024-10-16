//package com.agave.tests.Notification.Announcement;
//package com.agave.tests.Admin.Announcement;
// package com.agave.tests.Notification.Announcement;

// import static io.restassured.RestAssured.baseURI;
// import static io.restassured.RestAssured.given;

// import org.testng.Assert;
// import org.testng.annotations.BeforeClass;
// import org.testng.annotations.Test;

// import com.agave.tests.Utilities.JWT.JwtToken;

// import io.restassured.RestAssured;
// import io.restassured.module.jsv.JsonSchemaValidator;
// import io.restassured.response.Response;

// public class AnnouncementUnreadCountTest {

//     private String accessToken;
//     private JwtToken jwtToken = new JwtToken();
    
//     @BeforeClass
//     public void baseURISetUp(){
//         RestAssured.baseURI = "http://agave-api-test:8080/api/";
//         accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");
//     }

//     @Test(priority = 1)
//     public void userShouldSeeUnreadAnnouncementCountWithProperAuthorization(){

//         Response response = given()
//                 .header("Authorization", "Bearer " + accessToken)
//                 .header("Accept-Language", "jp")
//                 .when()
//                 .get(baseURI + "v1/announcement/unread-count");

//         Assert.assertEquals(response.getStatusCode(), 200);
//         response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("AnnouncementUnreadCountTestJSONSchema.json"));
//     }

//     @Test(priority = 2)
//     public void userShouldNotSeeUnreadAnnouncementCountWithoutAuthorization(){

//         Response response = given()
//                 .header("Accept-Language", "jp")
//                 .when()
//                 .get(baseURI + "v1/announcement/unread-count");

//         Assert.assertEquals(response.getStatusCode(), 403);
//     }
// }
