#How to Run Tests
Open the project in Katalon Studio

Create a Test Suite:

Right-click Test Suites > New > Test Suite

Name it (e.g., TS_API_Validation)

Add Test Cases:

Drag TC_5DayWeatherForecast and TC_AirPollutionForecast into the suite

Run the suite:

Click the green "Run" button

Select browser (irrelevant for API tests, but required by Katalon)

#How to Get Reports
Go to Reports tab

Double-click the latest execution

# Project Structure Explanation
/ProjectRoot/
├── Objects Repository/
│   └── WeatherAPIs/
│       ├── Get_5DayWeather_JakartaSelatan   # GET forecast endpoint config
│       └── Get_AirPollution_JakartaSelatan  # GET pollution endpoint config
│
├── Keywords/
│   ├── WeatherKeywords.groovy       # Validation logic for weather data
│   ├── PollutionKeywords.groovy     # Validation logic for pollution data
│   ├── RateLimitKeywords.groovy     # Rate limit handling
│   └── AssertionKeywords.groovy     # Common assertions
│
├── Test Cases/
│   ├── TC_5DayWeatherForecast       # Test for weather API
│   └── TC_AirPollutionForecast      # Test for pollution API
│
├── Include/
│   └── Schemas/
│       ├── weather_schema.json      # JSON schema for weather response
│       └── pollution_schema.json    # JSON schema for pollution response
│
├── Profiles/
│   └── default.properties           # API keys and global variables
│
└── Test Suites/                     # (Optional) Test suite collections
