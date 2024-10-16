package com.agave.tests.Admin;

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
import com.agave.tests.Utilities.TestUser;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ReviewDeleteTest {

    @BeforeClass
    public void dbSetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles", "products", "ratings", "products_ratings", "reviews", "products_reviews"});

        TestUser.setupUsers(1, 2, 3, 4);

        insertProduct(1, "Japan", new Date().getTime(), "Grafting", "BareRoot", "Taiwan", new Date().getTime(),
                "Six",
                new Date().getTime(), "Agave", new Date().getTime(), 1, 33410, new Date().getTime(),
                new Date().getTime(), new Date().getTime(), "454 m.n", "Tokyo",
                "CashOnDelivery",
                "FourthClassMail", "Hokkaido", "1200", "Town", "Small", "DELIVERED", "All Ok",
                "UmbrellaPlant",
                new Date().getTime(), "LessThanFive", 2, 1, "PD0000001");

        insertTestReview(1, "Need Improvement in Packaging", "Good", new Date().getTime(), "USER", "Buyer Survey", 1);
        
        insertTestRating(1, new Date().getTime(), 5, "SELLER_PRODUCT_QUALITY", 1);
        insertTestRating2(2, new Date().getTime(), 3, "SELLER_SERVICE_QUALITY", 1);
        insertTestRating3(3, new Date().getTime(), 5, "SELLER_PACKAGING_QUALITY", 1);
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

    private static void insertTestReview(int id, String adminSuggestion, String comments, long createdDateAt,
            String givenBy, String title, int product_id) throws SQLException {
        Timestamp createdDateAtTimestamp = new Timestamp(createdDateAt);
        DB.getConnection().executeUpdate(
                "INSERT INTO `reviews` (`id`, `adminSuggestion`, `comments`, `createdDateAt`, `givenBy`, `title`, `product_id`) VALUES ('"
                        + id + "', '" + adminSuggestion + "', '" + comments + "', '" + createdDateAtTimestamp + "', '"
                        + givenBy + "', '" + title + "', '" + product_id + "')");
    }

    private static void insertTestRating(int id, long createdDateAt, int rating, String type, int product_id) throws SQLException{
        Timestamp createdDateAtTimestamp = new Timestamp(createdDateAt);
        DB.getConnection().executeUpdate("INSERT INTO `ratings` (`id`, `createdDateAt`, `rating`, `type`, `product_id`) VALUES ('" + id + "', '" + createdDateAtTimestamp + "', '" + rating + "', '" + type + "', '" + product_id + "')");
    }

    private static void insertTestRating2(int id, long createdDateAt, int rating, String type, int product_id) throws SQLException{
        Timestamp createdDateAtTimestamp = new Timestamp(createdDateAt);
        DB.getConnection().executeUpdate("INSERT INTO `ratings` (`id`, `createdDateAt`, `rating`, `type`, `product_id`) VALUES ('" + id + "', '" + createdDateAtTimestamp + "', '" + rating + "', '" + type + "', '" + product_id + "')");
    }

    private static void insertTestRating3(int id, long createdDateAt, int rating, String type, int product_id) throws SQLException{
        Timestamp createdDateAtTimestamp = new Timestamp(createdDateAt);
        DB.getConnection().executeUpdate("INSERT INTO `ratings` (`id`, `createdDateAt`, `rating`, `type`, `product_id`) VALUES ('" + id + "', '" + createdDateAtTimestamp + "', '" + rating + "', '" + type + "', '" + product_id + "')");
    }


    @SuppressWarnings("unchecked")
    @Test(priority = 1)
    public void adminShouldDeleteReviewWithValidId(){

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap<>();
        requestBody.put("reviewId", 1);
        requestBody.put("key", "26452948404D635166546A576E5A7234753778214125442A462D4A614E6");
        requestBody.put("adminId", 2);

        Response response = given()
                 .contentType(ContentType.JSON)
                 .body(requestBody)
                 .header("Accept-Language", "jp")
                 .when()
                 .delete(baseURI + "v1/admin/review-delete");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 2)
    public void adminShouldNotDeleteReviewWithInvalidId(){

        @SuppressWarnings("rawtypes")
        HashMap requestBody = new HashMap<>();
        requestBody.put("reviewId", 5);
        requestBody.put("key", "26452948404D635166546A576E5A7234753778214125442A462D4A614E6");
        requestBody.put("adminId", 2);

        Response response = given()
                 .contentType(ContentType.JSON)
                 .body(requestBody)
                 .header("Accept-Language", "jp")
                 .when()
                 .delete(baseURI + "v1/admin/review-delete");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "レビューはありません");
    }
}
