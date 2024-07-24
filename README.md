# Mark's Computer Builds - Remote

Mark's Computer Builds is a JavaFX application that allows users to manage and organize their computer builds. The application integrates with a remote PostgreSQL database to store and retrieve build information.

## Features

- Add, edit, and remove computer builds
- Secure login system
- Integration with a remote PostgreSQL database

## Video Demonstration

Watch a short demo of the project:

[![Mark's Computer Builds - Demo](https://img.youtube.com/vi/warFaJIbG7M/0.jpg)](https://youtu.be/warFaJIbG7M)

## Getting Started

### Prerequisites

- Java JDK 21
- PostgreSQL database
- Maven

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/Mark-Langston/Marks_Computer_Builds_Remote.git
    cd Marks_Computer_Builds_Remote
    ```

2. Set up the PostgreSQL database:
    - Create a new database and two tables using the following SQL commands:

    ```sql
    CREATE TABLE markscomputerbuilds (
        title TEXT PRIMARY KEY,
        case_type TEXT,
        motherboard TEXT,
        cpu TEXT,
        cpu_cooler TEXT,
        ram TEXT,
        gpu TEXT,
        power_supply TEXT,
        mass_storage TEXT
    );

    CREATE TABLE users (
        username TEXT PRIMARY KEY,
        password TEXT NOT NULL
    );

    -- Insert a sample user for testing
    INSERT INTO users (username, password) VALUES ('admin', '12345');
    ```

3. Update the `config.properties` file with your database credentials:
    ```properties
    remote.db.url=jdbc:postgresql://your_database_url:port/your_database_name
    remote.db.user=your_database_user
    remote.db.password=your_database_password
    ```

4. Build and run the application using Maven:
    ```sh
    mvn clean install
    mvn javafx:run
    ```

## Usage

1. Run the application.
2. Log in using the username and password set up in the PostgreSQL database.
3. Use the interface to add, edit, and remove computer builds.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.
