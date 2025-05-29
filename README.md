
# 💳 Bank Management System

## ✨ Features

✅ Password Recovery – add 'Forgot Password' page with mock reset (no real email).  
✅ Profile Page – display user's name, email, and list of accounts.  
✅ Transaction Date Filter – filter transactions by date range.  
✅ Show Client Balance on Homepage – display total balance on login.  
~~Swagger API Documentation~~ → 🔁 **Replaced with PDF Export of Transactions**  ✅
✅ Minimum Deposit Restriction – disallow deposits < 100 KZT.  
✅ Error Alerts via Thymeleaf – show user-friendly error messages.  
✅ Role: Manager – new role with view-only access to client data.  
✅ Transaction Comment Field – save and display comments on transfers.  
✅ Monthly Spending Limit – notify user when spending exceeds set limit.  
✅ Client Name Search Filter – search clients by name.  
✅ User Account Blocking – admin disables user login via flag.  
✅ Operation Confirmation Popups – show confirmation dialog before deposit/withdrawal.  
✅ PDF Export – download transaction history as PDF.

---

## 🔧 Technologies Used

- Java 21, Spring Boot
- Spring Data JPA, MySQL
- Spring Security
- Thymeleaf
- Maven

---

## 🛠 Setup Instructions

1. **Clone the repository**:

```bash
git clone https://github.com/singulaaaris/bank-management-system.git
```

2. **Import into IntelliJ IDEA**:  
   `File > Open > Select the project folder`

3. **Configure the MySQL database**:

- Create a database:

```sql
CREATE DATABASE bank_db_new;
```

- In `application.properties`, ensure:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_db_new
spring.datasource.username=root
spring.datasource.password=1234
```

4. **Run the project**:  
   Запусти `BankApplication.java`

5. **Login credentials**:

- Зарегистрируйся через UI
- Для администратора: вручную вставь пользователя в БД с ролью `ROLE_ADMIN`

---

## 🧩 Database Schema

- **User**: `id`, `username`, `password`, `role`, `customer_id (FK)`
- **Customer**: `id`, `name`, `email`
- **Account**: `id`, `accountNumber`, `balance`, `customer_id (FK)`
- **Transaction**: `id`, `type`, `amount`, `timestamp`, `comment`, `account_id (FK)`

---

## 👥 Authors

**Group 8** – Team Project
