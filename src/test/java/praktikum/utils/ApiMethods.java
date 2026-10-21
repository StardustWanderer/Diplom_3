package praktikum.utils;

import io.restassured.response.Response;
import praktikum.model.UserLoginModel;
import praktikum.model.UserRegistrationModel;

import static io.restassured.RestAssured.given;
import static praktikum.config.ApiEndpoints.*;

public class ApiMethods extends BaseApiMethods {

    public Response registerUser(String name, String email, String password) {

        UserRegistrationModel userRegisterModel = new UserRegistrationModel(name, email, password);

        return given()
                .spec(getRequestSpecification())
                .body(userRegisterModel)
                .when()
                .post(REGISTER_PATH)
                .then()
                .extract()
                .response();
    }

    public Response loginUser(String email, String password) {

        UserLoginModel userLoginModel = new UserLoginModel(email, password);

        return given()
                .spec(getRequestSpecification())
                .body(userLoginModel)
                .when()
                .post(LOGIN_PATH)
                .then()
                .extract()
                .response();
    }

    public Response deleteUser(String accessToken) {

        return given()
                .spec(getRequestSpecification())
                .header("Authorization", accessToken)
                .when()
                .delete(USER_PATH)
                .then()
                .extract()
                .response();
    }
}