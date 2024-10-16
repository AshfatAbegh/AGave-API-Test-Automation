package com.agave.tests.Profile.Seller;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class SellerListTest {

        private String accessToken;
        private JwtToken jwtToken = new JwtToken();

        @BeforeClass
        public void dbUpdate() throws SQLException {
                RestAssured.baseURI = "http://agave-api-test:8080/api/";
                
                accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");
        }

        @Test(priority = 1)
        public void userShouldGetSellerListWithQueryParams() {

                Response response = given()
                                .header("Authorization", "Bearer " + accessToken)
                                .queryParam("limit", 10)
                                .queryParam("page", 0)
                                .queryParam("searchString", "a")
                                .queryParam("sortBy", "popularity")
                                .queryParam("sortBy", "newArrival")
                                .queryParam("reviewRatting", 5)
                                .queryParam("treeSpecies", "UmbrellaPlant")
                                .queryParam("platformName", "FaceBook, Instagram, Twitter")
                                .when()
                                .get(baseURI + "v1/profile/seller-list");
  
                Assert.assertEquals(response.getStatusCode(), 200);
                response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SellerListTestJSONSchema.json"));
        }

        @Test(priority = 2)
        public void userShouldGetSellerListWithDifferentQueryParams() {

                Response response = given()
                                .header("Authorization", "Bearer " + accessToken)
                                .queryParam("limit", 10)
                                .queryParam("page", 1)
                                .queryParam("searchString", "r")
                                .queryParam("sortBy", "popularity")
                                .queryParam("treeSpecies", "UmbrellaPlant")
                                .queryParam("platformName", "FaceBook, Instagram, Twitter")
                                .when()
                                .get(baseURI + "v1/profile/seller-list");

                Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test(priority = 3)
        public void userShouldGetSellerListWithProperAuthorization() {

                Response response = given()
                                .header("Authorization", "Bearer " + accessToken)
                                .queryParam("limit", 10)
                                .queryParam("page", 1)
                                .queryParam("searchString", "r")
                                .queryParam("sortBy", "popularity")
                                .queryParam("treeSpecies", "UmbrellaPlant")
                                .queryParam("platformName", "FaceBook, Instagram, Twitter")
                                .when()
                                .get(baseURI + "v1/profile/seller-list");

                Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test(priority = 4)
        public void userShouldNotGetSellerListWithoutAuthorization() {

                Response response = given()
                                .queryParam("limit", 10)
                                .queryParam("page", 1)
                                .queryParam("searchString", "r")
                                .queryParam("sortBy", "popularity")
                                .queryParam("treeSpecies", "UmbrellaPlant")
                                .queryParam("platformName", "FaceBook, Instagram, Twitter")

                                .when()
                                .get(baseURI + "v1/profile/seller-list");

                Assert.assertEquals(response.getStatusCode(), 500);
        }
}