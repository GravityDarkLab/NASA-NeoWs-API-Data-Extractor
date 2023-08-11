# NASA NeoWs API Data Extractor Dashboard

This project visualizes data fetched from [NASA-NeoWs-API-Data-Extractor](https://github.com/GravityDarkLab/NASA-NeoWs-API-Data-Extractor) , showing information about asteroids, their relative velocities, miss distances, and estimated diameters.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Conda**: This project uses Conda to manage environments and dependencies. If you haven't installed Conda, you can do so by following the instructions for [Miniconda](https://docs.conda.io/en/latest/miniconda.html) (a minimal Conda installer) or [Anaconda](https://www.anaconda.com/products/distribution) (includes Conda, Python, and many scientific libraries).

## Installation & Setup

1. **Clone the repository**:

   ```bash
   git clone https://github.com/GravityDarkLab/asteroids.git
   cd nasa-neows-extractor
   ```

2. **Run the server**:

   - Download the server from [here](https://drive.google.com/drive/folders/1EazGOFSEMiiH5TRHsR7EyJZPu8RAqT2r?usp=sharing) and follow the provided instructions.
   - Optional: Follow the steps [here](https://github.com/GravityDarkLab/NASA-NeoWs-API-Data-Extractor).
     
3. **Create and activate a Conda environment**:

   ```bash
   conda create --name neows-env python=3.8
   conda activate neows-env
   ```
4. **Install the required packages**:

   ```bash
   pip install -r requirements.txt
   ```
   
5. **Set up the Flask environment variables**:

   On **Windows**:

   ```bash
   set FLASK_APP=app.py
   set FLASK_ENV=development
   ```

   On **macOS/Linux**:

   ```bash
   export FLASK_APP=app.py
   export FLASK_ENV=development
   ```

6. **Run the Flask application**:

   ```bash
   flask run
   ```

7. Open a web browser and navigate to `http://127.0.0.1:5000/` to view the application.

## Usage

1. On the main page, select your desired start and end dates.
2. Click the "Plot Data" button.
3. View the plotted data related to asteroids for the selected date range.

## License

This project is licensed under the MIT License.
