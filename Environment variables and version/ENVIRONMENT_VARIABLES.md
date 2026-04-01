# Environment Variables Documentation

## Overview
This document describes the environment setup, required tools, and versions used in this Movie Ticket Booking System.

---

## Technology Stack

### Programming Language
- **Java** - Version: 17 or higher
  - Download: https://www.oracle.com/java/technologies/downloads/

### Build Tool
- **Maven** - Version: 3.8.x or higher
  - Download: https://maven.apache.org/download.cgi
  - Maven is used to manage project dependencies and build the project.

### Database
- **MySQL** - Version: 8.0 or higher
  - This project uses MySQL as the database.
  - A cloud-based MySQL instance from Aiven is pre-configured.

### JDBC Driver
- **MySQL Connector/J** - Version: 8.0.33
  - Maven dependency configured in pom.xml
  - Required for Java to connect to MySQL database

---

## Project Structure

```
TicketBookingJavaProject/
├── .env                          # Environment variables file
├── pom.xml                       # Maven project configuration
├── src/
│   └── main/
│       ├── java/org/expleo/TicketBookingJavaProject/
│       │   ├── App.java          # Main application entry point
│       │   ├── config/           # Configuration classes
│       │   │   ├── DBConnection.java       # Database connection
│       │   │   └── DatabaseSetup.java     # Database schema setup
│       │   ├── controller/       # Controller layer (handles user input)
│       │   │   ├── AppController.java
│       │   │   ├── AuthController.java
│       │   │   ├── BookingController.java
│       │   │   ├── MovieController.java
│       │   │   ├── OfficerController.java
│       │   │   ├── SearchController.java
│       │   │   ├── SeatController.java
│       │   │   ├── SelectionController.java
│       │   │   ├── SuperAdminController.java
│       │   │   ├── TheatreAdminController.java
│       │   │   ├── UserController.java
│       │   │   └── PaymentController.java
│       │   ├── dao/             # Data Access Object interfaces
│       │   │   ├── MovieDAO.java
│       │   │   ├── UserDAO.java
│       │   │   ├── TheatreDAO.java
│       │   │   ├── BookingDAO.java
│       │   │   └── SeatDAO.java
│       │   ├── repository/      # Repository implementations
│       │   │   └── impl/
│       │   │       ├── MovieRepositoryImpl.java
│       │   │       ├── UserRepositoryImpl.java
│       │   │       ├── TheatreRepositoryImpl.java
│       │   │       ├── BookingRepositoryImpl.java
│       │   │       ├── SeatRepositoryImpl.java
│       │   │       └── PaymentRepositoryImpl.java
│       │   ├── service/         # Business logic layer
│       │   │   ├── MovieService.java
│       │   │   ├── BookingService.java
│       │   │   ├── SeatService.java
│       │   │   ├── PaymentService.java
│       │   │   ├── SearchService.java
│       │   │   ├── UserService.java
│       │   │   ├── TheatreService.java
│       │   │   └── SelectionService.java
│       │   ├── model/          # Data model classes
│       │   │   ├── Movie.java
│       │   │   ├── User.java
│       │   │   ├── Theatre.java
│       │   │   ├── Booking.java
│       │   │   ├── Seat.java
│       │   │   ├── Payment.java
│       │   │   ├── City.java
│       │   │   └── ...
│       │   ├── util/            # Utility classes
│       │   │   ├── InputUtil.java
│       │   │   ├── PaymentUtil.java
│       │   │   └── BookingIdGenerator.java
│       │   └── exception/      # Custom exceptions
│       │       ├── CustomException.java
│       │       ├── BookingNotFoundException.java
│       │       ├── PaymentErrorException.java
│       │       └── InvalidSeatSelectionException.java
│       └── resources/
└── README.md                     # Project documentation
```

---

## Environment Variables

### Database Configuration
| Variable | Description | Example Value |
|----------|-------------|---------------|
| `DB_URL` | MySQL connection URL | `mysql://host:port/database` |
| `DB_USER` | Database username | `avnadmin` |
| `DB_PASSWORD` | Database password | `your_password_here` |

---

## Database Schema

### Tables

#### 1. users
| Column | Type | Description |
|--------|------|-------------|
| user_id | INT | Primary key, auto-increment |
| name | VARCHAR(100) | User's full name |
| email | VARCHAR(100) | User email (unique) |
| phone | VARCHAR(15) | Phone number |
| password | VARCHAR(100) | User password |
| role | VARCHAR(50) | User role (Super Admin, Theatre Admin, Officer, Customer) |

