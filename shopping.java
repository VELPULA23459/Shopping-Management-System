import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class shopping {
	 public static void main(String[] args) {

	        String url = "jdbc:mysql://localhost:3306/Shopping";
	        String user = "root";
	        String password = "himaja@23459"; // change if needed

	        Scanner sc = new Scanner(System.in);

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection(url, user, password);
	            while(true){
	                System.out.println("\n1 Add Customer");
	                System.out.println("2 Place Order");
	                System.out.println("3 View Orders");
	                System.out.println("4 Payment");
	                System.out.println("5 Exit");
	                int choice = sc.nextInt();
	                switch(choice){

	                    case 1:
	                        System.out.print("Enter Customer ID: ");
	                        int id = sc.nextInt();
	                        sc.nextLine();
	                        System.out.print("Enter Name: ");
	                        String name = sc.nextLine();
	                        System.out.print("Enter Email: ");
	                        String email = sc.nextLine();
	                        PreparedStatement ps = con.prepareStatement(
	                        "INSERT INTO Customers VALUES(?,?,?)");
	                        ps.setInt(1,id);
	                        ps.setString(2,name);
	                        ps.setString(3,email);
	                        ps.executeUpdate();
	                        System.out.println("Customer Added Successfully");
	                        break;

	                    case 2:
	                        System.out.print("Enter Order ID: ");
	                        int oid = sc.nextInt();
	                        System.out.print("Enter Customer ID: ");
	                        int cid = sc.nextInt();
	                        System.out.print("Enter Product ID: ");
	                        int pid = sc.nextInt();
	                        PreparedStatement ps2 = con.prepareStatement(
	                        "INSERT INTO Orders VALUES(?,?,?)");
	                        ps2.setInt(1,oid);
	                        ps2.setInt(2,cid);
	                        ps2.setInt(3,pid);
	                        ps2.executeUpdate();
	                        System.out.println("Order Placed Successfully");
	                        break;

	                    case 3:
	                        Statement st = con.createStatement();
	                        ResultSet rs = st.executeQuery(
	                        "SELECT Customers.name, Products.product_name " +
	                        "FROM Orders " +
	                        "JOIN Customers ON Orders.customer_id = Customers.id " +
	                        "JOIN Products ON Orders.product_id = Products.id");
	                        while(rs.next()){
	                            System.out.println(
	                            rs.getString("name") + " bought " +
	                            rs.getString("product_name"));
	                        }
	                        break;

	                    case 4:
	                    	System.out.print("Enter Payment ID: ");
	                    	int payid = sc.nextInt();
	                    	System.out.print("Enter Order ID: ");
	                    	int orderid = sc.nextInt();
	                    	System.out.print("Enter Amount: ");
	                    	int amount = sc.nextInt();
	                    	sc.nextLine();
	                    	System.out.print("Enter Payment Method (Cash/UPI/Card): ");
	                    	String method = sc.nextLine();
	                    	PreparedStatement ps3 = con.prepareStatement(
	                    	"INSERT INTO Payments VALUES(?,?,?,?)");
	                    	ps3.setInt(1,payid);
	                    	ps3.setInt(2,orderid);
	                    	ps3.setInt(3,amount);
	                    	ps3.setString(4,method);
	                    	ps3.executeUpdate();
	                    	System.out.println("Payment Successful");
	                    	break;
	                    	
	                    case 5:
	                        System.out.println("Program Ended");
	                        return;
	                }
	            }

	        } catch(Exception e){
	            System.out.println(e);
	        }
	    }

}
