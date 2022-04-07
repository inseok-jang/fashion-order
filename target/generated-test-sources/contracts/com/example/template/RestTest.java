package com.example.template;

import com.example.template.RestBase;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.RestAssured.*;
import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static org.springframework.cloud.contract.verifier.util.ContractVerifierUtil.*;

public class RestTest extends RestBase {

	@Test
	public void validate_productGet() throws Exception {
		// given:
			RequestSpecification request = given()
					.header("Content-Type", "application/json");

		// when:
			Response response = given().spec(request)
					.get("/product/1");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).matches("application/json.*");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
		// and:
			assertThat(parsedJson.read("$.id", String.class)).matches("[\\S\\s]+");
			assertThat(parsedJson.read("$.name", String.class)).matches("[\\S\\s]+");
			assertThat(parsedJson.read("$.price", String.class)).matches("[\\S\\s]+");
			assertThat(parsedJson.read("$.stock", String.class)).matches("[\\S\\s]+");
			assertThat(parsedJson.read("$.imageUrl", String.class)).matches("[\\S\\s]+");
	}

}
