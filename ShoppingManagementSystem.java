import java.sql.*;
import java.util.Scanner;

public class ShoppingManagementSystem {

    static final String url = "jdbc:mysql://localhost:3306/ShoppingSystem";
    static final String user = "root";
    static final String password = "himaja@23459"; 

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
                    System.out.println("4. View Orders (Customer Wise)");
                    System.out.println("5. Make Payment");
                    System.out.println("6. Customer Full Details");
                    System.out.println("7. Exit");

                    int choice = sc.nextInt();

                    switch (choice) {

               //  Add Customer
                    case 1:
                        sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        try {
                            PreparedStatement ps1 = con.prepareStatement(
                                    "insert into Customers(name, email) values (?, ?)",
                                    Statement.RETURN_GENERATED_KEYS);
                            ps1.setString(1, name);
                            ps1.setString(2, email);
                            ps1.executeUpdate();

                            ResultSet r = ps1.getGeneratedKeys();
                            if (r.next()) {
                                System.out.println("Customer Added. ID: " + r.getInt(1));
                            }

                        } catch (SQLIntegrityConstraintViolationException e) {
                            System.out.println("Email already exists!");
                        }
                        break;

               // View Products
                    case 2:
                    	ResultSet rs = con.createStatement().executeQuery("select * from Products");

                    	System.out.println("\n--------------------------------------");
                    	System.out.println("ID    | Name        | Price");
                    	System.out.println("--------------------------------------");

                    	while (rs.next()) {
                    	    System.out.printf("%-5d | %-10s | ₹%.2f\n",
                    	            rs.getInt("id"),
                    	            rs.getString("name"),
                    	            rs.getDouble("price"));
                    	}

                    	System.out.println("--------------------------------------");
                        break;

             // Place Order using Name + Email
                    case 3:
                        sc.nextLine();

                        System.out.print("Enter Customer Name: ");
                        String cname = sc.nextLine();

                        System.out.print("Enter Customer Email: ");
                        String cemail = sc.nextLine();

                        PreparedStatement ps = con.prepareStatement(
                                "select id from Customers where name=? and email=?");
                        ps.setString(1, cname);
                        ps.setString(2, cemail);

                        ResultSet rc = ps.executeQuery();

                        if (!rc.next()) {
                            System.out.println("Customer not found! Please register first.");
                            break;
                        }

                        int cusid = rc.getInt("id");

                        System.out.print("Enter Product ID: ");
                        int prodId = sc.nextInt();

                        System.out.print("Enter Quantity: ");
                        int qty = sc.nextInt();

                        PreparedStatement ps2 = con.prepareStatement(
                                "select price from Products where id=?");
                        ps2.setInt(1, prodId);
                        ResultSet rs2 = ps2.executeQuery();

                        if (rs2.next()) {
                            double total = rs2.getDouble("price") * qty;

                            PreparedStatement ps3 = con.prepareStatement(
                                    "insert into Orders (customer_id, product_id, quantity, total_price) values (?, ?, ?, ?)");
                            ps3.setInt(1, cusid);
                            ps3.setInt(2, prodId);
                            ps3.setInt(3, qty);
                            ps3.setDouble(4, total);
                            ps3.executeUpdate();

                            System.out.println("Order Placed Successfully! Total: ₹" + total);
                            System.out.println("Order Date: Auto Generated");
                        } else {
                            System.out.println("Product Not Found!");
                        }
                        break;

               // View Orders
                    case 4:
                        System.out.print("Enter Customer ID: ");
                        int custIdView = sc.nextInt();

                        PreparedStatement psView = con.prepareStatement(
                                "select o.order_id, c.name, p.name, o.quantity, o.total_price, o.order_date " +
                                        "from Orders o " +
                                        "join Customers c on o.customer_id = c.id " +
                                        "join Products p on o.product_id = p.id " +
                                        "where o.customer_id = ?");
                        psView.setInt(1, custIdView);

                        ResultSet rs3 = psView.executeQuery();
                        System.out.println("\nOrderID | Customer | Product | Qty | Total | Date");

                        while (rs3.next()) {
                            System.out.println(rs3.getInt(1) + " | " +
                                    rs3.getString(2) + " | " +
                                    rs3.getString(3) + " | " +
                                    rs3.getInt(4) + " | ₹" +
                                    rs3.getDouble(5) + " | " +
                                    rs3.getTimestamp(6));
                        }
                        break;

               //Payment (No duplicate)
                    case 5:
                        System.out.print("Enter Order ID: ");
                        int orderId = sc.nextInt();
                        sc.nextLine();

                        PreparedStatement checkPay = con.prepareStatement(
                                "select * from Payments where order_id=?");
                        checkPay.setInt(1, orderId);
                        ResultSet rp = checkPay.executeQuery();

                        if (rp.next()) {
                            System.out.println("Payment already done!");
                            break;
                        }

                        PreparedStatement psGet = con.prepareStatement(
                                "select total_price from Orders where order_id=?");
                        psGet.setInt(1, orderId);
                        ResultSet rsAmt = psGet.executeQuery();

                        if (!rsAmt.next()) {
                            System.out.println("Order Not Found!");
                            break;
                        }

                        double amount = rsAmt.getDouble("total_price");

                        System.out.print("Enter Payment Method (UPI/Card/Cash): ");
                        String method = sc.nextLine();

                        PreparedStatement psPay = con.prepareStatement(
                                "insert into Payments (order_id, amount, payment_method) values (?, ?, ?)");
                        psPay.setInt(1, orderId);
                        psPay.setDouble(2, amount);
                        psPay.setString(3, method);
                        psPay.executeUpdate();

                        System.out.println("Payment Successful! ₹" + amount);
                        break;

               //Customer Full Details
                    case 6:
                        sc.nextLine();
                        System.out.print("Enter Email: ");
                        String emailCheck = sc.nextLine();

                        PreparedStatement psC = con.prepareStatement(
                                "select * from Customers where email=?");
                        psC.setString(1, emailCheck);
                        ResultSet rsC = psC.executeQuery();

                        if (!rsC.next()) {
                            System.out.println("Customer not found!");
                            break;
                        }

                        int custIdCheck = rsC.getInt("id");

                        System.out.println("Name: " + rsC.getString("name"));
                        System.out.println("Email: " + rsC.getString("email"));

                        PreparedStatement psO = con.prepareStatement(
                                "select o.order_id, p.name, o.quantity, o.total_price, o.order_date, " +
                                        "if(pay.payment_id is null, 'Not Paid', 'Paid') as status " +
                                        "from Orders o " +
                                        "join Products p on o.product_id = p.id " +
                                        "left join Payments pay on o.order_id = pay.order_id " +
                                        "where o.customer_id = ?");
                        psO.setInt(1, custIdCheck);

                        ResultSet rsO = psO.executeQuery();

                        while (rsO.next()) {
                            System.out.println(rsO.getInt(1) + " | " +
                                    rsO.getString(2) + " | " +
                                    rsO.getInt(3) + " | ₹" +
                                    rsO.getDouble(4) + " | " +
                                    rsO.getTimestamp(5) + " | " +
                                    rsO.getString(6));
                        }
                        break;

                    case 7:
                        System.out.println("Thank You!");
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid Choice");
                }
            }
        }

    } catch (SQLException e) {
        System.out.println("Database Error: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
}