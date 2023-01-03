package com.bestbuy.steps;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductsPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductsSteps {
    @Step("Getting all Products")
    public ValidatableResponse getAllProducts() {
        return SerenityRest.given()
                .when()
                .get(EndPoints.productsGet)
                .then()
                .log().all().statusCode(200);
    }

    @Step("Creating student with productName : {0}")
    public ValidatableResponse createProducts(String productName) {

        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(productName);
        productsPojo.setType("HeadLight");
        productsPojo.setPrice(7.95);
        productsPojo.setShipping(0);
        productsPojo.setUpc("0987654");
        productsPojo.setDescription("Compatible with 4x4 cars only");
        productsPojo.setManufacturer("Philips");
        productsPojo.setModel("WHT150");
        productsPojo.setUrl("https://whitelight4x4.com");
        productsPojo.setImage("imgwhtlgt4x4.jpg");
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post(EndPoints.productsPost)
                .then().log().all().statusCode(201);
    }

    @Step("Getting product information by name: {0}")
    public HashMap<String, Object> getProductInfoByProductName(String productName) {
        String p1 = "data.findAll{it.name = '";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get(EndPoints.productsGet)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + productName + p2);
    }

    @Step("Updating product information by id: {0}, name: {1}")
    public ValidatableResponse updateProduct(int id, String productName) {
        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(productName);
        productsPojo.setType("HeadLight");
        productsPojo.setPrice(7.95);
        productsPojo.setShipping(0);
        productsPojo.setUpc("0987654");
        productsPojo.setDescription("Compatible with 4x4 cars only");
        productsPojo.setManufacturer("Philips");
        productsPojo.setModel("WHT150");
        productsPojo.setUrl("https://whitelight4x4.com");
        productsPojo.setImage("imgwhtlgt4x4.jpg");
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json; charset=UTF-8")
                .pathParam("id", id)
                .body(productsPojo)
                .when()
                .put(EndPoints.productsPatch)
                .then();
    }

    @Step("Deleting product information by is: {0}")
    public ValidatableResponse deleteProduct(int id) {
        return SerenityRest.given()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.productsDelete)
                .then();
    }

    @Step("Getting product information by id: {0}")
    public ValidatableResponse getProductById(int id){
        return SerenityRest.given().log().all()
                .pathParam("id", id)
                .when()
                .get(EndPoints.productsGetById)
                .then();
    }
}