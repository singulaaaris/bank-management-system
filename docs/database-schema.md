
# 🧩 Database Schema – bank_db_new

## 📄 Tables and Columns

---

### 🧍‍♂️ user
| Column       | Type        | Description                      |
|--------------|-------------|----------------------------------|
| id           | BIGINT      | Primary key                      |
| username     | VARCHAR     | Unique username                  |
| password     | VARCHAR     | Encrypted password               |
| role         | VARCHAR     | User role (e.g., ROLE_USER)      |
| enabled      | BOOLEAN     | Is account enabled               |
| customer_id  | BIGINT      | FK → customer(id)                |

---

### 👤 customer
| Column   | Type    | Description         |
|----------|---------|---------------------|
| id       | BIGINT  | Primary key         |
| name     | VARCHAR | Full name           |
| email    | VARCHAR | Email address       |

---

### 🏦 account
| Column        | Type    | Description                     |
|---------------|---------|---------------------------------|
| id            | BIGINT  | Primary key                     |
| account_number| VARCHAR | Unique account number           |
| account_type  | VARCHAR | Type of account (e.g. savings)  |
| balance       | DOUBLE  | Current balance                 |
| customer_id   | BIGINT  | FK → customer(id)               |

---

### 💸 transaction
| Column         | Type     | Description                      |
|----------------|----------|----------------------------------|
| id             | BIGINT   | Primary key                      |
| transactionType| VARCHAR  | Deposit, Withdrawal, Transfer    |
| amount         | DOUBLE   | Transaction amount               |
| timestamp      | DATETIME | Date and time of transaction     |
| comment        | TEXT     | Optional comment (for transfers) |
| account_id     | BIGINT   | FK → account(id)                 |

---

## 🔗 Foreign Keys

- `user.customer_id` → `customer.id`
- `account.customer_id` → `customer.id`
- `transaction.account_id` → `account.id`

