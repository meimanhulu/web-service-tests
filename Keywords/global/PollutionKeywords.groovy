package global
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import internal.GlobalVariable as GlobalVariable


class PollutionKeywords {
	static void validateAirPollution(def response, Number pm25Threshold = 25) {
		try {
			// 1. Validasi dasar respons
			validateBasicResponse(response)

			// 2. Validasi koordinat
			validateCoordinates(response)

			// 3. Validasi komponen polusi
			validatePollutionComponents(response, pm25Threshold)

			KeywordUtil.logInfo("Validasi polusi udara berhasil")
		} catch (Exception e) {
			KeywordUtil.markFailedAndStop("Validasi polusi gagal: " + e.getMessage())
		}
	}

	private static void validateBasicResponse(def response) {
		WS.verifyResponseStatusCode(response, 200, "Status code harus 200")

		// Validasi JSON schema
		def schema = new File('Include/Schemas/pollution_schema.json').text
		WS.validateJsonAgainstSchema(response.getResponseText(), schema)

		// Validasi struktur dasar
		WS.verifyElementPropertyValue(response, 'list[0].main.aqi', Integer.class,
				"Data AQI harus ada")
	}

	private static void validateCoordinates(def response) {
		def tolerance = 0.001
		def lon = WS.getElementPropertyValue(response, 'coord.lon')
		def lat = WS.getElementPropertyValue(response, 'coord.lat')

		WS.verifyEqual(lon, GlobalVariable.jakarta_lon.toDouble(), tolerance,
				"Longitude harus sesuai dengan Jakarta Selatan")
		WS.verifyEqual(lat, GlobalVariable.jakarta_lat.toDouble(), tolerance,
				"Latitude harus sesuai dengan Jakarta Selatan")
	}

	private static void validatePollutionComponents(def response, Number pm25Threshold) {
		def components = WS.getElementPropertyValue(response, 'list[0].components')
		def thresholds = [
			'pm2_5': [min: 0, warning: pm25Threshold, max: 500],
			'pm10' : [min: 0, warning: 50, max: 500],
			'co'   : [min: 0, warning: 4000, max: 10000],
			'no2'  : [min: 0, warning: 100, max: 200],
			'o3'   : [min: 0, warning: 100, max: 300],
			'so2'  : [min: 0, warning: 50, max: 200],
			'nh3'  : [min: 0, warning: 20, max: 100]
		]

		thresholds.each { name, range ->
			def value = components[name]

			// Validasi dasar
			WS.verifyGreaterThanOrEqual(value, range.min,
					"${name.toUpperCase()} tidak boleh negatif")

			// Warning jika melebihi ambang batas
			if (value > range.warning) {
				KeywordUtil.markWarning("${name.toUpperCase()} (${value} μg/m³) melebihi ambang aman (${range.warning})")
			}

			// Fail jika melebihi batas maksimum realistis
			if (value > range.max) {
				KeywordUtil.markFailed("${name.toUpperCase()} (${value}) melebihi batas maksimum (${range.max})")
			}
		}

		// Log detail polusi
		logPollutionDetails(components)
	}

	private static void logPollutionDetails(Map components) {
		def logMessage = """
        |=== DETAIL POLUSI ===
        |PM2.5: ${components.pm2_5} μg/m³
        |PM10: ${components.pm10} μg/m³
        |CO: ${components.co} μg/m³
        |NO2: ${components.no2} μg/m³
        |O3: ${components.o3} μg/m³
        |SO2: ${components.so2} μg/m³
        |NH3: ${components.nh3} μg/m³
        |""".stripMargin()

		KeywordUtil.logInfo(logMessage)
	}
}