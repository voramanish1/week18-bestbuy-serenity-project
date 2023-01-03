package com.bestbuy.steps;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StoresPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class StoresSteps {
    @Step("Getting all Stores")
    public ValidatableResponse getAllProducts() {
        return SerenityRest.given()
                .when()
                .get(EndPoints.storesGet)
                .then()
                .log().all().statusCode(200);
    }

    @Step("Creating store with Name : {0}")
    public ValidatableResponse createStores(String storeName) {
        StoresPojo storesPojo = new StoresPojo();
        storesPojo.setName(storeName);
        storesPojo.setType("Bulb");
        storesPojo.setAddress("10");
        storesPojo.setAddress2("Downing Street");
        storesPojo.setCity("WestMinister");
        storesPojo.setState("London");
        storesPojo.setZip("EC1B 2JL");
        storesPojo.setLat(0);
        storesPojo.setLng(0);
        storesPojo.setHours("Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8");
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(storesPojo)
                .when()
                .post(EndPoints.storesPost)
                .then().log().all().statusCode(201);
    }

    @Step("Getting store information by name: {0}")
    public HashMap<String, Object> getStoreInfoByProductName(String storeName) {
        String p1 = "data.findAll{it.name = '";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get(EndPoints.storesGet)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + storeName + p2);
    }

    @Step("Updating store information by id: {0}, name: {1}")
    public ValidatableResponse updateStore(int id, String storeName) {
        StoresPojo storesPojo = new StoresPojo();
        storesPojo.setName(storeName);
        storesPojo.setType("Bulb");
        storesPojo.setAddress("10 Downing Street");
        storesPojo.setAddress2("");
        storesPojo.setCity("WestMinister");
        storesPojo.setState("London");
        storesPojo.setZip("EC1B 2JL");
        storesPojo.setLat(0);
        storesPojo.setLng(0);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json; charset=UTF-8")
                .pathParam("id", id)
                .body(storesPojo)
                .when()
                .put(EndPoints.storesPatch)
                .then();
    }

    @Step("Deleting store information by is: {0}")
    public ValidatableResponse deleteStore(int id) {
        return SerenityRest.given()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.storesDelete)
                .then();
    }

    @Step("Getting store information by id: {0}")
    public ValidatableResponse getStoreById(int id){
        return SerenityRest.given().log().all()
                .pathParam("id", id)
                .when()
                .get(EndPoints.storesGetById)
                .then();
    }
}
