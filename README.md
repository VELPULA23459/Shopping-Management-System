# Shopping Management System (Java + JDBC + MySQL)

## Project Overview
The Shopping Management System is a console-based Java application developed using **Java, JDBC, and MySQL**.  
This project allows users to manage customers, place orders, view order details, and process payments.

It demonstrates how Java applications interact with a database using **JDBC connectivity**.

---

## Features
- Add new customers
- Place orders for products
- View order details
- Process payments
- Menu-driven console application

---

## Technologies Used
- Java
- JDBC (Java Database Connectivity)
- MySQL
- Eclipse IDE

---

## Database Structure

### Customers Table
Stores customer details.

| Column | Type |
|------|------|
| id | int |
| name | varchar |
| email | varchar |

### Products Table
Stores product details.

| Column | Type |
|------|------|
| id | int |
| product_name | varchar |
| price | int |

### Orders Table
Stores order information.

| Column | Type |
|------|------|
| order_id | int |
| customer_id | int |
| product_id | int |

### Payments Table
Stores payment details.

| Column | Type |
|------|------|
| payment_id | int |
| order_id | int |
| amount | int |
| payment_method | varchar |

---

## Sample Menu


---

## How to Run the Project

1. Install **Java JDK**
2. Install **MySQL**
3. Create the database and tables using the SQL script
4. Open the project in **Eclipse IDE**
5. Add MySQL Connector JAR file
6. Run `shopping.java`

---

## Learning Outcomes

This project helped in understanding:

- JDBC database connectivity
- SQL operations (INSERT, SELECT)
- PreparedStatement usage
- SQL Joins
- Java console applications

---

## Author

**Velpula Himaja**  
B.Tech ECE  
Aspiring Java Full Stack Developer
