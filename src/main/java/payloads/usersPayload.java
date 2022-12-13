package payloads;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import pojos.usersPojo;

public class usersPayload {
    protected Gson gson = new Gson();
    Faker faker = new Faker();

    public String getInsertUserPayload() {
        usersPojo UsersPojo = new usersPojo();
        UsersPojo.setName(faker.name().firstName() + " " + faker.name().lastName());
        UsersPojo.setEmail(faker.internet().emailAddress());
        UsersPojo.setGender("male");
        UsersPojo.setStatus("active");
        return gson.toJson(UsersPojo);
    }
}
