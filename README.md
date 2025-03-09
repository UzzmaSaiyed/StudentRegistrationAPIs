# Student Registration REST API

## Overview
This is a simple RESTful API for managing student records. It allows users to register students and retrieve student details using Java, Jakarta EE, and Jersey (JAX-RS). The API is designed to be deployed on a Jakarta EE-compliant server such as Apache Tomcat.

## Features
- Register a new student
- Retrieve student details by ID
- Uses in-memory storage (ConcurrentHashMap) for simplicity
- JSON-based request and response

## Technologies Used
- Java (Jakarta EE)
- Jersey (JAX-RS) for RESTful API
- Apache Tomcat as the application server
- JSON for data exchange

## Installation & Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/StudentRegistrationAPIs.git
   cd StudentRegistrationAPIs
   ```
2. Build the project using Maven:
   ```sh
   mvn clean package
   ```
3. Deploy the generated `.war` file to your Tomcat server:
   ```sh
   cp target/StudentRegistration.war $TOMCAT_HOME/webapps/
   ```
4. Start your Tomcat server and access the API at:
   ```sh
   http://localhost:8080/StudentRegistration
   ```

## API Endpoints

### 1. Register a Student
**Endpoint:** `POST api/students/registerStudent`

**Request Headers:**
```json
Content-Type: application/json
```

**Request Body:**
```json
{
  "id": "1",
  "name": "John Doe",
  "age": "22",
  "email": "john@example.com",
  "rollno": "12345",
  "course": "Computer Science"
}
```

**Response:**
```json
{
  "status": 201,
  "message": "Student registered successfully"
}
```

### 2. Get Student Details
**Endpoint:** `GET api/students/getStudent/{id}`

**Example Request:**
```sh
GET api/students/getStudent/1
```

**Response:**
```json
{
  "status": 200,
  "student": {
    "id": "1",
    "name": "John Doe",
    "age": "22",
    "email": "john@example.com",
    "rollno": "12345",
    "course": "Computer Science"
  }
}
```

## Error Handling
- **400 Bad Request** – Invalid input or missing fields
- **404 Not Found** – Student not found
- **500 Internal Server Error** – Unexpected server errors

## Contributing
Feel free to fork this repository, make improvements, and submit a pull request!

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author
**Uzzma Saiyed**

---
Happy Coding! 🚀

