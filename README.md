# NASA NeoWs API Data Extractor

This project interacts with NASA's NeoWs (Near Earth Object Web Service) API to fetch and extract information about Near-Earth Objects. It provides users with insights into asteroids based on their diameter, relative velocity, and miss distances and showcases this data via a user-friendly interface.

## Features

- Fetches asteroid data within a specific date range.
- Extracts and presents the maximum and minimum diameters of asteroids.
- Displays the relative velocities of asteroids.
- Showcases the miss distances of asteroids.
- Provides a web interface for users to interactively view asteroid data.

## Getting Started

### Prerequisites

- Java 11 or newer
- Maven
- A valid NASA API key. Get it from [NASA's API portal](https://api.nasa.gov/)

### Installation

```bash
git clone https://github.com/GravityDarkLab/asteroids.git
cd nasa-neows-extractor
mvn clean install
```

## Usage

1. Set up your NASA API key in `application.properties` or as an environment variable.
2. Run the application: 
```bash
mvn spring-boot:run
```
3. Navigate to `http://localhost:8080/` to access the user interface. Use the date selection to fetch data for a specific range.
4. For direct API access, use `http://localhost:8080/listRelativeVelocity?start_date=YYYY-MM-DD&end_date=YYYY-MM-DD` replacing `YYYY-MM-DD` with your desired date range.

## Web Interface

The application comes with an intuitive web interface that allows users to:

- **Select Date Range:** Specify the start and end date to fetch asteroid data.
- **View Data:** Displays the fetched asteroid data in a structured format.

## API Reference

This project utilizes the [NASA's NeoWs API](https://api.nasa.gov/). 

## License

Distributed under the Apache License. See `LICENSE` for more information.
