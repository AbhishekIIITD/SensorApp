Orientation Sensor Data Analysis App
This app is designed to collect orientation sensor data from an Android smartphone, store it in a database, export it to a computer, and perform predictive analysis on the collected data.

Features
1. Using the SensorManager to Collect Data
The app utilizes the Android SensorManager to collect orientation sensor data (pitch, roll, yaw) from the smartphone.

2. Proper Creation of Two Activities
The app is divided into two main activities:

MainActivity: Collects and displays real-time orientation sensor data.
DataAnalysisActivity: Performs predictive analysis on the collected data and displays the results.
3. Creation of Database and Schema
The app uses SQLite to create a database with the following schema:

ID: Unique identifier for each data entry.
Pitch: Pitch angle.
Roll: Roll angle.
Yaw: Yaw angle.
Timestamp: Timestamp of data collection.
4. Storage of Data in Database
Collected sensor data is stored in the SQLite database with the help of a DBHelper class that handles database operations.

5. Export of Data from Smartphone to Computer
The app provides functionality to export the stored sensor data from the smartphone to a computer via CSV format.

6. Use of Prediction and Plotting of Accuracy
The DataAnalysisActivity uses a machine learning model to predict the next 10 values for pitch, roll, and yaw based on the collected data. It also plots the predicted vs actual values and calculates the Mean Absolute Error (MAE) to evaluate the accuracy of predictions.

7. Change of Sensing Intervals
The app allows users to change the sensing intervals for data collection, providing flexibility in data collection frequency.

8. Working App with GitHub Repos
This GitHub repository contains the complete code for the app, including the Android project and Python scripts for data analysis and prediction.

Requirements
Android smartphone with orientation sensors
Android Studio
Python (for data analysis and prediction)
Setup
Clone this repository.
Open the Android project in Android Studio and build the app.
Install the app on your Android smartphone.
Run the Python scripts for data analysis and prediction on your computer.
Future Improvements
Add more sensor data collection options.
Improve prediction accuracy using advanced machine learning techniques.
Enhance user interface and user experience.
