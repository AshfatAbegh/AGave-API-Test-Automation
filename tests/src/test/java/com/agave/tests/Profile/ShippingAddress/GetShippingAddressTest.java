package com.agave.tests.Profile.ShippingAddress;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.TestUser;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class GetShippingAddressTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void dbSetUp() throws SQLException {
        
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles", "products", "products_images", "productHashtag", "products_browsedUsers", "products_likedUsers"});

        TestUser.setupUsers(1, 2, 3, 4);
        accessToken = jwtToken.generateAccessToken(2, "araf@gmail.com");
    }

    @Test(priority = 1)
    public void userShouldGetIndividualProductShippingAddressWithProperAuthorization() {

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .when()
                .get(baseURI + "v1/profile/shipping-address");

        Assert.assertEquals(response.getStatusCode(), 200);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetShippingAddressTestJSONSchema.json"));
    }

    @Test(priority = 2)
    public void userShouldNotGetIndividualProductShippingAddressWithoutAuthorization() {

        Response response = given()
                .header("Accept-Language", "jp")
                .when()
                .get(baseURI + "v1/profile/shipping-address");

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
