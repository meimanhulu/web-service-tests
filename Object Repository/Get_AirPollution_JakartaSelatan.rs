<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>Get_AirPollution_JakartaSelatan</name>
   <tag></tag>
   <elementGuidId>8e286b35-808e-4a05-908d-d9d28f3b6b5d</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>true</autoUpdateContent>
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
      <webElementGuid>6da02de8-40d3-4d58-8843-7bea026be6b6</webElementGuid>
   </httpHeaderProperties>
   <maxResponseSize>0</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <path></path>
   <restRequestMethod>GET</restRequestMethod>
   <restUrl>http://api.openweathermap.org/data/2.5/air_pollution/forecast?lat=-6.2182&amp;lon=106.858398&amp;appid=877028e0609b777d5571869d9ba831cd</restUrl>
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
WS.verifyElementPropertyValue(response, 'coord.lon', 106.8584)
WS.verifyElementPropertyValue(response, 'coord.lat', -6.2182)

// Verify the AQI (Air Quality Index) values for specific times (from list array)
assertThat(response.getResponseText()).contains(&quot;list&quot;)
String[] arrayResponse = [&quot;why&quot;, &quot;hello&quot;, &quot;there&quot;, &quot;how&quot;, &quot;are&quot;, &quot;you&quot;]

// Apply array validation
assertThat(arrayResponse).containsOnly(&quot;there&quot;, &quot;hello&quot;, &quot;why&quot;)  // Only &quot;there&quot;, &quot;hello&quot;, &quot;why&quot; are valid values
assertThat(arrayResponse).containsExactly(&quot;why&quot;, &quot;hello&quot;, &quot;there&quot;, &quot;how&quot;, &quot;are&quot;, &quot;you&quot;) // All values must match exactly

// Verify timestamp and AQI values for later times
WS.verifyElementPropertyValue(response, 'list[95].dt', 1747879200)
WS.verifyElementPropertyValue(response, 'list[95].components.nh3', 0.73)
WS.verifyElementPropertyValue(response, 'list[95].main.aqi', 2)
WS.verifyElementPropertyValue(response, 'list[95].components.co', 153.51)
WS.verifyElementPropertyValue(response, 'list[95].components.o3', 60.48)

// Verify elements for another data point in the list
WS.verifyElementPropertyValue(response, 'list[100].main.aqi', 1)
WS.verifyElementPropertyValue(response, 'list[100].components.pm2_5', 10.83)  // Corrected pm2_5 index

// Verifying the last entries in the list for completeness
WS.verifyElementPropertyValue(response, 'list[200].main.aqi', 2)
WS.verifyElementPropertyValue(response, 'list[200].components.nh3', 1.16)

// Additional checks for components in the list
WS.verifyElementPropertyValue(response, 'list[10].components.co', 245.86)
WS.verifyElementPropertyValue(response, 'list[10].components.o3', 51.77)
WS.verifyElementPropertyValue(response, 'list[20].components.so2', 0.35)
</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
