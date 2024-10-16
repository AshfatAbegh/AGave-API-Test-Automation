// package com.agave.tests.Notification;

// import static io.restassured.RestAssured.baseURI;
// import static io.restassured.RestAssured.given;

// import java.util.HashMap;

// import org.testng.Assert;
// import org.testng.annotations.BeforeClass;
// import org.testng.annotations.Test;

// import com.agave.tests.Utilities.DB;
// import com.agave.tests.Utilities.JWT.JwtToken;

// import io.restassured.RestAssured;
// import io.restassured.http.ContentType;
// import io.restassured.response.Response;

// public class NotificationSubscribeTest {

//     private String accessToken;
//     private JwtToken jwtToken = new JwtToken();

//     @BeforeClass
//     public void dbSetUp(){
//         RestAssured.baseURI = "http://agave-api-test:8080/api/";
//         DB.truncateTables2(new String[] {"notifications","push_subscriptions"});

//         accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
//     }

//     @SuppressWarnings("unchecked")
//     @Test(priority = 1)
//     public void notificationShouldBeSubscribedUsingDeviceSpecificFcmToken() {

//         @SuppressWarnings("rawtypes")
//         HashMap requestBody = new HashMap();
//         requestBody.put("fcmToken", "fN3B-QbTT0awI6zViIH0Xc:APA91bGhFVgcOg4EPcjnGVkX8JYun_n9myJpme40868lx_27s4laYEzS-ZNrds2xmFEssTQdY76rO0wh8hCvwHI2kFOQKelSAs5izEuSsqTXqvUiGC5Q5V7Tk1WVP4JXNmfGl0cUBZXM");
        
//         Response response = given()
//                  .header("Authorization", "Bearer " + accessToken)
//                  .contentType(ContentType.JSON)
//                  .body(requestBody)
//                  .when()
//                  .post(baseURI + "v1/notification/subscribe"); 
         
//         Assert.assertEquals(response.getStatusCode(), 200);
//     }

//     @Test(priority = 2)
//     public void notificationShouldNotBeSubscribedWithoutDeviceSpecificFcmToken() {
        
//         Response response = given()
//                  .header("Authorization", "Bearer " + accessToken)
//                  .contentType(ContentType.JSON)
//                  .when()
//                  .post(baseURI + "v1/notification/subscribe"); 
                 
//         Assert.assertEquals(response.getStatusCode(), 400);
//         String message = response.jsonPath().getString("message");
//         Assert.assertEquals(message,"無効な JSON 形式");
//     }

//     @SuppressWarnings("unchecked")
//     @Test(priority = 3)
//     public void notificationShouldNotBeSubscribedUsingDeviceSpecificFcmTokenWithoutAuthorization() {

//         @SuppressWarnings("rawtypes")
//         HashMap requestBody = new HashMap();
//         requestBody.put("fcmToken", "cPhSKkUaSoG_vMlfXmkYFj:APA91bEoQaL7-hZek7abST4wBsEMa8yekWvmFWdIzGMC0D22GgWJ9ZAreLSWzUHmsaARW-FeZrVUWrIWmuaIBHAf2hIfw-ufejQpIqWrr-KoeEZADcggmgF4gcIEhV_tw64LbQfZGqSy");
        
//         Response response = given()
//                  .contentType(ContentType.JSON)
//                  .body(requestBody)
//                  .when()
//                  .post(baseURI + "v1/notification/subscribe"); 
                 
//         Assert.assertEquals(response.getStatusCode(), 500);
//     }
// }
