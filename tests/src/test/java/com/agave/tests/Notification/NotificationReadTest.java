package com.agave.tests.Notification;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class NotificationReadTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();
 
    @BeforeClass
    public void dbSetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables2(new String[] {"notifications", "users"});

        accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
    
        insertNfTestUser(1, "User", "1", new Date().getTime());
        insertTestNotification(1, "This is from agave backend", "Agave test backend", "product/images/product_1717939182820.png", 1, new String[] {"push", "inApp"}, "ADMIN");
    }
    
    public void insertNfTestUser(int id, String user_type, String user_identifier, long created_at) throws SQLException {
        Timestamp createdAtTimestamp = new Timestamp(created_at);
        DB.getConnection2().executeUpdate("INSERT INTO `users`(`id`, `user_type`, `user_identifier`, `created_at`) VALUES ('" + id + "', '" + user_type + "', '" + user_identifier + "', '" + createdAtTimestamp + "')");
    }
    
    public void insertTestNotification(int id, String title, String description, String img_url, int user_id, String[] channels, String created_by) throws SQLException {
        String channelsJsonArray = "JSON_ARRAY('" + String.join("', '", channels) + "')";
        DB.getConnection2().executeUpdate("INSERT INTO `notifications`(`id`, `title`, `description`, `img_url`, `user_id`, `channels`, `created_by`) VALUES ('" + id + "', '" + title + "', '" + description + "', '" + img_url + "', '" + user_id + "', " + channelsJsonArray + ", '" + created_by + "')");
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 1)
    public void userShouldReadNotificationWithValidId(){

        String[] channels = {"push", "inApp"};

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("type","User");
        requestBody.put("title","This is from agave backend");
        requestBody.put("message","Agave test backend");
        requestBody.put("channels",channels);
        requestBody.put("createdBy","ADMIN");

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .header("Accept-Language","jp")
                 .contentType(ContentType.JSON)
                 .body(requestBody)
                 .when()
                 .put(baseURI + "v1/notification/read/1");    

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 2)
    public void userShouldNotReadNotificationWithInvalidId(){

        String[] channels = {"push", "inApp"};

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("type","User");
        requestBody.put("title","This is from agave backend");
        requestBody.put("message","Agave test backend");
        requestBody.put("channels",channels);
        requestBody.put("createdBy","ADMIN");

        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .header("Accept-Language","jp")
                 .contentType(ContentType.JSON)
                 .body(requestBody)
                 .when()
                 .put(baseURI + "v1/notification/read/4");    

        Assert.assertEquals(response.getStatusCode(), 500);
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 3)
    public void userShouldNotReadNotificationWithoutAuthorization(){

        String[] channels = {"push", "inApp"};

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap();
        requestBody.put("type","User");
        requestBody.put("title","This is from agave backend");
        requestBody.put("message","Agave test backend");
        requestBody.put("channels",channels);
        requestBody.put("createdBy","ADMIN");

        Response response = given()
                 .header("Accept-Language","jp")
                 .contentType(ContentType.JSON)
                 .body(requestBody)
                 .when()
                 .put(baseURI + "v1/notification/read/1");    

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
