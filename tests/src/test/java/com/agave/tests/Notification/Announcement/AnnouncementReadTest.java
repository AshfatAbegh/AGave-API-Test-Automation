//package com.agave.tests.Notification.Announcement;
//package com.agave.tests.Admin.Announcement;
// package com.agave.tests.Notification.Announcement;

// import static io.restassured.RestAssured.baseURI;
// import static io.restassured.RestAssured.given;

// import java.sql.SQLException;

// import org.testng.Assert;
// import org.testng.annotations.BeforeClass;
// import org.testng.annotations.Test;

// import com.agave.tests.Utilities.JWT.JwtToken;

// import io.restassured.RestAssured;
// import io.restassured.response.Response;

// public class AnnouncementReadTest {
//     private String accessToken;
//     private JwtToken jwtToken = new JwtToken();

//     @BeforeClass
//     public void baseURISetUp() throws SQLException {
//         RestAssured.baseURI = "http://agave-api-test:8080/api/";
//         accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");
//     }

//     @Test(priority = 1)
//     public void userShouldReadAnnouncementOfValidIdWithProperAuthorization() {

//         Response response = given()
//                 .header("Authorization", "Bearer " + accessToken)
//                 .header("Accept-Language", "jp")
//                 .when()
//                 .put(baseURI + "v1/announcement/read/1");

//         Assert.assertEquals(response.getStatusCode(), 200);
//     }

//     @Test(priority = 2)
//     public void userShouldNotReadAnnouncementOfInvalidId() {
        
//         Response response = given()
//                 .header("Authorization", "Bearer " + accessToken)
//                 .header("Accept-Language", "jp")
//                 .when()
//                 .put(baseURI + "v1/announcement/read/10000");

//         Assert.assertEquals(response.getStatusCode(), 404);
//         String message = response.jsonPath().getString("message");
//         Assert.assertEquals(message, "アナウンスはありません");
//     }

//     @Test(priority = 3)
//     public void userShouldNotReadAnnouncementOfValidIdWithoutAuthorization() {

//         Response response = given()
//                 .header("Accept-Language", "jp")
//                 .when()
//                 .put(baseURI + "v1/announcement/read/1");

//         Assert.assertEquals(response.getStatusCode(), 403);
//     }
// }