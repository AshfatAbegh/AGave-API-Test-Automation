package com.agave.tests.Utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class TestUser {

    public static void setupUsers(int userId, int userId2, int userId3, int userId4) throws SQLException {

        String plainTextPassword = "Asif9878";
        String encodedPassword = PasswordEncoder.encodePassword(plainTextPassword);

        if (!isUserPresent(userId)) {
            insertTestUser(userId, "polygon-03", "Washington 25/A", new Date().getTime(), "16/12/1998",
                    "asif1@gmail.com", "Sam",
                    "Asif", "false", "asif", "false", "false", "false", "false", "false", "false", encodedPassword,
                    "07012345644", new Date().getTime(), 1204, "abcd",
                    "profile/pictures/profile_1715864305402.jpg", 5, 10, "abcd", "Tokyo", "Polygon", "Hiroshima", 1204,
                    "abc", "Hossain",
                    "Islam", new Date().getTime(), "SELLER_APPROVE", 700, "ACTIVATE", "PL0000001", new Date().getTime());
        }

        if (!isUserPresent(userId2)) {
            insertTestUser(userId2, "polygon-03", "Washington 25/A", new Date().getTime(), "16/12/1998",
                    "araf@gmail.com", "Sam",
                    "Araf", "false", "araf", "false", "false", "false", "false", "false", "false", encodedPassword,
                    "07012345644", new Date().getTime(), 1204, "abcd",
                    "profile/pictures/profile_1715864305402.jpg", 5, 10, "abcd", "Tokyo", "Polygon", "Hiroshima", 1204,
                    "abc", "Hossain",
                    "Islam", new Date().getTime(), "USER", 0,  "ACTIVATE", "PL0000002", new Date().getTime());
        }

        if (!isUserPresent(userId3)) {
            insertTestUser(userId3, "polygon-03", "Washington 25/A", new Date().getTime(), "16/12/1998",
                    "rafi@gmail.com", "Sam",
                    "Rafi", "false", "rafi", "false", "false", "false", "false", "false", "false", encodedPassword,
                    "07012345644", new Date().getTime(), 1204, "abcd",
                    "profile/pictures/profile_1715864305402.jpg", 5, 10, "abcd", "Tokyo", "Polygon", "Hiroshima", 1204,
                    "abc", "Hossain",
                    "Islam", new Date().getTime(), "SELLER_APPROVE", 700, "ACTIVATE", "PL0000003", new Date().getTime());
        }

        if (!isUserPresent(userId4)) {
            insertTestUser(userId4, "polygon-03", "Washington 25/A", new Date().getTime(), "16/12/1998",
                    "alex@gmail.com", "Sam",
                    "Alex", "false", "alex", "false", "false", "false", "false", "false", "false", encodedPassword,
                    "07012345644", new Date().getTime(), 1204, "abcd",
                    "profile/pictures/profile_1715864305402.jpg", 5, 10, "abcd", "Tokyo", "Polygon", "Hiroshima", 1204,
                    "abc", "Hossain",
                    "Islam", new Date().getTime(), "SELLER_APPROVE", 700, "ACTIVATE", "PL0000004", new Date().getTime());
        }
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

    private static boolean isUserPresent(int userId) throws SQLException {
        ResultSet rs = DB.getConnection().executeQuery("SELECT id FROM profiles WHERE id = " + userId);
        return rs.next();
    }
}