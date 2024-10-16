package com.agave.tests.Admin;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.PasswordEncoder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SellerBulkStatusRejectedTest {

    private static final int userId = 1;
    private static final int userId2 = 2;
    private static final int userId3 = 3;

    @BeforeClass
    public void dbSetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles"});

        String plainTextPassword = "Asif9878";
        String encodedPassword = PasswordEncoder.encodePassword(plainTextPassword);

        if (!isUserPresent(userId)) {
            insertTestUser(userId, "polygon-03", "Washington 25/A", new Date().getTime(), "16/12/1998",
                    "asif1@gmail.com", "Sam", "Asif", "false", "asif", "false", "false", "false", "false", "false", "false",
                    encodedPassword, "07012345644", new Date().getTime(), 1204, "abcd",
                    "profile/pictures/profile_1715864305402.jpg", 5, 10, "abcd", "Tokyo", "Polygon", "Hiroshima", 1204,
                    "abc", "Hossain", "Islam", new Date().getTime(), "SELLER_UNDER_REVIEW", 0, "ACTIVATE", "PL0000001", new Date().getTime());
        }

        if (!isUserPresent(userId2)) {
            insertTestUser(userId2, "polygon-03", "Washington 25/A", new Date().getTime(), "16/12/1998",
                    "araf@gmail.com", "Sam", "Araf", "false", "araf", "false", "false", "false", "false", "false", "false",
                    encodedPassword, "07012345644", new Date().getTime(), 1204, "abcd",
                    "profile/pictures/profile_1715864305402.jpg", 5, 10, "abcd", "Tokyo", "Polygon", "Hiroshima", 1204,
                    "abc", "Hossain", "Islam", new Date().getTime(), "USER", 0, "ACTIVATE", "PL0000002", new Date().getTime());
        }

        if (!isUserPresent(userId3)) {
            insertTestUser(userId3, "polygon-03", "Washington 25/A", new Date().getTime(), "16/12/1998",
                    "rafi@gmail.com", "Sam", "Rafi", "false", "rafi", "false", "false", "false", "false", "false", "false",
                    encodedPassword, "07012345644", new Date().getTime(), 1204, "abcd",
                    "profile/pictures/profile_1715864305402.jpg", 5, 10, "abcd", "Tokyo", "Polygon", "Hiroshima", 1204,
                    "abc", "Hossain", "Islam", new Date().getTime(), "SELLER_APPROVE", 0, "ACTIVATE", "PL0000003", new Date().getTime());
        }
    }

    private static void insertTestUser(int id, String buildingNameAndRoomNumber, String cityStreetAddress,
            long createdDateAt, String dateOfBirth, String email, String firstNameFurigana, String firstNameKanji,
            String isFromGoogle, String nickName, String notifyProductCommenters, String notifyProductPublished,
            String notifyProductRejected, String notifySellerProductLikes, String notifySellerProductUserComments,
            String notifyUserProductLikeComment, String password, String phoneNumber, long phoneVerifiedAt,
            long postalCode, String prefectures, String profilePictureUrl, double ratedPerson, double rating,
            String selfIntroductoryStatement, String shippingAddress, String shippingAddressBuildingNameAndRoomNumber,
            String shippingAddressCityStreetAddress, int shippingAddressPostalCode, String shippingAddressPrefectures, 
            String surNameFurigana, String surNameKanji, long updatedDateAt, String userStatus, double balance, 
            String accountStatus, String readableId, long activatedDateAt) throws SQLException {
        Timestamp createdAtTimestamp = new Timestamp(createdDateAt);
        Timestamp phoneVerifiedAtTimestamp = new Timestamp(phoneVerifiedAt);
        Timestamp updatedDateAtTimestamp = new Timestamp(updatedDateAt);
        Timestamp activatedDateAtTimestamp = new Timestamp(activatedDateAt);
        DB.getConnection().executeUpdate(
                "INSERT INTO `profiles` (`id`, `buildingNameAndRoomNumber`, `cityStreetAddress`, `createdDateAt`, `dateOfBirth`, `email`, `firstNameFurigana`, `firstNameKanji`, `isFromGoogle`, `nickName`, `notifyProductCommenters`, `notifyProductPublished`, `notifyProductRejected`, `notifySellerProductLikes`, `notifySellerProductUserComments`, `notifyUserProductLikeComment`, `password`, `phoneNumber`, `phoneVerifiedAt`, `postalCode`, `prefectures`, `profilePictureUrl`, `ratedPerson`, `rating`, `selfIntroductoryStatement`, `shippingAddress`, `shippingAddressBuildingNameAndRoomNumber`, `shippingAddressCityStreetAddress`, `shippingAddressPostalCode`, `shippingAddressPrefectures`, `surNameFurigana`, `surNameKanji`, `updatedDateAt`, `userStatus`, `balance`, `accountStatus`, `readableId`, `activatedDateAt`) VALUES ('"
                        + id + "', '"
                        + buildingNameAndRoomNumber + "', '" + cityStreetAddress + "', '"
                        + createdAtTimestamp + "', '"
                        + dateOfBirth + "', '" + email + "', '" + firstNameFurigana + "', '"
                        + firstNameKanji + "', "
                        + (isFromGoogle.equals("true") ? 1 : 0) + ", '" + nickName + "', "
                        + (notifyProductCommenters.equals("true") ? 1 : 0) + ", "
                        + (notifyProductPublished.equals("true") ? 1 : 0) + ", "
                        + (notifyProductRejected.equals("true") ? 1 : 0) + ",  "
                        + (notifySellerProductLikes.equals("true") ? 1 : 0) + ", "
                        + (notifySellerProductUserComments.equals("true") ? 1 : 0) + ", "
                        + (notifyUserProductLikeComment.equals("true") ? 1 : 0) + ", '"
                        + password + "', '"
                        + phoneNumber + "', '" + phoneVerifiedAtTimestamp + "', '" + postalCode
                        + "', '" + prefectures
                        + "', '" + profilePictureUrl + "', '" + ratedPerson + "', '" + rating
                        + "', '"
                        + selfIntroductoryStatement + "', '" + shippingAddress + "', '"
                        + shippingAddressBuildingNameAndRoomNumber + "', '"
                        + shippingAddressCityStreetAddress + "', '"
                        + shippingAddressPostalCode + "', '" + shippingAddressPrefectures + "', '" + surNameFurigana
                        + "', '" + surNameKanji + "', '" + updatedDateAtTimestamp + "', '"
                        + userStatus + "', '" + balance + "', '" + accountStatus + "', '" + readableId + "', '" + activatedDateAtTimestamp + "')");
    }

    private static boolean isUserPresent(int userId) throws SQLException {
        ResultSet rs = DB.getConnection().executeQuery("SELECT id FROM profiles WHERE id = " + userId);
        return rs.next();
    }

    @Test(priority = 1)
    public void adminShouldNotRejectTheSellerBulkStatusWithInvalidId() throws SQLException {
        HashMap<String, Object> requestBody = new HashMap<>();

        List<Long> bulkList = new ArrayList<>();
        bulkList.add(4L);

        requestBody.put("bulkList", bulkList);
        requestBody.put("sellerStatus", "SELLER_REJECT");
        requestBody.put("key", "26452948404D635166546A576E5A7234753778214125442A462D4A614E6");
        requestBody.put("adminId", 2);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/admin/seller-bulk-reject");

        Assert.assertEquals(response.getStatusCode(), 404);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "プロフィールがありません");
    }

    @Test(priority = 2)
    public void adminShouldRejectTheSellerBulkStatusWhichIsInUnderReviewConditionWithValidId() throws SQLException {
        HashMap<String, Object> requestBody = new HashMap<>();

        List<Long> bulkList = new ArrayList<>();
        bulkList.add(1L);

        requestBody.put("bulkList", bulkList);
        requestBody.put("sellerStatus", "SELLER_REJECT");
        requestBody.put("key", "26452948404D635166546A576E5A7234753778214125442A462D4A614E6");
        requestBody.put("adminId", 2);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/admin/seller-bulk-reject");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void adminShouldNotRejectTheSellerBulkStatusWhichIsNotInUnderReviewCondition() throws SQLException {
        HashMap<String, Object> requestBody = new HashMap<>();

        List<Long> bulkList = new ArrayList<>();
        bulkList.add(2L);

        requestBody.put("bulkList", bulkList);
        requestBody.put("sellerStatus", "SELLER_REJECT");
        requestBody.put("key", "26452948404D635166546A576E5A7234753778214125442A462D4A614E6");
        requestBody.put("adminId", 2);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "v1/admin/seller-bulk-reject");

        Assert.assertEquals(response.getStatusCode(), 404);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "まずは販売者に申請してください");
    }
}