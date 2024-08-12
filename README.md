## API Testing Project

This project is designed to test the JSONPlaceholder API using Kotlin, Rest-Assured, JUnit 5, and
Allure for reporting.

## Setup

- Clone the repository:

 ```bash
git clone <your-repo-url>
cd <your-repo-directory>
 ```

- Create a .env file in the root directory of the project with the following content:

```plaintext
URI=https://jsonplaceholder.typicode.com
```

## Running Tests

- All Tests: Run all tests using the AllIntegratedTest class.

 ```bash
./gradlew test --tests "AllIntegratedTest"
```

- Specific Test: Run tests from the ItemsTests class.

 ```bash
./gradlew test --tests "tests.ItemsTests"
```

## Generate Allure Report:

- After running the tests, generate the Allure report using:

 ```bash
./gradlew allureServe
```

This will start a web server where you can view the test report.

## Project Structure

- **requests package**: Contains request classes and endpoints.
- **tests package**: Contains test classes. The main test classes are AllIntegratedTest and
  ItemsTests.
- **core package**: Contains setup configurations and utilities for the tests.

## Dependencies

- Gradle
- Kotlin
- Rest-Assured
- JUnit 5
