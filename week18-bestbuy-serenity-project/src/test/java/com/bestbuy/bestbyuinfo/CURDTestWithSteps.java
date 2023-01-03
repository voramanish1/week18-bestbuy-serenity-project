package com.bestbuy.bestbyuinfo;

import com.bestbuy.steps.ProductsSteps;
import com.bestbuy.steps.StoresSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class CURDTestWithSteps extends TestBase {
//------------------------------Products----------------------------//
    static String productName = "White Light" + TestUtils.getRandomValue();
    static String storeName = "White Light" + TestUtils.getRandomValue();
    static int id;

    @Steps
    ProductsSteps productsSteps;
    StoresSteps storesSteps;

    @Title("Get all Products")
    @Test
    public void test01() {
        ValidatableResponse response = productsSteps.getAllProducts();
        response.log().all().statusCode(200);
    }

    @Title("Create new Product")
    @Test
    public void test02() {
        ValidatableResponse response = productsSteps.createProducts(productName);
        response.log().all().statusCode(201);
    }

    @Title("Verify new product's created in application")
    @Test
    public void test03() {
        HashMap<String, Object> productsMap = productsSteps.getProductInfoByProductName(productName);
        Assert.assertThat(productsMap, hasValue(productName));
        id = (int) productsMap.get("id");
    }

    @Title("Update the product and verify the product information is updated")
    @Test
    public void test04() {
        productName = productName + "_updated";

        productsSteps.updateProduct(id, productName)
                .log().all().statusCode(200);

        HashMap<String, Object> productsMap = productsSteps.getProductInfoByProductName(productName);
        Assert.assertThat(productsMap, hasValue(productName));
    }

    @Title("Delete the product and verify if the product is deleted")
    @Test
    public void test05 (){
        productsSteps.deleteProduct(id).statusCode(204);
        productsSteps.getProductById(id).statusCode(404);
    }

//------------------------------Stores----------------------------//
    @Title("Get all Stores")
    @Test
    public void test06() {
        ValidatableResponse response = storesSteps.getAllProducts();
        response.log().all().statusCode(200);
    }

    @Title("Create new Store")
    @Test
    public void test07() {
        ValidatableResponse response = storesSteps.createStores(storeName);
        response.log().all().statusCode(201);
    }

    @Title("Verify new store's created in application")
    @Test
    public void test08() {
        HashMap<String, Object> storesMap = storesSteps.getStoreInfoByProductName(productName);
        Assert.assertThat(storesMap, hasValue(storeName));
        id = (int) storesMap.get("id");
    }

    @Title("Update the store and verify the product information is updated")
    @Test
    public void test09() {
        storeName = storeName + "_updated";

        storesSteps.updateStore(id, storeName)
                .log().all().statusCode(200);

        HashMap<String, Object> productsMap = storesSteps.getStoreInfoByProductName(storeName);
        Assert.assertThat(productsMap, hasValue(storeName));
    }

    @Title("Delete the store and verify if the product is deleted")
    @Test
    public void test10() {
        storesSteps.deleteStore(id).statusCode(204);
        storesSteps.getStoreById(id).statusCode(404);
    }

}
