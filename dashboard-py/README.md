# NASA NeoWs API Data Extractor Dashboard

This project visualizes data fetched from .... , showing information about asteroids, their relative velocities, miss distances, and estimated diameters.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Conda**: This project uses Conda to manage environments and dependencies. If you haven't installed Conda, you can do so by following the instructions for [Miniconda](https://docs.conda.io/en/latest/miniconda.html) (a minimal Conda installer) or [Anaconda](https://www.anaconda.com/products/distribution) (includes Conda, Python, and many scientific libraries).

## Installation & Setup

1. **Clone the repository**:

   ```bash
   git clone [YOUR REPOSITORY URL]
   cd [YOUR REPOSITORY DIRECTORY]
   ```

2. **Create and activate a Conda environment**:

   ```bash
   conda create --name neows-env python=3.8
   conda activate neows-env
   ```

3. **Install the required packages**:

   ```bash
   pip install -r requirements.txt
   ```

4. **Set up the Flask environment variables**:

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

5. **Run the Flask application**:

   ```bash
   flask run
   ```

6. Open a web browser and navigate to `http://127.0.0.1:5000/` to view the application.

## Usage

1. On the main page, select your desired start and end dates.
2. Click the "Plot Data" button.
3. View the plotted data related to asteroids for the selected date range.

## License

This project is licensed under the MIT License.

---

Again, make sure to replace `[YOUR REPOSITORY URL]` and `[YOUR REPOSITORY DIRECTORY]` with the appropriate values related to your project.

With this, your `README.md` provides a clear guide for users on how to set up and run your project locally using Conda.
