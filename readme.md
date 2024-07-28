# Exchange Rate Application
In this project, it is aimed to develop simple foreign exchange application. 
* **Java 17** and **Spring Boot** framework was used this application. 
* Also **H2** was used for the in memory database. You can see database with [h2](http://localhost:8080/h2-console) url. Username and password information is located in the **application.properties** file.
* **maven** was used to compile the project.
* **Fixer.io** was used to pull the exchange rates.
* You can see the dependencies used in the pom.xml file.

## Run Project With Docker

## API Informations
You can reach swagger iu by 8080 port with this link: [swagger](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)

### Explanation
#### 1-Exchange Rate Endpoint: 
**1.1** `GET /api/v1/exchange/rate` -> This endpoint list exchange rate from foreign service.
#### _Example Request:_
```bash
curl --location --request GET 'http://localhost:8080/api/v1/exchange/rate' \
--header 'Authorization: api_key=android,api_hash=a46ea4066bda6162641240a163c06dba,api_random=random'
```
There is no need to enter any parameters.

**1.2** `POST /api/v1/exchange/rate/calculate` -> This endpoint calculate exchange rate between two currency and then save to history table.

Params | Value | Mandatory 
--- | --- | --- 
sourceCurrencyCode | String | Yes 
targetCurrencyCode | String | Yes 
#### _Example Request:_
```bash
curl --location --request POST 'http://localhost:8080/api/v1/exchange/rate/calculate?sourceCurrencyCode=USD&targetCurrencyCode=TRY' \
--header 'Authorization: api_key=android,api_hash=a46ea4066bda6162641240a163c06dba,api_random=random'
```
#### _Example Response:_
```bash
{
    "conversionInfo": "USD to TRY",
    "conversionTime": "2024-07-28T18:52:08.907+00:00",
    "conversionResult": 32.983
}
```
-----
#### 2-Currency Conversion Endpoint:
***2.1*** `POST /api/v1/currency/conversion` -> This endpoint calculate currency conversion between two currency and then save to history table.

Params | Value | Mandatory
--- | --- | --- 
sourceCurrencyCode | String | Yes
targetCurrencyCode | String | Yes
sourceAmount | Integer | Yes

#### _Example Request:_
```bash
curl --location --request POST 'http://localhost:8080/api/v1/currency/conversion?sourceCurrencyCode=TRY&targetCurrencyCode=USD&sourceAmount=1' \
--header 'Authorization: api_key=android,api_hash=a46ea4066bda6162641240a163c06dba,api_random=random'
```
#### _Example Response:_
```bash
{
    "conversionInfo": "1 TRY to USD",
    "conversionTime": "2024-07-28T19:18:44.957+00:00",
    "conversionResult": 0.031
}
```
-----
#### 3-Conversion History Endpoint:
***3.1*** `GET /api/v1/conversionHistory` -> This endpoint returns conversion history information in a paginated format.

Params | Value | Mandatory | Default
--- | --- | --- | ---
startDate | Long (Timestamp) | No | null
endDate | Long (Timestamp) | No | null
pageNo | Integer | No | 0
pageSize | Integer | No | 200
sortBy | String | No | time
sortDirection | String | No | DESC

* startDate and endDate -> If you want to give startDate or endDate, it can be given in the format 1722194221000
* If startDate and endDate are entered, it will bring the history information between the entered dates.
* If startDate is entered and endDate is not entered, it returns the history information after startDate.
* If endDate is entered and startDate is not entered, it returns the history information before endDate.
* If startDate and endDate are not entered, it returns the entire history.

#### _Example Request Without Parameters:_
```bash
curl --location --request GET 'http://localhost:8080/api/v1/conversionHistory' \
--header 'Authorization: api_key=android,api_hash=a46ea4066bda6162641240a163c06dba,api_random=random'
```
#### _Example Response Without Parameters:_
```bash
[
    {
        "id": 7,
        "info": "5.6 EUR to USD",
        "time": "2024-07-28T19:32:16.984+00:00",
        "result": 6.09
    },
    {
        "id": 6,
        "info": "6 EUR to USD",
        "time": "2024-07-28T19:32:09.307+00:00",
        "result": 6.53
    },
    {
        "id": 5,
        "info": "3 AZN to USD",
        "time": "2024-07-28T19:32:00.019+00:00",
        "result": 1.76
    },
    {
        "id": 4,
        "info": "AZN to USD",
        "time": "2024-07-28T19:31:37.815+00:00",
        "result": 0.59
    },
    {
        "id": 3,
        "info": "TRY to USD",
        "time": "2024-07-28T19:31:31.344+00:00",
        "result": 0.03
    },
    {
        "id": 2,
        "info": "1 TRY to USD",
        "time": "2024-07-28T19:18:44.957+00:00",
        "result": 0.03
    },
    {
        "id": 1,
        "info": "1 TRY to USD",
        "time": "2024-07-28T19:18:15.601+00:00",
        "result": 0.03
    }
]
```

#### _Example Request With Parameters:_
```bash
curl --location --request GET 'http://localhost:8080/api/v1/conversionHistory?startDate=1722107821000&endDate=1722280621000&pageNo=2&pageSize=2&sortBy=info&sortDirection=asc' \
--header 'Authorization: api_key=android,api_hash=a46ea4066bda6162641240a163c06dba,api_random=random'
```
#### _Example Response With Parameters:_
```bash
[
    {
        "id": 6,
        "info": "6 EUR to USD",
        "time": "2024-07-28T19:32:09.307+00:00",
        "result": 6.53
    },
    {
        "id": 4,
        "info": "AZN to USD",
        "time": "2024-07-28T19:31:37.815+00:00",
        "result": 0.59
    }
]
```