#### 2. theatres
| Column | Type | Description |
|--------|------|-------------|
| id | INT | Primary key, auto-increment |
| name | VARCHAR(100) | Theatre name |
| city | VARCHAR(100) | City where theatre is located |
| adminId | INT | Foreign key to users table |

#### 3. movies
| Column | Type | Description |
|--------|------|-------------|
| id | VARCHAR(50) | Primary key (movie ID) |
| title | VARCHAR(150) | Movie title |
| genre | VARCHAR(50) | Movie genre |
| language | VARCHAR(50) | Movie language |
| duration | INT | Duration in minutes |
| releaseDate | VARCHAR(50) | Release date |
| theatre_id | INT | Foreign key to theatres table |

#### 4. seats
| Column | Type | Description |
|--------|------|-------------|
| seat_id | INT | Primary key, auto-increment |
| session_key | VARCHAR(100) | Session identifier (theatre_movie_showtime) |
| row_char | VARCHAR(5) | Row letter (A-J) |
| seat_number | INT | Seat number (1-10) |
| status | VARCHAR(30) | Seat status (AVAILABLE, BOOKED) |
| price | DOUBLE | Seat price |

#### 5. bookings
| Column | Type | Description |
|--------|------|-------------|
| booking_id | VARCHAR(50) | Primary key |
| user_id | VARCHAR(50) | User who made booking |
| movie_id | VARCHAR(50) | Movie ID |
| theatre_id | INT | Theatre ID |
| showtime | VARCHAR(50) | Show timing |
| seat_labels | TEXT | Comma-separated seat labels |
| total_amount | DOUBLE | Total booking amount |
| status | VARCHAR(50) | Booking status |

---

## How to Run the Project

### Prerequisites
1. Install Java JDK 17 or higher
2. Install Maven 3.8.x or higher
3. Ensure MySQL is accessible

### Steps
1. Clone or download the project
2. Navigate to project directory:
   ```bash
   cd TicketBookingJavaProject
   ```
3. Update `.env` file with your database credentials
4. Build the project:
   ```bash
   mvn clean compile
   ```
5. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="org.expleo.TicketBookingJavaProject.App"
   ```
   Or use an IDE like Eclipse/IntelliJ to run the App.java class

### Default Login Credentials
- **Email**: admin@gmail.com
- **Password**: admin123
- **Role**: Super Admin

---

## Maven Dependencies (from pom.xml)

```xml
<dependencies>
    <!-- JUnit Testing -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>3.8.1</version>
        <scope>test</scope>
    </dependency>
    
    <!-- MySQL JDBC Driver -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>
```

---

## User Roles and Permissions

### 1. Super Admin
- Create Theatre
- Create Theatre Admin
- Remove Theatre
- Remove Theatre Admin
- View/Update Profile

### 2. Theatre Admin
- Add Movie
- Update Movie
- Delete Movie
- Create Officer
- View Movie List
- View/Update Profile

### 3. Officer
- View Movie List
- Book Ticket
- Cancel Ticket

### 4. Customer
- Search Movie
- Book Ticket
- View Movie List
- Cancel Ticket
- View/Update Profile

### 5. Guest
- Search Movie
- View Movie List

---

## Features

1. **User Management**
   - Registration and Login
   - Role-based access control
   - Profile management

2. **Movie Management**
   - Add, Update, Delete movies
   - Search by name or language
   - View movie list

3. **Theatre Management**
   - Create/Remove theatres
   - Assign admins to theatres
   - City-based theatre listing

4. **Booking System**
   - Select city, theatre, movie, showtime
   - View seat layout
   - Select and book seats
   - Multiple payment options (Card, UPI, Cash)

5. **Payment Processing**
   - Card payment validation
   - UPI payment validation
   - Cash payment option

---

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Check if `.env` file exists and has correct credentials
   - Verify MySQL server is running
   - Check if database is accessible from your network

2. **Scanner/Input Issues**
   - Ensure you're entering valid integer values when prompted
   - Press Enter after each input

3. **Movie Not Found After Search**
   - Movies are associated with theatres
   - Ensure movies are added to a theatre first

---

## Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0.0 | Current | Initial release with all features |

---

## Contact

For any issues or questions, please refer to the project documentation or contact the development team.
