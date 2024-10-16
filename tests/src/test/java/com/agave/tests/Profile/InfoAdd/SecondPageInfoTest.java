package com.agave.tests.Profile.InfoAdd;

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
import io.restassured.response.Response;

public class SecondPageInfoTest {

    private String accessToken;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void baseURISetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[]{"profiles"});

        accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");

        String plainTextPassword = "Asif9878";
        String encodedPassword = PasswordEncoder.encodePassword(plainTextPassword);

        insertTestUser(1, "polygon-03", "Washington 25/A", new Date().getTime(), "16/12/1998",
                "asif1@gmail.com", "Sam", "Asif", "false", "asif", "false", "false", "false", "false", "false", 
                "false", encodedPassword, "07012345644", new Date().getTime(), 1204, "abcd",
                "profile/pictures/profile_1715864305402.jpg", 5, 10, "abcd", "Tokyo", "Polygon", "Hiroshima", 
                1204, "abc", "Hossain", "Islam", new Date().getTime(), "USER", 0, "ACTIVATE", "PL0000001", new Date().getTime());
    }

    private static void insertTestUser(int id, String buildingNameAndRoomNumber, String cityStreetAddress,
                                       long createdDateAt, String dateOfBirth, String email, String firstNameFurigana,
                                       String firstNameKanji, String isFromGoogle, String nickName,
                                       String notifyProductCommenters, String notifyProductPublished,
                                       String notifyProductRejected, String notifySellerProductLikes,
                                       String notifySellerProductUserComments, String notifyUserProductLikeComment,
                                       String password, String phoneNumber, long phoneVerifiedAt, long postalCode,
                                       String prefectures, String profilePictureUrl, double ratedPerson, double rating,
                                       String selfIntroductoryStatement, String shippingAddress,
                                       String shippingAddressBuildingNameAndRoomNumber, String shippingAddressCityStreetAddress,
                                       int shippingAddressPostalCode, String shippingAddressPrefectures,
                                       String surNameFurigana, String surNameKanji, long updatedDateAt, String userStatus,
                                       double balance, String accountStatus, String readableId, long activatedDateAt) throws SQLException {
        Timestamp createdAtTimestamp = new Timestamp(createdDateAt);
        Timestamp phoneVerifiedAtTimestamp = new Timestamp(phoneVerifiedAt);
        Timestamp updatedDateAtTimestamp = new Timestamp(updatedDateAt);
        Timestamp activatedDateAtTimestamp = new Timestamp(activatedDateAt);
        DB.getConnection().executeUpdate(
                "INSERT INTO `profiles` (`id`, `buildingNameAndRoomNumber`, `cityStreetAddress`, `createdDateAt`, " +
                        "`dateOfBirth`, `email`, `firstNameFurigana`, `firstNameKanji`, `isFromGoogle`, `nickName`, " +
                        "`notifyProductCommenters`, `notifyProductPublished`, `notifyProductRejected`, `notifySellerProductLikes`, " +
                        "`notifySellerProductUserComments`, `notifyUserProductLikeComment`, `password`, `phoneNumber`, " +
                        "`phoneVerifiedAt`, `postalCode`, `prefectures`, `profilePictureUrl`, `ratedPerson`, `rating`, " +
                        "`selfIntroductoryStatement`, `shippingAddress`, `shippingAddressBuildingNameAndRoomNumber`, " +
                        "`shippingAddressCityStreetAddress`, `shippingAddressPostalCode`, `shippingAddressPrefectures`, " +
                        "`surNameFurigana`, `surNameKanji`, `updatedDateAt`, `userStatus`, `balance`, `accountStatus`, " +
                        "`readableId`, `activatedDateAt`) VALUES ('" + id + "', '" + buildingNameAndRoomNumber + "', '" +
                        cityStreetAddress + "', '" + createdAtTimestamp + "', '" + dateOfBirth + "', '" + email + "', '" +
                        firstNameFurigana + "', '" + firstNameKanji + "', " + (isFromGoogle.equals("true") ? 1 : 0) + ", '" +
                        nickName + "', " + (notifyProductCommenters.equals("true") ? 1 : 0) + ", " +
                        (notifyProductPublished.equals("true") ? 1 : 0) + ", " + (notifyProductRejected.equals("true") ? 1 : 0) + ", " +
                        (notifySellerProductLikes.equals("true") ? 1 : 0) + ", " + (notifySellerProductUserComments.equals("true") ? 1 : 0) + ", " +
                        (notifyUserProductLikeComment.equals("true") ? 1 : 0) + ", '" + password + "', '" + phoneNumber + "', '" +
                        phoneVerifiedAtTimestamp + "', '" + postalCode + "', '" + prefectures + "', '" + profilePictureUrl + "', '" +
                        ratedPerson + "', '" + rating + "', '" + selfIntroductoryStatement + "', '" + shippingAddress + "', '" +
                        shippingAddressBuildingNameAndRoomNumber + "', '" + shippingAddressCityStreetAddress + "', '" + shippingAddressPostalCode +
                        "', '" + shippingAddressPrefectures + "', '" + surNameFurigana + "', '" + surNameKanji + "', '" + updatedDateAtTimestamp +
                        "', '" + userStatus + "', '" + balance + "', '" + accountStatus + "', '" + readableId + "', '" + activatedDateAtTimestamp + "')");
    }

    @Test(priority = 1)
    public void userShouldSaveProfileInfoSuccessfullyWithProperAuthorization() {
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .multiPart("sur_name_kanji", "Islam")
                .multiPart("first_name_kanji", "Asif")
                .multiPart("sur_name_furigana", "Hossain")
                .multiPart("first_name_furigana", "Sam")
                .multiPart("date_of_birth", "16/12/1998")
                .multiPart("postal_code", "1204")
                .multiPart("prefectures", "abcd")
                .multiPart("city_street_address", "Washington 25/A")
                .multiPart("building_name_and_room_number", "polygon-03")
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/profile/add-info-two");

        Assert.assertEquals(response.getStatusCode(), 200);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "成功");
    }

    @Test(priority = 2)
    public void userShouldNotSaveProfileInfoWithoutAuthorization() {
        Response response = given()
                .multiPart("sur_name_kanji", "Islam")
                .multiPart("first_name_kanji", "Safi")
                .multiPart("sur_name_furigana", "Hossain")
                .multiPart("first_name_furigana", "Sam")
                .multiPart("date_of_birth", "16/12/1998")
                .multiPart("postal_code", "1204")
                .multiPart("prefectures", "abcd")
                .multiPart("city_street_address", "Washington 25/A")
                .multiPart("building_name_and_room_number", "polygon-03")
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/profile/add-info-two");

        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
