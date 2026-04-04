# ⚡ Water and Electricity Billing Management System

A modern, robust, and secure management system designed for efficient tracking and billing of water and electricity usage. Featuring a premium glassmorphism UI, automated billing logic, and OTP-based secure registration.

---

## 🚀 Features

- **📊 Management Dashboard**: Real-time overview of readings, bills, and payments.
- **📱 Responsive UI**: Beautiful glassmorphism design that works on all devices.
- **🔐 Secure Authentication**: JWT-based login and OTP email verification for new users.
- **📜 Billing Logic**: Automated calculation of water and electricity bills based on meter readings.
- **💳 Payment Integration**: Track and manage customer payments securely.
- **📧 Email Notifications**: Automatic emails for OTP and billing updates.

## 🛠️ Technology Stack

- **Backend**: Java 25, Spring Boot 4.x
- **Database**: MySQL
- **Security**: Spring Security, JWT
- **Frontend**: Thymeleaf, Vanilla CSS (Glassmorphism)
- **Mailing**: Spring Boot Mail (SMTP)

---

## 💻 Local Setup

### Prerequisites
- JDK 25
- Maven 3.9+
- MySQL Server

### Installation Steps
1. **Clone the repository**:
   ```bash
   git clone https://github.com/Iihirwer/WaterAndElectricityBillingManagementSystem.git
   cd WaterAndElectricityBillingManagementSystem
   ```

2. **Setup Database**:
   - Create a database: `CREATE DATABASE water_electricity_db;`
   - Import the schema: `mysql -u root -p water_electricity_db < init_db.sql`

3. **Configure Environment**:
   - Update `src/main/resources/application.properties` with your MySQL credentials.

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```
   Access at `http://localhost:8080`

---

## 🌐 Deployment (Railway.app)

To host this project online:
1. Connect your GitHub repository to [Railway.app](https://railway.app/).
2. Add a **MySQL** database plugin to your project.
3. Configure the following **Environment Variables**:
   - `SPRING_DATASOURCE_URL`: `jdbc:mysql://${{MYSQL_HOST}}:${{MYSQL_PORT}}/${{MYSQL_DATABASE}}`
   - `SPRING_DATASOURCE_USERNAME`: `${{MYSQL_USER}}`
   - `SPRING_DATASOURCE_PASSWORD`: `${{MYSQL_PASSWORD}}`
   - `JWT_SECRET`: Your random 64-char string.
   - `MAIL_USERNAME`: Your SMTP email.
   - `MAIL_PASSWORD`: Your SMTP app password.

---

## 📝 License
This project is for educational purposes.
