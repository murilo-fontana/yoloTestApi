import endpoints.APIConstants;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import payloads.usersPayload;

import static io.restassured.config.RestAssuredConfig.newConfig;

public class baseTest {
    protected RequestSpecification repoSpec;
    protected usersPayload UsersPayload = new usersPayload();
    protected Response responseInsert;

    @BeforeMethod
    protected void configSetUp(){
        repoSpec = new RequestSpecBuilder().setBaseUri(APIConstants.baseUrl).setConfig(newConfig())
                .addHeader("Authorization", APIConstants.bearerToken).build();
    }

    @BeforeMethod
    protected Response insertDataUser(){
        return responseInsert = RestAssured.given().spec(repoSpec).contentType(ContentType.JSON)
                .when().body(UsersPayload.getInsertUserPayload())
                .post(APIConstants.users);
    }

    @AfterMethod
    protected void deleteDataUser(){
        RestAssured.given().spec(repoSpec).when().post(APIConstants.users+responseInsert.jsonPath().get("id"));
    }
}
