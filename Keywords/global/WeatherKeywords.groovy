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

class WeatherKeywords {
	static void validate5DayForecast(def response, String expectedCity = "Jakarta") {
		try {
			// 1. Validasi dasar respons
			validateBasicResponse(response)

			// 2. Validasi data kota
			validateCityData(response, expectedCity)

			// 3. Validasi data forecast
			validateForecastData(response)

			KeywordUtil.logInfo("Validasi cuaca untuk $expectedCity berhasil")
		} catch (Exception e) {
			KeywordUtil.markFailedAndStop("Validasi cuaca gagal: " + e.getMessage())
		}
	}

	private static void validateBasicResponse(def response) {
		WS.verifyResponseStatusCode(response, 200, "Status code harus 200")

		// Validasi JSON schema
		def schema = new File('Include/Schemas/weather_schema.json').text
		WS.validateJsonAgainstSchema(response.getResponseText(), schema)

		// Validasi field wajib
		WS.verifyElementPropertyValue(response, 'cod', '200', "Kode respons harus '200'")
		WS.verifyElementPropertyValue(response, 'cnt', 40, "Harus ada 40 data forecast")
	}

	private static void validateCityData(def response, String expectedCity) {
		WS.verifyElementPropertyValue(response, 'city.name', expectedCity,
				"Nama kota harus $expectedCity")
		WS.verifyElementPropertyValue(response, 'city.country', 'ID',
				"Kode negara harus 'ID'")
	}

	private static void validateForecastData(def response) {
		def forecastList = WS.getElementPropertyValue(response, 'list')

		// Validasi sample data (3 titik waktu untuk efisiensi)
		def sampleData = [
			forecastList[0],
			forecastList[forecastList.size()/2],
			forecastList[-1]
		]

		sampleData.each { forecast ->
			validateTemperature(forecast.main.temp, forecast.dt)
			validateWeatherConditions(forecast.weather)
			validateTimestamps(forecast.dt, forecast.dt_txt)
		}
	}

	private static void validateTemperature(Number temp, Long timestamp) {
		if (temp < -20 || temp > 50) {
			KeywordUtil.markWarning("Suhu tidak realistis: ${temp}°C (timestamp: $timestamp)")
		}
	}

	private static void validateWeatherConditions(List weatherData) {
		def validConditions = [
			'Clear',
			'Clouds',
			'Rain',
			'Thunderstorm',
			'Drizzle',
			'Snow'
		]
		def condition = weatherData[0].main

		if (!validConditions.contains(condition)) {
			KeywordUtil.markWarning("Kondisi cuaca tidak dikenali: $condition")
		}
	}

	private static void validateTimestamps(Long unixTime, String formattedTime) {
		// Validasi format datetime
		if (!formattedTime.matches(/\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}/)) {
			KeywordUtil.markFailed("Format timestamp tidak valid: $formattedTime")
		}
	}
}