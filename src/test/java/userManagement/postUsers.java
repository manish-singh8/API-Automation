package userManagement;

import core.StatusCode;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.cityRequestBody;
import pojo.postRequestBody;
import utils.SoftAssertionUtil;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class postUsers  {

    @Test
    public  void validatePostWithPojo(){

        postRequestBody requestBody= new postRequestBody();
        requestBody.setJob("leader");
        requestBody.setName("Morpheus");
        Response response=given().
                header("Content-Type","application/json").
                body(requestBody).
                when()
                .post("https://reqres.in/api/users")
                .then()
                .extract().response();

        assertEquals(response.statusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostWithPojo passed");
        System.out.println(response.getBody().asString());

    }

    @Test
    public void validatePostWithPojoWithListBody()
    {
        postRequestBody postRequestBody= new postRequestBody();
        List<String> languages=new ArrayList<>();
        languages.add("Java");
        languages.add("Python");
        //setting values
        postRequestBody.setLanguage(languages);
        postRequestBody.setJob("leader");
        postRequestBody.setName("Morpheus");


       Response response=given()
               .headers("Content-Type","application/JSON")
                       .body(postRequestBody)
                               .post("https://reqres.in/api/users");

        assertEquals(response.statusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostWithPojoWithListBody passed");
        System.out.println(response.getBody().asString());

    }

    @Test
    public void validatePutWithPojo() {

        postRequestBody putRequest = new postRequestBody();
        putRequest.setJob("SDET");
        putRequest.setName("Manish");
        Response response = given()
                .header("Content-Type", "application/json")
                .body(putRequest)
                .when()
                .put("https://reqres.in/api/users/2");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("validatePutWithPojo executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePostWithPojoWithComplexListBody()
    {
        postRequestBody postRequestBody= new postRequestBody();
        List<String> languages=new ArrayList<>();
        languages.add("Java");
        languages.add("Python");
        cityRequestBody cityRequestBody1 = new cityRequestBody();
        cityRequestBody cityRequestBody2 = new cityRequestBody();
        List<cityRequestBody> cities=new ArrayList<>();


        //city1
        cityRequestBody1.setName("Bangalore");
        cityRequestBody1.setTemperature("30");
        //city2
        cityRequestBody2.setName("Delhi");
        cityRequestBody2.setTemperature("40");

        cities.add(cityRequestBody1);
        cities.add(cityRequestBody2);

        //setting values
        postRequestBody.setLanguage(languages);
        postRequestBody.setJob("leader");
        postRequestBody.setName("Morpheus");
        postRequestBody.setCity(cities);


        Response response=given()
                .headers("Content-Type","application/JSON")
                .body(postRequestBody)
                .post("https://reqres.in/api/users");

        assertEquals(response.statusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostWithPojoWithComplexListBody passed");
        System.out.println(response.getBody().asString());

    }

    @Test(description  ="Deserialisation")

    public void validatePatchWithResponsePojo() {
        String job = "morpheus";
        postRequestBody patchRequest = new postRequestBody();
        patchRequest.setJob(job);
        Response response = given()
                .header("Content-Type", "application/json")
                .body(patchRequest)
                .when()
                .patch("https://reqres.in/api/users/2");
        postRequestBody responseBody = response.as(postRequestBody.class);
        System.out.println(responseBody.getJob());
        assertEquals(responseBody.getJob(), job);
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("validatePatchWithPojo executed successfully");
        System.out.println(response.getBody().asString());
    }

}
