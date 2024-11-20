package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.ExtentReport;
import utils.PropertyReader;
import utils.SoftAssertionUtil;
import utils.jsonReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;
import static utils.jsonReader.getJsonArray;

public class getUsers extends BaseTest {


    @Test
    public void getUserData()
    {

        given()
                .when().get("https://reqres.in/api/users?page=2").then().assertThat().statusCode(200);

    }

    @Test
    public void validataeGetResponseBody()
    {
        //set BaseURI
       RestAssured.baseURI="https://jsonplaceholder.typicode.com";

       //Call get
        given().
                when().
                get("/todos/1").
                then().assertThat().
                statusCode(200).
                body(not(isEmptyString())).
                body("title",equalTo("delectus aut autem")).
                body("userId",equalTo(1));

    }

    @Test
    public void validateHasItems()
    {
        RestAssured.baseURI="https://jsonplaceholder.typicode.com";
//send a GET request and store response in variable
        Response response=given().
                when().
                get("/posts").then().
                extract().response();

        assertThat(response.jsonPath().getList("title"),hasItems("sunt aut facere repellat provident occaecati excepturi optio reprehenderit","qui est esse"));
    }

    @Test
    public void vlidateHasSize()
    {
        //Set base URI
        baseURI="https://jsonplaceholder.typicode.com";
        //call the method and store response

           Response response=  given().when().get("/comments").then().extract().response();

           assertThat(response.jsonPath().getList(""),hasSize(500));
    }

    @Test
    public  void validateListContainsOrder()
    {
        baseURI="https://jsonplaceholder.typicode.com";
        Response response=given().when().get("/comments?postId=1").then().extract().response();
        System.out.println("validateListContainsOrder RESPONSE:\n"+response.getBody().asString());

        //List to comapre the expected to
        List<String> expectedEmails= Arrays.asList("Eliseo@gardner.biz","Jayne_Kuhic@sydney.com","Nikita@garfield.biz","Lew@alysha.tv","Hayden@althea.biz");

        assertThat(response.jsonPath().getList("email"),contains(expectedEmails.toArray(new String[0])));

    }
    @Test
    public void validateIs()
    {

        baseURI="https://reqres.in/api";
        Response response=given().queryParam("page",2).
                when().get("/users").then().extract().response();

        //Assert body has 6 items
        assertThat(response.jsonPath().getList("data"),hasSize(6));

        //2nd way
        response.then().body("data",hasSize(6));

        //assert 1st user has all the values

        response.then().body("data[0].id",is(7));
        response.then().body("data[0].email",is("michael.lawson@reqres.in"));
        response.then().body("data[0].first_name",is("Michael"));
        response.then().body("data[0].last_name",is("Lawson"));
        response.then().body("data[0].avatar",is("https://reqres.in/img/faces/7-image.jpg"));

    }
    @Test
    public  void getTestUserWithHeader()
    {
        baseURI="https://reqres.in/api";
        given()
                .queryParam("page",2)
                .header("Content-type","Application/json")
                .when()
                .get("/users").
                then()
                .statusCode(200)
                .body("page",equalTo(2))
                .body("data[0].first_name",is("Michael"))
                .body("data[0].last_name",equalTo("Lawson"));

    }
    @Test
    public void verifyStatusCodeDelete() {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("verifyStatusCodeDelete", "Validate 204 status code for DELETE Method");
        Response resp = given()
                .delete("https://reqres.in/api/users/2");
        assertEquals(resp.getStatusCode(), StatusCode.NOCONTENT.code);
        System.out.println("verifyStatusCodeDelete executed successfully");
    }

    @Test
    public  void validataeWithTestDataFromJson() throws IOException, ParseException {
        String username= jsonReader.getTestData("username");
        String password= jsonReader.getTestData("password");

        Response response=given()
                .auth()
                .basic(username,password)
                .when()
                .get("https://postman-echo.com/basic-auth");

        int actualStatuscode=response.statusCode();
        assertEquals(actualStatuscode,StatusCode.SUCCESS.code);

        System.out.println("Validate from by reading testdata.json executed successfully");
    }

    @Test
    public void validateWithDataFromPropertiesFile() throws IOException, ParseException {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("validateWithDataFromPropertiesFile", "Validate 200 Status Code for GET method");
        String serverAddress = PropertyReader.propertyReader("config.properties", "server");
        String endpoint=jsonReader.getTestData("endpoint");
        String URL=serverAddress+endpoint;
        System.out.println("Server Address is : " + serverAddress);
        System.out.println("URL is : " + URL);
        Response resp =
                given()
                        .when()
                        .get(URL);
        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(actualStatusCode, 200); //Testng
        System.out.println("validateWithDataFromPropertiesFile executed successfully" + serverAddress);
    }
    @Test
    public void hardAssertion() {
        System.out.println("hardAssert");
        Assert.assertTrue(false);
        Assert.assertTrue(false);
        Assert.assertTrue(false);
        Assert.assertTrue(false);
        Assert.assertTrue(false);
        System.out.println("hardAssert");
    }

    @Test
    public void softAssertion() {

        System.out.println("softAssert");
        SoftAssertionUtil.assertTrue(true, "");
        SoftAssertionUtil.assertAll();
    }

    @Test
    public void validateWithSoftAssertUtil() {
        RestAssured.baseURI = "https://reqres.in/api";
        Response response = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        SoftAssertionUtil.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Status code is not 200");
        SoftAssertionUtil.assertAll();
        System.out.println("validateWithSoftAssertUtil executed successfully");
    }

    @DataProvider(name="testdata")
    public Object[][]testdata()
    {
        return new Object[][]{
                {"1", "John"},
                {"2", "Jane"},
                {"3", "Bob"}
    };
    }

    @Test(dataProvider = "testdata")
    @Parameters({"id","name"})
    public void ValidateDataProviderMethod(String id, String name)
    {
        given()
                .queryParam(id)
                .queryParam(name)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200);
        System.out.println("Executed with id "+id+" and name "+name);

    }
    @Test
    public void TestReadingJsonArrayFromtestDataJson() throws IOException, ParseException {

        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("TestReadingJsonArrayFromtestDataJson", "Validate Reading from testdataArray");
        jsonReader.getJsonArrayData("getPostmanEcho", 1);
        JSONArray jsonArray = getJsonArray("getPostmanEcho");
        Iterator<Object> iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        //assertEquals(200,200);
   }

}
