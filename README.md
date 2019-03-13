# coordinateConversionService
Creates a service to wrap the geoSpatialConversion wrapper which interfaces with GeoTrans libraries to provide coordinate conversion and datum transformation.

## setup

### build environment
- Make sure you have the latest stable Java SE JDK installed (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- Make sure you have the latest version of gradle loaded on your system, and it's bin directory available in your path. (https://gradle.org/install/)
- Make sure you have the geoSpatialConversion jar file available for build integration (https://gitlab.gs.mil/)
- Make sure you have the GeoTrans jar file available for build integration.
- Edit the `build.gradle` file to update the repositories{} section to include the location(s) of the geoSpatialConversion and GeoTrans jar files.
- Open a Terminal window at the git root location (i.e. the same level as the `build.gradle` above).
- Make sure you have latest code `git pull` (This requires a git client to be installed, e.g. https://git-scm.com/)
- Create gradle wrapper for the project. `gradle wrapper`


### execution environment
- Make sure you have the latest stable Java SE Runtime installed (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- Make sure you have the GeoTrans program installed locally. Both the 64-bit Linux or 64-bit Windows build are supported. (http://earth-info.nga.mil/GandG/update/index.php) (Note: *The GeoTrans download and documentation can be found under the Apps part of the Apps & Services section*)
- The GeoTrans libraries must be available in the `PATH` (Windows) or `LD_LIBRARY_PATH` (Linux).
- The environment variable `MSPCCS_DATA` must be set to the full path to the data folder of the GeoTrans install.

## build
- Open a Terminal to the base location of the project (where gradlew is located)
- Run the build `.\gradlew build`
- Resulting output will be located in `build\libs\` directory.

## execution
- Open a Terminal, ensure the environment variable `MSPCCS_DATA` is set.
- Ensure the libraries for GeoTrans are available in the `PATH` (Windows) or `LD_LIBRARY_PATH` (Linux) variables.
- Run the executable .war file to start the coordinateConversion web service. `java -jar gs-rest-service-2.0.1.war`
- The service will run until it is terminated.  Use `Ctrl-C` in the terminal to stop the running service.

## REST local server on port 8080
- To test or use the coordinateConversionService, use a restful client to point to `http://localhost:8080/{apiPrefix}`

| Endpoint | Type | Description |
| ------ | ------ | ------ |
| /coordinateTypes | GET | Returns JSON containing the available coordinate types |
| /datums | GET | Returns JSON containing the available datums |
| /doConversion | POST | Returns JSON containing the converted coordinates and ODS.Processors structure with status, version, and timestamp. |
| /doTranslation | POST | Returns JSON containing the translated coordinates and ODS.Processors structure with status, version, and timestamp. |
| /ellipsoids | GET | Returns JSON containing the available ellipsoids |
| /sourceCoordinateInputByType | GET | Returns JSON containing the required sourceCoordinate input fields by coordinate type |
| /health | GET | Returns HTTP 200 status and JSON containing status, version and timestamp strings if the service is reachable |

## development notes
- Git is rooted at the same level as this README.md file. To perform git commands properly against this repo you should execute those commands from that level e.g. `user/path/coordinateConverstionService/:-> git pull`
- If you have any pending changes on your local machine and want to pull latest, you must stash or discard these changes before pulling. The easiest command in git to use is `git stash`. There are a variety of optional arguments to this command depending on what you want to do.
- The build must be able to reach either the MavenCenral() repository or you define a path to your own repo which contains the required dependencies listed in the build.gradle for this project.

## stack
- Spring Boot (https://spring.io/): Framework for building the Java RESTful interface.
- geoSpatialConversion (https://gitlab.gs.mil/): Java wrapper for the GeoTrans native code application.
- GeoTrans (http://earth-info.nga.mil/GandG/update/index.php): The NGA and DOD approved coordinate converter and datum translator. Both 64-bit Linux and Windows versions of GeoTrans are supported.


