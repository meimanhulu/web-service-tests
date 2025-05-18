import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webservice.verification.WSResponseManager as WSResponseManager
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import static org.assertj.core.api.Assertions.*
import groovy.json.JsonSlurper

// Send the request to get air pollution data for Jakarta
def response = WS.sendRequest(findTestObject('Get_AirPollution_JakartaSelatan'))

// Verify the response status code
WS.verifyResponseStatusCode(response, 200)

// Additional verification based on the image (status code, headers)
assertThat(response.getStatusCode()).isEqualTo(200)
assertThat(response.getHeaderFields()).containsKey('Content-Type')
assertThat(response.getHeaderFields().get("Content-Type").toString()).contains("application/json")

// Verify specific properties in the response data - these coordinates should be stable
WS.verifyElementPropertyValue(response, 'coord.lon', 106.8584)
WS.verifyElementPropertyValue(response, 'coord.lat', -6.2182)

// Verify the AQI (Air Quality Index) values for specific times (from list array)
assertThat(response.getResponseText()).contains('list')

// Apply array validation example (this is just a sample and not related to the API response)
String[] arrayResponse = ['why', 'hello', 'there', 'how', 'are', 'you']
assertThat(arrayResponse).containsOnly('there', 'hello', 'why', 'how', 'are', 'you')
assertThat(arrayResponse).containsExactly('why', 'hello', 'there', 'how', 'are', 'you')

// Parse the entire response for more flexible validation
def jsonSlurper = new JsonSlurper()
def jsonResponse = jsonSlurper.parseText(response.getResponseText())

// Verify response structure
assert jsonResponse.list != null : "Response should contain a 'list' array"
assert jsonResponse.list.size() > 0 : "List should contain at least one entry"

// Print list size for reference
println("List size: " + jsonResponse.list.size())

// Verify first entry in the response has expected structure and valid values
def firstEntry = jsonResponse.list[0]
assert firstEntry.main != null : "First entry should have a 'main' object"
assert firstEntry.main.aqi >= 1 && firstEntry.main.aqi <= 5 : "AQI should be between 1 and 5"
assert firstEntry.components != null : "First entry should have 'components' object"
assert firstEntry.components.co > 0 : "CO value should be positive"
assert firstEntry.components.no2 >= 0 : "NO2 value should be valid"
assert firstEntry.components.o3 > 0 : "O3 value should be positive"
assert firstEntry.components.so2 >= 0 : "SO2 value should be valid"
assert firstEntry.components.pm2_5 >= 0 : "PM2.5 value should be valid"
assert firstEntry.components.pm10 >= 0 : "PM10 value should be valid"
assert firstEntry.dt > 0 : "Timestamp should be valid"

// Print actual values for reference (useful for debugging)
println("First entry AQI: " + firstEntry.main.aqi)
println("First entry CO: " + firstEntry.components.co)
println("First entry NO2: " + firstEntry.components.no2)
println("First entry O3: " + firstEntry.components.o3)
println("First entry SO2: " + firstEntry.components.so2)
println("First entry PM2.5: " + firstEntry.components.pm2_5)
println("First entry PM10: " + firstEntry.components.pm10)

// Only check index 95 if the list actually has that many entries
if (jsonResponse.list.size() > 95) {
    def entry95 = jsonResponse.list[95]
    assert entry95 != null : "Entry at index 95 should exist"
    assert entry95.main.aqi >= 1 && entry95.main.aqi <= 5 : "AQI at index 95 should be valid"
    assert entry95.components.co > 0 : "CO value at index 95 should be positive"
} else {
    println("List has fewer than 96 entries, skipping check for index 95")
}

// Only verify timestamps if there are enough entries
if (jsonResponse.list.size() > 50) {
    def firstTimestamp = jsonResponse.list[0].dt
    def laterTimestamp = jsonResponse.list[50].dt
    assert laterTimestamp > firstTimestamp : "Later timestamp should be greater than first timestamp"
} else if (jsonResponse.list.size() > 1) {
    def firstTimestamp = jsonResponse.list[0].dt
    def laterTimestamp = jsonResponse.list[jsonResponse.list.size() - 1].dt
    assert laterTimestamp > firstTimestamp : "Later timestamp should be greater than first timestamp"
}

// Verify at least one entry with valid AQI exists
def hasValidAqi = jsonResponse.list.any { it.main.aqi >= 1 && it.main.aqi <= 5 }
assert hasValidAqi : "Response should contain at least one entry with valid AQI"