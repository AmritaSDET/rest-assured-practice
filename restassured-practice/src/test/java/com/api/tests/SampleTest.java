package com.api.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.Assert;
// import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

// CRUD operations on dummyjson.com API to test create, read, update and delete product endpoints.
public class SampleTest {
    private static int id;
//     String extitle= "Dyson straightener";

    @Test(dataProvider = "ProductsData" , dataProviderClass = ProductDataProvider.class)

    public void createUser(String title, double price) {
        RestAssured.reset();
        String requestBody = "{\n" +
                "  \"title\": \"" + title + "\",\n" +
                "  \"price\": " + price + ",\n" +
                "  \"stock\": 100\n" +
                "}";
            
             id = given()
                .baseUri("https://dummyjson.com")
                .contentType("application/json")
                .body(requestBody)
                .log().all()
        .when()
                .post("/products/add") 
                .then()
                .log().all()
                .statusCode(anyOf(is(200), is(201)))
                // .body("title", equalTo("Dyson straightener"))
                .body("id", notNullValue())
                .extract()
                .path("id");
                 System.out.println("new products added"+ id);

    }
// @Test
public void readUser() {

    Response response =
        given()
            .baseUri("https://dummyjson.com")
            .log().all()
        .when()
            .get("/products/1")
        .then()
            .log().all()
            .statusCode(200)
            .extract().response();

    JsonPath jsonPath = response.jsonPath();
    String Actualtitle = jsonPath.getString("title");
    System.out.println("Actual Title: " + Actualtitle );
    String Expectedtitle = "Essence Mascara Lash Princess";
    Assert.assertEquals(Actualtitle, Expectedtitle, "Title does not match");
    

}

    
// @Test 
     public void updateUser() {
      
String requestBody = "{\n" +
        "  \"title\": \"Dyson\",\n" +
        "  \"price\": 299.99,\n" +
        "  \"stock\": 100\n" +
        "}";

        given()
                .baseUri("https://dummyjson.com")
                .contentType("application/json")
                .body(requestBody)
                .log().all()
        .when()
                .put("/products/1") 
        .then()
                .log().all()
                // .statusCode(anyOf(is(400), is(200)))
                 .statusCode(200)
                 .body("title", equalTo("Dyson"))
                 .body("price", equalTo(299.99F))
                 .body("stock", equalTo(100));
     }

//      @Test
        public void deleteUser() {
        given()
                .baseUri("https://dummyjson.com")
                .log().all()
        .when()
                .delete("/products/1")
        .then()
                .log().all()
                .statusCode(anyOf(is(400), is(200)));
                
        }

}
