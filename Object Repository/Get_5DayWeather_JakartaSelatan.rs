<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>Get_5DayWeather_JakartaSelatan</name>
   <tag></tag>
   <elementGuidId>4fc9a73f-74bc-40c9-8fc4-73c6c0da00d6</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>true</autoUpdateContent>
   <connectionTimeout>0</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\n  \&quot;$schema\&quot;: \&quot;http://json-schema.org/draft-07/schema#\&quot;,\n  \&quot;type\&quot;: \&quot;object\&quot;,\n  \&quot;properties\&quot;: {\n    \&quot;cod\&quot;: {\&quot;type\&quot;: \&quot;string\&quot;},\n    \&quot;message\&quot;: {\&quot;type\&quot;: \&quot;number\&quot;},\n    \&quot;cnt\&quot;: {\&quot;type\&quot;: \&quot;integer\&quot;},\n    \&quot;list\&quot;: {\n      \&quot;type\&quot;: \&quot;array\&quot;,\n      \&quot;items\&quot;: {\n        \&quot;type\&quot;: \&quot;object\&quot;,\n        \&quot;properties\&quot;: {\n          \&quot;dt\&quot;: {\&quot;type\&quot;: \&quot;integer\&quot;},\n          \&quot;main\&quot;: {\n            \&quot;type\&quot;: \&quot;object\&quot;,\n            \&quot;properties\&quot;: {\n              \&quot;temp\&quot;: {\&quot;type\&quot;: \&quot;number\&quot;},\n              \&quot;temp_min\&quot;: {\&quot;type\&quot;: \&quot;number\&quot;},\n              \&quot;temp_max\&quot;: {\&quot;type\&quot;: \&quot;number\&quot;},\n              \&quot;humidity\&quot;: {\&quot;type\&quot;: \&quot;integer\&quot;}\n            },\n            \&quot;required\&quot;: [\&quot;temp\&quot;, \&quot;humidity\&quot;]\n          },\n          \&quot;weather\&quot;: {\n            \&quot;type\&quot;: \&quot;array\&quot;,\n            \&quot;items\&quot;: {\n              \&quot;type\&quot;: \&quot;object\&quot;,\n              \&quot;properties\&quot;: {\n                \&quot;main\&quot;: {\&quot;type\&quot;: \&quot;string\&quot;},\n                \&quot;description\&quot;: {\&quot;type\&quot;: \&quot;string\&quot;}\n              }\n            }\n          }\n        },\n        \&quot;required\&quot;: [\&quot;dt\&quot;, \&quot;main\&quot;, \&quot;weather\&quot;]\n      }\n    },\n    \&quot;city\&quot;: {\n      \&quot;type\&quot;: \&quot;object\&quot;,\n      \&quot;properties\&quot;: {\n        \&quot;name\&quot;: {\&quot;type\&quot;: \&quot;string\&quot;},\n        \&quot;country\&quot;: {\&quot;type\&quot;: \&quot;string\&quot;}\n      }\n    }\n  },\n  \&quot;required\&quot;: [\&quot;cod\&quot;, \&quot;list\&quot;, \&quot;city\&quot;]\n}&quot;,
  &quot;contentType&quot;: &quot;text/plain&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>text/plain</value>
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
   <variables>
      <defaultValue>21</defaultValue>
      <description></description>
      <id>0de4fa76-1864-4d5e-aa8a-70ad8f4f4342</id>
      <masked>false</masked>
      <name>age</name>
   </variables>
   <variables>
      <defaultValue>'ngoc'</defaultValue>
      <description></description>
      <id>086a27a9-05ed-4e07-8465-e17bddfdeac1</id>
      <masked>false</masked>
      <name>username</name>
   </variables>
   <variables>
      <defaultValue>'1234567890'</defaultValue>
      <description></description>
      <id>311c2780-afd0-4352-a23f-fc00c2c42271</id>
      <masked>false</masked>
      <name>password</name>
   </variables>
   <variables>
      <defaultValue>'https://www.rd.com/wp-content/uploads/2019/06/lily-of-the-valley-760x506.jpg'</defaultValue>
      <description></description>
      <id>bcddbada-1d5e-422c-b810-55f7c48bb931</id>
      <masked>false</masked>
      <name>avatar</name>
   </variables>
   <variables>
      <defaultValue>'FEMALE'</defaultValue>
      <description></description>
      <id>3b3cb3f6-19ff-4f44-ae2c-a1c680bd3044</id>
      <masked>false</masked>
      <name>gender</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.successCode</defaultValue>
      <description></description>
      <id>f1c9042c-8dca-4d38-ba9e-77542f9e8d20</id>
      <masked>false</masked>
      <name>expectedStatusCode</name>
   </variables>
   <variables>
      <defaultValue>7</defaultValue>
      <description></description>
      <id>c4249c68-7dc1-4ffc-ad8e-a4109ce4bb7b</id>
      <masked>false</masked>
      <name>id</name>
   </variables>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import internal.GlobalVariable as GlobalVariable

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()
ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()
assert response.getStatusCode() == 200
</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
