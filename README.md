#  Shopping Management System (Java + JDBC + MySQL)

##  Project Overview

The Shopping Management System is a console-based Java application developed using **Core Java, JDBC, and MySQL**.
This project allows users to manage customers, place orders, view order details, and process payments.

It demonstrates how Java applications interact with databases using JDBC connectivity.

---

##  Technologies Used

* Java (Core Java)
* JDBC (Java Database Connectivity)
* MySQL
* Eclipse IDE

---

##  Features

*  Add new customers
*  View products
*  Place orders using customer name and email
*  View orders (customer-wise)
*  Make payments (prevents duplicate payments)
*  View full customer details with order & payment status

---

##  Database Structure

The system uses the following tables:

* Customers
* Products
* Orders
* Payments

---

##  How to Run

###  Setup Database

* Open MySQL
* Run the SQL script from `Project.sql`

###  Configure Database

Update in Java code:
url = jdbc:mysql://localhost:3306/ShoppingSystem
username = root
password = your_password

###  Run Program

* Open in Eclipse
* Run `ShoppingManagementSystem.java`

---

##  Sample Data

* Laptop – ₹50000
* Mobile – ₹20000
* Headphones – ₹2000

---

##  Key Concepts

* JDBC Connectivity
* PreparedStatement
* ResultSet Handling
* Exception Handling
* SQL Joins & Constraints
* Menu-driven application

---

##  Future Enhancements

* GUI using Java Swing
* Admin login
* Bill generation
* Search functionality

---

##  Author

Velpula Himaja
