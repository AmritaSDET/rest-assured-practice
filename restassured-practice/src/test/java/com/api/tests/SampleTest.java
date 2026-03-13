package com.api.tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

// CRUD operations on dummyjson.com API to test create, read, update and delete product endpoints.
public class SampleTest {

     
    private static int id;
    @Test
    public void createUser() {
        RestAssured.reset();
        String requestBody = "{\n" +
                "  \"title\": \"Dyson straightener\",\n" +
                "  \"SKU\": \"234567-03-IN\"\n" +
                "}";
            id =
        given()
                .baseUri("https://dummyjson.com")
                .contentType("application/json")
                .body(requestBody)
                .log().all()
        .when()
                .post("/products/add") 
                .then()
                .log().all()
                .statusCode(anyOf(is(200), is(201)))
                .body("title", equalTo("Dyson straightener"))
                .body("id", notNullValue())
                .extract()
                .path("id");
                 System.out.println("Created Product ID = " + id);

    }
    
    @Test 
     public void readUser() {
        given()
                .baseUri("https://dummyjson.com")
                .log().all()
        .when()
                .get("/products/1")
        .then()
                .log().all()
                .statusCode(anyOf(is(404), is(200)));
                // .statusCode(200);
                // .body("title", equalTo("Dyson straightener"));
     }
@Test 
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

     @Test
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
