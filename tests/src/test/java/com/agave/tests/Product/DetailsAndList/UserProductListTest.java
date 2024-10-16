package com.agave.tests.Product.DetailsAndList;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserProductListTest {

        private String accessToken;
        private JwtToken jwtToken = new JwtToken();

        @BeforeClass
        public void baseURISetUp() {
                RestAssured.baseURI = "http://agave-api-test:8080/api/";
                accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
        }

        @Test(priority = 1)
        public void userShouldGetProductListWithQueryParams() {

                Response response = given()
                                .header("Authorization", "Bearer " + accessToken)
                                .queryParam("sortBy", "NEW_ARRIVAL")
                                .queryParam("category", "BROWSED_PRODUCT")
                                .queryParam("search", "t")
                                .queryParam("species", "UmbrellaPlant")
                                .queryParam("size", "Pup")
                                .queryParam("origin", "Taiwan")
                                .queryParam("limit", 10)
                                .queryParam("pageNumber", 0)
                                .queryParam("maxPrice", 6000000)
                                .queryParam("minPrice", 0)
                                .when()
                                .get(baseURI + "v1/product/user-products");
                   
                Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test(priority = 2)
        public void userShouldGetProductListByDifferentQueryParams() {

                Response response = given()
                                .header("Authorization", "Bearer " + accessToken)
                                .queryParam("species", "UmbrellaPlant")
                                .queryParam("size", "Pup")
                                .queryParam("origin", "Taiwan")
                                .queryParam("limit", 10)
                                .queryParam("pageNumber", 0)
                                .queryParam("maxPrice", 10000)
                                .queryParam("minPrice", 0)
                                .when()
                                .get(baseURI + "v1/product/user-products");

                Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test(priority = 3)
        public void userShouldNotGetProductListWithoutAuthorization() {

                Response response = given()
                                .queryParam("sortBy", "NEW_ARRIVAL")
                                .queryParam("category", "PRODUCT_LIST")
                                .queryParam("search", "t")
                                .queryParam("species", "UmbrellaPlant")
                                .queryParam("size", "Pup")
                                .queryParam("origin", "Taiwan")
                                .queryParam("limit", 10)
                                .queryParam("pageNumber", 0)
                                .queryParam("maxPrice", 10000)
                                .queryParam("minPrice", 0)
                                .when()
                                .get(baseURI + "v1/product/user-products");

                Assert.assertEquals(response.getStatusCode(), 500);
        }
}