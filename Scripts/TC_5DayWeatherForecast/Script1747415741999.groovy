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
import static org.assertj.core.api.Assertions.*
import groovy.json.JsonSlurper

// Send the request
def response = WS.sendRequest(findTestObject('Get_5DayWeather_JakartaSelatan'))

// Verify
WS.verifyResponseStatusCode(response, 200)

assertThat(response.getStatusCode()).isEqualTo(200)
assertThat(response.getHeaderFields()).containsKey('Content-Type')
assertThat(response.getHeaderFields().get('Content-Type').toString()).contains('application/json')

WS.verifyElementPropertyValue(response, 'city.name', 'Jakarta Special Capital Region')
WS.verifyElementPropertyValue(response, 'city.coord.lat', -6.2182)
WS.verifyElementPropertyValue(response, 'city.coord.lon', 106.8584)

def jsonSlurper = new JsonSlurper()
def jsonResponse = jsonSlurper.parseText(response.getResponseText())
println("Weather Icons:")
String[] actualIcons = new String[20] 
for (int i = 0; i < 20; i++) {
    actualIcons[i] = WS.getElementPropertyValue(response, "list[" + i + "].weather[0].icon")
    println("Icon " + i + ": " + actualIcons[i])
    assert actualIcons[i].length() == 3 : "Icon at index " + i + " should be 3 characters long"
    assert actualIcons[i][2] == 'd' || actualIcons[i][2] == 'n' : "Icon at index " + i + " should end with 'd' or 'n'"
}
boolean hasDay = actualIcons.any { it.endsWith('d') }
boolean hasNight = actualIcons.any { it.endsWith('n') }
assert hasDay : "Response should contain at least one day icon (ending with 'd')"
assert hasNight : "Response should contain at least one night icon (ending with 'n')"

// Verify temperatures
Double[] actualTemps = new Double[6]
for (int i = 0; i < 6; i++) {
    actualTemps[i] = WS.getElementPropertyValue(response, "list[" + i + "].main.temp")
    println("Temperature " + i + ": " + actualTemps[i])
    assert actualTemps[i] >= 290 && actualTemps[i] <= 320 : "Temperature at index " + i + " should be in a reasonable range for Jakarta"
}

// Verify wind speeds
Double[] actualWindSpeeds = new Double[6]
for (int i = 0; i < 6; i++) {
    actualWindSpeeds[i] = WS.getElementPropertyValue(response, "list[" + i + "].wind.speed")
    println("Wind Speed " + i + ": " + actualWindSpeeds[i])
    assert actualWindSpeeds[i] >= 0 : "Wind speed at index " + i + " should be non-negative"
    assert actualWindSpeeds[i] < 30 : "Wind speed at index " + i + " should be less than 30 m/s"
}

// Verify precipitation values
Double[] actualPop = new Double[6]
for (int i = 0; i < 6; i++) {
    actualPop[i] = WS.getElementPropertyValue(response, "list[" + i + "].pop")
    println("Precipitation Probability " + i + ": " + actualPop[i])
    assert actualPop[i] >= 0 && actualPop[i] <= 1 : "Precipitation probability at index " + i + " should be between 0 and 1"
}

def mainTemp = WS.getElementPropertyValue(response, 'list[0].main.temp')
def feelsLike = WS.getElementPropertyValue(response, 'list[0].main.feels_like')
def tempMin = WS.getElementPropertyValue(response, 'list[0].main.temp_min')
def tempMax = WS.getElementPropertyValue(response, 'list[0].main.temp_max')
def pressure = WS.getElementPropertyValue(response, 'list[0].main.pressure')
def seaLevel = WS.getElementPropertyValue(response, 'list[0].main.sea_level')
def humidity = WS.getElementPropertyValue(response, 'list[0].main.humidity')

assert mainTemp != null : "Main temperature should be present"
assert feelsLike != null : "Feels like temperature should be present"
assert tempMin != null : "Min temperature should be present"
assert tempMax != null : "Max temperature should be present"
assert tempMax >= tempMin : "Max temperature should be greater than or equal to min temperature"
assert pressure > 0 : "Pressure should be positive"
assert humidity >= 0 && humidity <= 100 : "Humidity should be between 0 and 100"