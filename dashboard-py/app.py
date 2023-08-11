from flask import Flask, render_template, request, send_from_directory
import matplotlib.pyplot as plt
import os
import requests
import matplotlib
matplotlib.use('Agg')


app = Flask(__name__)
BASE_URL = "http://localhost:8080/"
PLOT_PATH = "static/plots"
os.makedirs(PLOT_PATH, exist_ok=True)


def fetch_data(endpoint, start_date, end_date):
    url = f"{BASE_URL}{endpoint}?start_date={start_date}&end_date={end_date}"
    response = requests.get(url)
    if response.status_code == 200:
        return response.json()
    else:
        print(f"Error fetching data from {endpoint}: {response.status_code}")
        return []


def plot_miss_distance(ax, data):
    asteroid_names = [d['name'] for d in data]
    distances = [d['distance'] / 1e7 for d in data]
    ax.barh(asteroid_names, distances, color='red')
    ax.set_xlabel('Miss Distance (10^7 km)')
    ax.set_title('Miss Distances of Asteroids')
    ax.invert_yaxis()


def plot_velocity(ax, data):
    asteroid_names = [d['name'] for d in data]
    velocities = [d['velocity'] for d in data]
    ax.barh(asteroid_names, velocities, color='blue')
    ax.set_xlabel('Relative Velocity (km/s)')
    ax.set_title('Relative Velocities of Asteroids')
    ax.invert_yaxis()


def plot_diameter_range(ax, data):
    min_diameter = data['estimated_diameter_min']
    max_diameter = data['estimated_diameter_max']

    # Using logarithmic scale for y-axis for better visualization
    ax.set_yscale('log')

    # Adjust the bars to account for the logarithmic scale
    ax.bar(0, max_diameter, color='yellow',
           label='Max Diameter', align='center', width=0.4)
    ax.bar(0, min_diameter, color='green',
           label='Min Diameter', align='center', width=0.4)

    ax.set_ylabel('Diameter (km, log scale)')
    ax.set_title('Estimated Diameter of Asteroid (Min/Max)')
    ax.legend()
    ax.set_xlim(-1, 1)
    ax.set_xticks([])

    # Ensure small values are visible
    ax.set_ylim(bottom=0.001)


def plot_all_data(start_date, end_date):
    fig, axes = plt.subplots(3, 1, figsize=(10, 15))

    data = fetch_data("listMissDistanceJson", start_date, end_date)
    plot_miss_distance(axes[0], data)

    data = fetch_data("listRelativeVelocityJson", start_date, end_date)
    plot_velocity(axes[1], data)

    data = fetch_data("listMaxMinDiameterJson", start_date, end_date)
    plot_diameter_range(axes[2], data)

    plt.tight_layout()
    plt.show()


@app.route('/', methods=['GET'])
def index():
    return render_template('index.html')


@app.route('/plot', methods=['POST'])
def plot_data():
    start_date = request.form.get('start_date')
    end_date = request.form.get('end_date')

    # Plotting the data and saving the plot
    plot_all_data(start_date, end_date)
    plot_filename = os.path.join(
        PLOT_PATH, f"plot_{start_date}_to_{end_date}.png")
    plt.savefig(plot_filename)
    plt.close()

    return render_template('index.html', plot_url=plot_filename)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001, debug=True)
