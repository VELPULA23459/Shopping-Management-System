create database ShoppingSystem;
use ShoppingSystem;

-- Customers Table
create table Customers (
    id int auto_increment primary key,
    name varchar(50) not null,
    email varchar(50) unique not null
);

-- Products Table
create table Products (
    id int primary key,
    name varchar(50) not null,
    price double not null
);

-- Orders Table
create table Orders (
    order_id int auto_increment primary key,
    customer_id int not null,
    product_id int not null,
    quantity int not null,
    total_price double not null,
    order_date timestamp default current_timestamp,
    foreign key (customer_id) references Customers(id),
    foreign key (product_id) references Products(id)
);

-- Payments Table
create table Payments (
    payment_id int auto_increment primary key,
    order_id int not null,
    amount double not null,
    payment_method varchar(20) not null,
    foreign key (order_id) references Orders(order_id)
);

-- Insert Products
insert into Products values
(101, 'Laptop', 50000),
(102, 'Mobile', 20000),
(103, 'Headphones', 2000);
