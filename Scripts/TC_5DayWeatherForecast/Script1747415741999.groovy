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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import internal.GlobalVariable as GlobalVariable
import global.WeatherKeywords
import global.RateLimitKeywords
import global.AssertionKeywords

WebUI.comment("TEST CASE: 5-Day Weather Forecast for Jakarta")

try {
    // 1. Send API request with rate limit handling
    def response = CustomKeywords.'RateLimitKeywords.sendRequestWithRetry'(
        findTestObject('WeatherAPIs/Get_5DayWeather_JakartaSelatan'),
        2 // Max retries
    )

    // 2. Validate using custom keyword
    CustomKeywords.'WeatherKeywords.validate5DayForecast'(response)

    // 3. Additional specific validations
    def forecastList = WS.getElementPropertyValue(response, 'list')
    forecastList.take(5).each { forecast -> // Validate first 5 entries for efficiency
        // Temperature validation with custom message
        WS.verifyGreaterThanOrEqual(forecast.main.temp, -20, 
            "Temperature too low: ${forecast.main.temp}°C at timestamp ${forecast.dt}")
            
        WS.verifyLessThanOrEqual(forecast.main.temp, 50, 
            "Temperature too high: ${forecast.main.temp}°C at timestamp ${forecast.dt}")
    }

    KeywordUtil.markPassed("Weather forecast validation successful")
} catch (Exception e) {
    KeywordUtil.markFailedAndStop("Test failed: " + e.getMessage())
} finally {
    WebUI.comment("Test completed. Check reports for details.")
}