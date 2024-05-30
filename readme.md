## Blood Request Analysis with MapReduce

This document outlines the functionalities and usage instructions for the Blood Request Analysis with MapReduce project.

### Project Description

This repository provides Java code for a MapReduce program that analyzes blood request data from a hospital chain. The program helps identify:

Minimum and maximum blood type requests by year (part a)
City with the maximum total blood requests in a year (part b)
Prerequisites

Apache Hadoop environment set up and configured
Data Format

The program expects the blood request data in a CSV file with the following structure:

| Column Name | Description |
|---|---|
| request_key | Unique identifier for each blood request |
| city | City where the blood request originated |
| blood_type | Blood type (A+, A-, B+, etc.) |
| rh_factor | Rh factor (+ or -) |
| amount_ml | Blood amount requested (in milliliters) |
| year | Year the blood request was made |


Export to Sheets
Running the Program

### Generate Data (Optional)
The code includes a data faker (BloodRequestDataFaker.java) that generates sample data. You can adjust the number of requests and years in the main method. Run this class to create a CSV file (e.g., blood_requests.csv).

### Compile the Code
Use a Java compiler (e.g., javac) to compile the source files:

javac -cp /path/to/hadoop-core.jar BloodRequestMR.java BloodRequestMapper.java BloodRequestReducer.java BloodRequestWritable.java
Run the MapReduce Job
Use the hadoop jar command to run the MapReduce job, specifying the input and output paths (replace with your actual file paths):

hadoop jar BloodRequestMR.jar BloodRequestMR blood_requests.csv blood_request_results
Output

The program generates a text file containing the results in the specified output directory (e.g., blood_request_results). The output format displays information for each year-blood type combination:

Year-Blood Type: YYYY-BloodType
Min Request: amount ml (City: city name, Rh: rh factor)
Max Request: amount ml (City: city name, Rh: rh factor)
Note:

This code assumes the data is stored in a single CSV file. You may need to modify it for distributed storage on HDFS.
Further Enhancements

Extend the program to analyze additional aspects of the data, such as average blood request per city.
Visualize the results using libraries like Matplotlib or Plotly.






