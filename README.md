# NASA NeoWs API Data Extractor

This project interacts with NASA's NeoWs (Near Earth Object Web Service) API to fetch and extract information about Near-Earth Objects. It provides users with insights into asteroids based on their diameter, relative velocity, and miss distances and showcases this data via a user-friendly interface.

## Features

- Fetches asteroid data within a specific date range.
- Extracts and presents the maximum and minimum diameters of asteroids.
- Displays the relative velocities of asteroids.
- Showcases the miss distances of asteroids.
- Provides a web interface for users to interactively view asteroid data.
- Docker support for easy deployment.

## Getting Started

### Prerequisites

- Java 11 or newer
- Maven
- Docker (if deploying using Docker)
- A valid NASA API key. Get it from [NASA's API portal](https://api.nasa.gov/)

### Installation

```bash
git clone https://github.com/GravityDarkLab/asteroids.git
cd nasa-neows-extractor
mvn clean install
```

## Using Docker Compose

1. Build and start the application:

```bash
docker-compose up --build
```

This command will build the Docker image using the configurations provided in `docker-compose.yml` and then run the container.

Now, you can access the application at `http://localhost:8080/`.

2. Stop the application:

If you wish to stop the application and the associated container, you can use:

```bash
docker-compose down
```

## Usage

1. Set up your NASA API key in `application.properties` or as an environment variable.
2. Run the application: 
```bash
mvn spring-boot:run
```
3. Navigate to `http://localhost:8080/` to access the user interface. Use the date selection to fetch data for a specific range.
4. For direct API access, use `http://localhost:8080/listRelativeVelocity?start_date=YYYY-MM-DD&end_date=YYYY-MM-DD` replacing `YYYY-MM-DD` with your desired date range.

## Using Postman or Other Services to Fetch Data

To retrieve asteroid-related data in JSON format, you can use the following endpoints:

1. **Miss Distance Data**:  
Endpoint: `/listMissDistanceJson`  
Usage: `http://localhost:8080/listMissDistanceJson?start_date=YYYY-MM-DD&end_date=YYYY-MM-DD`  
Example: `http://localhost:8080/listMissDistanceJson?start_date=2023-08-10&end_date=2023-08-11`

2. **Relative Velocity Data**:  
Endpoint: `/listRelativeVelocityJson`  
Usage: `http://localhost:8080/listRelativeVelocityJson?start_date=YYYY-MM-DD&end_date=YYYY-MM-DD`  
Example: `http://localhost:8080/listRelativeVelocityJson?start_date=2023-08-10&end_date=2023-08-11`

3. **Max and Min Diameter Data**:  
Endpoint: `/listMaxMinDiameterJson`  
Usage: `http://localhost:8080/listMaxMinDiameterJson?start_date=YYYY-MM-DD&end_date=YYYY-MM-DD`  
Example: `http://localhost:8080/listMaxMinDiameterJson?start_date=2023-08-10&end_date=2023-08-11`

Replace `YYYY-MM-DD` with the desired start and end date for which you want to retrieve asteroid-related data.

Remember, you can use tools like Postman or any service that allows HTTP requests to call these endpoints. Simply input the URL, set the request type to `GET`, and hit send!

## Web Interface

The application comes with an intuitive web interface that allows users to:

- **Select Date Range:** Specify the start and end date to fetch asteroid data.
- **View Data:** Displays the fetched asteroid data in a structured format.

## API Reference

This project utilizes the [NASA's NeoWs API](https://api.nasa.gov/). 

## License

Distributed under the Apache License. See `LICENSE` for more information.
