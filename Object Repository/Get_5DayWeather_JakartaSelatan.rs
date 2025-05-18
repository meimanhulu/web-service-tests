<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>Get_5DayWeather_JakartaSelatan</name>
   <tag></tag>
   <elementGuidId>4fc9a73f-74bc-40c9-8fc4-73c6c0da00d6</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>0</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent></httpBodyContent>
   <httpBodyType></httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>b26ac082-b75d-4d42-bbd5-e690d519f25f</webElementGuid>
   </httpHeaderProperties>
   <maxResponseSize>0</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <path></path>
   <restRequestMethod>GET</restRequestMethod>
   <restUrl>http://api.openweathermap.org/data/2.5/forecast?id=1642907&amp;appid=877028e0609b777d5571869d9ba831cd</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>0</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager
import internal.GlobalVariable as GlobalVariable

// Get the current response object
ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()

// Verify the response status code
WS.verifyResponseStatusCode(response, 200)

// Additional verification based on the image (status code, headers)
assertThat(response.getStatusCode()).isEqualTo(200)
assertThat(response.getHeaderFields()).containsKey(&quot;Content-Type&quot;)
assertThat(response.getHeaderFields().get(&quot;Content-Type&quot;)).contains(&quot;application/json&quot;)

// Verify specific properties in the response data
// Verify City Name and Coordinates
WS.verifyElementPropertyValue(response, 'city.name', &quot;Jakarta Special Capital Region&quot;)
WS.verifyElementPropertyValue(response, 'city.coord.lat', -6.2182)
WS.verifyElementPropertyValue(response, 'city.coord.lon', 106.8584)

// Verify Weather icons at different intervals
String[] expectedIcons = [
    &quot;03d&quot;, &quot;03d&quot;, &quot;10n&quot;, &quot;10n&quot;, &quot;10n&quot;, &quot;10n&quot;, &quot;10n&quot;, &quot;10n&quot;, &quot;10n&quot;, &quot;10n&quot;, 
    &quot;10d&quot;, &quot;10d&quot;, &quot;10d&quot;, &quot;10d&quot;, &quot;10d&quot;, &quot;10d&quot;, &quot;10d&quot;, &quot;10d&quot;, &quot;10d&quot;, &quot;10d&quot;
]
String[] actualIcons = new String[20]
for (int i = 0; i &lt; 20; i++) {
    actualIcons[i] = WS.getElementPropertyValue(response, &quot;list[&quot; + i + &quot;].weather[0].icon&quot;)
}
assertThat(actualIcons).containsExactly(expectedIcons)

// Verify temperatures at different times
Double[] expectedTemps = [
    305.38, 307.09, 306.48, 305.95, 305.32, 300.67
]
Double[] actualTemps = new Double[6]
for (int i = 0; i &lt; 6; i++) {
    actualTemps[i] = WS.getElementPropertyValue(response, &quot;list[&quot; + i + &quot;].main.temp&quot;)
}
assertThat(actualTemps).containsExactly(expectedTemps)

// Verify wind speeds at different times
Double[] expectedWindSpeeds = [
    2.16, 4.46, 1.64, 1.13, 0.73, 0.42
]
Double[] actualWindSpeeds = new Double[6]
for (int i = 0; i &lt; 6; i++) {
    actualWindSpeeds[i] = WS.getElementPropertyValue(response, &quot;list[&quot; + i + &quot;].wind.speed&quot;)
}
assertThat(actualWindSpeeds).containsExactly(expectedWindSpeeds)

// Verify precipitation (pop) for each time period
Double[] expectedPop = [
    0.0, 0.2, 1.0, 0.77, 0.56, 0.2
]
Double[] actualPop = new Double[6]
for (int i = 0; i &lt; 6; i++) {
    actualPop[i] = WS.getElementPropertyValue(response, &quot;list[&quot; + i + &quot;].pop&quot;)
}
assertThat(actualPop).containsExactly(expectedPop)
</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
