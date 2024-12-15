# R2RML ETL Pipeline API
> JSON to RDF Conversion Service

This project is a Spring Boot application that converts JSON data into RDF (Resource Description Framework) using RML (RDF Mapping Language). The application exposes an API endpoint that accepts JSON data, processes it using a specified RML mapping, and returns the RDF data in Turtle format.

## Features

- Accepts JSON data via a POST request
- Uses RML mapping to convert JSON to RDF
- Returns RDF data in Turtle format
- Handles dynamic JSON input and temporary file management
- Provides error handling and logging

## Prerequisites

- Java 11 or higher
- Gradle 6.0 or higher

## Getting Started

### Clone the Repository

```sh
git clone https://github.com/your-repository/json-to-rdf-conversion-service.git
cd json-to-rdf-conversion-service
```

### Build the Project

```sh
./gradlew build
```

### Run the Application

```sh
./gradlew bootRun
```

The application will start on `http://localhost:8080`.

## Usage

### API Endpoint

- **URL:** `/api/generate-rdf`
- **Method:** `POST`
- **Content-Type:** `application/json`
- **Produces:** `text/turtle; charset=utf-8`

### Request Body

Send a JSON payload that matches the structure expected by the RML mapping.

#### Example Request

```json
{
    "dataModel": "source_a",
    "jsonData": {
      "devices": [
        {
          "id": "dev_001",
          "name": "Primary Device",
          "serialNumber": "SN123456",
          "owner": "usr_001"
        }
      ],
      "actuators": [
        {
          "id": "act_001",
          "deviceId": "dev_001"
        }
      ],
      "uiDevices": [
        {
          "id": "ui_001",
          "sensorId": "sens_001",
          "actuatorId": "act_001",
          "deviceId": "dev_001"
        }
      ],
      "sensors": [
        {
          "id": "sens_001",
          "type": "TEMPERATURE",
          "location": "Living Room",
          "threshold": 35.0,
          "deviceId": "dev_001"
        }
      ],
      "events": [
        {
          "timestamp": "2021-09-29T11:19:11.788Z",
          "id": "evt_001",
          "userId": "usr_002",
          "assetId": "asset_001",
          "dimension": "VALUE",
          "value": 28.7,
          "originId": "sens_001",
          "originType": "TEMPERATURE"
        },
        {
          "timestamp": "2024-06-01T00:00:00.000Z",
          "id": "evt_002",
          "userId": "usr_003",
          "assetId": "asset_002",
          "dimension": "VALUE",
          "value": 38.24,
          "originId": "sens_002",
          "originType": "HUMIDITY"
        }
      ]
    }
}
```

### Example Response

```turtle
@prefix fhir: <http://hl7.org/fhir/> .
@prefix pharaon: <http://hultig.ubi.pt/ontology/> .

<http://hultig.ubi.pt/ontology/amicare/user/User2> a fhir:Patient ;
    fhir:hasRole "Carer1" ;
    fhir:Patient.identifier "User2" ;
    fhir:Practitioner "Carer1" ;
    fhir:Patient.name "John" ;
    fhir:HumanName.family "Doe" .
```

## Project Structure

- **src/main/java**: Contains the Java source code
    - **controller**: Contains the REST controller
    - **service**: Contains the service logic
    - **util**: Contains utility classes
- **src/main/resources**: Contains application resources
    - **jar**: Contains the RMLMapper JAR file
    - **rml**: Contains the RML mapping files

## Configuration

### RML Mapping

The RML mapping file should be placed in the `src/main/resources/rml` directory. The path to the RML mapping file should be specified when calling the API.

### Temporary Files

Temporary files for JSON input and RDF output are created in a temporary directory. This directory is cleaned up after each conversion.

## Error Handling

The application provides detailed error messages and logs for troubleshooting. If the RMLMapper process fails, an appropriate error message is returned.