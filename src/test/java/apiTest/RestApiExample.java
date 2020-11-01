package apiTest;

import apiTest.models.Employee;
import apiTest.models.EmployeeResponse;
import apiTest.models.PostEmployeeModel;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class RestApiExample {

    @BeforeClass
    public void start(){
        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
    }

    @Test
    public void getAllEmployeesTest() {
        given()
                .log().all()
                .when()
                .get("/employees")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("status", equalTo("success"))
                .body("data.id", hasItems("1", "2", "3"));
//                .body("data.id", instanceOf(String.class));
    }

    @Test
    public void getEmployeesByIdTest() {
        Employee expectedEmployee = new Employee("Tiger Nixon", 320800, 61, "");
        EmployeeResponse expectedResponse = new EmployeeResponse("success", expectedEmployee, "Successfully! Record has been fetched.");

        EmployeeResponse response = given()
                .when()
                .get("/employee/1")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(EmployeeResponse.class);

        assertEquals(response, expectedResponse);

    }
    @Test
    public void postEmployeesTest() {
        PostEmployeeModel employee = new PostEmployeeModel("QWERTY", "990099", "12");
        EmployeeResponse expectedResponse = new EmployeeResponse("success", new Employee(), "Successfully! Record has been added.");

        EmployeeResponse response = given()
                .with()
                .body(employee)
                .post("/create")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(EmployeeResponse.class);

        assertEquals(response, expectedResponse);
    }

    @Test
    public void post1EmployeesTest() {
        PostEmployeeModel employee = new PostEmployeeModel("QWERTY", "990099", "12");
        EmployeeResponse expectedResponse = new EmployeeResponse("success", new Employee(), "Successfully! Record has been added.");

        EmployeeResponse response = given()
                .with()
                .body(employee)
                .post("/create")
                .then()
                .log().all()
                .statusCode(429)
                .extract()
                .as(EmployeeResponse.class);

        assertEquals(response, expectedResponse);

    }
}
