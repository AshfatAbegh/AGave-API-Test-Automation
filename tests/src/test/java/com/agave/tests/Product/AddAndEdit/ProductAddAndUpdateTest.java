package com.agave.tests.Product.AddAndEdit;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.TestUser;
import com.agave.tests.Utilities.JWT.JwtToken;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ProductAddAndUpdateTest {

    private String accessToken;
    private String accessToken2;
    private JwtToken jwtToken = new JwtToken();

    @BeforeClass
    public void dbSetUp() throws SQLException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        DB.truncateTables(new String[] {"profiles", "products", "products_images", "productHashtag", "products_browsedUsers", "products_likedUsers"});

        TestUser.setupUsers(1, 2, 3, 4);
        accessToken = jwtToken.generateAccessToken(1, "asif1@gmail.com");
        accessToken2 = jwtToken.generateAccessToken(2, "araf@gmail.com");
    }

    @Test(priority = 1)
    public void userShouldSuccessfullyAddProductWithProperAuthorization() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void userShouldNotAddProductWithoutName() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "名前フィールドは必須です");
    }

    @Test(priority = 3)
    public void userShouldNotAddProductWithoutPrice() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "価格フィールドは必須です");
    }

    @Test(priority = 4)
    public void userShouldNotAddProductWithoutAcquisitionRouteDescription() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "フィールドは必須です");
    }

    @Test(priority = 5)
    public void userShouldNotAddProductWithoutTreeSpecies() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "フィールドは必須です");
    }

    @Test(priority = 6)
    public void userShouldNotAddProductWithotCountryOfOrigin() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "フィールドは必須です");
    }

    @Test(priority = 7)
    public void userShouldNotAddProductWithoutConditionAtTheTimeOfShipment() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "フィールドは必須です");
    }

    @Test(priority = 8)
    public void userShouldNotAddProductWithoutShippingOrigin() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "フィールドは必須です");
    }

    @Test(priority = 9)
    public void userShouldNotAddProductWithoutShippingCost() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "フィールドは必須です");
    }

    @Test(priority = 10)
    public void userShouldNotAddProductWithoutDaysOfDelivery() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "フィールドは必須です");
    }

    @Test(priority = 11)
    public void userShouldNotAddProductWithoutStatus() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "製品のステータスは審査中またはドラフトのいずれかです");
    }

    @Test(priority = 12)
    public void userShouldNotAddProductWhichProductStatusIsNotInUnderReviewOrDraft() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "PUBLISHED")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "製品のステータスは審査中またはドラフトのいずれかです");
    }

    @Test(priority = 13)
    public void userShouldNotAddProductWithoutHashTags() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "フィールドは必須です");
    }

    @Test(priority = 14)
    public void userShouldNotAddProductWithoutShippingMethod() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "フィールドは必須です");
    }

    @Test(priority = 15)
    public void userShouldNotAddProductWitoutProductImages() {

        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "フィールドは必須です");
    }

    @Test(priority = 16)
    public void userShouldNotAddProductWithoutSize() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "フィールドは必須");
    }

    @Test(priority = 17)
    public void userShouldNotAddProductWithoutWidth() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "フィールドは必須");
    }

    @Test(priority = 18)
    public void userShouldNotAddProductWithoutAuthorization() {

        File file = new File("assets/New.jpg");

        Response response = given()
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 500);
    }

    @Test(priority = 19)
    public void userShouldNotAddProductWithoutApplyongForSeller() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken2)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "販売者はまだ承認されていません");
    }

    @Test(priority = 20)
    public void productNameShouldNotContainMoreThan200Character() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Automobili Lamborghini S.p.A. (Italian pronunciation: [autoˈmɔːbili lamborˈɡiːni]) is an Italian manufacturer of luxury sports cars and SUVs based in Sant'Agata Bolognese. The company is owned by the Volkswagen Group through its subsidiary Audi.\n" + //
                                        "\n" + //
                                        "Ferruccio Lamborghini (1916–1993), an Italian manufacturing magnate, founded Automobili Ferruccio Lamborghini S.p.A. in 1963 to compete with Ferrari. The company was noted for using a rear mid-engine, rear-wheel drive layout. Lamborghini grew rapidly during its first decade, but sales plunged in the wake of the 1973 worldwide financial downturn and the oil crisis. The firm's ownership changed three times after 1973, including a bankruptcy in 1978. American Chrysler Corporation took control of Lamborghini in 1987 and sold it to Malaysian investment group Mycom Setdco and Indonesian group V'Power Corporation in 1994. In 1998, Mycom Setdco and V'Power sold Lamborghini to the Volkswagen Group where it was placed under the control of the group's Audi division.\n" + //
                                        "\n" + //
                                        "New products and model lines were introduced to the brand's portfolio and brought to the market and saw an increased productivity for the brand. In the late 2000s, during the worldwide financial crisis and the subsequent economic crisis, Lamborghini's sales saw a drop of nearly 50 per cent.")
                .multiPart("price", 33410)
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "製品名は最大200文字です");
    }


    @Test(priority = 21)
    public void productPriceShouldNotExceed10lac() {

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        File file3 = new File("assets/Test.png");

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Language", "jp")
                .multiPart("name", "Toy")
                .multiPart("price", "334100000000")
                .multiPart("supplementaryInformation", "abcd")
                .multiPart("breedingMethod", "ABC")
                .multiPart("acquisitionRouteDescription", "Japan")
                .multiPart("treeSpecies", "UmbrellaPlant")
                .multiPart("countryOfOrigin", "Taiwan")
                .multiPart("conditionAtTheTimeOfShipment", "BareRoot")
                .multiPart("shippingOrigin", "Ibaraki")
                .multiPart("shippingCost", "CashOnDelivery")
                .multiPart("daysOfDelivery", "Six")
                .multiPart("purchaserDesignation", "Tree Lover")
                .multiPart("status", "UNDER_REVIEW")
                .multiPart("hashTags", "RecommendedForBeginners, RecommendedAsAGift")
                .multiPart("shippingMethod", "FourthClassMail")
                .multiPart("productImages", file)
                .multiPart("productImages", file2)
                .multiPart("size", "Pup")
                .multiPart("width", "LessThanFive")
                .multiPart("productDoc", file3)
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "商品価格は1000000を超えることはできません");
    }
}