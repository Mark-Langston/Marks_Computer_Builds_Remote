# Mark's Computer Builds

Mark's Computer Builds is a JavaFX application designed to manage computer build configurations. The application allows users to add, edit, and remove computer builds, with all data securely stored in a remote PostgreSQL database.

## Features

- Add, edit, and remove computer build configurations
- Store and retrieve data from a remote PostgreSQL database
- Secure user authentication

## Getting Started

These instructions will help you set up and run the application on your local machine.

### Prerequisites

- Java Development Kit (JDK) 22 or later
- Apache Maven
- PostgreSQL database

### Installation

1. **Clone the repository**
    ```sh
    git clone https://github.com/Mark-Langston/Marks_Computer_Builds_Remote.git
    cd Marks_Computer_Builds
    ```

2. **Configure the database connection**

    Create a `config.settings or config.properties` file in the `src/main/resources` directory with the following content:
    ```properties
    remote.db.url=jdbc:postgresql://your-database-url:port/your-database-name
    remote.db.user=your-database-username
    remote.db.password=your-database-password
    ```

3. **Build the project using Maven**
    ```sh
    mvn clean install
    ```

4. **Run the application**
    ```sh
    mvn javafx:run
    ```

### Usage

1. **Login Screen**

    - Use the credentials provided in the `config.properties` file.
    - Example: username: `admin`, password: `password123`

2. **Main Screen**

    - Add new computer builds using the "Add" button.
    - Edit existing builds by selecting a build and clicking the "Edit" button.
    - Remove builds by selecting a build and clicking the "Remove" button.

## Database Setup

Ensure you have a PostgreSQL database set up with the following schema:

```sql
CREATE TABLE MarksComputerBuilds (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    case_type TEXT,
    motherboard TEXT,
    cpu TEXT,
    cpu_cooler TEXT,
    ram TEXT,
    gpu TEXT,
    power_supply TEXT,
    mass_storage TEXT
);

CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

-- Insert a sample user for testing
INSERT INTO Users (username, password) VALUES ('admin', 'password123');
