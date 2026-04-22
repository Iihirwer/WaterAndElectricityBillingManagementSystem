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

---

## 🏗️ System Architecture

The application follows a standard **MVC (Model-View-Controller)** pattern:
- **Presentation Layer**: Thymeleaf templates with Vanilla CSS (Glassmorphism).
- **Control Layer**: Spring MVC Controllers handling routing and data binding.
- **Service Layer**: Business logic for billing, meter management, and notifications.
- **Persistence Layer**: Spring Data JPA with MySQL/H2 support.
- **Security**: Spring Security with JWT (JSON Web Tokens) for API protection and Session-based auth for MVC.

---

## 💻 Local Setup

### Prerequisites
- JDK 25
- Maven 3.9+
- MySQL Server or Docker

### Installation Steps
1. **Clone the repository**:
   ```bash
   git clone https://github.com/Iihirwer/WaterAndElectricityBillingManagementSystem.git
   cd WaterAndElectricityBillingManagementSystem
   ```

2. **Run with Maven**:
   ```bash
   mvn spring-boot:run
   ```
   Access at `http://localhost:8080`

3. **Run with Docker**:
   ```bash
   docker build -t water-electricity-system .
   docker run -p 8080:8080 water-electricity-system
   ```

---

## 🌐 Deployment (Free Hosting)

### Deploy to Render.com
1. Create a free account on [Render](https://render.com/).
2. Click **New +** > **Blueprint**.
3. Connect this GitHub repository.
4. Render will automatically detect `render.yaml` and configure the project.
5. Your app will be live at `https://water-electricity-system-xxxx.onrender.com`.

---

## 📄 Documentation & Reports
- [PHASE_9_INTEGRATION_REPORT.md](PHASE_9_INTEGRATION_REPORT.md)
- [PHASE_11_COMPLETION_REPORT.md](PHASE_11_COMPLETION_REPORT.md)

---

## 📝 License
This project is for educational purposes.
