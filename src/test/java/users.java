import endpoints.APIConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;

public class users extends baseTest {

    //DELETE
    @Test
    public void deleteNonExistentUser() {
        RestAssured.given().spec(repoSpec)
                .when().delete(APIConstants.users + "453412")
                .then().statusCode(404);
    }

    @Test
    public void deleteExistentUser() {
        RestAssured.given().spec(repoSpec)
                .when().delete(APIConstants.users + responseInsert.jsonPath().get("id"))
                .then().statusCode(204);
    }

    @Test
    public void deleteUserNoBearer() {
        RestAssured.given()
                .when().delete(APIConstants.baseUrl + APIConstants.users + responseInsert.jsonPath().get("id"))
                .then().statusCode(404);
    }

    //POST
    @Test
    public void postUsersucessfuly() {
        RestAssured.given().spec(repoSpec).contentType(ContentType.JSON)
                .when().body(UsersPayload.getInsertUserPayload()).post(APIConstants.users)
                .then().statusCode(201);
    }

    @Test
    public void postUserEmptyObj() {
        RestAssured.given().spec(repoSpec).contentType(ContentType.JSON)
                .when().body("{}").post(APIConstants.users)
                .then().statusCode(422).assertThat()
                .body("field", hasItems("email", "name", "gender", "status"));
    }

    @Test
    public void postUserInvalidMail() {
        RestAssured.given().spec(repoSpec).contentType(ContentType.JSON)
                .when().body(UsersPayload.getInsertUserPayload().replace("@", ""))
                .post(APIConstants.users)
                .then().statusCode(422).assertThat().body("message", hasItem("is invalid"));
    }

    @Test
    public void postUserInvalidGender() {
        RestAssured.given().spec(repoSpec).contentType(ContentType.JSON)
                .when().body(UsersPayload.getInsertUserPayload().replace("male", "invalid"))
                .post(APIConstants.users)
                .then().statusCode(422).assertThat().body("message", hasItem("can't be blank, can be male of female"));
    }

    @Test
    public void postUserInvalidStatus() {
        RestAssured.given().spec(repoSpec).contentType(ContentType.JSON)
                .when().body(UsersPayload.getInsertUserPayload().replace("active", "invalid"))
                .post(APIConstants.users)
                .then().statusCode(422).assertThat().body("message", hasItem("can't be blank"));
    }

    @Test
    public void postUserNoBearer() {
        RestAssured.given().contentType(ContentType.JSON)
                .when().body(UsersPayload.getInsertUserPayload())
                .post(APIConstants.baseUrl + APIConstants.users)
                .then().statusCode(401);
    }

    //GET
    @Test
    public void getUserExistent() {
        Response response = RestAssured.given().spec(repoSpec)
                .when().get(APIConstants.users + responseInsert.jsonPath().get("id"));
        response.then().statusCode(200);
        Assert.assertEquals(response.jsonPath().get().toString(), responseInsert.jsonPath().get().toString());
    }

    @Test
    public void getUserNonExistent() {
        RestAssured.given().spec(repoSpec)
                .when().get(APIConstants.users + "453412")
                .then().statusCode(404);
    }

    @Test
    public void getUserNoBearer() {
        RestAssured.given()
                .when().get(APIConstants.baseUrl + APIConstants.users + responseInsert.jsonPath().get("id"))
                .then().statusCode(404);
    }

}
