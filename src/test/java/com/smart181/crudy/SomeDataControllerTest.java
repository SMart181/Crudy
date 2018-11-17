package com.smart181.crudy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.smart181.crudy.model.CustomEntityRep;
import com.smart181.crudy.model.SomeData;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SomeDataControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	private HttpHeaders headers = new HttpHeaders();
	
	private DateFormat dateFormatter = new StdDateFormat();
	
	@Test
	public void testSomeDataControllerGetAllSomeData() throws Exception {
		
		HttpEntity<SomeData> entity = new HttpEntity<SomeData>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange("/someData", HttpMethod.GET, entity, String.class);
		
		// Check http response status
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		// Check the content of the request result
		DocumentContext context = JsonPath.parse(response.getBody());
		
		int length = context.read("$.length()");
		assertTrue(length >= 20);
		
		for(int i=0 ; i<20; i++) {
			assertEquals(context.read("$[" + i + "].someText"), "DataText." + (i+1));
		}
	}

	@Test
	public void testSomeDataControllerGetOneExistingSomeDataById() throws Exception {

		HttpEntity<SomeData> entity = new HttpEntity<SomeData>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange("/someData/1", HttpMethod.GET, entity, String.class);
		
		// Check http response status
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		// Check the content of the request result
		DocumentContext context = JsonPath.parse(response.getBody());
		assertEquals(context.read("$.someText"), "DataText.1");
		assertEquals((int) context.read("$.id"), 1);
	}
	
	@Test
	public void testSomeDataControllerGetOneNotExistingSomeDataById() throws Exception {

		HttpEntity<SomeData> entity = new HttpEntity<SomeData>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				"/someData/100000",
				HttpMethod.GET, entity, String.class);
		
		// Check http response status
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		
		// Check the content of the body is null
		assertNull(response.getBody());
	}
	
	@Test
	public void testSomeDataControllerPostNewSomeData() throws Exception {
	
		// Initialize data
		String someTextValue = "NewData";
		int someIntValue = -1000;
		double someDoubleValue = -1000;
		boolean someBooleanValue = false;
		Date someDateValue = new Date();
		
		// Create the SomeData object for request
		SomeData newData = new SomeData();
		newData.setSomeText(someTextValue);
		newData.setSomeInteger(someIntValue);
		newData.setSomeDouble(someDoubleValue);
		newData.setSomeBoolean(someBooleanValue);
		newData.setSomeDate(someDateValue);
		
		
		HttpEntity<SomeData> entity = new HttpEntity<SomeData>(newData, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				"/someData",
				HttpMethod.POST, entity, String.class);
		
		// Check http response status
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		// Check the content of the body
		DocumentContext context = JsonPath.parse(response.getBody());
		assertEquals(someTextValue, context.read("$.someText"));
		assertEquals(someIntValue, (int) context.read("$.someInteger"));
		assertEquals(someDoubleValue, (double)context.read("$.someDouble"), 0);
		assertSame(someBooleanValue, context.read("$.someBoolean"));
		assertEquals(someDateValue, dateFormatter.parse(context.read("$.someDate")));
		assertEquals(23, (int) context.read("$.id"));
	}
	
	@Test
	public void testSomeDataControllerPutSomeData() throws Exception {

		// Initialize data
		String someTextValue = "UpdatedData";
		int someIntValue = -1000;
		double someDoubleValue = -1000;
		boolean someBooleanValue = false;
		Date someDateValue = new Date();
		
		// Create the SomeData object for request
		SomeData dataToUpdate = new SomeData();
		dataToUpdate.setSomeText(someTextValue);
		dataToUpdate.setSomeInteger(someIntValue);
		dataToUpdate.setSomeDouble(someDoubleValue);
		dataToUpdate.setSomeBoolean(someBooleanValue);
		dataToUpdate.setSomeDate(someDateValue);
		
		HttpEntity<SomeData> entity = new HttpEntity<SomeData>(dataToUpdate, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				"/someData/21",
				HttpMethod.PUT, entity, String.class);
		
		// Check http response status
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		// Check the content of the body
		DocumentContext context = JsonPath.parse(response.getBody());
		assertEquals(someTextValue, context.read("$.someText"));
		assertEquals(someIntValue, (int) context.read("$.someInteger"));
		assertEquals(someDoubleValue, (double)context.read("$.someDouble"), 0);
		assertSame(someBooleanValue, context.read("$.someBoolean"));
		assertEquals(someDateValue, dateFormatter.parse(context.read("$.someDate")));
		assertEquals((int) context.read("$.id"), 21);
	}
	
	@Test
	public void testSomeDataControllerPutInvalidSomeData() throws Exception {

		// Initialize data
		String someTextValue = "PatchedData";
		
		// Create the SomeData object for request
		CustomEntityRep entityforUpdate = new CustomEntityRep();
		entityforUpdate.addPropertie("someText", someTextValue);
		
		HttpEntity<Object> entity = new HttpEntity<Object>(entityforUpdate.getEntity(), headers);
		ResponseEntity<String> response = restTemplate.exchange(
				"/someData/21",
				HttpMethod.PATCH, entity, String.class);
		
		// Check http response status
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		// Check the content of the body
		DocumentContext context = JsonPath.parse(response.getBody());
		assertEquals(someTextValue, context.read("$.someText"));
		assertEquals((int) context.read("$.id"), 21);
	}
	
	@Test
	public void testSomeDataControllerPutOnNotExistingSomeData() throws Exception {

		// Initialize data
		String someTextValue = "UpdatedData";
		int someIntValue = -1000;
		double someDoubleValue = -1000;
		boolean someBooleanValue = false;
		Date someDateValue = new Date();
		
		// Create the SomeData object for request
		SomeData dataToUpdate = new SomeData();
		dataToUpdate.setSomeText(someTextValue);
		dataToUpdate.setSomeInteger(someIntValue);
		dataToUpdate.setSomeDouble(someDoubleValue);
		dataToUpdate.setSomeBoolean(someBooleanValue);
		dataToUpdate.setSomeDate(someDateValue);
		
		HttpEntity<SomeData> entity = new HttpEntity<SomeData>(dataToUpdate, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				"/someData/100000",
				HttpMethod.PUT, entity, String.class);
		
		// Check http response status
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		
		// Check the content of the body is null
		assertNull(response.getBody());
	}
	
	@Test
	public void testSomeDataControllerDeleteSomeData() throws Exception {

		HttpEntity<SomeData> entity = new HttpEntity<SomeData>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				"/someData/22",
				HttpMethod.DELETE, entity, String.class);
		
		// Check http response status
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		// Check the content of the body
		DocumentContext context = JsonPath.parse(response.getBody());
		assertEquals((int) context.read("$.id"), 22);
	}
	
	@Test
	public void testSomeDataControllerDeleteOnNotExistingSomeDataById() throws Exception {

		HttpEntity<SomeData> entity = new HttpEntity<SomeData>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				"/someData/100000",
				HttpMethod.DELETE, entity, String.class);
		
		// Check http response status
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		
		// Check the content of the body is null
		assertNull(response.getBody());
	}
}
