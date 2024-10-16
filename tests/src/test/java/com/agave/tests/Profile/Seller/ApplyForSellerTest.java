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
import com.agave.tests.Utilities.PasswordEncoder;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApplyForSellerTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void baseURISetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles"});
        
        String plainTextPassword = "Asif9878";
        String encodedPassword = PasswordEncoder.encodePassword(plainTextPassword);

        insertTestUser(1, "polygon-03", "Washington 25/A", new Date().getTime(), "16/12/1998",
                "asif1@gmail.com", "Sam",
                "Asif", "false", "asif", "false", "false", "false", "false", "false", "false", encodedPassword,
                "07012345644", new Date().getTime(), 1204, "abcd",
                "profile/pictures/profile_1715864305402.jpg", 5, 10, "abcd", "Tokyo", "Polygon", "Hiroshima", 1204,
                "abc", "Hossain",
                "Islam", new Date().getTime(), "USER", 0, "ACTIVATE", "PL0000001", new Date().getTime());

        accessToken = jwtToken.generateAccessToken(1,"asif1@gmail.com");
    }

    private static void insertTestUser(int id, String buildingNameAndRoomNumber, String cityStreetAddress,
        long createdDateAt,
        String dateOfBirth, String email, String firstNameFurigana, String firstNameKanji, String isFromGoogle,
        String nickName, String notifyProductCommenters, String notifyProductPublished,
        String notifyProductRejected, String notifySellerProductLikes, String notifySellerProductUserComments,
        String notifyUserProductLikeComment, String password, String phoneNumber, long phoneVerifiedAt,
        long postalCode,
        String prefectures, String profilePictureUrl, double ratedPerson, double rating,
        String selfIntroductoryStatement,
        String shippingAddress, String shippingAddressBuildingNameAndRoomNumber,
        String shippingAddressCityStreetAddress, int i,
        String shippingAddressPrefectures, String surNameFurigana, String surNameKanji, long updatedDateAt,
        String userStatus, double balance, String accountStatus, String readableId, long activatedDateAt) throws SQLException {
    Timestamp createdAtTimestamp = new Timestamp(createdDateAt);
    Timestamp phoneVerifiedAtTimestamp = new Timestamp(phoneVerifiedAt);
    Timestamp updatedDateAtTimestamp = new Timestamp(updatedDateAt);
    Timestamp activatedDateAtTimestamp = new Timestamp(activatedDateAt);
    DB.getConnection().executeUpdate(
            "INSERT INTO `profiles` (`id`, `buildingNameAndRoomNumber`, `cityStreetAddress`, `createdDateAt`, `dateOfBirth`, `email`, `firstNameFurigana`, `firstNameKanji`, `isFromGoogle`, `nickName`, `notifyProductCommenters`, `notifyProductPublished`, `notifyProductRejected`, `notifySellerProductLikes`, `notifySellerProductUserComments`, `notifyUserProductLikeComment`, `password`, `phoneNumber`, `phoneVerifiedAt`, `postalCode`, `prefectures`, `profilePictureUrl`, `ratedPerson`, `rating`, `selfIntroductoryStatement`, `shippingAddress`, `shippingAddressBuildingNameAndRoomNumber`, `shippingAddressCityStreetAddress`, `shippingAddressPostalCode`, `shippingAddressPrefectures`, `surNameFurigana`, `surNameKanji`, `updatedDateAt`, `userStatus`, `balance`, `accountStatus`, `readableId`, `activatedDateAt`) VALUES ('"
                    + id + "', '"
                    + buildingNameAndRoomNumber + "', '" + cityStreetAddress + "', '" + createdAtTimestamp + "', '"
                    + dateOfBirth + "', '" + email + "', '" + firstNameFurigana + "', '" + firstNameKanji + "', "
                    + (isFromGoogle.equals("true") ? 1 : 0) + ", '" + nickName + "', "
                    + (notifyProductCommenters.equals("true") ? 1 : 0) + ", "
                    + (notifyProductPublished.equals("true") ? 1 : 0) + ", "
                    + (notifyProductRejected.equals("true") ? 1 : 0) + ",  "
                    + (notifySellerProductLikes.equals("true") ? 1 : 0) + ", "
                    + (notifySellerProductUserComments.equals("true") ? 1 : 0) + ", "
                    + (notifyUserProductLikeComment.equals("true") ? 1 : 0) + ", '" + password + "', '"
                    + phoneNumber + "', '" + phoneVerifiedAtTimestamp + "', '" + postalCode + "', '" + prefectures
                    + "', '" + profilePictureUrl + "', '" + ratedPerson + "', '" + rating + "', '"
                    + selfIntroductoryStatement + "', '" + shippingAddress + "', '"
                    + shippingAddressBuildingNameAndRoomNumber + "', '" + shippingAddressCityStreetAddress + "', '"
                    + i + "', '" + shippingAddressPrefectures + "', '" + surNameFurigana
                    + "', '" + surNameKanji + "', '" + updatedDateAtTimestamp + "', '" + userStatus + "', '" + balance + "', '" + accountStatus + "', '" + readableId + "', '" + activatedDateAtTimestamp + "')");
    }

    @Test(priority = 1)
    public void userShouldApplyForSellerSuccessfullyWithProperAuthorization() {

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .header("Accept-Language", "jp")
                .when()
                .post(baseURI + "v1/profile/apply-seller");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void userShouldNotApplyForSellerWithoutAuthorization() {

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Accept-Language", "jp")
                .when()
                .post(baseURI + "v1/profile/apply-seller");

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
