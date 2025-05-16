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
import global.PollutionKeywords
import global.RateLimitKeywords
import global.AssertionKeywords

WebUI.comment("TEST CASE: Air Pollution Forecast for Jakarta")

try {
    // 1. Send API request with rate limit handling
    def response = CustomKeywords.'RateLimitKeywords.sendRequestWithRetry'(
        findTestObject('WeatherAPIs/Get_AirPollution_JakartaSelatan'),
        2 // Max retries
    )

    // 2. Basic response validation
    WS.verifyResponseStatusCode(response, 200, "Status code should be 200")
    
    // 3. JSON Schema validation
    def pollutionSchema = new File('Include/Schemas/pollution_schema.json').text
    WS.validateJsonAgainstSchema(response.getResponseText(), pollutionSchema)

    // 4. Coordinate validation
    def tolerance = 0.001
    WS.verifyEqual(
        WS.getElementPropertyValue(response, 'coord.lon').toDouble(), 
        GlobalVariable.jakarta_lon.toDouble(), 
        tolerance,
        "Longitude should match Jakarta's coordinates"
    )
    
    WS.verifyEqual(
        WS.getElementPropertyValue(response, 'coord.lat').toDouble(), 
        GlobalVariable.jakarta_lat.toDouble(), 
        tolerance,
        "Latitude should match Jakarta's coordinates"
    )

    // 5. Pollution data validation
    def pollutionList = WS.getElementPropertyValue(response, 'list')
    WS.verifyGreaterThan(pollutionList.size(), 0, "Pollution data should not be empty")

    // 6. Validate latest pollution data
    def latestPollution = pollutionList[0]
    
    // AQI validation (1-5)
    def aqi = latestPollution.main.aqi
    WS.verifyGreaterThanOrEqual(aqi, 1, "AQI should be ≥ 1")
    WS.verifyLessThanOrEqual(aqi, 5, "AQI should be ≤ 5")

    // 7. Validate pollution components
    def components = latestPollution.components
    def thresholds = [
        'pm2_5': [min: 0, warning: 25, max: 500],
        'pm10' : [min: 0, warning: 50, max: 500],
        'co'   : [min: 0, warning: 4000, max: 10000],
        'no2'  : [min: 0, warning: 100, max: 200],
        'o3'   : [min: 0, warning: 100, max: 300],
        'so2'  : [min: 0, warning: 50, max: 200]
    ]

    thresholds.each { name, range ->
        def value = components[name]
        
        // Basic value validation
        WS.verifyGreaterThanOrEqual(
            value, 
            range.min, 
            "${name.toUpperCase()} value (${value}) should not be negative"
        )
        
        // Warning for WHO threshold exceedance
        if (value > range.warning) {
            KeywordUtil.markWarning("${name.toUpperCase()} (${value} μg/m³) exceeds WHO warning threshold (${range.warning})")
        }
    }

    KeywordUtil.markPassed("Air pollution validation successful")
} catch (Exception e) {
    KeywordUtil.markFailedAndStop("Test failed: " + e.getMessage())
} finally {
    WebUI.comment("Test completed. Check reports for pollution levels.")
}