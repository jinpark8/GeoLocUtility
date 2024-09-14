<a id="readme-top"></a>

# GeoLocUtility

An Open Weather Geocoding API utility that takes a city, state, or zip code and returns the latitude, longitude, place
name, and any other necessary information from the API.


<!-- GETTING STARTED -->
## Getting Started

Here are some instructions on setting up your project locally.
To get a local copy up and running follow these simple steps.


### Prerequisites

1. OpenJDK 22.0.2 - openjdk-22.0.2 - Get it at https://www.oracle.com/java/technologies/downloads/
2. JUnit 5 - org.junit.jupiter - Get it from Maven.
3. JSON - org.json - Get it from Maven.

Note:  You can also use IntelliJ IDE to install the above.


### Installation

1. Get IntelliJ IDEA Community Edition IDE at [https://www.jetbrains.com/idea/download](https://www.jetbrains.com/idea/download)
2. Clone the repo
   ```sh
   git clone https://github.com/jinpark8/GeoLocUtility.git
   ```
3. Install JUnit 5:
   - Open project in IntelliJ IDE
   - File -> Project Structure -> Project Settings -> Libraries
   - Click the "+" symbol
   - Select "From Maven..."
   - Type "org.junit.jupiter" in the search box
   - Select junit-jupiter 5.9.1 (or latest)

4. Install JSON:
   - Open project in IntelliJ IDE
   - File -> Project Structure -> Project Settings -> Libraries
   - Click the "+" symbol
   - Select "From Maven..."
   - Type "org.json" in the search box
   - Select org.json:20240303 (or latest)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->
## Usage

GeoLocUtility accepts an array of String args containing City and State or Zip Code.  Inputs will be given in the following formats:
- City and State: “Madison, WI”
- Zip Codes: “12345”

You can also input multiple arguments.  Some possible examples:
GeoLocUtility --locations “Madison, WI” “12345”
GeoLocUtility “Madison, WI” “12345” “Chicago, IL” “10001"

### IntelliJ IDE

You can build the project by selecting:  Build -> Rebuild Project
Once the project has successfully built, a GeoLocUtility.class file be generated in the /out/production/GeoLocUtility/ directory.
Run the utility by selecting the Play button next to the main function inside the GeoLocUtility.java file.


<img width="1736" alt="Screenshot 2024-09-14 at 7 07 41 AM" src="https://github.com/user-attachments/assets/d50a7e48-f1b8-49cc-af68-2c4239253dd4">


Note:  Before running the utility, you may want to change the arguments passed in.
You will need to select "Modify Run Configuration..." and add your arguments in the box labeled Program arguments:

<img width="651" alt="Screenshot 2024-09-14 at 6 55 14 AM" src="https://github.com/user-attachments/assets/b5459032-3bfe-46bc-ace6-2e21fd615d9f">



### Command-Line
**You must first build and output the GeoLocUtility.class file.**


Navigate to "GeoLocUtility/out/production/GeoLocUtility/" and run the utility.  Here is an example:
   ```sh
   java GeoLocUtility "98109" "Salt Lake City, UT"
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## Testing

### IntelliJ IDE

Tests can be found in the files named GeoLocUtilityTests.java and GeoLocUtilityUsageErrorTests.java.
- You can run all tests by selecting the Play icon next to the test class name.
- You can run individual tests by selecting the Play icon next to each test function name.


<img width="1647" alt="Screenshot 2024-09-14 at 7 08 50 AM" src="https://github.com/user-attachments/assets/cf8e1b4e-d2f5-4cab-90a8-26d87cf30d3f">

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- CONTACT -->
## Contact

Jin Park - jinpark8@gmail.com

Project Link: [https://github.com/jinpark8/GeoLocUtility](https://github.com/jinpark8/GeoLocUtility)

<p align="right">(<a href="#readme-top">back to top</a>)</p>
