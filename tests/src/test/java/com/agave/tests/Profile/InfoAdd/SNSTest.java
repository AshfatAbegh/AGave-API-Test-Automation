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
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class SNSTest {

        private String accessToken;
        private String accessToken2;
        private JwtToken jwtToken = new JwtToken();

        @BeforeClass
        public void dbSetUp() throws SQLException {
                RestAssured.baseURI = "http://agave-api-test:8080/api/";
                DB.truncateTables(new String[] {"profiles", "social_links"});

                String plainTextPassword = "Asif9878";
                String encodedPassword = PasswordEncoder.encodePassword(plainTextPassword);

                insertTestUser1(1, "polygon-03", "Washington 25/A", new Date().getTime(), "16/12/1998",
                                "asif1@gmail.com", "Sam",
                                "Asif", "false", "asif", "false", "false", "false", "false", "false", "false",
                                encodedPassword,
                                "07012345644", new Date().getTime(), 1204, "abcd",
                                "profile/pictures/profile_1715864305402.jpg", 5, 10, "abcd", "Tokyo", "Polygon",
                                "Hiroshima", 1204,
                                "abc", "Hossain",
                                "Islam", new Date().getTime(), "USER", 0, "ACTIVATE", "PL0000001", new Date().getTime());

                insertTestUser2(3, "polygon-03", "Washington 25/A", new Date().getTime(), "16/12/1998",
                                "rafi@gmail.com", "Sam",
                                "Asif", "false", "rafi", "false", "false", "false", "false", "false", "false",
                                encodedPassword,
                                "07012345644", new Date().getTime(), 1204, "abcd",
                                "profile/pictures/profile_1715864305402.jpg", 5, 10, "abcd", "Tokyo", "Polygon",
                                "Hiroshima", 1204,
                                "abc", "Hossain",
                                "Islam", new Date().getTime(), "USER", 0, "ACTIVATE", "PL0000003", new Date().getTime());

                accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");
                accessToken2 = jwtToken.generateAccessToken(3, "rafi@gmail.com");
        }

        private static void insertTestUser1(int id, String buildingNameAndRoomNumber, String cityStreetAddress,
                        long createdDateAt,
                        String dateOfBirth, String email, String firstNameFurigana, String firstNameKanji,
                        String isFromGoogle,
                        String nickName, String notifyProductCommenters, String notifyProductPublished,
                        String notifyProductRejected, String notifySellerProductLikes,
                        String notifySellerProductUserComments,
                        String notifyUserProductLikeComment, String password, String phoneNumber, long phoneVerifiedAt,
                        long postalCode,
                        String prefectures, String profilePictureUrl, double ratedPerson, double rating,
                        String selfIntroductoryStatement,
                        String shippingAddress, String shippingAddressBuildingNameAndRoomNumber,
                        String shippingAddressCityStreetAddress, int i,
                        String shippingAddressPrefectures, String surNameFurigana, String surNameKanji,
                        long updatedDateAt,
                        String userStatus, double balance, String accountStatus, String readableId,
                        long activatedDateAt) throws SQLException {
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
                                                + i + "', '" + shippingAddressPrefectures + "', '" + surNameFurigana
                                                + "', '" + surNameKanji + "', '" + updatedDateAtTimestamp + "', '"
                                                + userStatus + "', '" + balance + "', '" + accountStatus + "', '"
                                                + readableId + "', '" + activatedDateAtTimestamp + "')");
        }

        private static void insertTestUser2(int id, String buildingNameAndRoomNumber, String cityStreetAddress,
                        long createdDateAt,
                        String dateOfBirth, String email, String firstNameFurigana, String firstNameKanji,
                        String isFromGoogle,
                        String nickName, String notifyProductCommenters, String notifyProductPublished,
                        String notifyProductRejected, String notifySellerProductLikes,
                        String notifySellerProductUserComments,
                        String notifyUserProductLikeComment, String password, String phoneNumber, long phoneVerifiedAt,
                        long postalCode,
                        String prefectures, String profilePictureUrl, double ratedPerson, double rating,
                        String selfIntroductoryStatement,
                        String shippingAddress, String shippingAddressBuildingNameAndRoomNumber,
                        String shippingAddressCityStreetAddress, int i,
                        String shippingAddressPrefectures, String surNameFurigana, String surNameKanji,
                        long updatedDateAt,
                        String userStatus, double balance, String accountStatus, String readableId,
                        long activatedDateAt) throws SQLException {
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
                                                + i + "', '" + shippingAddressPrefectures + "', '" + surNameFurigana
                                                + "', '" + surNameKanji + "', '" + updatedDateAtTimestamp + "', '"
                                                + userStatus + "', '" + balance + "', '" + accountStatus + "', '"
                                                + readableId + "', '" + activatedDateAtTimestamp + "')");
        }

        @Test(priority = 1)
        public void userShouldSuccessfullyAddSocialLinkWithProperAuthorization() throws SQLException {

                Response response = given()
                                .header("Authorization", "Bearer " + accessToken)
                                .header("Accept-Language", "jp")
                                .multiPart("name", "asif")
                                .multiPart("profile_picture_url",
                                                "https://identity.getpostman.com/browser-auth/success")
                                .multiPart("link", "https://identity.getpostman.com/browser-auth/success")
                                .multiPart("platformName", "Instagram")
                                .contentType("multipart/form-data")
                                .when()
                                .post(baseURI + "v1/profile/social-link-add");

                Assert.assertEquals(response.getStatusCode(), 200);
                response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SNSTestJSONSchema.json"));
        }

        @Test(priority = 2)
        public void userShouldNotAddSocialLinkByProvidingOnlyName() throws SQLException {

                Response response = given()
                                .header("Authorization", "Bearer " + accessToken)
                                .header("Accept-Language", "jp")
                                .multiPart("name", "asif")
                                .contentType("multipart/form-data")
                                .when()
                                .post(baseURI + "v1/profile/social-link-add");

                Assert.assertEquals(response.getStatusCode(), 406);
                String message = response.jsonPath().getString("message");
                Assert.assertEquals(message, "リンク フィールドは null であってはなりません");
        }

        @Test(priority = 3)
        public void userShouldNotAddSocialLinkByProvidingOnlyProfilePictureURL() throws SQLException {

                Response response = given()
                                .header("Authorization", "Bearer " + accessToken)
                                .header("Accept-Language", "jp")
                                .multiPart("profile_picture_url",
                                                "https://identity.getpostman.com/browser-auth/success")
                                .contentType("multipart/form-data")
                                .when()
                                .post(baseURI + "v1/profile/social-link-add");

                Assert.assertEquals(response.getStatusCode(), 406);
                String message = response.jsonPath().getString("message");
                Assert.assertEquals(message, "リンク フィールドは null であってはなりません");
        }

        @Test(priority = 4)
        public void userShouldNotAddSocialLinkByProvidingOnlyLink() throws SQLException {

                Response response = given()
                                .header("Authorization", "Bearer " + accessToken)
                                .header("Accept-Language", "jp")
                                .multiPart("link", "https://identity.getpostman.com/browser-auth/success")
                                .contentType("multipart/form-data")
                                .when()
                                .post(baseURI + "v1/profile/social-link-add");

                Assert.assertEquals(response.getStatusCode(), 406);
                String message = response.jsonPath().getString("message");
                Assert.assertEquals(message, "プラットフォーム名フィールドが無効です");
        }

        @Test(priority = 5)
        public void userShouldNotAddSocialLinkWithoutProvidingLink() throws SQLException {

                Response response = given()
                                .header("Authorization", "Bearer " + accessToken)
                                .header("Accept-Language", "jp")
                                .multiPart("name", "asif")
                                .multiPart("profile_picture_url",
                                                "https://identity.getpostman.com/browser-auth/success")
                                .multiPart("platformName", "Instagram")
                                .contentType("multipart/form-data")
                                .when()
                                .post(baseURI + "v1/profile/social-link-add");

                Assert.assertEquals(response.getStatusCode(), 406);
                String message = response.jsonPath().getString("message");
                Assert.assertEquals(message, "リンク フィールドは null であってはなりません");
        }

        @Test(priority = 6)
        public void userShouldNotAddSocialLinkByProvidingOnlyPlatformName() throws SQLException {

                Response response = given()
                                .header("Authorization", "Bearer " + accessToken)
                                .header("Accept-Language", "jp")
                                .multiPart("platformName", "Instagram")
                                .contentType("multipart/form-data")
                                .when()
                                .post(baseURI + "v1/profile/social-link-add");

                Assert.assertEquals(response.getStatusCode(), 406);
                String message = response.jsonPath().getString("message");
                Assert.assertEquals(message, "リンク フィールドは null であってはなりません");
        }

        @Test(priority = 7)
        public void userShouldNotAddSocialLinkForTheSameProfileWhichIsAlreadyPresent() throws SQLException {

                Response response = given()
                                .header("Authorization", "Bearer " + accessToken)
                                .header("Accept-Language", "jp")
                                .multiPart("link", "https://identity.getpostman.com/browser-auth/success")
                                .multiPart("platformName", "Instagram")
                                .contentType("multipart/form-data")
                                .when()
                                .post(baseURI + "v1/profile/social-link-add");

                Assert.assertEquals(response.getStatusCode(), 404);
                String message = response.jsonPath().getString("message");
                Assert.assertEquals(message, "プロファイルのリンクがすでに存在します");
        }

        @Test(priority = 8)
        public void userShouldSuccessfullyAddSocialLinkWithoutNameAndProfilePictureURL() throws SQLException {

                Response response = given()
                                .header("Authorization", "Bearer " + accessToken2)
                                .header("Accept-Language", "jp")
                                .multiPart("link", "https://identity.getpostman.com/browser-auth/success")
                                .multiPart("platformName", "Instagram")
                                .contentType("multipart/form-data")
                                .when()
                                .post(baseURI + "v1/profile/social-link-add");

                Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test(priority = 9)
        public void userShouldNotAddSocialLinkWithoutAuthorization() throws SQLException {

                Response response = given()
                                .header("Accept-Language", "jp")
                                .multiPart("name", "asif")
                                .multiPart("profile_picture_url",
                                                "https://identity.getpostman.com/browser-auth/success")
                                .multiPart("link", "https://identity.getpostman.com/browser-auth/success")
                                .multiPart("platformName", "Instagram")
                                .contentType("multipart/form-data")
                                .when()
                                .post(baseURI + "v1/profile/social-link-add");

                Assert.assertEquals(response.getStatusCode(), 500);
        }
}
