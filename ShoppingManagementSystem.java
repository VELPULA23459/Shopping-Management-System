import java.sql.*;
import java.util.Scanner;

public class ShoppingManagementSystem {

	  static final String url = "jdbc:mysql://localhost:3306/ShoppingSystem";
      static final String user = "root";
      static final String password = "himaja@23459"; // can be moved to env later

            public static void main(String[] args) {

                Scanner sc = new Scanner(System.in);

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    try (Connection con = DriverManager.getConnection(url, user, password)) {

                        System.out.println("Database Connected Successfully");

                        while (true) {

                            System.out.println("\n===== SHOP MENU =====");
                            System.out.println("1. Add Customer");
                            System.out.println("2. View Products");
                            System.out.println("3. Place Order");
                            System.out.println("4. View Orders");
                            System.out.println("5. Make Payment");
                            System.out.println("6. Customer Full Details");
                            System.out.println("7. Exit");

                            if (!sc.hasNextInt()) {
                                System.out.println("Invalid input!");
                                sc.next();
                                continue;
                            }

                            int choice = sc.nextInt();

                            switch (choice) {

                        // Add Customer
                                case 1:
                                    sc.nextLine();

                                    System.out.print("Enter Name: ");
                                    String name = sc.nextLine();

                                    System.out.print("Enter Email: ");
                                    String email = sc.nextLine();

                                    try {
                                        PreparedStatement ps = con.prepareStatement(
                                                "insert into Customers(name, email) values (?, ?)",
                                                Statement.RETURN_GENERATED_KEYS);

                                        ps.setString(1, name);
                                        ps.setString(2, email);
                                        ps.executeUpdate();

                                        ResultSet rs = ps.getGeneratedKeys();
                                        if (rs.next()) {
                                            System.out.println("Customer Added. ID: " + rs.getInt(1));
                                        }

                                    } catch (SQLIntegrityConstraintViolationException e) {
                                        System.out.println("Email already exists!");
                                    }
                                    break;

                       // View Products
                                case 2:
                                    PreparedStatement ps2 = con.prepareStatement("select * from Products");
                                    ResultSet rs2 = ps2.executeQuery();

                                    System.out.println("\n--------------------------------------");
                                    System.out.println("ID    | Name        | Price");
                                    System.out.println("--------------------------------------");

                                    while (rs2.next()) {
                                        System.out.printf("%-5d | %-10s | ₹%.2f\n",
                                                rs2.getInt("id"),
                                                rs2.getString("name"),
                                                rs2.getDouble("price"));
                                    }

                                    System.out.println("--------------------------------------");
                                    break;

                         //  Place Order
                                case 3:
                                    sc.nextLine();

                                    System.out.print("Enter Customer Name: ");
                                    String cname = sc.nextLine();

                                    System.out.print("Enter Customer Email: ");
                                    String cemail = sc.nextLine();

                                    PreparedStatement ps3 = con.prepareStatement(
                                            "select id from Customers where name=? and email=?");
                                    ps3.setString(1, cname);
                                    ps3.setString(2, cemail);

                                    ResultSet rc = ps3.executeQuery();

                                    if (!rc.next()) {
                                        System.out.println("Customer not found!");
                                        break;
                                    }

                                    int cusid = rc.getInt("id");

                                    System.out.print("Enter Product ID: ");
                                    int prodId = sc.nextInt();

                                    System.out.print("Enter Quantity: ");
                                    int qty = sc.nextInt();

                                    if (qty <= 0) {
                                        System.out.println("Invalid quantity!");
                                        break;
                                    }

                                    PreparedStatement ps4 = con.prepareStatement(
                                            "select price from Products where id=?");
                                    ps4.setInt(1, prodId);
                                    ResultSet rs4 = ps4.executeQuery();

                                    if (rs4.next()) {
                                        double total = rs4.getDouble("price") * qty;

                                        PreparedStatement ps5 = con.prepareStatement(
                                                "insert into Orders (customer_id, product_id, quantity, total_price) values (?, ?, ?, ?)");

                                        ps5.setInt(1, cusid);
                                        ps5.setInt(2, prodId);
                                        ps5.setInt(3, qty);
                                        ps5.setDouble(4, total);
                                        ps5.executeUpdate();

                                        System.out.println("Order Placed Successfully! Total: ₹" + total);

                                    } else {
                                        System.out.println("Product not found!");
                                    }
                                    break;

                        // View Orders
                                case 4:
                                    System.out.print("Enter Customer ID: ");
                                    int custId = sc.nextInt();

                                    PreparedStatement ps6 = con.prepareStatement(
                                            "select o.order_id, c.name as cname, p.name as pname, o.quantity, o.total_price, o.order_date " +
                                                    "from Orders o " +
                                                    "join Customers c on o.customer_id = c.id " +
                                                    "join Products p on o.product_id = p.id " +
                                                    "where o.customer_id = ?");

                                    ps6.setInt(1, custId);
                                    ResultSet rs6 = ps6.executeQuery();

                                    boolean found = false;

                                    while (rs6.next()) {
                                        found = true;
                                        System.out.println(rs6.getInt("order_id") + " | " +
                                                rs6.getString("cname") + " | " +
                                                rs6.getString("pname") + " | " +
                                                rs6.getInt("quantity") + " | ₹" +
                                                rs6.getDouble("total_price") + " | " +
                                                rs6.getTimestamp("order_date"));
                                    }

                                    if (!found) {
                                        System.out.println("No orders found!");
                                    }
                                    break;

                         // Make Payment
                                case 5:
                                    System.out.print("Enter Order ID: ");
                                    int orderId = sc.nextInt();
                                    sc.nextLine();

                                    PreparedStatement check = con.prepareStatement(
                                            "select * from Payments where order_id=?");
                                    check.setInt(1, orderId);
                                    ResultSet rp = check.executeQuery();

                                    if (rp.next()) {
                                        System.out.println("Payment already done!");
                                        break;
                                    }

                                    PreparedStatement ps7 = con.prepareStatement(
                                            "select total_price from Orders where order_id=?");
                                    ps7.setInt(1, orderId);
                                    ResultSet rs7 = ps7.executeQuery();

                                    if (!rs7.next()) {
                                        System.out.println("Order not found!");
                                        break;
                                    }

                                    double amount = rs7.getDouble("total_price");

                                    System.out.print("Enter Payment Method (UPI/Card/Cash): ");
                                    String method = sc.nextLine();

                                    if (!method.equalsIgnoreCase("UPI") &&
                                            !method.equalsIgnoreCase("Card") &&
                                            !method.equalsIgnoreCase("Cash")) {
                                        System.out.println("Invalid payment method!");
                                        break;
                                    }

                                    PreparedStatement ps8 = con.prepareStatement(
                                            "insert into Payments (order_id, amount, payment_method) values (?, ?, ?)");

                                    ps8.setInt(1, orderId);
                                    ps8.setDouble(2, amount);
                                    ps8.setString(3, method);
                                    ps8.executeUpdate();

                                    System.out.println("Payment Successful! ₹" + amount);
                                    break;

                         // Customer Full Details
                                case 6:
                                    sc.nextLine();
                                    System.out.print("Enter Email: ");
                                    String emailCheck = sc.nextLine();

                                    PreparedStatement ps9 = con.prepareStatement(
                                            "select * from Customers where email=?");
                                    ps9.setString(1, emailCheck);

                                    ResultSet rs9 = ps9.executeQuery();

                                    if (!rs9.next()) {
                                        System.out.println("Customer not found!");
                                        break;
                                    }

                                    int custIdCheck = rs9.getInt("id");

                                    System.out.println("Name: " + rs9.getString("name"));
                                    System.out.println("Email: " + rs9.getString("email"));

                                    PreparedStatement ps10 = con.prepareStatement(
                                            "select o.order_id, p.name, o.quantity, o.total_price, o.order_date, " +
                                                    "if(pay.payment_id is null, 'Not Paid', 'Paid') as status " +
                                                    "from Orders o " +
                                                    "join Products p on o.product_id = p.id " +
                                                    "left join Payments pay on o.order_id = pay.order_id " +
                                                    "where o.customer_id = ?");

                                    ps10.setInt(1, custIdCheck);
                                    ResultSet rs10 = ps10.executeQuery();

                                    while (rs10.next()) {
                                        System.out.println(rs10.getInt("order_id") + " | " +
                                                rs10.getString("name") + " | " +
                                                rs10.getInt("quantity") + " | ₹" +
                                                rs10.getDouble("total_price") + " | " +
                                                rs10.getTimestamp("order_date") + " | " +
                                                rs10.getString("status"));
                                    }
                                    break;

                          // 7.Exit
                                case 7:
                                    System.out.println("Thank You!");
                                    sc.close();
                                    System.exit(0);

                                default:
                                    System.out.println("Invalid Choice");
                            }
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }