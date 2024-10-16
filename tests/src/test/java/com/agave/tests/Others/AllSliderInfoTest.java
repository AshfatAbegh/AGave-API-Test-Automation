package com.agave.tests.Others;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class AllSliderInfoTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();
    
    @BeforeClass
    public void baseURISteup() throws SQLException{
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"sliders"});

        accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");

        insertSliderInfo(1, "banner/images/banner_1719399870161.png", "sss", "ss", new Date().getTime());
    }

    public void insertSliderInfo(int id, String bannerImageUrl, String link, String title, long created_at) throws SQLException{
        Timestamp createdAtTimestamp = new Timestamp(created_at);
        DB.getConnection().executeUpdate("INSERT INTO sliders(`id`, `bannerImageUrl`, `link`, `title`, `created_at`) VALUES('" + id + "', '" + bannerImageUrl + "', '" + link + "', '" + title + "', '" + createdAtTimestamp + "')");
    } 

    @Test(priority = 1)
    public void userShouldGetAllSliderInfoWithProperAuthorization(){
        
        Response response = given()
                 .header("Authorization", "Bearer " + accessToken)
                 .header("Accept-Language", "jp")
                 .when()
                 .get(baseURI + "v1/others/slider-all");

        Assert.assertEquals(response.getStatusCode(), 200);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("AllSliderInfoTestJSONSchema.json"));
    }

    @Test(priority = 2)
    public void userShouldNotGetAllSliderInfoWithoutAuthorization(){
        
        Response response = given()
                 .header("Accept-Language", "jp")
                 .when()
                 .get(baseURI + "v1/others/slider-all");
                 
        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
