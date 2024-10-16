package com.agave.tests.Product.Order;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.TestUser;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class OrderPlaceTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void dbSetUp() throws SQLException {

        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles", "products", "transactions"});

        TestUser.setupUsers(1, 2, 3, 4);

        accessToken = jwtToken.generateAccessToken(2, "araf@gmail.com");

        insertProduct(1, "Japan", new Date().getTime(), "Grafting", "BareRoot", "Taiwan", new Date().getTime(),
                "Six",
                new Date().getTime(), "Agave", new Date().getTime(),  33410, new Date().getTime(),
                new Date().getTime(), new Date().getTime(), "454 m.n", "Tokyo",
                "CashOnDelivery",
                "FourthClassMail", "Hokkaido", "1200", "Town", "Small", "PUBLISHED", "All Ok",
                "UmbrellaPlant",
                new Date().getTime(), "LessThanFive", 1, "PD0000001");
    }

    private static void insertProduct(int id, String acquisitionRouteDescription, long appliedAt,
            String breedingMethod,
            String conditionAtTheTimeOfShipment, String countryOfOrigin, long createdAt,
            String daysOfDelivery,
            long deliveredAt, String name, long orderCreatedAt, double price, long publishedAt,
            long purchasedAt,
            long shippedAt, String shippingBuildingName, String shippingCity,
            String shippingCost, String shippingMethod, String shippingOrigin, String shippingPostalCode,
            String shippingPrefecture, String size, String status, String supplementaryInformation,
            String treeSpecies,
            long updatedAt, String width, int sellerId_id, String readableId) throws SQLException {
        Timestamp appliedAtTimestamp = new Timestamp(appliedAt);
        Timestamp createdAtTimestamp = new Timestamp(createdAt);
        Timestamp deliveredAtTimestamp = new Timestamp(deliveredAt);
        Timestamp orderCreatedAtTimestamp = new Timestamp(orderCreatedAt);
        Timestamp publishedAtTimestamp = new Timestamp(publishedAt);
        Timestamp purchasedAtTimestamp = new Timestamp(purchasedAt);
        Timestamp shippedAtTimestamp = new Timestamp(shippedAt);
        Timestamp updatedAtTimestamp = new Timestamp(updatedAt);
        DB.getConnection().executeUpdate(
                "INSERT INTO `products` (`id`, `acquisitionRouteDescription`, `appliedAt`, `breedingMethod`, `conditionAtTheTimeOfShipment`, `countryOfOrigin`, `createdAt`, `daysOfDelivery`, `deliveredAt`, `name`, `orderCreatedAt`, `price`, `publishedAt`, `purchasedAt`, `shippedAt`, `shippingBuildingName`, `shippingCity`, `shippingCost`, `shippingMethod`, `shippingOrigin`, `shippingPostalCode`, `shippingPrefecture`, `size`, `status`, `supplementaryInformation`, `treeSpecies`, `updatedAt`, `width`, `sellerId_id`, `readableId`) VALUES ('"
                        + id + "', '"
                        + acquisitionRouteDescription + "', '" + appliedAtTimestamp + "', '"
                        + breedingMethod + "', '"
                        + conditionAtTheTimeOfShipment
                        + "', '" + countryOfOrigin + "', '" + createdAtTimestamp + "', '"
                        + daysOfDelivery + "', '"
                        + deliveredAtTimestamp + "', '" + name + "', '"
                        + orderCreatedAtTimestamp + "', '" + price + "', '"
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
                        + "', '" + sellerId_id + "', '" + readableId + "')");
    }

    @Test(priority = 1)
    public void userShouldPlaceOrderOnThePublishedProductWithValidId() throws SQLException {

        HashMap<String, String> hm = new HashMap<>();
        hm.put("id", "1");
        hm.put("shippingPostalCode", "12348445");
        hm.put("shippingPrefecture", "ccc");
        hm.put("shippingCity", "99");
        hm.put("shippingBuildingName", "454 m n");
        hm.put("cardSeq", "0");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(hm)
                .when()
                .post(baseURI + "v1/product/order");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void userShouldNotPlaceOrderOnTheUnderReviewProduct() throws SQLException {

        HashMap<String, String> hm = new HashMap<>();
        hm.put("id", "4");
        hm.put("shippingPostalCode", "12348445");
        hm.put("shippingPrefecture", "ccc");
        hm.put("shippingCity", "99");
        hm.put("shippingBuildingName", "454 m n");
        hm.put("cardSeq", "0");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(hm)
                .when()
                .post(baseURI + "v1/product/order");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "製品が見つかりませんでした");
    }

    @Test(priority = 3)
    public void userShouldNotPlaceOrderOnThePublishedProductWithoutAuthorization() throws SQLException {

        HashMap<String, String> hm = new HashMap<>();
        hm.put("id", "1");
        hm.put("shippingPostalCode", "12348445");
        hm.put("shippingPrefecture", "ccc");
        hm.put("shippingCity", "99");
        hm.put("shippingBuildingName", "454 m n");
        hm.put("cardSeq", "0");

        Response response = given()
                .header("Accept-Language", "jp")
                .contentType(ContentType.JSON)
                .body(hm)
                .when()
                .post(baseURI + "v1/product/order");

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}

