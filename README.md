# Successful Referral API  

## Project Overview  
This project implements a referral-based user signup system where users can register with or without a referral code. A referral is considered successful only when both the referrer and the referred user complete their profiles.

## Technologies Used  
- **Spring Boot** - Backend framework  
- **MySQL** - Database  
- **JUnit & Mockito** - Testing  
- **Postman** - API testing  

## Features  
✅ User signup with or without a referral code  
✅ Unique referral code generation  
✅ Referral validation and tracking  
✅ Referral marked successful only when both profiles are completed  
✅ API for referral report generation in CSV format  
✅ Unit tests for core functionalities  

---

## 🚀 Getting Started

### 1️⃣ Clone the Repository

```sh
git clone https://github.com/NikhilSingh2310/successful-referral-api.git
cd successful-referral-api
```
### 2️⃣ Configure MySQL Database
Create a database named successful_referral

Update src/main/resources/application.properties with MySQL credentials:

properties
```sh
spring.datasource.url=jdbc:mysql://localhost:3306/successful_referral
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```
### 3️⃣ Build & Run the Application
```sh
mvn clean install
mvn spring-boot:run
```
## 📌 API Endpoints
### 1️⃣ User Signup
Registers a new user with an optional referral code.
```sh
curl -X POST "http://localhost:8080/api/users/signup" \
     -H "Content-Type: application/json" \
     -d '{"name":"John Doe", "email":"john@example.com", "password":"password123", "referralCode":"abc12345"}'
```
### 2️⃣ Complete Profile
Marks the user’s profile as complete.

```sh
curl -X POST "http://localhost:8080/api/users/profile/complete" \
     -H "Content-Type: application/json" \
     -d '{"userId": 1}'
```
### 3️⃣ Get Referrals by Referrer
Retrieves all users referred by a specific referrer.
```sh
curl -X GET "http://localhost:8080/api/users/1/referrals"
```
### 4️⃣ Generate Referral Report (CSV Download)
Generates a CSV report of all referrals.
```sh
curl -X GET "http://localhost:8080/api/referrals/report" -o referral_report.csv
```
## 📌 Deployment Notes
Deployment Status: Not deployed yet.

Next Steps: Exploring deployment options on Render or AWS.

Learning Approach: Gaining experience in cloud deployment and best practices.

## 🔍 Testing
### ✅ Unit Tests
Unit tests validate core functionalities such as signup, profile completion, and referral tracking.
```sh
mvn test
```
**🧪 Tools Used**
JUnit & Mockito for unit testing.

Postman for API testing.

🚀 Future Enhancements
🔒 Security Improvements (Password hashing, authentication)

📊 More Reports & Analytics

⚡ Improved Performance with Caching

👤 Author
Nikhil Singh
GitHub: NikhilSingh2310
