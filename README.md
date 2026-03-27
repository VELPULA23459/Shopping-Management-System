# Shopping Management System (Java + JDBC + MySQL)

## Project Overview

The Shopping Management System is a console-based Java application developed using **Core Java, JDBC, and MySQL**.
This project allows users to manage customers, place orders, view order details, and process payments.

It demonstrates how Java applications interact with databases using JDBC connectivity with proper validation and secure query handling.

---

## Technologies Used

* Java (Core Java)
* JDBC (Java Database Connectivity)
* MySQL
* Eclipse IDE

---

## Features

* Add new customers
* View products
* Place orders using customer name and email
* View orders (customer-wise)
* Make payments (prevents duplicate payments)
* View full customer details with order & payment status
* Input validation for better user experience
* Secure database operations using PreparedStatement

---

## Database Structure

The system uses the following tables:

* Customers
* Products
* Orders
* Payments

---

## How to Run

### Setup Database

* Open MySQL
* Run the SQL script from `Project.sql`

### Configure Database

Update in Java code:

url = jdbc:mysql://localhost:3306/ShoppingSystem
username = root
password = your_password

### Run Program

* Open in Eclipse
* Run `ShoppingManagementSystem.java`

---

## Sample Output

===== SHOP MENU =====

1. Add Customer
2. View Products
3. Place Order
4. View Orders (Customer Wise)
5. Make Payment
6. Customer Full Details
7. Exit

Database Connected Successfully

--- View Products ---
ID   | Name        | Price
101  | Laptop      | ₹50000.00
102  | Mobile      | ₹20000.00
103  | Headphones  | ₹2000.00

--- Place Order ---
Enter Customer Name: Himaja
Enter Email: [himaja@gmail.com](mailto:himaja@gmail.com)
Enter Product ID: 101
Enter Quantity: 2

Order Placed Successfully!
Total: ₹100000.0

--- Payment ---
Enter Order ID: 1
Enter Payment Method: UPI

Payment Successful! ₹100000.0

---

## Key Concepts

* JDBC Connectivity
* PreparedStatement (for secure queries)
* ResultSet Handling
* Exception Handling
* SQL Joins & Constraints
* Menu-driven application

---

## Future Enhancements

* GUI using Java Swing
* Admin login
* Bill generation
* Search functionality

---

## Author

**Velpula Himaja**

