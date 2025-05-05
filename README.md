# 🔐 Expense Tracker

A secure and scalable authentication backend built with **Java Spring Boot**, featuring **JWT-based authentication**, **BCrypt password hashing**, **PostgreSQL integration**, and custom **exception handling**. Ideal for modern web and mobile applications.


---

## 🛠️ Tech Stack

<p align="left">
  <img src="https://www.vectorlogo.zone/logos/java/java-icon.svg" height="60"/>
  <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" height="60"/>
  <img src="https://www.vectorlogo.zone/logos/postgresql/postgresql-icon.svg" height="60"/>
  <img src="https://svgmix.com/uploads/44dd3e-jwt-icon.svg" height="60"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/intellij/intellij-original.svg" height="60"/>
</p>


---

## 🚀 Features

- ✅ User registration and login via RESTful APIs
- 🔐 Secure password hashing using BCrypt
- 🛡️ JWT token generation & validation
- 🧪 Unit-tested authentication flow
- 📂 Layered architecture (Controller → Service → Repository)
- ⚠️ Custom exception classes for clear error handling
- 🕐 Brute force protection with account timeout

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com.expenses.expensetracker/
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── filters/
│   │       └── exceptions/
│   └── resources/
│       └── application.properties
└── test/
```

---

## 📦 Setup Instructions

1. **Clone the repo**  
   `git clone https://github.com/your-username/expenseTracker.git`

2. **Configure database** in `application.properties` - set all necessary environment variables

3. Create a Spring service Java class to configure your API Key for JWT Authentication. 

3. **Run the app**  
   `mvn spring-boot:run` or run from your IDE

4. **Test API endpoints**  
   Use Postman or curl to test `/api/users/register` and `/api/users/login`

---