

# 📅 Student Appointment Booking System

A **web-based appointment booking system** built using **Spring Boot**, **Thymeleaf**, and **MySQL**.
This application allows **students to book, view, and manage appointments** with ease through a simple and user-friendly interface.

---

## 🚀 Features

* 👨‍🎓 Student registration & login
* 📅 Book appointments easily
* ⏰ Select preferred date & time
* 📋 View booked appointments
* ❌ Cancel appointments
* 🔐 Secure authentication & authorization
* 🧩 MVC-based clean architecture
* 🖥️ Server-side rendering using Thymeleaf

---

## 🛠️ Tech Stack

* **Backend:** Spring Boot
* **Frontend:** Thymeleaf, HTML, CSS, Bootstrap
* **Database:** MySQL
* **ORM:** Spring Data JPA (Hibernate)
* **Security:** Spring Security (optional)
* **Build Tool:** Maven
* **Server:** Embedded Tomcat

---

## 📂 Project Structure

```
src/main/java
 └── com.example.appointment
      ├── controller
      ├── service
      ├── repository
      ├── model
      └── AppointmentApplication.java

src/main/resources
 ├── templates
 │    ├── book-appointment.html
 │    ├── appointments.html
 │    └── dashboard.html
 ├── static
 │    ├── css
 │    └── js
 └── application.properties
```

---

## ⚙️ Installation & Setup

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/your-username/student-appointment-booking.git
```

### 2️⃣ Configure MySQL Database

Create a database in MySQL:

```sql
CREATE DATABASE student_appointment_db;
```

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_appointment_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false
```

---

### 3️⃣ Run the Application

```bash
mvn spring-boot:run
```

Access the app at:

```
http://localhost:8080
```

---



* Login / Register Page
* Student Dashboard
* Appointment Booking Page
* Appointment List Page

---

## 🎯 Future Enhancements

* Admin panel for appointment management
* Email notifications for bookings
* Appointment approval workflow
* Pagination & search
* Role-based access (Student / Admin)

---

## 👨‍💻 Developer

**Ajay Yadav**
Java & Spring Boot Developer

* Spring Boot | Hibernate | Thymeleaf | MySQL
* Docker | Git | Linux

---
