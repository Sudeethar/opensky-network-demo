# opensky-network-demo


### Prerequisites

- JDK 1.8
- Apache Maven 3.9.6
- Scala code runner version 2.13.13
- IntelliJ IDEA

### Getting Started
Once you have the prerequisites installed, you can clone this repository to your local machine:

    git clone https://github.com/Sudeethar/opensky-network-demo.git

NOTE : input sample sales data csv file can be found in "data" directory in root project



To build and run the project locally, simply open the project's root directory in the command line and execute the following Maven commands

Build project

     mvn clean install


Run project

     mvn scala:run -DmainClass=com.opensky.OpenSkyMain

After executing this command, you can find the CSV file with filtered data in the "output" folder located in the project directory.

#### References

- https://spark.apache.org/docs/
-  https://sparkbyexamples.com/
- Apache   Spark with Scala Course
  https://youtu.be/4kb5Ju3TJLs?si=CsE9JMw45rL7pQf4


