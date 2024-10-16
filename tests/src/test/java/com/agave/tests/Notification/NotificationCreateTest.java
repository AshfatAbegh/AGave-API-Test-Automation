// package com.agave.tests.Notification;                       //commented for whole system testing

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

// public class NotificationCreateTest {

//     private String accessToken;
//     private JwtToken jwtToken = new JwtToken();

//     @BeforeClass
//     public void dbSetUp(){
//         RestAssured.baseURI = "http://agave-api-test:8080/api/";
//         DB.truncateTables2(new String[] {"notifications","users"});

//         accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
//     }

//     @SuppressWarnings("unchecked")
//     @Test(priority = 1)
//     public void notificationShouldBeCreatedUsingProperInfo(){

//         String[] channels = {"push", "inApp"};

//         @SuppressWarnings("rawtypes")
//         HashMap requestBody = new HashMap();
//         requestBody.put("type","User");
//         requestBody.put("title","This is from agave backend");
//         requestBody.put("message","Agave test backend");
//         requestBody.put("channels",channels);
//         requestBody.put("createdBy","ADMIN");

//         Response response = given()
//                  .header("Authorization", "Bearer " + accessToken)
//                  .contentType(ContentType.JSON)
//                  .body(requestBody)
//                  .when()
//                  .post(baseURI + "v1/notification/create");    

//         Assert.assertEquals(response.getStatusCode(), 200);
//     } 
// }
