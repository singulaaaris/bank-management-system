
# 💳 Bank Management System

## ✨ Features

1) Password Recovery – added 'Forgot Password' page with mock reset (no real email). ✅
2) Profile Page – displays user's name, email, and list of accounts.  ✅
3) Transaction Date Filter – filter transactions by date range.  ✅
4) Show Client Balance on Homepage – display total balance upon login.  
5) ~~Swagger API Documentation~~ → 🔁 **Replaced with PDF Export of Transactions.** ✅
6) Minimum Deposit Restriction – disallows deposits below 100 KZT.  ✅
7) Error Alerts via Thymeleaf – user-friendly error messages.  ✅
8) Role: Manager – new role with view-only access to client data. ✅ 
9) Transaction Comment Field – saves and displays comments for transfers. ✅  
10) Monthly Spending Limit – notifies user when spending exceeds the limit.  ✅
11) Client Name Search Filter – search for clients by name.  ✅
12) User Account Blocking – admin disables login via a toggle.  ✅
13) Operation Confirmation Popups – confirmation dialogs before deposits/withdrawals.  ✅
14) PDF Export – download transaction history as a PDF file. ✅

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
   Go to `File > Open > Select the project folder`

3. **Configure the MySQL database**:

- Create the database:

```sql
CREATE DATABASE bank_db_new;
```

- In `application.properties`, make sure to set:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_db_new
spring.datasource.username=root
spring.datasource.password=1234
```

4. **Run the project**:  
   Run `BankApplication.java`

5. **Login credentials**:

- Register through the UI
- For admin: manually insert a user into the DB with role `ROLE_ADMIN`

---

## 🧩 Database Schema

- **User**: `id`, `username`, `password`, `role`, `customer_id (FK)`
- **Customer**: `id`, `name`, `email`
- **Account**: `id`, `accountNumber`, `balance`, `customer_id (FK)`
- **Transaction**: `id`, `type`, `amount`, `timestamp`, `comment`, `account_id (FK)`

---

## 👥 Authors

**Group 8** – Team Project
