package com.agave.tests.Profile.Seller;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.TestUser;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class SellerReviewListTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void baseURISetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles", "products", "ratings", "products_ratings", "reviews", "products_reviews"});

        TestUser.setupUsers(1, 2, 3, 4);
        accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");

        insertProduct(1, "Japan", new Date().getTime(), "Grafting", "BareRoot", "Taiwan", new Date().getTime(),
                "Six",
                new Date().getTime(), "Agave", new Date().getTime(), 1, 33410, new Date().getTime(),
                new Date().getTime(), new Date().getTime(), "454 m.n", "Tokyo",
                "CashOnDelivery",
                "FourthClassMail", "Hokkaido", "1200", "Town", "Small", "SHIPPED", "All Ok",
                "UmbrellaPlant",
                new Date().getTime(), "LessThanFive", 2, 1, "PD0000001");

        insertReviews(1, "Upgrade Quality", "Overall Nice", new Date().getTime(), "USER", "AGave", 1);
    }

    private static void insertProduct(int id, String acquisitionRouteDescription, long appliedAt,
            String breedingMethod,
            String conditionAtTheTimeOfShipment, String countryOfOrigin, long createdAt,
            String daysOfDelivery,
            long deliveredAt, String name, long orderCreatedAt, int orderId, double price, long publishedAt,
            long purchasedAt,
            long shippedAt, String shippingBuildingName, String shippingCity,
            String shippingCost, String shippingMethod, String shippingOrigin, String shippingPostalCode,
            String shippingPrefecture, String size, String status, String supplementaryInformation,
            String treeSpecies,
            long updatedAt, String width, int buyerId_id, int sellerId_id, String readableId) throws SQLException {
        Timestamp appliedAtTimestamp = new Timestamp(appliedAt);
        Timestamp createdAtTimestamp = new Timestamp(createdAt);
        Timestamp deliveredAtTimestamp = new Timestamp(deliveredAt);
        Timestamp orderCreatedAtTimestamp = new Timestamp(orderCreatedAt);
        Timestamp publishedAtTimestamp = new Timestamp(publishedAt);
        Timestamp purchasedAtTimestamp = new Timestamp(purchasedAt);
        Timestamp shippedAtTimestamp = new Timestamp(shippedAt);
        Timestamp updatedAtTimestamp = new Timestamp(updatedAt);
        DB.getConnection().executeUpdate(
                "INSERT INTO `products` (`id`, `acquisitionRouteDescription`, `appliedAt`, `breedingMethod`, `conditionAtTheTimeOfShipment`, `countryOfOrigin`, `createdAt`, `daysOfDelivery`, `deliveredAt`, `name`, `orderCreatedAt`, `orderId`, `price`, `publishedAt`, `purchasedAt`, `shippedAt`, `shippingBuildingName`, `shippingCity`, `shippingCost`, `shippingMethod`, `shippingOrigin`, `shippingPostalCode`, `shippingPrefecture`, `size`, `status`, `supplementaryInformation`, `treeSpecies`, `updatedAt`, `width`, `buyerId_id`, `sellerId_id`, `readableId`) VALUES ('"
                        + id + "', '"
                        + acquisitionRouteDescription + "', '" + appliedAtTimestamp + "', '"
                        + breedingMethod + "', '"
                        + conditionAtTheTimeOfShipment
                        + "', '" + countryOfOrigin + "', '" + createdAtTimestamp + "', '"
                        + daysOfDelivery + "', '"
                        + deliveredAtTimestamp + "', '" + name + "', '"
                        + orderCreatedAtTimestamp + "', '" + orderId + "', '" + price + "', '"
                        + publishedAtTimestamp
                        + "', '" + purchasedAtTimestamp + "', '"
                        + shippedAtTimestamp
                        + "', '" + shippingBuildingName + "', '" + shippingCity + "', '"
                        + shippingCost + "', '"
                        + shippingMethod + "', '" + shippingOrigin + "', '" + shippingPostalCode
                        + "', '"
                        + shippingPrefecture + "', '" + size + "', '" + status + "', '"
                        + supplementaryInformation + "', '" + treeSpecies + "', '"
                        + updatedAtTimestamp + "', '" + width
                        + "', '" + buyerId_id + "', '" + sellerId_id + "', '" + readableId + "')");
    }

    private void insertReviews(int id, String adminSuggestion, String comments, long createdDateAt, String givenBy,
            String title, int product_id) throws SQLException {
        Timestamp createdDateAtTimestamp = new Timestamp(createdDateAt);
        DB.getConnection().executeUpdate(
                "INSERT INTO `reviews` (`id`, `adminSuggestion`, `comments`, `createdDateAt`, `givenBy`, `title`, `product_id`) VALUES('"
                        + id + "', '" + adminSuggestion + "', '" + comments + "', '" + createdDateAtTimestamp + "', '"
                        + givenBy + "', '" + title + "', '" + product_id + "')");
    }

    @Test(priority = 1)
    public void sellerReviewListShouldBeSeenByBothSellerAndBuyerWithValidReviewId() {

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(baseURI + "v1/profile/seller-review-list/1");

        Assert.assertEquals(response.getStatusCode(), 200);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SellerReviewListTestJSONSchema.json"));
    }

    @Test(priority = 2)
    public void sellerReviewListShouldNotBeSeenByBothSellerAndBuyerWithInvalidReviewId() {

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(baseURI + "v1/profile/seller-review-list/10");

        Assert.assertEquals(response.getStatusCode(), 404);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "プロフィールがありません");
    }

    @Test(priority = 3)
    public void sellerReviewListShouldNotBeSeenByBothSellerAndBuyerWithoutAuthorization() {

        Response response = given()
                .when()
                .get(baseURI + "v1/profile/seller-review-list/1");

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
